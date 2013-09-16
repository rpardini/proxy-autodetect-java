package net.pardini.proxy.autodetect;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: pardini
 * Date: 15/09/13
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
public class SmartProxyAutodetectorTest {

    private Logger log = LoggerFactory.getLogger(getClass());
    private ProxyAutodetector proxyAutodetector;


    @BeforeMethod
    public void setUp() throws Exception {
        proxyAutodetector = new SmartProxyAutodetector();
    }

    @Test
    public void testDetectProxyForURL() throws Exception {
        ProxyInfo proxyHttp = proxyAutodetector.detectProxyForURL("http://www.google.com");
        log.info("Proxy for http url is {}", proxyHttp);
        ProxyInfo proxyHttps = proxyAutodetector.detectProxyForURL("https://www.google.com");
        log.info("Proxy for https url is {}", proxyHttps);
    }

    @Test
    public void testUsingHttpComponents() throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://www.google.com/";
        proxyAutodetector.setProxyForHttpClient(httpClient, url);

        HttpGet httpGet = new HttpGet(url);

        org.apache.http.HttpResponse httpResponse = httpClient.execute(httpGet);
        log.info("Got a response of {}", httpResponse.getStatusLine().toString());
    }
}
