package ua.taxistation.controller.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ua.taxistation.controller.command.realization.HomeCommand;
import ua.taxistation.controller.command.realization.authorization.LoginPageCommand;

public class CommandFactory {

	private static String INDEX_PATH = ".*/taxistation/";
	private static String REPLACEMENT = "";

	private static Map<String, Command> commands = new HashMap<>();
	static {
		commands.put("", new HomeCommand());
		commands.put("errorPage", new HomeCommand());
		commands.put("loginPage", new LoginPageCommand());
	}

	public static Command getCommand(HttpServletRequest request) {
		String key = extractKeyFromRequest(request);
		Command command = commands.get(key);
		return (command == null) ? commands.get("errorPage") : command;
	}

	public static String extractKeyFromRequest(HttpServletRequest request) {
		return request.getRequestURI().replaceAll(INDEX_PATH, REPLACEMENT);
	}
}
