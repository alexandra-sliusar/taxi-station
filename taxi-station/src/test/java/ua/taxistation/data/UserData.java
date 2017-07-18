package ua.taxistation.data;

import java.util.Optional;

import ua.taxistation.controller.dto.CredentialsDto;
import ua.taxistation.entity.User;
import ua.taxistation.entity.enums.Role;

public final class UserData {
	UserData() {
	}

	public static User getClient() {
		return new User.Builder().setId(new Long(1)).setLogin("Alex1997@ua.fm")
				.setPassword("0c00ce64fc161a302637eae20aa86207228a510997ec63fb079b9262dce0da1f")
				.setPhonenumber("(000) 000-00-00")
				.setSalt(new byte[] { 115, -106, -114, -26, -54, -46, 35, 50, 0, 8, 54, 69, 118, -30, -116, 48 })
				.setRole(Role.CLIENT).build();
	}

	public static User getDriver() {
		return new User.Builder().setId(new Long(7)).setLogin("Driver_3@gmail.com")
				.setPassword("7915b0a36f28a3bbff478d93a4b0e813157f41a950c7a13f79b2c68f6a227dc2")
				.setPhonenumber("(098) 333-33-33")
				.setSalt(new byte[] { 98, -4, 4, -97, -111, -48, -37, -33, 83, 21, -17, -85, -80, 95, -88, -113 })
				.setRole(Role.DRIVER).build();
	}


	public static Optional<User> getOptionalClient() {
		return Optional.of(getClient());
	}

	public static CredentialsDto getCredentialsDtoWithValidPassword() {
		return new CredentialsDto("Alex1997@ua.fm", "Alex1997", "", "");
	}

	public static CredentialsDto getCredentialsDtoWithInvalidPassword() {
		return new CredentialsDto("Alex1997@ua.fm", "OtherPasw12", "", "");
	}

	public static CredentialsDto getCredentialsDtoForNotSignedUpUser() {
		return new CredentialsDto("testingMail@gmail.com", "TestPa123", "TestPa123", "(077) 777-77-77");
	}
	
	public static CredentialsDto getCredentialsDtoForSignedUpUser() {
		return new CredentialsDto("Alex1997@ua.fm", "Alex1997", "", "");
	}

	public static User getUserFromCredentialsDto(CredentialsDto dto) {
		return new User.Builder().setLogin(dto.getLogin()).setPassword(dto.getPassword())
				.setPhonenumber(dto.getPhonenumber()).build();
	}
}
