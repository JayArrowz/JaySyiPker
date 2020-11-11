package jay.syi.commands;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "", sortOptions = false, description = { "", "", }, subcommands = { LoginCommand.class }, optionListHeading = "@|bold %nOptions|@:%n", footer = {})
public class CommandRoot implements Runnable {

	private static String[] header = {
			"      _,---.       ,----.  .-._         \r\n" + "  _.='.'-,  \\   ,-.--` , \\/==/ \\  .-._  \r\n"
					+ " /==.'-     /  |==|-  _.-`|==|, \\/ /, / \r\n" + "/==/ -   .-'   |==|   `.-.|==|-  \\|  |  \r\n"
					+ "|==|_   /_,-. /==/_ ,    /|==| ,  | -|  \r\n" + "|==|  , \\_.' )|==|    .-' |==| -   _ |  \r\n"
					+ "\\==\\-  ,    ( |==|_  ,`-._|==|  /\\ , |  \r\n" + " /==/ _  ,  / /==/ ,     //==/, | |- |  \r\n"
					+ " `--`------'  `--`-----`` `--`./  `--`  \r\n" + "           JaySyiPker CLI" };

	public void run() {
		for (String line : header) {
			System.out.println(CommandLine.Help.Ansi.AUTO.string("@|green" + line + "|@"));
		}
	}
}
