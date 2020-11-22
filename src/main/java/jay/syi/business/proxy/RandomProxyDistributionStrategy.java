package jay.syi.business.proxy;

import jay.syi.interfaces.client.IProxyDistributionStrategy;
import jay.syi.interfaces.proxy.IProxyListProvider;
import jay.syi.model.ProxyDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("random-proxy-dist")
public class RandomProxyDistributionStrategy implements IProxyDistributionStrategy {

	private final IProxyDistributionStrategy copiedProxyDistStrategy;

	public RandomProxyDistributionStrategy(List<IProxyListProvider> proxyListProvider) {
		this.copiedProxyDistStrategy = new CopiedProxyDistributionStrategy(proxyListProvider);
	}

	@Override
	public List<ProxyDetails> getNext(int count) {
		var copiedProxyList = copiedProxyDistStrategy.getNext(count);
		Collections.shuffle(copiedProxyList);
		return copiedProxyList;
	}
}
