package test;

import org.junit.Before;
import org.junit.Test;

import main.generateInvoice;
import main.customer;
import main.printOrder;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link GenerateInvoice}.
 */
public class GenerateInvoiceTest {

    private generateInvoice generateInvoice;

    @Before
    public void setUp() {
        generateInvoice = new generateInvoice();
    }

    @Test
    public void testGenerate_ContainsAllCustomerAndOrderDetails() {
        customer customer = new customer("C400", "Dana Lee", "dana@mail.com",
                "0199999999", "Student", 3);
        printOrder order = new printOrder(customer, "Colour", "A4", "Single-sided",
                5, 2, "Staple", false, false);
        order.setBasePrintingCharge(8.00);
        order.setAdditionalServiceCharges(2.00);
        order.setDiscountAmount(1.00);
        order.setTotalPrintingCharge(9.00);

        String invoice = generateInvoice.generate(order);

        assertTrue(invoice.contains("C400"));
        assertTrue(invoice.contains("Dana Lee"));
        assertTrue(invoice.contains("dana@mail.com"));
        assertTrue(invoice.contains("0199999999"));
        assertTrue(invoice.contains("Colour"));
        assertTrue(invoice.contains("A4"));
        assertTrue(invoice.contains("Single-sided"));
        assertTrue(invoice.contains("RM8.00"));
        assertTrue(invoice.contains("RM2.00"));
        assertTrue(invoice.contains("RM1.00"));
        assertTrue(invoice.contains("RM9.00"));
    }

    @Test
    public void testGenerate_ZeroChargeOrder_FormatsCorrectly() {
        customer customer = new customer("C401", "Zero Case", "zero@mail.com",
                "0100000000", "Regular", 0);
        printOrder order = new printOrder(customer, "Black & White", "A5", "Double-sided",
                1, 1, null, false, false);
        order.setBasePrintingCharge(0.13);
        order.setAdditionalServiceCharges(0.00);
        order.setDiscountAmount(0.00);
        order.setTotalPrintingCharge(0.13);

        String invoice = generateInvoice.generate(order);

        assertTrue(invoice.contains("RM0.13"));
        assertTrue(invoice.contains("RM0.00"));
    }

    @Test(expected = NullPointerException.class)
    public void testGenerate_NullOrder_ThrowsException() {
        generateInvoice.generate(null);
    }
}
