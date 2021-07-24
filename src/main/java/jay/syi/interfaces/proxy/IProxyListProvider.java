package jay.syi.interfaces.proxy;

import jay.syi.model.ProxyDetails;

import java.io.IOException;
import java.util.List;

public interface IProxyListProvider {
	void add(ProxyDetails details);

	List<ProxyDetails> get();

	void load() throws IOException;
}
