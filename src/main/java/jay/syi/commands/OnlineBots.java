package jay.syi.commands;

import jay.syi.interfaces.IChannelHandlerContextRegister;
import jay.syi.model.LoginDetails;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "online", sortOptions = false, header = {}, description = { "prints username of online bots" },
		optionListHeading = "@|bold %nOptions|@:%n", footer = {})
public class OnlineBots implements Runnable {
	private final IChannelHandlerContextRegister channelHandlerContextRegister;

	public OnlineBots(IChannelHandlerContextRegister channelHandlerContextRegister) {
		this.channelHandlerContextRegister = channelHandlerContextRegister;
	}

	@Override
	public void run() {
		System.out.println("Online Bots:");
		channelHandlerContextRegister
				.getRegister()
				.keySet()
				.stream()
				.map(LoginDetails::getUsername)
				.forEach(System.out::println);
	}

}
