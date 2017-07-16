package ua.taxistation.controller.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ua.taxistation.controller.command.realization.ChangeLocaleCommand;
import ua.taxistation.controller.command.realization.HomeCommand;
import ua.taxistation.controller.command.realization.authorization.GetLoginCommand;
import ua.taxistation.controller.command.realization.authorization.GetSignupCommand;
import ua.taxistation.controller.command.realization.authorization.LogoutCommand;
import ua.taxistation.controller.command.realization.authorization.PostLoginCommand;
import ua.taxistation.controller.command.realization.authorization.PostSignupCommand;
import ua.taxistation.controller.command.realization.client.ClientHistoryCommand;
import ua.taxistation.controller.command.realization.client.GetOrderCarCommand;
import ua.taxistation.controller.command.realization.client.PostOrderCarCommand;
import ua.taxistation.controller.command.realization.dispatcher.GetAllCarsCommand;
import ua.taxistation.controller.command.realization.dispatcher.GetAllRequestsCommand;
import ua.taxistation.controller.command.realization.dispatcher.GetRequestCommand;
import ua.taxistation.controller.command.realization.dispatcher.PostRequestCommand;
import ua.taxistation.controller.command.realization.dispatcher.RejectRequestCommand;
import ua.taxistation.controller.command.realization.driver.ChangeCarStatusCommand;
import ua.taxistation.controller.command.realization.driver.ChangeOrderStatusCommand;
import ua.taxistation.controller.command.realization.driver.DriverHistoryCommand;
import ua.taxistation.controller.command.realization.driver.ProfileCommand;
import ua.taxistation.services.CarService;
import ua.taxistation.services.OrderService;
import ua.taxistation.services.RequestService;
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
		commands.put("GET:ordercar", new GetOrderCarCommand());
		commands.put("POST:ordercar", new PostOrderCarCommand(RequestService.getInstance()));
		commands.put("GET:requests", new GetAllRequestsCommand(RequestService.getInstance()));
		commands.put("GET:requests/request",
				new GetRequestCommand(RequestService.getInstance(), CarService.getInstance()));
		commands.put("POST:requests/request/submit", new PostRequestCommand(OrderService.getInstance()));
		commands.put("POST:requests/request/reject", new RejectRequestCommand(RequestService.getInstance()));
		commands.put("GET:cars", new GetAllCarsCommand(CarService.getInstance()));
		commands.put("GET:client/history", new ClientHistoryCommand(OrderService.getInstance()));
		commands.put("GET:driver/history", new DriverHistoryCommand(OrderService.getInstance()));
		commands.put("GET:profile", new ProfileCommand(CarService.getInstance(), OrderService.getInstance()));
		commands.put("POST:profile/changeCarStatus", new ChangeCarStatusCommand(CarService.getInstance(), OrderService.getInstance()));
		commands.put("POST:profile/changeOrderStatus",
				new ChangeOrderStatusCommand(OrderService.getInstance(), CarService.getInstance()));
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
		System.out.println(key);
		return key;
	}
}
