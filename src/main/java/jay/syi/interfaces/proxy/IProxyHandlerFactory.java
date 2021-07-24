package jay.syi.interfaces.proxy;

import io.netty.handler.proxy.ProxyHandler;
import jay.syi.model.ProxyDetails;

public interface IProxyHandlerFactory {
	ProxyHandler create(ProxyDetails details);
}
