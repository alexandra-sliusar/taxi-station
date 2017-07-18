package ua.taxistation.controller.command.realization.driver;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.LocaleEnum;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.entity.Order;
import ua.taxistation.entity.User;
import ua.taxistation.services.OrderService;
import ua.taxistation.utilities.LocaleManager;

public class DriverHistoryCommand implements Command {
	OrderService orderService;

	public DriverHistoryCommand(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Locale chosenLocale = LocaleEnum.getLocaleByLang((String) request.getSession().getAttribute(Parameters.LANG));
		LocaleManager.setResourceBundleLocale(chosenLocale);
		
		List<Order> orderList = orderService
				.getOrdersByDriver((User) request.getSession().getAttribute(Parameters.USER));
		request.setAttribute(Parameters.ORDER_LIST, orderList);
		return Page.HISTORY_PAGE;
	}
}
