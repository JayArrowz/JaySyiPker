package jay.syi.business.proxy.provider;

import jay.syi.model.ProxyDetails;
import jay.syi.model.ProxyType;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

@Component("text-sock5-proxy")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ProxySock5TextProvider extends BaseProxyProvider {
    @Override
    public void load() {
        File f = new File("./sock5.txt");
        if (!f.exists()) {
            return;
        }
        try (Scanner s = new Scanner(new FileInputStream("./sock5.txt"))) {
            var proxyDetails = new ArrayList<ProxyDetails>();
            while (s.hasNextLine()) {
                var lineData = s.nextLine().split(":");
                ProxyDetails details = new ProxyDetails();
                details.setIp(lineData[0]);
                details.setPort(Integer.parseInt(lineData[1]));
                details.setProxyType(ProxyType.SOCKS_5);
                proxyDetails.add(details);
            }
            this.get().addAll(proxyDetails);
            LOGGER.info("Loaded {} proxies from sock5.txt", proxyDetails.size());
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
