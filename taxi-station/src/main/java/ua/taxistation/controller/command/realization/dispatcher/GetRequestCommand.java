package ua.taxistation.controller.command.realization.dispatcher;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.entity.Car;
import ua.taxistation.entity.Request;
import ua.taxistation.services.CarService;
import ua.taxistation.services.RequestService;

public class GetRequestCommand implements Command {
	private RequestService requestService;
	private CarService carService;

	public GetRequestCommand(RequestService requestService, CarService carService) {
		this.requestService = requestService;
		this.carService = carService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		Long carRequestId = Long.parseLong(request.getParameter(Parameters.REQUEST_ID));
		Request carRequest = requestService.getRequestById(carRequestId);
		List<Car> availableCars = carService.getCarsByCharacteristics(carRequest.getCarCharacteristics());
		request.setAttribute(Parameters.CARS, availableCars);
		request.setAttribute(Parameters.REQUEST_ID, carRequestId);
		return Page.REQUEST_PAGE;
	}

}
