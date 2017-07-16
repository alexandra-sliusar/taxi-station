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
import ua.taxistation.entity.enums.CarStatus;
import ua.taxistation.services.CarService;
import ua.taxistation.services.OrderService;

public class ChangeCarStatusCommand implements Command {
	CarService carService;
	OrderService orderService;

	public ChangeCarStatusCommand(CarService carService,OrderService orderService) {
		this.carService = carService;
		this.orderService = orderService;
	}

	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Car car = carService.getCarByDriver(((User) request.getSession().getAttribute(Parameters.USER)).getId());
		CarStatus status = CarStatus.valueOf(request.getParameter(Parameters.CAR_STATUS).toUpperCase());
		car.setCarStatus(status);
		carService.updateCarStatus(car);
		request.setAttribute(Parameters.CAR, car);
		Optional<Order> order = orderService.getLastCarOrder(car);
		if (order.isPresent())
			request.setAttribute(Parameters.ORDER, order.get());
		RedirectionManager.getInstance().redirect(request, response, Page.PROFILE_PAGE_TO_REDIRECT);
		return RedirectionManager.REDIRECTION;

	}
}
