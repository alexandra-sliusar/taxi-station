package ua.taxistation.controller.command.realization.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.entity.Order;
import ua.taxistation.entity.User;
import ua.taxistation.services.OrderService;

public class ClientHistoryCommand implements Command {
	OrderService orderService;

	public ClientHistoryCommand(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		
		List<Order> orderList = orderService.getOrdersByUser((User) request.getSession().getAttribute(Parameters.USER));
		request.setAttribute(Parameters.ORDER_LIST, orderList);
		return Page.HISTORY_PAGE;
	}
}
