package ua.taxistation.controller.command.realization.driver;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.entity.Car;
import ua.taxistation.entity.Order;
import ua.taxistation.entity.User;
import ua.taxistation.services.CarService;
import ua.taxistation.services.OrderService;

public class ProfileCommand implements Command {
	CarService carService;
	OrderService orderService;

	public ProfileCommand(CarService carService, OrderService orderService) {
		this.carService = carService;
		this.orderService = orderService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Car car = carService.getCarByDriver(((User) request.getSession().getAttribute(Parameters.USER)).getId());
		request.setAttribute(Parameters.CAR, car);
		Optional<Order> order = orderService.getLastCarOrder(car);
		if (order.isPresent())
			request.setAttribute(Parameters.ORDER, order.get());
		return Page.PROFILE_PAGE;
	}

}
