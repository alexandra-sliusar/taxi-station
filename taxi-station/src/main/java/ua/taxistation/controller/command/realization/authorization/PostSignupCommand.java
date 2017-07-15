package ua.taxistation.controller.command.realization.authorization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.controller.dto.CredentialsDto;
import ua.taxistation.controller.utilities.RedirectionManager;
import ua.taxistation.entity.User;
import ua.taxistation.exceptions.ServerAppException;
import ua.taxistation.services.UserService;
import ua.taxistation.utilities.LocaleManager;
import ua.taxistation.utilities.Validator;

public class PostSignupCommand implements Command {
	UserService userService;

	public PostSignupCommand(UserService userService) {
		this.userService = userService;
	}

	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (session.getAttribute(Parameters.USER) != null) {
			RedirectionManager.getInstance().redirect(request, response, Page.MAIN_PATH);
			return RedirectionManager.REDIRECTION;
		}

		CredentialsDto credentialsDto = getUserInput(request);
		List<String> errors = new ArrayList<>();
		errors.addAll(validateUserInput(credentialsDto));

		if (!errors.isEmpty()) {
			request.setAttribute(Parameters.USEREMAIL, credentialsDto.getLogin());
			request.setAttribute(Parameters.USER_PHONE_NUMBER, credentialsDto.getPhonenumber());
			request.setAttribute(Parameters.ERRORS, errors);
			return Page.SIGNUP_PAGE;
		}

		try {
			User user = userService.signUpUser(credentialsDto);
			session.setAttribute(Parameters.USER, user);
			RedirectionManager.getInstance().redirect(request, response, Page.MAIN_PATH);
			return RedirectionManager.REDIRECTION;
		} catch (ServerAppException e) {
			errors.add(LocaleManager.SIGNUP_DB_ERROR);
			request.setAttribute(Parameters.ERRORS, errors);
			return Page.SIGNUP_PAGE;
		}
	}

	private CredentialsDto getUserInput(HttpServletRequest request) {
		return new CredentialsDto(request.getParameter(Parameters.EMAIL), request.getParameter(Parameters.PASSWORD),
				request.getParameter(Parameters.CONFIRM_PASSWORD), request.getParameter(Parameters.PHONE_NUMBER));
	}

	private List<String> validateUserInput(CredentialsDto credentialsDto) {
		List<String> errors = new ArrayList<>();
		if (!credentialsDto.getPassword().equals(credentialsDto.getConfirmPassword())) {
			errors.add(LocaleManager.CONFIRM_PASS_ERROR);
		}
		if (!Validator.getInstance().validateLogin(credentialsDto.getLogin())) {
			errors.add(LocaleManager.LOGIN_REGEX_ERROR);
		}
		if (!Validator.getInstance().validatePassword(credentialsDto.getPassword())) {
			errors.add(LocaleManager.PASS_REGEX_ERROR);
		}
		if (!Validator.getInstance().validatePhoneNumber(credentialsDto.getPhonenumber())) {
			errors.add(LocaleManager.PHONE_NUMBER_REGEX_ERROR);
		}
		return errors;
	}

}
