package jay.syi.interfaces.client;

import jay.syi.model.ProxyDetails;

import java.util.List;

public interface IProxyDistributionStrategy {
	List<ProxyDetails> getNext(int count);
}
