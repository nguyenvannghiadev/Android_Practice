package com.nghianv.rxjava6.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {
   @SerializedName("price")
   @Expose
   float price;
   @SerializedName("seats")
   @Expose
   String seats;
   @SerializedName("currency")
   @Expose
   String currency;
   @SerializedName("flight_number")
   @Expose
   String flightNumber;
   @SerializedName("from")
   @Expose
   String from;
   @SerializedName("to")
   @Expose
   String to;

   public float getPrice() {
      return price;
   }

   public void setPrice(float price) {
      this.price = price;
   }

   public String getSeats() {
      return seats;
   }

   public void setSeats(String seats) {
      this.seats = seats;
   }

   public String getCurrency() {
      return currency;
   }

   public void setCurrency(String currency) {
      this.currency = currency;
   }

   public String getFlightNumber() {
      return flightNumber;
   }

   public void setFlightNumber(String flightNumber) {
      this.flightNumber = flightNumber;
   }

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
}
