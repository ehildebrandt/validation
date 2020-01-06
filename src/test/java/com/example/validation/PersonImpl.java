package com.example.validation;

public class PersonImpl implements Person {

    private String phoneNumber;

    public PersonImpl() {
    }

    public PersonImpl(@PhoneNumberConstraint String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    @PhoneNumberConstraint
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(@PhoneNumberConstraint String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
