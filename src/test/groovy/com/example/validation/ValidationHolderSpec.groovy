package com.example.validation

import spock.lang.Specification

import java.lang.reflect.InvocationTargetException

class ValidationHolderSpec extends Specification {

    def "should protect singleton validator instance"() {
        given: "we requested the first validator instance"
        ValidatorHolder.getValidator();

        when: "we try "
        def constructor = ValidatorHolder.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();

        then:
        InvocationTargetException e = thrown()
        e.cause.message == "This class can only be access through getValidator()"
    }

}
