package jay.syi.business.proxy;

import jay.syi.interfaces.client.IProxyDistributionStrategy;
import jay.syi.interfaces.proxy.IProxyListProvider;
import jay.syi.model.ProxyDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("copied-proxy-dist")
public class CopiedProxyDistributionStrategy implements IProxyDistributionStrategy {

	private final IProxyListProvider proxyListProvider;

	public CopiedProxyDistributionStrategy(IProxyListProvider proxyListProvider) {
		this.proxyListProvider = proxyListProvider;
	}

	@Override
	public List<ProxyDetails> getNext(int count) {
		var proxyProviderList = this.proxyListProvider.get();

		if(proxyProviderList.isEmpty()) {
			return new ArrayList<>();
		}

		var proxyListToReturn = new ArrayList<ProxyDetails>();

		var proxyIdx = 0;
		while (count > 0) {
			var proxyDetails = proxyProviderList.get(proxyIdx);
			proxyListToReturn.add(proxyDetails);
			if (proxyIdx < proxyProviderList.size()) {
				proxyIdx++;
			} else {
				proxyIdx = 0;
			}
			count--;
		}
		return proxyListToReturn;
	}
}
