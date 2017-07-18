package ua.taxistation.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ua.taxistation.utilities.Validator;

public class EmailValidationTest {
	
	@Test
	public void testPlainEmailSuccess() {
		String email = "Alex@ua.fm";
		boolean isValid = Validator.getInstance().validateLogin(email);
		assertTrue(isValid);
	}
	@Test
	public void testEmailWithNumbersSuccess() {
		String email = "23Alex1997@gmail.com";
		boolean isValid = Validator.getInstance().validateLogin(email);
		assertTrue(isValid);
	}
	@Test
	public void testEmailWithHyphenAndDotsSuccess() {
		String email = "-Alex.-alex.-alex-@gmail.com";
		boolean isValid = Validator.getInstance().validateLogin(email);
		assertTrue(isValid);
	}
	@Test
	public void testEmailWithUnderscoreSuccess() {
		String email = "_Alex_kfw@gmail.com";
		boolean isValid = Validator.getInstance().validateLogin(email);
		assertTrue(isValid);
	}
	@Test
	public void testEmailWithManyDomainsSuccess() {
		String email = "Alex1997@gmail.com.com.com.com.com";
		boolean isValid = Validator.getInstance().validateLogin(email);
		assertTrue(isValid);
	}
	@Test
	public void testEmailWithNumbersInDomainSuccess() {
		String email = "Alex1997@gmail123.com";
		boolean isValid = Validator.getInstance().validateLogin(email);
		assertTrue(isValid);
	}
	@Test
	public void testEmailWithHyphenInDomainSuccess() {
		String email = "Alex1997@gmail-com.com";
		boolean isValid = Validator.getInstance().validateLogin(email);
		assertTrue(isValid);
	}
	@Test
	public void testEmailWithNumbersInLastDomainFailure() {
		String email = "Alex1997@gmail.com123";
		boolean isValid = Validator.getInstance().validateLogin(email);
		assertFalse(isValid);
	}
	@Test
	public void testEmailWithTooShortLastDomainFailure() {
		String email = "Alex1997@gmail.c";
		boolean isValid = Validator.getInstance().validateLogin(email);
		assertFalse(isValid);
	}
	@Test
	public void testEmailWithTooLongLastDomainFailure() {
		String email = "Alex1997@gmail.comcomcomcom";
		boolean isValid = Validator.getInstance().validateLogin(email);
		assertFalse(isValid);
	}
	@Test
	public void testEmailWithoutAtSymbolFailure() {
		String email = "Alex1997gmail.com";
		boolean isValid = Validator.getInstance().validateLogin(email);
		assertFalse(isValid);
	}
	@Test
	public void testEmailWithRussianLettersFailure() {
		String email = "Aleббб1997gmail.com";
		boolean isValid = Validator.getInstance().validateLogin(email);
		assertFalse(isValid);
	}

}
