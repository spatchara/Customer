package com.digitaltechcamp.customer.util;

import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Data
class Person{
    private String firstName;
    private String lastName;
    private String test;

    public Person(String firstName,String lastName, String test){
        this.firstName=firstName;
        this.lastName=lastName;
        this.test=test;
    }
}

public class CalculatorTest {

    private final Calculator calculator = new Calculator();
    private final Person person = new Person("Jane","Doy","test");

    @Test
    void groupAssertions() {
        assertAll("person",
                () -> assertEquals("Jane",person.getFirstName()),
                () ->assertEquals("Doy",person.getLastName()),
                () ->assertEquals("test", person.getTest()));
    }

    @Test
    void testAddMethod(){
        assertEquals(2,calculator.add(1,1));
    }

    @Test
    void testMultiplyMethod() {
        assertEquals(20,calculator.multiply(4,5));
    }

    @Test
    void testDivideMethod() {
        assertEquals(4,calculator.divide(20,5));
        //assertEquals(22,calculator.divide(20,5));
    }
}