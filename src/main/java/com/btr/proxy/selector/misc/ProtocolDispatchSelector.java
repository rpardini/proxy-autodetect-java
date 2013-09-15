package com.btr.proxy.selector.misc;

import com.btr.proxy.selector.direct.NoProxySelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * **************************************************************************
 * This is a facade for a list of ProxySelecor objects. You can register
 * different ProxySelectors per Protocol.
 *
 * @author Bernd Rosstauscher (proxyvole@rosstauscher.de) Copyright 2009
 *         **************************************************************************
 */

public class ProtocolDispatchSelector extends ProxySelector {
// ------------------------------ FIELDS ------------------------------

    private Logger log = LoggerFactory.getLogger(getClass());

    private Map<String, ProxySelector> selectors;
    private ProxySelector fallbackSelector;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * **********************************************************************
     * Constructor
     * **********************************************************************
     */
    public ProtocolDispatchSelector() {
        super();
        this.selectors = new ConcurrentHashMap<String, ProxySelector>();
        this.fallbackSelector = NoProxySelector.getInstance();
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * **********************************************************************
     * Sets the fallback selector that is always called when no matching
     * protocol selector was found..
     *
     * @param selector the selector to use.
     *                 **********************************************************************
     */
    public void setFallbackSelector(ProxySelector selector) {
        if (selector == null) {
            throw new NullPointerException("Selector must not be null.");
        }
        this.fallbackSelector = selector;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * **********************************************************************
     * connectFailed
     *
     * @see java.net.ProxySelector#connectFailed(java.net.URI, java.net.SocketAddress, java.io.IOException)
     *      **********************************************************************
     */
    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        ProxySelector selector = this.fallbackSelector;
        String protocol = uri.getScheme();
        if (protocol != null && this.selectors.get(protocol) != null) {
            selector = this.selectors.get(protocol);
        }
        selector.connectFailed(uri, sa, ioe);
    }

    /**
     * **********************************************************************
     * Gets the selector installed for the given protocol.
     *
     * @param protocol the protocol name.
     * @return the selector for that protocol, null if none is currently set.
     *         **********************************************************************
     */

    public ProxySelector getSelector(String protocol) {
        return this.selectors.get(protocol);
    }

    /**
     * **********************************************************************
     * Removes the selector installed for the given protocol.
     *
     * @param protocol the protocol name.
     * @return the old selector that is removed.
     *         **********************************************************************
     */

    public ProxySelector removeSelector(String protocol) {
        return this.selectors.remove(protocol);
    }

    /**
     * **********************************************************************
     * select
     *
     * @see java.net.ProxySelector#select(java.net.URI)
     *      **********************************************************************
     */

    @Override
    public List<Proxy> select(URI uri) {
        ProxySelector selector = this.fallbackSelector;
        String protocol = uri.getScheme();
        if (protocol != null && this.selectors.get(protocol) != null) {
            selector = this.selectors.get(protocol);
        }
        return selector.select(uri);
    }

    /**
     * **********************************************************************
     * Sets a selector responsible for the given protocol.
     *
     * @param protocol the name of the protocol.
     * @param selector the selector to use.
     *                 **********************************************************************
     */

    public void setSelector(String protocol, ProxySelector selector) {
        if (protocol == null) {
            throw new NullPointerException("Protocol must not be null.");
        }
        if (selector == null) {
            throw new NullPointerException("Selector must not be null.");
        }
        this.selectors.put(protocol, selector);
    }
}
