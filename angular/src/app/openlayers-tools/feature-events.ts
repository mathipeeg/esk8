import { ImageWMS, TileWMS } from 'ol/source';
import { BehaviorSubject, Subject, Subscription } from 'rxjs';
import { GeoJSON } from 'ol/format';
import Feature from 'ol/Feature';
import { MouseEvents } from './mouse-events';
import {LineString} from "ol/geom";

export class FeatureEvents {
  private source: ImageWMS | TileWMS | undefined;
  private queryLayers: string | undefined;
  private subscription: Subscription | undefined;
  private readonly format = new GeoJSON();

  private featuresSubject = new BehaviorSubject<Feature<LineString>[]>([]);
  features = this.featuresSubject.asObservable();

  private errorsSubject = new Subject<Error | TypeError | Response>();
  /**
   * We do not want (temporary network) errors to terminate the feature subscriptions, so we report errors on a separate
   * stream.
   */
  errors = this.errorsSubject.asObservable();

  setSource(source: ImageWMS | TileWMS | undefined, queryLayers: string | undefined): this {
    this.source = source;
    this.queryLayers = queryLayers;
    return this;
  }

  setMouseEvents(mouseEvents: MouseEvents): this {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.subscription = mouseEvents.clicks.subscribe(e => {
      if (this.source && this.queryLayers) {
        const url = this.source.getFeatureInfoUrl(
          e.coordinate,
          <number> e.map.getView().getResolution(),
          e.map.getView().getProjection(),
          {
            INFO_FORMAT: 'application/json',
            FEATURE_COUNT: 20,
            QUERY_LAYERS: this.queryLayers,
            LAYERS: this.queryLayers
          }
        );

        fetch(<string> url)
          .then(response => {
            if (!response.ok) {
              throw response;
            }
            return response;
          })
          .then(response => response.text())
          .then(text => this.format.readFeatures(text))
          .then(features => {
            if (features && features.length) {
              this.featuresSubject.next(features);
            } else {
              this.featuresSubject.next([]);
            }
          })
          .catch(error => {
            this.featuresSubject.next([]);
            if (error instanceof Error || error instanceof TypeError || error instanceof Response) {
              this.errorsSubject.next(error);
            } else {
              this.errorsSubject.next(new Error(error.toString()));
            }
          });
      }
    });
    return this;
  }
}
