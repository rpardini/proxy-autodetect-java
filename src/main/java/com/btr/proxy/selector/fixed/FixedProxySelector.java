package com.btr.proxy.selector.fixed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * **************************************************************************
 * This proxy selector is configured with a fixed proxy. This proxy will be
 * returned for all URIs passed to the select method.
 *
 * @author Bernd Rosstauscher (proxyvole@rosstauscher.de) Copyright 2009
 *         **************************************************************************
 */

public class FixedProxySelector extends ProxySelector {
// ------------------------------ FIELDS ------------------------------

    private Logger log = LoggerFactory.getLogger(getClass());

    private final List<Proxy> proxyList;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * **********************************************************************
     * Constructor
     *
     * @param proxy the proxy to use.
     *              **********************************************************************
     */
    public FixedProxySelector(Proxy proxy) {
        super();

        List<Proxy> list = new ArrayList<Proxy>(1);
        list.add(proxy);
        this.proxyList = Collections.unmodifiableList(list);
    }

    /**
     * **********************************************************************
     * Constructor
     *
     * @param proxyHost the host name or IP address of the proxy to use.
     * @param proxyPort the port of the proxy.
     *                  **********************************************************************
     */

    public FixedProxySelector(String proxyHost, int proxyPort) {
        this(new Proxy(Proxy.Type.HTTP,
                InetSocketAddress.createUnresolved(proxyHost, proxyPort)));
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
        // Not used
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
        return this.proxyList;
    }
}
