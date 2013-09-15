package com.btr.proxy.util;

/**
 * **************************************************************************
 * Indicates an exception in the proxy framework.
 *
 * @author Bernd Rosstauscher (proxyvole@rosstauscher.de) Copyright 2009
 *         **************************************************************************
 */

public class ProxyException extends Exception {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = 1L;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * **********************************************************************
     * Constructor
     * **********************************************************************
     */

    public ProxyException() {
        super();
    }

    /**
     * **********************************************************************
     * Constructor
     *
     * @param message the error message
     *                **********************************************************************
     */

    public ProxyException(String message) {
        super(message);
    }

    /**
     * **********************************************************************
     * Constructor
     *
     * @param cause the causing exception for chaining exceptions.
     *              **********************************************************************
     */

    public ProxyException(Throwable cause) {
        super(cause);
    }

    /**
     * **********************************************************************
     * Constructor
     *
     * @param message the error message
     * @param cause   the causing exception for chaining exceptions.
     *                **********************************************************************
     */

    public ProxyException(String message, Throwable cause) {
        super(message, cause);
    }
}
