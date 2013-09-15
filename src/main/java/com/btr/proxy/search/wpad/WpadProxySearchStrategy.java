package com.btr.proxy.search.wpad;

import com.btr.proxy.search.ProxySearchStrategy;
import com.btr.proxy.selector.pac.PacProxySelector;
import com.btr.proxy.selector.pac.UrlPacScriptSource;
import com.btr.proxy.util.ProxyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.util.Properties;

/**
 * **************************************************************************
 * Uses automatic proxy script search (WPAD) to find an PAC file automatically.
 * <p>
 * Note: at the moment only the DNS name guessing schema is implemented.
 * All others are missing.
 * </p><p>
 * For more information about WPAD:
 * <a href="http://en.wikipedia.org/wiki/Web_Proxy_Autodiscovery_Protocol">Web_Proxy_Autodiscovery_Protocol</a>
 * </p><p>
 * Outdated RFC draft:
 * <a href="http://www.web-cache.com/Writings/Internet-Drafts/draft-ietf-wrec-wpad-01.txt">draft-ietf-wrec-wpad-01.txt</a>
 * </p>
 *
 * @author Bernd Rosstauscher (proxyvole@rosstauscher.de) Copyright 2009
 *         **************************************************************************
 */

public class WpadProxySearchStrategy implements ProxySearchStrategy {
// ------------------------------ FIELDS ------------------------------

    private Logger log = LoggerFactory.getLogger(getClass());

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * **********************************************************************
     * Constructor
     * **********************************************************************
     */

    public WpadProxySearchStrategy() {
        super();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ProxySearchStrategy ---------------------

    /**
     * **********************************************************************
     * Loads the proxy settings from a PAC file.
     * The location of the PAC file is determined automatically.
     *
     * @return a configured ProxySelector, null if none is found.
     * @throws ProxyException on error.
     *                        **********************************************************************
     */
    public ProxySelector getProxySelector() throws ProxyException {
        try {
            log.debug("Using WPAD to find a proxy");

            String pacScriptUrl = detectScriptUrlPerDHCP();
            if (pacScriptUrl == null) {
                pacScriptUrl = detectScriptUrlPerDNS();
            }
            if (pacScriptUrl == null) {
                return null;
            }
            log.debug("PAC script url found: {0}", pacScriptUrl);
            return new PacProxySelector(new UrlPacScriptSource(pacScriptUrl));
        } catch (IOException e) {
            log.error("Error during WPAD search.", e);
            throw new ProxyException(e);
        }
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * **********************************************************************
     * Loads the settings and stores them in a properties map.
     *
     * @return the settings.
     *         **********************************************************************
     */
    public Properties readSettings() {
        try {
            String pacScriptUrl = detectScriptUrlPerDHCP();
            if (pacScriptUrl == null) {
                pacScriptUrl = detectScriptUrlPerDNS();
            }
            if (pacScriptUrl == null) {
                return null;
            }
            Properties result = new Properties();
            result.setProperty("url", pacScriptUrl);
            return result;
        } catch (IOException e) {
            // Irnore and return empty properties.
            return new Properties();
        }
    }

    /**
     * **********************************************************************
     * Uses DHCP to find the script URL.
     *
     * @return the URL, null if not found.
     *         **********************************************************************
     */

    private String detectScriptUrlPerDHCP() {
        log.debug("Searching per DHCP not supported yet.");
        // TODO Rossi 28.04.2009 Not implemented yet.
        return null;
    }

    /**
     * **********************************************************************
     * Uses DNS to find the script URL.
     * Attention: this detection method is known to have some severe security issues.
     *
     * @return the URL, null if not found.
     *         **********************************************************************
     */

    private String detectScriptUrlPerDNS() throws IOException {
        String result = null;
        String fqdn = InetAddress.getLocalHost().getCanonicalHostName();

        log.debug("Searching per DNS guessing.");

        int index = fqdn.indexOf('.');
        while (index != -1 && result == null) {
            fqdn = fqdn.substring(index + 1);

            // if we are already on TLD level then escape 
            if (fqdn.indexOf('.') == -1) {
                break;
            }

            // Try to connect to URL
            try {
                URL lookupURL = new URL("http://wpad." + fqdn + "/wpad.dat");
                log.debug("Trying url: {0}", lookupURL);

                HttpURLConnection con = (HttpURLConnection) lookupURL.openConnection(Proxy.NO_PROXY);
                con.setInstanceFollowRedirects(true);
                con.setRequestProperty("accept", "application/x-ns-proxy-autoconfig");
                if (con.getResponseCode() == 200) {
                    result = lookupURL.toString();
                }
                con.disconnect();
            } catch (UnknownHostException e) {
                log.debug("Not available!");
                // Not a real error, try next address
            }

            index = fqdn.indexOf('.');
        }

        return result;
    }
}
