package ua.taxistation.controller.command.realization.driver;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.controller.utilities.RedirectionManager;
import ua.taxistation.entity.Car;
import ua.taxistation.entity.Order;
import ua.taxistation.entity.User;
import ua.taxistation.entity.enums.OrderStatus;
import ua.taxistation.services.CarService;
import ua.taxistation.services.OrderService;

public class ChangeOrderStatusCommand implements Command {
	OrderService orderService;
	CarService carService;

	public ChangeOrderStatusCommand(OrderService orderService, CarService carService) {
		this.orderService = orderService;
		this.carService = carService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Car car = carService.getCarByDriver(((User) request.getSession().getAttribute(Parameters.USER)).getId());
		Order order = orderService.getLastCarOrder(car).get();
		order.setOrderStatus(OrderStatus.COMPLETE);
		orderService.updateOrder(order);
		request.setAttribute(Parameters.CAR, car);
		Optional<Order> optOrder = orderService.getLastCarOrder(car);
		if (optOrder.isPresent())
			request.setAttribute(Parameters.ORDER, optOrder.get());
		RedirectionManager.getInstance().redirect(request, response, Page.PROFILE_PAGE_TO_REDIRECT);
		return RedirectionManager.REDIRECTION;
	}

}
