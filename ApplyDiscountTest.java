package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.FileParameters;

import main.applyDiscount;
import main.customer;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class ApplyDiscountTest {

    private applyDiscount applyDiscount;

    @Before
    public void setUp() {
        applyDiscount = new applyDiscount();
    }


    // Valid cases - data driven from a text file (requirement #4)

    @Test
    @FileParameters("discount_test_data.csv")
    public void testCalculateDiscount_ValidCasesFromFile(String customerType,
                                                           int previousOrders,
                                                           double subtotal,
                                                           double expectedDiscount) {
        customer customer = new customer("C001", "Test Customer", "test@mail.com",
                "0123456789", customerType, previousOrders);

        double actualDiscount = applyDiscount.calculateDiscount(customer, subtotal);

        assertEquals(expectedDiscount, actualDiscount, 0.01);
    }

    
    @Test
    @Parameters({
            "Student, 5, 200.0, 20.0",
            "Corporate, 5, 200.0, 30.0",
            "Regular, 5, 200.0, 0.0"
    })
    public void testCalculateDiscount_ValidCasesInline(String customerType,
                                                         int previousOrders,
                                                         double subtotal,
                                                         double expectedDiscount) {
        customer customer = new customer("C002", "Inline Customer", "inline@mail.com",
                "0123456789", customerType, previousOrders);

        double actualDiscount = applyDiscount.calculateDiscount(customer, subtotal);

        assertEquals(expectedDiscount, actualDiscount, 0.01);
    }


    // Invalid

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateDiscount_NullCustomer_ThrowsException() {
        applyDiscount.calculateDiscount(null, 100.0);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"-1.0", "-100.50"})
    public void testCalculateDiscount_NegativeSubtotal_ThrowsException(double subtotal) {
        customer customer = new customer("C003", "Neg Customer", "neg@mail.com",
                "0123456789", "Regular", 0);

        applyDiscount.calculateDiscount(customer, subtotal);
    }
}
