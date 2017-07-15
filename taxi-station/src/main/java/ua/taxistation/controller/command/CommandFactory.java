package ua.taxistation.controller.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ua.taxistation.controller.command.realization.HomeCommand;
import ua.taxistation.controller.command.realization.authorization.GetLoginCommand;
import ua.taxistation.controller.command.realization.authorization.GetSignupCommand;
import ua.taxistation.controller.command.realization.authorization.LogoutCommand;
import ua.taxistation.controller.command.realization.authorization.PostLoginCommand;
import ua.taxistation.controller.command.realization.authorization.PostSignupCommand;
import ua.taxistation.services.UserService;

public class CommandFactory {

	private static String INDEX_PATH = ".*/main/";
	private static String ID = "\\d+";
	private static String REPLACEMENT = "";
	private static String DELIMITER = ":";

	private static Map<String, Command> commands = new HashMap<>();
	static {
		commands.put("GET:", new HomeCommand());
		commands.put("GET:errorPage", new HomeCommand());
		commands.put("GET:login", new GetLoginCommand());
		commands.put("POST:login", new PostLoginCommand(UserService.getInstance()));
		commands.put("GET:signup", new GetSignupCommand());
		commands.put("POST:signup", new PostSignupCommand(UserService.getInstance()));
		commands.put("GET:locale", new ChangeLocaleCommand());
		commands.put("GET:logout", new LogoutCommand());
	}

	public static Command getCommand(HttpServletRequest request) {
		String key = extractKeyFromRequest(request);
		Command command = commands.get(key);
		return (command == null) ? commands.get("errorPage") : command;
	}

	public static String extractKeyFromRequest(HttpServletRequest request) {
		String method = request.getMethod().toUpperCase();
		String path = request.getRequestURI().replaceAll(INDEX_PATH, REPLACEMENT).replaceAll(ID, REPLACEMENT);
		String key = method + DELIMITER + path;
		return key;
	}
}
