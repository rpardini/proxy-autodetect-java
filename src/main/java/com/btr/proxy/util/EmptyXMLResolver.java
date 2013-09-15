package com.btr.proxy.util;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * **************************************************************************
 * This resolver is used to prevent network lookups of DTD or XML schemas.
 *
 * @author Bernd Rosstauscher (proxyvole@rosstauscher.de) Copyright 2009
 *         **************************************************************************
 */

public class EmptyXMLResolver implements EntityResolver {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface EntityResolver ---------------------

    /**
     * **********************************************************************
     * Overwritten to return an empty entity.
     *
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     *      **********************************************************************
     */

    public InputSource resolveEntity(String arg0, String arg1) throws SAXException, IOException {
        return new InputSource(new ByteArrayInputStream("".getBytes()));
    }
}
