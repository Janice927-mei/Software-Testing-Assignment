import static org.junit.Assert.*;

import java.util.Arrays;

import java.util.Collection;

import org.junit.Test;

import ApplicationCode.customer;

import org.junit.runner.RunWith;

import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)

public class CustomerTest {

    private String customerID;

    private String name;

    private String emailAddress;

    private String phoneNumber;

    private String customerType;

    private int previousOrders;

    public void CustomerParameterizedTest(

            String customerID,

            String name,

            String emailAddress,

            String phoneNumber,

            String customerType,

            int previousOrders) {

        this.customerID = customerID;

        this.name = name;

        this.emailAddress = emailAddress;

        this.phoneNumber = phoneNumber;

        this.customerType = customerType;

        this.previousOrders = previousOrders;

    }

    @Parameterized.Parameters

    public static Collection<Object[]> customerData() {

        return Arrays.asList(new Object[][] {

            {

                "C001",

                "John Tan",

                "john@gmail.com",

                "0123456789",

                "Regular",

                5

            },

            {

                "C002",

                "Mary Lee",

                "mary@gmail.com",

                "0133333333",

                "VIP",

                10

            },

            {

                "C003",

                "Ali Ahmad",

                "ali@gmail.com",

                "0144444444",

                "Regular",

                2

            }

        });

    }

    @Test

    public void testCustomer() {

        customer c = new customer(

                customerID,

                name,

                emailAddress,

                phoneNumber,

                customerType,

                previousOrders

        );

        assertEquals(customerID, c.getCustomerID());

        assertEquals(name, c.getName());

        assertEquals(emailAddress, c.getEmailAddress());

        assertEquals(phoneNumber, c.getPhoneNumber());

        assertEquals(customerType, c.getCustomerType());

        assertEquals(previousOrders, c.getPreviousOrders());

    }

}
