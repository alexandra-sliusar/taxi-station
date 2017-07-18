package ua.taxistation.data;

import java.util.Arrays;
import java.util.List;

import ua.taxistation.entity.Car;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.entity.enums.CarStatus;

public final class CarData {

	public static List<Car> getAllCars() {
		return Arrays
				.asList(new Car[] {
						new Car.Builder().setId(new Long(1)).setDriver(UserData.getClient()).setModel("AA")
								.setNumber("AA")
								.setCarCharacteristics(
										Arrays.asList(new CarCharacteristics[] { CarCharacteristics.NONE }))
								.setCarStatus(CarStatus.AVAILABLE).setColor("color").build(),

						new Car.Builder().setId(new Long(1)).setDriver(UserData.getClient()).setModel("AB")
								.setNumber("AB")
								.setCarCharacteristics(
										Arrays.asList(new CarCharacteristics[] { CarCharacteristics.NONE }))
								.setCarStatus(CarStatus.AVAILABLE).setColor("color").build(),

						new Car.Builder().setId(new Long(1)).setDriver(UserData.getClient()).setModel("AC")
								.setNumber("AC")
								.setCarCharacteristics(
										Arrays.asList(new CarCharacteristics[] { CarCharacteristics.NONE }))
								.setCarStatus(CarStatus.UNAVAILABLE).setColor("color").build(),

						new Car.Builder().setId(new Long(1)).setDriver(UserData.getClient()).setModel("AD")
								.setNumber("AD")
								.setCarCharacteristics(
										Arrays.asList(new CarCharacteristics[] { CarCharacteristics.NONE }))
								.setCarStatus(CarStatus.AVAILABLE).setColor("color").build(),

						new Car.Builder().setId(new Long(1)).setDriver(UserData.getClient()).setModel("AE")
								.setNumber("AE")
								.setCarCharacteristics(Arrays.asList(new CarCharacteristics[] {
										CarCharacteristics.ANIMAL_TRANSPORTATION, CarCharacteristics.CREDITCARD_PAYMENT,
										CarCharacteristics.MINIBUS, CarCharacteristics.PREMIUM,
										CarCharacteristics.RECEIPT, CarCharacteristics.WIFI }))
								.setCarStatus(CarStatus.AVAILABLE).setColor("color").build() }

		);
	}

	public static List<Car> getAvailableCars() {

		return Arrays
				.asList(new Car[] {
						new Car.Builder().setId(new Long(1)).setDriver(UserData.getClient()).setModel("AA")
								.setNumber("AA")
								.setCarCharacteristics(
										Arrays.asList(new CarCharacteristics[] { CarCharacteristics.NONE }))
								.setCarStatus(CarStatus.AVAILABLE).setColor("color").build(),

						new Car.Builder().setId(new Long(1)).setDriver(UserData.getClient()).setModel("AB")
								.setNumber("AB")
								.setCarCharacteristics(
										Arrays.asList(new CarCharacteristics[] { CarCharacteristics.NONE }))
								.setCarStatus(CarStatus.AVAILABLE).setColor("color").build(),

						new Car.Builder().setId(new Long(1)).setDriver(UserData.getClient()).setModel("AD")
								.setNumber("AD")
								.setCarCharacteristics(
										Arrays.asList(new CarCharacteristics[] { CarCharacteristics.NONE }))
								.setCarStatus(CarStatus.AVAILABLE).setColor("color").build(),

						new Car.Builder().setId(new Long(1)).setDriver(UserData.getClient()).setModel("AE")
								.setNumber("AE")
								.setCarCharacteristics(Arrays.asList(new CarCharacteristics[] {
										CarCharacteristics.ANIMAL_TRANSPORTATION, CarCharacteristics.CREDITCARD_PAYMENT,
										CarCharacteristics.MINIBUS, CarCharacteristics.PREMIUM,
										CarCharacteristics.RECEIPT, CarCharacteristics.WIFI }))
								.setCarStatus(CarStatus.AVAILABLE).setColor("color").build() }

		);
	}

	public static List<Car> getCarsWithCharacteristics() {
		return Arrays
				.asList(new Car[] {
					new Car.Builder().setId(new Long(1)).setDriver(UserData.getClient()).setModel("AE")
								.setNumber("AE")
								.setCarCharacteristics(Arrays.asList(new CarCharacteristics[] {
										CarCharacteristics.ANIMAL_TRANSPORTATION, CarCharacteristics.CREDITCARD_PAYMENT,
										CarCharacteristics.MINIBUS, CarCharacteristics.PREMIUM,
										CarCharacteristics.RECEIPT, CarCharacteristics.WIFI }))
								.setCarStatus(CarStatus.AVAILABLE).setColor("color").build() }

		);
	}

	public static Car getCar() {
		return new Car.Builder().setId(new Long(1)).setDriver(UserData.getDriver()).setModel("AA").setNumber("AA")
				.setCarStatus(CarStatus.AVAILABLE)
				.setCarCharacteristics(Arrays.asList(new CarCharacteristics[] { CarCharacteristics.NONE }))
				.setColor("color").build();
	}
	
	public static Car getUnavailableCar() {
		return new Car.Builder().setId(new Long(1)).setDriver(UserData.getDriver()).setModel("AA").setNumber("AA")
				.setCarStatus(CarStatus.UNAVAILABLE)
				.setCarCharacteristics(Arrays.asList(new CarCharacteristics[] { CarCharacteristics.NONE }))
				.setColor("color").build();
	}
}
