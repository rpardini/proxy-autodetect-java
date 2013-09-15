package com.btr.proxy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * **************************************************************************
 * Defines some helper methods to find the correct platform.
 *
 * @author Bernd Rosstauscher (proxyvole@rosstauscher.de) Copyright 2009
 *         **************************************************************************
 */

public class PlatformUtil {
// ------------------------------ FIELDS ------------------------------

    private static Logger log = LoggerFactory.getLogger(PlatformUtil.class);

// -------------------------- STATIC METHODS --------------------------

    /**
     * **********************************************************************
     * Gets the ID for the platform default browser.
     *
     * @return a browser ID, null if no supported browser was detected.
     *         **********************************************************************
     */
    public static Browser getDefaultBrowser() {
        // Use better logic to detect default browser?
        if (getCurrentPlattform() == Platform.WIN) {
            log.debug("Detected Browser is InternetExplorer");
            return Browser.IE;
        } else {
            log.debug("Detected Browser Firefox. Fallback?");
            return Browser.FIREFOX;
        }
    }

    /**
     * **********************************************************************
     * Gets the platform we are currently running on.
     *
     * @return a platform code.
     *         **********************************************************************
     */

    public static Platform getCurrentPlattform() {
        String osName = System.getProperty("os.name");
        log.debug("Detecting platform. Name is: {}", osName);

        if (osName.toLowerCase().contains("windows")) {
            log.debug("Detected Windows platform: {}", osName);
            return Platform.WIN;
        }
        if (osName.toLowerCase().contains("linux")) {
            log.debug("Detected Linux platform: {}", osName);
            return Platform.LINUX;
        }
        if (osName.startsWith("Mac OS")) {
            log.debug("Detected Mac OS platform: {}", osName);
            return Platform.MAC_OS;
        }
        if (osName.startsWith("SunOS")) {
            log.debug("Detected Solaris platform: {}", osName);
            return Platform.SOLARIS;
        }

        return Platform.OTHER;
    }

    /**
     * **********************************************************************
     * Gets the desktop that we are running on.
     *
     * @return the desktop identifier.
     *         **********************************************************************
     */

    public static Desktop getCurrentDesktop() {
        Platform platf = getCurrentPlattform();

        if (platf == Platform.WIN) {
            log.debug("Detected Windows desktop");
            return Desktop.WIN;
        }
        if (platf == Platform.MAC_OS) {
            log.debug("Detected Mac OS desktop");
            return Desktop.MAC_OS;
        }

        if (platf == Platform.LINUX
                || platf == Platform.SOLARIS
                || platf == Platform.OTHER) {
            if (isKDE()) {
                log.debug("Detected KDE desktop");
                return Desktop.KDE;
            }
            if (isGnome()) {
                log.debug("Detected Gnome desktop");
                return Desktop.GNOME;
            }
        }
        log.debug("Detected Unknown desktop");
        return Desktop.OTHER;
    }

    /**
     * **********************************************************************
     * Checks if we are currently running under KDE desktop.
     *
     * @return true if it is a KDE else false.
     *         **********************************************************************
     */

    private static boolean isKDE() {
        return System.getenv("KDE_SESSION_VERSION") != null;
    }

    /**
     * **********************************************************************
     * Checks if we are currently running under Gnome desktop.
     *
     * @return true if it is a Gnome else false.
     *         **********************************************************************
     */

    private static boolean isGnome() {
        return System.getenv("GNOME_DESKTOP_SESSION_ID") != null;
    }

// -------------------------- ENUMERATIONS --------------------------

    public enum Platform {WIN, LINUX, MAC_OS, SOLARIS, OTHER}

    public enum Desktop {WIN, KDE, GNOME, MAC_OS, OTHER}

    public enum Browser {IE, FIREFOX}
}
