package ua.taxistation.controller.command.realization.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.controller.dto.RequestDto;
import ua.taxistation.controller.utilities.RedirectionManager;
import ua.taxistation.entity.User;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.exceptions.ServerAppException;
import ua.taxistation.services.RequestService;
import ua.taxistation.utilities.LocaleManager;
import ua.taxistation.utilities.Validator;

public class PostOrderCarCommand implements Command {
	RequestService requestService;

	public PostOrderCarCommand(RequestService requestService) {
		this.requestService = requestService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		RequestDto requestDto = getUserInput(request);
		boolean isCorrect = validateUserInput(requestDto);
		if (isCorrect) {
			requestService.createRequest(requestDto, (User) request.getSession().getAttribute(Parameters.USER));
			request.setAttribute(Parameters.SUCCESS,
					LocaleManager.BUNDLE.getString(LocaleManager.SUCCESS_REQUEST_ADDITION));
			return Page.ORDERCAR_PAGE;
		}
		request.setAttribute(Parameters.ERROR, LocaleManager.BUNDLE.getString(LocaleManager.INVALID_CAR_ORDER_DATA));
		return Page.ORDERCAR_PAGE;
	}

	private RequestDto getUserInput(HttpServletRequest request) {
		List<CarCharacteristics> chars = new ArrayList<>();
		if (request.getParameter(Parameters.CARCHAR_MINIBUS) != null)
			chars.add(CarCharacteristics.MINIBUS);
		if (request.getParameter(Parameters.CARCHAR_ANIMAL) != null)
			chars.add(CarCharacteristics.ANIMAL_TRANSPORTATION);
		if (request.getParameter(Parameters.CARCHAR_RECEIPT) != null)
			chars.add(CarCharacteristics.RECEIPT);
		if (request.getParameter(Parameters.CARCHAR_CREDITCARD) != null)
			chars.add(CarCharacteristics.CREDITCARD_PAYMENT);
		if (request.getParameter(Parameters.CARCHAR_WIFI) != null)
			chars.add(CarCharacteristics.WIFI);
		if (request.getParameter(Parameters.CARCHAR_PREMIUM) != null)
			chars.add(CarCharacteristics.PREMIUM);

		return new RequestDto(request.getParameter(Parameters.PICKUP), request.getParameter(Parameters.DESTINATION),
				chars);
	}

	private boolean validateUserInput(RequestDto requestDto) {
		return Validator.getInstance().validateAddress(requestDto.getPickup())
				&& Validator.getInstance().validateAddress(requestDto.getDestination());
	}

}
