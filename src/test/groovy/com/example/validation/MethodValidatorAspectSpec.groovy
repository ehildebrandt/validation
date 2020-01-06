package com.example.validation

import org.assertj.core.util.Lists
import spock.lang.Specification

import javax.validation.ConstraintViolationException

class MethodValidatorAspectSpec extends Specification {

    def "should throw an ConstraintViolationException in case one of the parameters violate the JSR 303 annotations on implementation class"() {
        given: "a new Foo instance"
        Foo foo = new Foo();

        when: "we call the foo operation with a null parameter"
        foo.foo(null)

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept parameters in case they comply with the JSR 303 annotations on implementation class"() {
        given: "a new Foo instance"
        Foo foo = new Foo();

        when: "we call the foo operation with a valid string"
        foo.foo("12345")

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should throw an ConstraintViolationException in case one of the parameters violate custom JSR 303 annotations on implementation class"() {
        given: "a new Contact instance"
        Contact contact = new Contact();

        when: "we call the setter with an invalid parameter"
        contact.setPhoneNumber("abc")

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept parameters in case they comply with custom JSR 303 annotations on implementation class"() {
        given: "a new Contact instance"
        Contact contact = new Contact();

        when: "we call the setter with a valid parameter"
        contact.setPhoneNumber("123456789")

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should throw an ConstraintViolationException in case one of the parameters violate the JSR 303 annotations on interface"() {
        given: "a new Bar instance"
        Bar bar = new BarImpl();

        when: "we call the bar operation with a null parameter"
        bar.bar(null)

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept parameters in case they comply with the JSR 303 annotations on interface"() {
        given: "a new Bar instance"
        Bar bar = new BarImpl();

        when: "we call the bar operation with a list of valid strings"
        bar.bar(List.of("test"))

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should throw an ConstraintViolationException in case one of the parameters violate custom JSR 303 annotations on interface"() {
        given: "a new Bar instance"
        Person person = new PersonImpl();

        when: "we call the setter with an invalid parameter"
        person.setPhoneNumber("abc")

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept parameters in case they comply with custom JSR 303 annotations on interface"() {
        given: "a new Bar instance"
        Person person = new PersonImpl();

        when: "we call the setter with a valid parameter"
        person.setPhoneNumber("123456789")

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should throw an ConstraintViolationException in case one of the parameters violate the JSR 303 annotations on constructor"() {
        when: "we create an new FooBar instance with a null parameter"
        new FooBar(null)

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept parameters in case they comply with the JSR 303 annotations on constructor"() {
        when: "we create an new FooBar instance with a valid integer as parameter"
        new FooBar(1)

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should throw an ConstraintViolationException in case one of the parameters violate custom JSR 303 annotations on constructor"() {
        when: "we create an new Person instance with a invalid phone number as parameter"
        new PersonImpl("abc")

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept parameters in case they comply with custom JSR 303 annotations on constructor"() {
        when: "we create an new Person instance with a valid phone number as parameter"
        new PersonImpl("123456789")

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should throw an ConstraintViolationException in case one of the parameters violate the JSR 303 annotations on constructor after creation"() {
        when: "we create an new MyList instance with no elements"
        new MyList(Lists.emptyList())

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept parameters in case they comply with the JSR 303 annotations on constructor after creation"() {
        when: "we create an new MyList instance with no elements"
        new MyList(["test"])

        then: "we except a ConstraintViolationException to be thrown"
        notThrown(ConstraintViolationException)
    }

    def "should throw an ConstraintViolationException in case one of the return value violate the JSR 303 annotations on method after execution"() {
        given: "an instance of other list"
        OtherList otherList = new OtherList()

        when: "we create an empty MyList instance with no elements"
        otherList.createEmptyList()

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should not validate in case one of the return value violate the JSR 303 annotations on a static method after execution"() {
        when: "we create an empty MyList instance with no elements"
        OtherList.createEmptyListStatic()

        then: "we except a ConstraintViolationException to be thrown"
        notThrown(ConstraintViolationException)
    }

    def "should accept return value in case they comply with the JSR 303 annotations on method after execution"() {
        given:
        OtherList otherList = new OtherList(List.of("test"))

        when: "we copy MyList instance with no elements"
        otherList.copy()

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

    def "should throw an ConstraintViolationException in case one of the parameters violate the JSR 303 annotations on implementation class with groups"() {
        given: "a new Foo instance"
        FooWithGroups foo = new FooWithGroups();

        when: "we call the foo operation with a null parameter"
        foo.foo(null)

        then: "we except a ConstraintViolationException to be thrown"
        thrown(ConstraintViolationException)
    }

    def "should accept parameters in case they comply with the JSR 303 annotations on implementation class with groups"() {
        given: "a new Foo instance"
        FooWithGroups foo = new FooWithGroups();

        when: "we call the foo operation with a valid string"
        foo.foo("12345")

        then: "we expect that the ConstraintViolationException was not thrown"
        notThrown(ConstraintViolationException)
    }

}
