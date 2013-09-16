package net.pardini.proxy.autodetect;

import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: pardini
 * Date: 15/09/13
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
public class SmartProxyAutodetectorTest {
    @Test
    public void testDetectProxyForURL() throws Exception {
        ProxyAutodetector proxyAutodetector = new SmartProxyAutodetector();
        String proxyHttp = proxyAutodetector.detectProxyForURL("http://www.google.com");
        String proxyHttps = proxyAutodetector.detectProxyForURL("https://www.google.com");
    }
}
