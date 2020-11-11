import jay.syi.business.CliDependencyFactory;
import jay.syi.commands.CommandRoot;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import picocli.CommandLine;
import java.net.UnknownHostException;
import java.util.Scanner;

@SpringBootApplication
@ComponentScan(basePackages = {
		"jay.syi"
})
public class ExampleProgram implements InitializingBean {
	private static final String NOOB_USERNAME = "Luke132";
	private static final String NOOB_PASSWORD = "Noob123";
	private static final String SERVER_IP = "hydrixps.ddns.net";
	private final CommandRoot commandRoot;
	private final ApplicationContext applicationContext;
	private boolean quit;

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication.run(ExampleProgram.class, args);
	}

	public ExampleProgram(CommandRoot commandRoot, ApplicationContext applicationContext) {
		this.commandRoot = commandRoot;
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() {
		Scanner input = new Scanner(System.in).useDelimiter("\n"); // So that prompt shows once each time
		CommandLine.IFactory factory = new CliDependencyFactory(applicationContext);

		//TODO Remove deprecated method
		CommandLine.run(CommandRoot.class, factory, System.out, CommandLine.Help.Ansi.ON);

		System.out.print(CommandLine.Help.Ansi.AUTO.string("@|yellow cli>|@ "));
		while (!quit) {
			if (input.hasNext()) {
				String cmd = input.next();
				CommandLine.run(CommandRoot.class, factory, System.out, CommandLine.Help.Ansi.ON, cmd.trim().split(" "));
				System.out.println();
				System.out.print(CommandLine.Help.Ansi.AUTO.string("@|yellow cli>|@ "));
			}
		}
		input.close();
	}
}
