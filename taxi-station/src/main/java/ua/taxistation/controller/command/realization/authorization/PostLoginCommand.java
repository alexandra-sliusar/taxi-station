package ua.taxistation.controller.command.realization.authorization;

import java.io.IOException;

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
import ua.taxistation.utilities.LocaleMessage;
import ua.taxistation.utilities.Validator;

public class PostLoginCommand implements Command {
	private UserService userService;

	public PostLoginCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		if (session.getAttribute(Parameters.USER) != null) {
			RedirectionManager.getInstance().redirect(request, response, Page.MAIN_PATH);
			return RedirectionManager.REDIRECTION;
		}

		CredentialsDto credentialsDto = getUserInput(request);
		Boolean ifInputCorrect = validateUserInput(credentialsDto);

		if (!ifInputCorrect) {
			if (credentialsDto.getLogin() != null)
				request.setAttribute(Parameters.USEREMAIL, credentialsDto.getLogin());
			request.setAttribute(Parameters.ERROR, LocaleManager.getString(LocaleMessage.INVALID_DATA_AUTH));
			return Page.LOGIN_PAGE;
		}

		try {
			User user = userService.signInUser(credentialsDto);
			request.getSession().setAttribute(Parameters.USER, user);
			RedirectionManager.getInstance().redirect(request, response, Page.MAIN_PATH);
			return RedirectionManager.REDIRECTION;
		} catch (ServerAppException e) {
			request.setAttribute(Parameters.ERROR, LocaleManager.getString(LocaleMessage.WRONG_DATA_AUTH));
			return Page.LOGIN_PAGE;
		}
	}

	private CredentialsDto getUserInput(HttpServletRequest request) {
		return new CredentialsDto(request.getParameter(Parameters.EMAIL), request.getParameter(Parameters.PASSWORD),
				Parameters.EMPTY_STRING, Parameters.EMPTY_STRING);
	}

	private boolean validateUserInput(CredentialsDto credentialsDto) {
		return Validator.getInstance().validateLogin(credentialsDto.getLogin())
				&& Validator.getInstance().validatePassword(credentialsDto.getPassword());

	}
}