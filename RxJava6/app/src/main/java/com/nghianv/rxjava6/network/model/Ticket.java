package com.nghianv.rxjava6.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticket {
	@SerializedName("from")
	@Expose
	private String from;
	@SerializedName("to")
	@Expose
	private String to;
	@SerializedName("flight_number")
	@Expose
	private String flightNumber;
	@SerializedName("departure")
	@Expose
	private String departure;
	@SerializedName("arrival")
	@Expose
	private String arrival;
	@SerializedName("duration")
	@Expose
	private String duration;
	@SerializedName("instructions")
	@Expose
	private String instructions;
	@SerializedName("stops")
	@Expose
	private int numberOfStops;
	@SerializedName("airline")
	@Expose
	private Airline airline;

	Price price;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public int getNumberOfStops() {
		return numberOfStops;
	}

	public void setNumberOfStops(int numberOfStops) {
		this.numberOfStops = numberOfStops;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ticket ticket = (Ticket) o;
		return flightNumber.equalsIgnoreCase(ticket.getFlightNumber());
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this.flightNumber != null ? this.flightNumber.hashCode() : 0);
		return hash;
	}
}
