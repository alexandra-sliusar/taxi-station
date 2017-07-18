package ua.taxistation.controller.command.realization.dispatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.controller.utilities.RedirectionManager;
import ua.taxistation.entity.Request;
import ua.taxistation.entity.enums.RequestStatus;
import ua.taxistation.services.RequestService;
import ua.taxistation.utilities.LocaleManager;
import ua.taxistation.utilities.LocaleMessage;

public class RejectRequestCommand implements Command {

	RequestService requestService;

	public RejectRequestCommand(RequestService requestService) {
		this.requestService = requestService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long carRequestId = Long.parseLong(request.getParameter(Parameters.REQUEST_ID));
		Request carRequest = new Request.Builder().setId(carRequestId).setRequestStatus(RequestStatus.REJECTED).build();
		requestService.updateRequestStatus(carRequest);
		Map<String, String> message = new HashMap<>();
		message.put(Parameters.SUCCESS, LocaleManager.getString(LocaleMessage.REQUEST_REJECTED));
		RedirectionManager.getInstance().redirectWithMessages(request, response, Page.MAIN_PATH,
				message);
		return RedirectionManager.REDIRECTION;
	}

}
