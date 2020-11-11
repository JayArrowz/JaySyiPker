package jay.syi.business;

import org.springframework.context.ApplicationContext;
import picocli.CommandLine;

public class CliDependencyFactory implements CommandLine.IFactory {

	private ApplicationContext appContext;

	public CliDependencyFactory(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	@Override
	public <K> K create(Class<K> cls) {
		return this.appContext.getBean(cls);
	}
}