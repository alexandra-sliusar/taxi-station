package ua.taxistation.controller.utilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.exceptions.ServerAppException;
import ua.taxistation.utilities.LocaleManager;

public class RedirectionManager {
	/**
	 * class contains methods for correct redirection with specific messages of without
	 * @method generateUrlParameters - adding parameters to url in format ?name1=value1&name2=value2
	 */

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

	public void redirectWithMessages(HttpServletRequest request, HttpServletResponse response, String redirectionPath,
			Map<String, String> parameters) throws IOException {
		String resultPath = redirectionPath + generateUrlParameters(parameters);
		redirect(request, response, resultPath);
	}

	public String generateUrlParameters(Map<String, String> parameters) throws UnsupportedEncodingException {
		StringBuffer stringBuffer = new StringBuffer(Parameters.QUESTION_MARK);
		for (String parameterName : parameters.keySet()) {
			stringBuffer.append(parameterName).append(Parameters.EQUALITY_SIGN)
					.append(URLEncoder.encode(parameters.get(parameterName), MESSAGE_ENCODING))
					.append(Parameters.AMPERSAND);
		}
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		return stringBuffer.toString();
	}
}
