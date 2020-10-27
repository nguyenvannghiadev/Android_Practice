package com.android.haule.eventbusexample;

/**
 * Created by Hau Le on 2018-10-18.
 */
public class ReturnEvent {
    private User user;

    public ReturnEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
