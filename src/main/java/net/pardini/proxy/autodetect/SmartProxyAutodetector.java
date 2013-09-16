package net.pardini.proxy.autodetect;

import com.btr.proxy.search.ProxySearch;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pardini
 * Date: 15/09/13
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */
public class SmartProxyAutodetector implements ProxyAutodetector {
// ------------------------------ FIELDS ------------------------------

    private Logger log = LoggerFactory.getLogger(getClass());
    private ProxySelector proxySelector;

// --------------------------- CONSTRUCTORS ---------------------------

    public SmartProxyAutodetector() {
        ProxySearch defaultProxySearch = ProxySearch.getDefaultProxySearch();
        proxySelector = defaultProxySearch.getProxySelector();
        if (proxySelector == null) {
            log.debug("No proxy detector available, no proxies ever.");
        }
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ProxyAutodetector ---------------------

    @Override
    public ProxyInfo detectProxyForURL(final String url) {
        if (proxySelector == null) return null;

        URI uri = parseURL(url);

        final List<Proxy> select = proxySelector.select(uri);
        for (Proxy proxy : select) {
            if (proxy.type() == Proxy.Type.HTTP) {
                ProxyInfo info = new ProxyInfo(proxy);
                return info;
            }
        }

        return null;
    }

    @Override
    public void setProxyForHttpClient(final HttpClient client, final String url) {
        ProxyInfo theProxy = this.detectProxyForURL(url);
        if (theProxy != null) {
            HttpHost proxy = new HttpHost(theProxy.getHost(), theProxy.getPort(), "http");
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
    }

// -------------------------- OTHER METHODS --------------------------

    private URI parseURL(final String url) {
        try {
            URI uri = new URI(url);
            return uri;
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Error parsing URL '%s': %s", url, e.getMessage()), e);
        }
    }
}
