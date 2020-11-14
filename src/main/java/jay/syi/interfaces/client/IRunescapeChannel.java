package jay.syi.interfaces.client;

import jay.syi.model.ProxyDetails;

import java.io.Closeable;

public interface IRunescapeChannel extends Closeable {
	void login(ProxyDetails proxyDetails);

	void logout();
}
