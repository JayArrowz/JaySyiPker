package jay.syi.business.proxy;

import io.netty.handler.proxy.ProxyHandler;
import io.netty.handler.proxy.Socks4ProxyHandler;
import io.netty.handler.proxy.Socks5ProxyHandler;
import jay.syi.interfaces.proxy.IProxyHandlerFactory;
import jay.syi.model.ProxyDetails;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class ProxyHandlerFactory implements IProxyHandlerFactory {
	@Override
	public ProxyHandler create(ProxyDetails details) {
		var socketAddr = new InetSocketAddress(details.getIp(), details.getPort());
		ProxyHandler proxyHandler = switch(details.getProxyType()) {
			case SOCKS_5 -> new Socks5ProxyHandler(socketAddr, details.getUsername(), details.getPassword());
			case SOCKS_4 -> new Socks4ProxyHandler(socketAddr, details.getUsername());
		};
		return proxyHandler;
	}
}
