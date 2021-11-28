import { Observable, Subject } from 'rxjs';
import { Map, MapBrowserEvent } from 'ol';
import { EventsKey } from 'ol/events';
import { unByKey } from 'ol/Observable';
import { get, getTransform, ProjectionLike, TransformFunction } from 'ol/proj';
import { Coordinate } from 'ol/coordinate';
import Projection from 'ol/proj/Projection';

/**
 * This converts the OpenLayers mouse events to Observables, making them easily accessible/mappable in a Reactive style.
 */
export class MouseEvents {
  private readonly clicksSubject = new Subject<MapBrowserEvent<UIEvent>>();
  private readonly movesSubject = new Subject<MapBrowserEvent<UIEvent> | undefined>();
  private clickHandle: EventsKey | EventsKey[] | undefined;
  private moveHandle: EventsKey | EventsKey[] | undefined;
  private targetHandle: EventsKey | EventsKey[] | undefined;
  private currentMapTarget: Element | undefined;

  readonly clicks: Observable<MapBrowserEvent<UIEvent>> = this.clicksSubject.asObservable();
  readonly moves: Observable<MapBrowserEvent<UIEvent> | undefined> = this.movesSubject.asObservable();

  /**
   * Registers a given map to provide the events. Registering a new map will automatically de-register the previous.
   */
  setMap(map: Map): this {
    // De-register existing click- and move listeners...
    if (this.clickHandle) {
      unByKey(this.clickHandle);
    }
    if (this.moveHandle) {
      unByKey(this.moveHandle);
    }

    // ...and register new ones on the new map:
    // (We *could* set this up as a cold observable, only connecting the ol events when we have a subscriber, but
    // realistically, that would be a lot of boilerplate for no gain, and possibly even a loss as event handlers are set
    // up and torn down excessively).
    this.clickHandle = map.on('click', (e: MapBrowserEvent<UIEvent>) => this.clicksSubject.next(e));
    this.moveHandle = map.on('pointermove', (e: MapBrowserEvent<UIEvent>) => this.movesSubject.next(e));

    // Track mouse-out on the HTML element which contains the map:
    const signalMouseOut = () => {
      this.movesSubject.next(undefined);
    };

    // Switch the mouseout event handler to the new map's HTML element:
    const setMouseOutOnTarget = () => {
      if (this.currentMapTarget) {
        this.currentMapTarget.removeEventListener('mouseout', signalMouseOut);
      }
      const target = map.getTargetElement();
      if (target) {
        target.addEventListener('mouseout', signalMouseOut);
      }
      this.currentMapTarget = target;
    };

    // De- and re-register a listener for the map changing target HTML element:
    if (this.targetHandle) {
      unByKey(this.targetHandle);
    }
    this.targetHandle = map.on('change:target', () => {
      setMouseOutOnTarget();
    });
    setMouseOutOnTarget();
    return this;
  }
}

export function mouseCoordinateConverter(targetProjectionLike: ProjectionLike): (e: MapBrowserEvent<UIEvent>) => Coordinate {
  const targetProjection = get(targetProjectionLike);
  if (!targetProjection) {
    throw new Error('No Proj4 definition registered for ' + targetProjectionLike);
  }
  let lastKnownViewProjection: Projection | undefined;
  let transform: TransformFunction;
  return e => {
    const viewProjection = e.map.getView().getProjection();
    if (viewProjection !== lastKnownViewProjection) {
      lastKnownViewProjection = viewProjection;
      transform = viewProjection === targetProjection
                  ? c => c
                  : getTransform(viewProjection, targetProjection);
    }
    return transform(e.coordinate) as Coordinate;
  };
}
