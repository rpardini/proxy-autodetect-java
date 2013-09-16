package net.pardini.proxy.autodetect;

import com.btr.proxy.search.ProxySearch;
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

// ------------------------ INTERFACE METHODS ------------------------

    public SmartProxyAutodetector() {
        ProxySearch defaultProxySearch = ProxySearch.getDefaultProxySearch();
        proxySelector = defaultProxySearch.getProxySelector();
        if (proxySelector == null) {
            log.debug("No proxy detector available, no proxies ever.");
        }
    }


// --------------------- Interface ProxyAutodetector ---------------------

    @Override
    public String detectProxyForURL(final String url) {
        if (proxySelector == null) return null;

        for (Proxy proxy : parseURL(url)) {
            log.info("Detected proxy {}", proxy.toString());
            return proxy.toString();
        }

        return null;
    }

    private List<Proxy> parseURL(final String url) {
        final List<Proxy> select;
        try {
            select = proxySelector.select(new URI(url));
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Error parsing URL '%s': %s", url, e.getMessage()), e);
        }
        return select;
    }
}
