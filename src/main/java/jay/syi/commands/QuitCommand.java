package jay.syi.commands;

import jay.syi.ExampleProgram;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "quit", sortOptions = false, description = { "quit cli" },
		optionListHeading = "@|bold %nOptions|@:%n")
public class QuitCommand implements Runnable {
	public void run() {
		//Shouldn't really be doing it like this but oh well
		ExampleProgram.quit();
	}
}
