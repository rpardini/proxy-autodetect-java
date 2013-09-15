package com.btr.proxy.search;

import com.btr.proxy.util.ProxyException;

import java.net.ProxySelector;

/**
 * **************************************************************************
 * Interface for a proxy search strategy.
 *
 * @author Bernd Rosstauscher (proxyvole@rosstauscher.de) Copyright 2009
 *         **************************************************************************
 */

public interface ProxySearchStrategy {
// -------------------------- OTHER METHODS --------------------------

    /**
     * **********************************************************************
     * Gets the a ProxySelector found by applying the search strategy.
     *
     * @return a ProxySelector, null if none is found.
     *         **********************************************************************
     */

    public ProxySelector getProxySelector() throws ProxyException;
}
