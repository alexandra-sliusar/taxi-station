package ua.taxistation.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ua.taxistation.utilities.Validator;

public class AddressValidation {
	@Test
	public void testPlainAddressSuccess() {
		String address = "Oxford Str., 12";
		boolean isValid = Validator.getInstance().validateAddress(address);
		assertTrue(isValid);
	}
	@Test
	public void testAddressWithSlashSuccess() {
		String address = "Oxford Str., 12/32";
		boolean isValid = Validator.getInstance().validateAddress(address);
		assertTrue(isValid);
	}
	@Test
	public void testAddressRussianSuccess() {
		String address = "Оксфорд Стр., 12";
		boolean isValid = Validator.getInstance().validateAddress(address);
		assertTrue(isValid);
	}
	@Test
	public void testEmptyAddressFailure() {
		String address = "";
		boolean isValid = Validator.getInstance().validateAddress(address);
		assertFalse(isValid);
	}
	@Test
	public void testNullAddressFailure() {
		String address = null;
		boolean isValid = Validator.getInstance().validateAddress(address);
		assertFalse(isValid);
	}
	@Test
	public void testAddressWithSymbolsFailure() {
		String address = "Angw_35 @ $mkog";
		boolean isValid = Validator.getInstance().validateAddress(address);
		assertFalse(isValid);
	}
	@Test
	public void testTooShortAddressFailure() {
		String address = "ad";
		boolean isValid = Validator.getInstance().validateAddress(address);
		assertFalse(isValid);
	}
}
