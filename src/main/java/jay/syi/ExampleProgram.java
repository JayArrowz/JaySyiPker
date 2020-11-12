package jay.syi;

import jay.syi.business.CliDependencyFactory;
import jay.syi.commands.CommandRoot;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import picocli.CommandLine;

import java.util.Scanner;

@SpringBootApplication
@ComponentScan(basePackages = {
		"jay.syi"
})
public class ExampleProgram implements InitializingBean {
	private final ApplicationContext applicationContext;
	private static boolean quit;

	public static void main(String[] args) {
		SpringApplication.run(ExampleProgram.class, args);
	}

	public static void quit() {
		quit = true;
	}

	public ExampleProgram(ApplicationContext applicationContext) {
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
