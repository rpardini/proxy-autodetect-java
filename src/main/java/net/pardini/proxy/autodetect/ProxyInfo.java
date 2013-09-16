package net.pardini.proxy.autodetect;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Created with IntelliJ IDEA.
 * User: pardini
 * Date: 15/09/13
 * Time: 23:04
 * To change this template use File | Settings | File Templates.
 */
public class ProxyInfo {

    private Proxy proxy;
    private String host;
    private int port;

    public ProxyInfo(final Proxy proxy) {
        this.proxy = proxy;
        if (proxy.type() == Proxy.Type.HTTP) {
            InetSocketAddress addr = (InetSocketAddress) proxy.address();
            if (addr != null) {
                this.host = addr.getHostName();
                this.port = addr.getPort();
            }
        }
    }

    public Proxy getProxy() {
        return proxy;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "ProxyInfo{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
