package com.example.validation;

public interface Person {

    @PhoneNumberConstraint
    String getPhoneNumber();

    void setPhoneNumber(@PhoneNumberConstraint String phoneNumber);

}
