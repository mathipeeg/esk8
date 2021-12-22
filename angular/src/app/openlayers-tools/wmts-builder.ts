  import { Options, optionsFromCapabilities } from 'ol/source/WMTS';
import { from, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { WMTSCapabilities } from 'ol/format';
import { WMTS } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import TileSource from "ol/source/Tile";

/**
 * Creates an OpenLayers WMTS source based on a capabilities URL. This is mostly boilerplate around a fetch and
 * OpenLayers' own optionsFromCapabilities.
 * @param capabilitiesUrl The URL to the capabilities file
 * @param config Any additional configuration we want when creating the source. This should include the layer name, and can additionally
 * specify things like like Matrix Set or format.
 * See {@link https://openlayers.org/en/latest/apidoc/module-ol_source_WMTS.html#.optionsFromCapabilities}
 * @param urlMapper A function converting the URL(s) reported by the capabilities to an array of other URLs. Typically,
 * this can be used if the server uses a scheme of multiple subdomain aliases to work around the browser limitation on
 * concurrent requests to the same domain, though this is less relevant in modern browsers.
 */
export function createWmtsSource(
  capabilitiesUrl: string,
  config: Partial<Options>,
  urlMapper?: (urls: string[]) => string[]
): Observable<WMTS> {
  return from(
    fetch(capabilitiesUrl)
      .then(response => {
        if (!response.ok) {
          throw response;
        }
        return response;
      })
      .then(response => response.text())
  )
    .pipe(
      map(text => {
        const capabilities = new WMTSCapabilities().read(text);
        const options = <Options>optionsFromCapabilities(capabilities, config);
        if (!options.format) {
          options.format = 'image/png';
        }
        if (options.urls && urlMapper) {
          options.urls = urlMapper(options.urls);
        }
        return new WMTS(options);
      })
    );
}

export function createWmtsLayer(url: string, config: Partial<Options>, urlMapper?: (urls: string[]) => string[]): Observable<TileLayer<TileSource>> {
  return createWmtsSource(url, config, urlMapper).pipe(
    map(source => new TileLayer({source}))
  );
}
