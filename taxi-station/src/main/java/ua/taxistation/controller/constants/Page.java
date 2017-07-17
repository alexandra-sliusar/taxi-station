package ua.taxistation.controller.constants;

public class Page {
	public static String PREFIX = "/WEB-INF/pages/";
	public static String SUFFIX = ".jsp";

	public static String MAIN_PATH = "/";
	public static String HOME_PAGE = "/index" + SUFFIX;
	public static String LOGIN_PAGE = PREFIX + "login" + SUFFIX;
	public static String SIGNUP_PAGE = PREFIX + "signup" + SUFFIX;
	public static String ORDERCAR_PAGE = PREFIX + "ordercar" + SUFFIX;
	public static String REQUESTS_PAGE = PREFIX + "requests" + SUFFIX;
	public static String REQUEST_PAGE = PREFIX + "request" + SUFFIX;
	public static String CARS_PAGE = PREFIX + "cars" + SUFFIX;
	public static String HISTORY_PAGE = PREFIX + "history" + SUFFIX;
	public static String PROFILE_PAGE = PREFIX + "profile" + SUFFIX;
	public static String PROFILE_PAGE_TO_REDIRECT = "/driver/profile";
	public static String PAGE_NOT_FOUND = PREFIX + "pagenotfound" + SUFFIX; 

}
