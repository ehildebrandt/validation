package com.example.validation;

public class Contact {

    private String phoneNumber;

    @PhoneNumberConstraint
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@PhoneNumberConstraint String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
