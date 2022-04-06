package com.nghianv.rxjava6.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Airline {
   @SerializedName("id")
   @Expose
   int id;
   @SerializedName("name")
   @Expose
   String name;
   @SerializedName("logo")
   @Expose
   String logo;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getLogo() {
      return logo;
   }

   public void setLogo(String logo) {
      this.logo = logo;
   }
}
