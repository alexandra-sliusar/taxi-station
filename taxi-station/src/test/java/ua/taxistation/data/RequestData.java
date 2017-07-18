package ua.taxistation.data;

import java.time.LocalDateTime;

import ua.taxistation.entity.Request;
import ua.taxistation.entity.User;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.entity.enums.RequestStatus;

public class RequestData {
	public static Request getRequest() {
		Request r = new Request.Builder().setId(new Long(1)).setRequestStatus(RequestStatus.UNPROCESSED)
				.setPickup("From").setDestination("To").setDateOfRequest(LocalDateTime.now())
				.setUser(new User.Builder().setId(new Long(2)).build()).build();
		r.addCarCharacteristic(CarCharacteristics.ANIMAL_TRANSPORTATION);
		return r;
	}
	public static Request getProcessedRequest() {
		Request r = new Request.Builder().setId(new Long(1)).setRequestStatus(RequestStatus.PROCESSED)
				.setPickup("From").setDestination("To").setDateOfRequest(LocalDateTime.now())
				.setUser(new User.Builder().setId(new Long(2)).build()).build();
		r.addCarCharacteristic(CarCharacteristics.ANIMAL_TRANSPORTATION);
		return r;
	}
}
