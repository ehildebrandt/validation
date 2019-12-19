package com.example.validation

import spock.lang.Specification

import javax.validation.ConstraintViolationException

class MethodValidatorAspectSpec extends Specification {

    def "should check @NotNull annotation on implementation"() {
        given: "a new Foo instance"
        Foo foo = new Foo();

        when: "we call the foo operation with a null parameter"
        foo.foo(null)

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should check min value of @Size annotation on implementation"() {
        given: "a new Foo instance"
        Foo foo = new Foo();

        when: "we call the foo operation with an empty string"
        foo.foo("")

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should check max value of @Size annotation on implementation"() {
        given: "a new Foo instance"
        Foo foo = new Foo();

        when: "we call the foo operation with a to long string"
        foo.foo("123456")

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept valid parameters on implementation"() {
        given: "a new Foo instance"
        Foo foo = new Foo();

        when: "we call the foo operation with a valid string"
        foo.foo("12345")

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should check @NotNull annotation on interface"() {
        given: "a new Bar instance"
        Bar bar = new BarImpl();

        when: "we call the bar operation with a null parameter"
        bar.bar(null)

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should check @NotEmpty annotation on interface"() {
        given: "a new Bar instance"
        Bar bar = new BarImpl();

        when: "we call the bar operation with an empty list"
        bar.bar(Collections.emptyList())

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept valid parameters on interface"() {
        given: "a new Bar instance"
        Bar bar = new BarImpl();

        when: "we call the bar operation with a list of valid strings"
        bar.bar(List.of("test"))

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should check @NotNull annotation on constructor"() {
        when: "we create an new FooBar instance with a null parameter"
        new FooBar(null)

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should check @Min annotation on constructor"() {
        when: "we create an new FooBar instance with a to small integer as parameter"
        new FooBar(0)

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept valid parameters on constructor"() {
        when: "we create an new FooBar instance with a valid integer as parameter"
        new FooBar(1)

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should check @NotEmpty annotation on constructor after creation"() {
        when: "we create an new MyList instance with no elements"
        new MyList()

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept valid parameters on constructor after creation"() {
        when: "we create an new MyList instance with no elements"
        new MyList(["test"])

        then: "we except a ConstraintViolationException to be thrown"
        notThrown(ConstraintViolationException)
    }

    def "should check @NotEmpty annotation on methode after execution"() {
        when: "we create an empty MyList instance with no elements"
        MyList.createEmptyList()

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should check @NotNull annotation on methode after execution"() {
        given:
        MyList myList = new MyList(List.of("test"))

        when: "we copy MyList instance with no elements"
        myList.copy()

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should check @NotNull annotation on implementation with groups"() {
        given: "a new Foo instance"
        FooWithGroups foo = new FooWithGroups();

        when: "we call the foo operation with a null parameter"
        foo.foo(null)

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should check min value of @Size annotation on implementation with groups"() {
        given: "a new Foo instance"
        FooWithGroups foo = new FooWithGroups();

        when: "we call the foo operation with an empty string"
        foo.foo("")

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should accept valid parameters on implementation with groups"() {
        given: "a new Foo instance"
        FooWithGroups foo = new FooWithGroups();

        when: "we call the foo operation with a valid string"
        foo.foo("12345")

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

}
