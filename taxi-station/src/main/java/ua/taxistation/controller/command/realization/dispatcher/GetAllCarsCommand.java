package ua.taxistation.controller.command.realization.dispatcher;

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
import ua.taxistation.entity.Car;
import ua.taxistation.services.CarService;
import ua.taxistation.utilities.LocaleManager;

public class GetAllCarsCommand implements Command {

	private CarService carService;

	public GetAllCarsCommand(CarService carService) {
		this.carService = carService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Locale chosenLocale = LocaleEnum.getLocaleByLang((String) request.getSession().getAttribute(Parameters.LANG));
		LocaleManager.setResourceBundleLocale(chosenLocale);
		List<Car> carList = carService.getAllCars();
		request.setAttribute(Parameters.CAR_LIST, carList);
		return Page.CARS_PAGE;
	}
}