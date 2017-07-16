package ua.taxistation.controller.command.realization.dispatcher;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.entity.Car;
import ua.taxistation.entity.Request;
import ua.taxistation.services.CarService;
import ua.taxistation.services.RequestService;

public class GetAllCarsCommand implements Command {

	private CarService carService;

	public GetAllCarsCommand(CarService carService) {
		this.carService = carService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Car> carList = carService.getAllCars();
		request.setAttribute(Parameters.CAR_LIST, carList);
		return Page.CARS_PAGE;
	}
}