package ua.taxistation.controller.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.controller.utilities.RedirectionManager;
import ua.taxistation.entity.User;
import ua.taxistation.entity.enums.Role;
import ua.taxistation.utilities.LocaleManager;
import ua.taxistation.utilities.LocaleMessage;

@WebFilter(urlPatterns = { "/main/client/*", "/main/driver/*", "/main/dispatcher/*" })
public class IllegalAccessFilter implements Filter {
	/**
	 * filter class to prevent illegal access to pages
	 */
	private final static Logger LOGGER = Logger.getLogger(IllegalAccessFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

		User user = (User) httpRequest.getSession().getAttribute(Parameters.USER);

		if (!isUserSignIn(user) || !isPathLegalForUser(httpRequest.getRequestURI(), user)) {
			LOGGER.info("Unauthorized access to the resource: " + httpRequest.getRequestURI());
			Map<String, String> message = new HashMap<>();
			message.put(Parameters.ERROR, LocaleManager.getString(LocaleMessage.ILLEGAL_ACCESS));
			RedirectionManager.getInstance().redirectWithMessages(httpRequest, httpResponse, Page.MAIN_PATH, message);
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
	}

	private boolean isUserSignIn(User user) {
		return user != null;

	}

	private boolean isPathLegalForUser(String servletPath, User user) {
		return (isDriverPage(servletPath) && user.getRole().equals(Role.DRIVER))
				|| (isDispatcherPage(servletPath) && user.getRole().equals(Role.DISPATCHER))
				|| (isClientPage(servletPath) && user.getRole().equals(Role.CLIENT));
	}

	private boolean isDriverPage(String requestURI) {
		return requestURI.contains(Role.DRIVER.name().toLowerCase());
	}

	private boolean isDispatcherPage(String requestURI) {
		return requestURI.contains(Role.DISPATCHER.name().toLowerCase());
	}

	private boolean isClientPage(String requestURI) {
		return requestURI.contains(Role.CLIENT.name().toLowerCase());
	}

}