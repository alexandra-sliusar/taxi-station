package ua.taxistation.controller.utilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.exceptions.ServerAppException;

public class RedirectionManager {

	public static String REDIRECTION = "REDIRECTION";
	private static String MESSAGE_ENCODING = "UTF-8";

	private RedirectionManager() {
	}

	private static final class Holder {
		static final RedirectionManager INSTANCE = new RedirectionManager();
	}

	public static RedirectionManager getInstance() {
		return Holder.INSTANCE;
	}


	public void redirect(HttpServletRequest request, HttpServletResponse response, String path) {
		try {
			response.sendRedirect(generateUrlPath(request, path));
		} catch (IOException e) {
			throw new ServerAppException(e);
		}
	}

	private String generateUrlPath(HttpServletRequest request, String path) {
		return request.getContextPath() + request.getServletPath() + path;
	}

}
