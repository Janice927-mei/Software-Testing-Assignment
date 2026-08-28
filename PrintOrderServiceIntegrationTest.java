package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import main.printerAvailability;
import main.payment;
import main.email_Invoice;
import main.customer;
import main.printOrder;
import main.calculatePrintingCharge;
import main.applyDiscount;
import main.generateInvoice;
import main.PrintOrderService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This is run AFTER all individual units (ApplyDiscount,
 * CalculatePrintingCharge, GenerateInvoice, AddNewCustomer/ReadCustomer,
 * Payment, EmailInvoice, PrinterAvailability) have their own unit tests
 * passing. Here, the REAL ApplyDiscount, CalculatePrintingCharge and
 * GenerateInvoice objects are wired together to prove they collaborate
 * correctly; only the genuinely external systems - PrinterAvailability,
 * Payment and EmailInvoice - are replaced with Mockito test doubles.
 */
@RunWith(JUnitParamsRunner.class)
public class PrintOrderServiceIntegrationTest {

    @Mock
    private printerAvailability printerService;
    @Mock
    private payment paymentService;
    @Mock
    private email_Invoice emailService;

    private PrintOrderService PrintOrderService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        applyDiscount applyDiscount = new applyDiscount();
        calculatePrintingCharge chargeCalculator =
                new calculatePrintingCharge(printerService, applyDiscount);
        generateInvoice invoiceGenerator = new generateInvoice();

        PrintOrderService = new PrintOrderService(chargeCalculator, paymentService,
                invoiceGenerator, emailService);
    }

    @Test
    public void testProcessOrder_SuccessfulPayment_SendsInvoiceAndUpdatesStatus() {
        when(printerService.isPrinterAvailable("A4", "Black & White")).thenReturn(true);
        when(paymentService.makePayment(anyDouble(), eq("Credit Card"))).thenReturn(true);
        when(emailService.sendInvoice(anyString(), anyString())).thenReturn(true);

        customer customer = new customer("C500", "Integration Tester", "integ@mail.com",
                "0177777777", "Student", 25);
        printOrder order = new printOrder(customer, "Black & White", "A4", "Single-sided",
                10, 2, "Comb", false, false);

        String invoice = PrintOrderService.processOrder(order, "Credit Card");

        assertEquals("Paid", order.getPaymentStatus());
        assertEquals("Completed", order.getOrderStatus());
        assertEquals(7.70, order.getTotalPrintingCharge(), 0.05);

        assertNotNull(invoice);
        assertTrue(invoice.contains("C500"));
        assertTrue(invoice.contains("Integration Tester"));

        verify(printerService, times(1)).isPrinterAvailable("A4", "Black & White");
        verify(paymentService, times(1)).makePayment(anyDouble(), eq("Credit Card"));
        verify(emailService, times(1)).sendInvoice(eq("integ@mail.com"), anyString());
    }

    @Test
    public void testProcessOrder_FailedPayment_DoesNotSendInvoice() {
        when(printerService.isPrinterAvailable("A4", "Black & White")).thenReturn(true);
        when(paymentService.makePayment(anyDouble(), anyString())).thenReturn(false);

        customer customer = new customer("C501", "Failed Payment", "fail@mail.com",
                "0166666666", "Regular", 0);
        printOrder order = new printOrder(customer, "Black & White", "A4", "Single-sided",
                1, 1, null, false, false);

        String invoice = PrintOrderService.processOrder(order, "Cash");

        assertEquals("Unpaid", order.getPaymentStatus());
        assertEquals("Payment Failed", order.getOrderStatus());
        assertNotNull(invoice);

        verify(emailService, never()).sendInvoice(anyString(), anyString());
    }

    @Test(expected = IllegalStateException.class)
    public void testProcessOrder_PrinterUnavailable_ThrowsExceptionBeforePayment() {
        when(printerService.isPrinterAvailable(anyString(), anyString())).thenReturn(false);

        customer customer = new customer("C502", "No Printer", "noprint@mail.com",
                "0155555555", "Regular", 0);
        printOrder order = new printOrder(customer, "Black & White", "A4", "Single-sided",
                1, 1, null, false, false);

        try {
            PrintOrderService.processOrder(order, "Cash");
        } finally {
            verify(paymentService, never()).makePayment(anyDouble(), anyString());
            verify(emailService, never()).sendInvoice(anyString(), anyString());
        }
    }
}
