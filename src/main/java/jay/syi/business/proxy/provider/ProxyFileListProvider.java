package jay.syi.business.proxy.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import jay.syi.interfaces.proxy.IProxyListProvider;
import jay.syi.model.ProxyDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component("file-proxy")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ProxyFileListProvider implements IProxyListProvider, InitializingBean {
	private List<ProxyDetails> proxyDetails = new ArrayList<>();
	private static final Logger LOGGER = LogManager.getLogger(ProxyFileListProvider.class);

	@Override
	public void add(ProxyDetails details) {
		proxyDetails.add(details);
	}

	@Override
	public List<ProxyDetails> get() {
		return proxyDetails;
	}

	@Override
	public void load() {
		var objectMapper = new ObjectMapper();
		var proxyFile = new File("./sock5.json");

		if (proxyFile.exists()) {
			try {
				List<ProxyDetails> loadedProxyDetails =
						objectMapper.readValue(proxyFile, objectMapper
								.getTypeFactory()
								.constructCollectionType(List.class, ProxyDetails.class));

				LOGGER.info("Loaded {} proxies", loadedProxyDetails.size());
				proxyDetails.addAll(loadedProxyDetails);
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}
	}

	@Override
	public void afterPropertiesSet() {
		load();
	}
}
