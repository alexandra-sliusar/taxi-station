package ua.taxistation.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ua.taxistation.utilities.Validator;

public class PasswordValidationTest {
	
	@Test
	public void testPlainPasswordSuccess() {
		String password = "Alex.1997";
		boolean isValid = Validator.getInstance().validatePassword(password);
		assertTrue(isValid);
	}
	@Test
	public void testPasswordWithoutBigLetterFailure() {
		String password = "alex1997";
		boolean isValid = Validator.getInstance().validatePassword(password);
		assertFalse(isValid);
	}
	@Test
	public void testPasswordWithoutSmaillLetterFailure() {
		String password = "ALEX1997";
		boolean isValid = Validator.getInstance().validatePassword(password);
		assertFalse(isValid);
	}
	@Test
	public void testPasswordWithoutDigitsFailure() {
		String password = "alexALEX";
		boolean isValid = Validator.getInstance().validatePassword(password);
		assertFalse(isValid);
	}
	@Test
	public void testToSmallPasswordFailure() {
		String password = "Al1";
		boolean isValid = Validator.getInstance().validatePassword(password);
		assertFalse(isValid);
	}
	@Test
	public void testToLongPasswordFailure() {
		String password = "alalaALALA123132Alfwfj124jveojw124";
		boolean isValid = Validator.getInstance().validatePassword(password);
		assertFalse(isValid);
	}
	@Test
	public void testPasswordWithSpacesFailure() {
		String password = "Alex 1997	123";
		boolean isValid = Validator.getInstance().validatePassword(password);
		assertFalse(isValid);
	}
	@Test
	public void testPasswordWithOtherSymbolsFailure() {
		String password = "al-ex@19_97";
		boolean isValid = Validator.getInstance().validatePassword(password);
		assertFalse(isValid);
	}	
	
	public void testEmptyFailure() {
		String password = "";
		boolean isValid = Validator.getInstance().validatePassword(password);
		assertFalse(isValid);
	}
	public void testNullPasswordFailure() {
		String password = null;
		boolean isValid = Validator.getInstance().validatePassword(password);
		assertFalse(isValid);
	}
}