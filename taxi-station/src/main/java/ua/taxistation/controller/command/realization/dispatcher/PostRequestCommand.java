package ua.taxistation.controller.command.realization.dispatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.controller.dto.RequestDto;
import ua.taxistation.controller.utilities.RedirectionManager;
import ua.taxistation.entity.User;
import ua.taxistation.services.OrderService;
import ua.taxistation.utilities.LocaleManager;

public class PostRequestCommand implements Command {

	OrderService orderService;

	public PostRequestCommand(OrderService orderService) {
		this.orderService = orderService;
	}

	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String carId = "";
		Long carRequestId = Long.parseLong(request.getParameter(Parameters.REQUEST_ID));
		try {
			carId = request.getParameter(Parameters.CAR_AVAILABLE);
		} catch (Exception e) {
			request.setAttribute(Parameters.ERROR, LocaleManager.BUNDLE.getString(LocaleManager.NOT_SELECTED));
			return Page.REQUEST_PAGE;
		}

		try {
			orderService.createOrder(carRequestId, Long.parseLong(carId));
			Map<String, String> message = new HashMap<>();
			message.put(Parameters.SUCCESS, LocaleManager.BUNDLE.getString(LocaleManager.ORDER_ADDITION_SUCCESS));
			RedirectionManager.getInstance().redirectWithMessages(request, response, Page.MAIN_PATH,
					message);
			return RedirectionManager.REDIRECTION;
		} catch (Exception e) {
			Map<String, String> message = new HashMap<>();
			message.put(Parameters.ERROR, LocaleManager.BUNDLE.getString(LocaleManager.ORDER_ADDITION_ERROR));
			RedirectionManager.getInstance().redirectWithMessages(request, response, Page.MAIN_PATH,
					message);
			return RedirectionManager.REDIRECTION;
		}

	}

}
