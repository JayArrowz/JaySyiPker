package jay.syi.business.proxy.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import jay.syi.model.ProxyDetails;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component("file-proxy")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ProxyFileListProvider extends BaseProxyProvider {
    @Override
    public void load() {
        var objectMapper = new ObjectMapper();
        var proxyFile = new File("./sock5.json");

        if (!proxyFile.exists()) {
            return;
        }
        try {
            List<ProxyDetails> loadedProxyDetails =
                    objectMapper.readValue(proxyFile, objectMapper
                            .getTypeFactory()
                            .constructCollectionType(List.class, ProxyDetails.class));

            LOGGER.info("Loaded {} proxies", loadedProxyDetails.size());
            get().addAll(loadedProxyDetails);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        load();
    }
}
