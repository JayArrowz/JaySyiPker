package jay.syi.interfaces.proxy;

import io.netty.channel.ChannelHandler;
import io.netty.handler.proxy.ProxyHandler;
import io.netty.handler.proxy.Socks5ProxyHandler;
import jay.syi.model.ProxyDetails;
import jay.syi.model.ProxyType;

public interface IProxyHandlerFactory {
	ProxyHandler create(ProxyDetails details);
}
