package ua.taxistation.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.entity.enums.RequestStatus;

public class Request implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private User user;
	private String pickup;
	private String destination;
	private LocalDateTime dateOfRequest;
	private List<CarCharacteristics> carCharacteristics;
	private RequestStatus requestStatus = RequestStatus.UNPROCESSED;

	public Request() {
		carCharacteristics = new ArrayList<>();
	}

	public static class Builder {
		protected Request request = new Request();

		public Builder() {
		}

		public Builder setId(Long id) {
			request.id = id;
			return this;
		}

		public Builder setUser(User user) {
			request.user = user;
			return this;
		}

		public Builder setPickup(String pickup) {
			request.pickup = pickup;
			return this;
		}

		public Builder setDestination(String destination) {
			request.destination = destination;
			return this;
		}

		public Builder setDateOfRequest(LocalDateTime dateOfRequest) {
			request.dateOfRequest = dateOfRequest;
			return this;
		}

		public Builder setCarCharacteristics(Collection<CarCharacteristics> carCharacteristics) {
			request.carCharacteristics.addAll(carCharacteristics);
			return this;
		}

		public Builder setRequestStatus(RequestStatus requestStatus) {
			request.requestStatus = requestStatus;
			return this;
		}

		public Request build() {
			return request;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public LocalDateTime getDateOfRequest() {
		return dateOfRequest;
	}

	public void setDateOfRequest(LocalDateTime dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}

	public List<CarCharacteristics> getCarCharacteristics() {
		return carCharacteristics;
	}

	public void setCarCharacteristics(Collection<CarCharacteristics> carCharacteristics) {
		this.carCharacteristics.addAll(carCharacteristics);
	}
	
	public void addCarCharacteristic(CarCharacteristics carCharacteristics) {
		this.carCharacteristics.add(carCharacteristics);
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result += prime * result + ((dateOfRequest == null) ? 0 : dateOfRequest.hashCode());
		result += prime * result + ((user == null) ? 0 : user.hashCode());
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
		Request other = (Request) obj;
		if (dateOfRequest == null) {
			if (other.dateOfRequest != null)
				return false;
		} else if (!dateOfRequest.equals(other.dateOfRequest))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", user=" + user + ", pickup=" + pickup + ", destination=" + destination
				+ ", dateOfRequest=" + dateOfRequest + ", carCharacteristics=" + carCharacteristics + ", requestStatus="
				+ requestStatus + "]";
	}



}
