package ua.taxistation.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.entity.enums.CarStatus;

public class Car implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private User driver;
	private String number;
	private String model;
	private String color;
	private Set<CarCharacteristics> carCharacteristics;
	private CarStatus carStatus = CarStatus.AVAILABLE;

	public Car() {
		carCharacteristics = new HashSet<>();
	}

	public static class Builder {
		protected Car car;

		public Builder() {
		}

		public Builder setId(Long id) {
			car.id = id;
			return this;
		}

		public Builder setDriver(User driver) {
			car.driver = driver;
			return this;
		}

		public Builder setNumber(String number) {
			car.number = number;
			return this;
		}

		public Builder setColor(String color) {
			car.color = color;
			return this;
		}

		public Builder setModel(String model) {
			car.model = model;
			return this;
		}

		public Builder setCarStatus(CarStatus carStatus) {
			car.carStatus = carStatus;
			return this;
		}

		public Builder setCarCharacteristics(Collection<CarCharacteristics> carCharacteristics) {
			car.carCharacteristics.addAll(carCharacteristics);
			return this;
		}

		public Car build() {
			return car;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public CarStatus getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(CarStatus carStatus) {
		this.carStatus = carStatus;
	}

	public Set<CarCharacteristics> getCarCharacteristics() {
		return carCharacteristics;
	}

	public void setCarCharacteristics(Collection<CarCharacteristics> carCharacteristics) {
		this.carCharacteristics.addAll(carCharacteristics);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result += prime * result + ((model == null) ? 0 : model.hashCode());
		result += prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", driver=" + driver + ", number=" + number + ", model=" + model + ", color=" + color
				+ ", carStatus=" + carStatus + ", carСharacteristics=" + carCharacteristics + "]";
	}
}
