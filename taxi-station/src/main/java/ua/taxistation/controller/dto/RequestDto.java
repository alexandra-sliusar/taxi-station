package ua.taxistation.controller.dto;

import java.util.ArrayList;
import java.util.List;


import ua.taxistation.entity.enums.CarCharacteristics;

public class RequestDto {
	
	/**
	 * class to get user request data from page and transfer it into service
	 * @param pickup - place where user wanted to be picked up
	 * @param destination - place where user should be delivered
	 * @param carCharacteristics - list of special car features that user wants 
	 */
	private String pickup;
	private String destination;
	private List<CarCharacteristics> carCharacteristics = new ArrayList<>();

	public RequestDto() {

	}

	public RequestDto(String pickup, String destination, List<CarCharacteristics> carCharacteristics) {
		super();
		this.pickup = pickup;
		this.destination = destination;
		this.carCharacteristics = carCharacteristics;
	}

	public String getPickup() {
		return pickup;
	}

	public void setPickup(String pickup) {
		this.pickup = pickup;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<CarCharacteristics> getCarCharacteristics() {
		return carCharacteristics;
	}

	public void setCarCharacteristics(List<CarCharacteristics> carCharacteristics) {
		this.carCharacteristics = carCharacteristics;
	}

	@Override
	public String toString() {
		return "RequestDto [pickup=" + pickup + ", destination=" + destination + ", carCharacteristics="
				+ carCharacteristics + "]";
	}
}
