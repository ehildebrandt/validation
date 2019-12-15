package com.example.validation

import spock.lang.Specification

import javax.validation.ConstraintViolationException

class ValidatableSpec extends Specification {

    def "should check min value of @Size annotation on build"() {
        when: "build a Sample instance with a to short name"
        ImmutableSample.builder().name("").build()

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should check max value of @Size annotation on build"() {
        when: "build a Sample instance with a to long name"
        ImmutableSample.builder().name("123456").build()

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept valid parameters on build"() {
        when: "build a Sample instance with valid parameters"
        ImmutableSample.builder().name("12345").build()

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should check min value of @Size annotation on build with groups"() {
        when: "build a SampleWithGroups instance with a to short name"
        ImmutableSampleWithGroups.builder().name("1").build()

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept valid parameters on build with groups"() {
        when: "build a SampleWithGroups instance with valid parameters"
        ImmutableSampleWithGroups.builder().name("12345").build()

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }


}
