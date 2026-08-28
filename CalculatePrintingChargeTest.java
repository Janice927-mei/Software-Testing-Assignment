package test;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.FileParameters;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import main.printerAvailability;
import main.applyDiscount;
import main.calculatePrintingCharge;
import main.customer;
import main.printOrder;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(JUnitParamsRunner.class)
public class CalculatePrintingChargeTest {

    @Mock
    private printerAvailability printerService;

    @Mock
    private applyDiscount discountService;

    private calculatePrintingCharge calculator;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        calculator = new calculatePrintingCharge(printerService, discountService);
    }

    // Valid

    @Test
    @FileParameters("printing_rate_test_data.csv")
    public void testCalculateTotal_BaseRates_FromFile(String paperSize,
                                                        String printType,
                                                        String printingSide,
                                                        double expectedRate) {
        when(printerService.isPrinterAvailable(paperSize, printType)).thenReturn(true);
        when(discountService.calculateDiscount(any(customer.class), anyDouble())).thenReturn(0.0);

        customer customer = new customer("C010", "Rate Tester", "rate@mail.com",
                "0123456789", "Regular", 0);
        printOrder order = new printOrder(customer, printType, paperSize, printingSide,
                1, 1, null, false, false);

        double total = calculator.calculateTotal(order);

        assertEquals(expectedRate, total, 0.01);
        verify(printerService, times(1)).isPrinterAvailable(paperSize, printType);
    }

    // Valid

    @Test
    @Parameters({
            "Staple, 2.00",
            "Comb, 5.00",
	            "Spiral, 8.00"
	    })
	    public void testCalculateTotal_BindingOptions(String binding, double expectedServiceCharge) {
	        when(printerService.isPrinterAvailable("A4", "Black & White")).thenReturn(true);
	        when(discountService.calculateDiscount(any(customer.class), anyDouble())).thenReturn(0.0);
	
	        customer customer = new customer("C011", "Binding Tester", "bind@mail.com",
	                "0123456789", "Regular", 0);
	        printOrder order = new printOrder(customer, "Black & White", "A4", "Single-sided",
	                1, 1, binding, false, false);
	
	        double total = calculator.calculateTotal(order);
	
	        assertEquals(0.20 + expectedServiceCharge, total, 0.01);
	    }
	
	    @Test
	    public void testCalculateTotal_ExpressPrinting_AddsTwentyRinggit() {
	        when(printerService.isPrinterAvailable("A4", "Black & White")).thenReturn(true);
	        when(discountService.calculateDiscount(any(customer.class), anyDouble())).thenReturn(0.0);
	
	        customer customer = new customer("C012", "Express Tester", "exp@mail.com",
	                "0123456789", "Regular", 0);
	        printOrder order = new printOrder(customer, "Black & White", "A4", "Single-sided",
	                1, 1, null, false, true);
	
	        double total = calculator.calculateTotal(order);
	
	        assertEquals(20.20, total, 0.01);
	    }
	
	    @Test
	    public void testCalculateTotal_Lamination_ChargedPerPageAndCopy() {
	        when(printerService.isPrinterAvailable("A4", "Black & White")).thenReturn(true);
	        when(discountService.calculateDiscount(any(customer.class), anyDouble())).thenReturn(0.0);
	
	        customer customer = new customer("C013", "Lamination Tester", "lam@mail.com",
	                "0123456789", "Regular", 0);
	        printOrder order = new printOrder(customer, "Black & White", "A4", "Single-sided",
	                2, 3, null, true, false);
	
	        double total = calculator.calculateTotal(order);
	
	        assertEquals(10.20, total, 0.01);
	    }
	
	    // Valid

	    @Test
	    public void testCalculateTotal_AppliesDiscountFromDiscountService() {
	        when(printerService.isPrinterAvailable("A4", "Black & White")).thenReturn(true);
	        when(discountService.calculateDiscount(any(customer.class), anyDouble())).thenReturn(5.0);
	
	        customer customer = new customer("C014", "Discount Tester", "disc@mail.com",
	                "0123456789", "Regular", 0);
	        printOrder order = new printOrder(customer, "Black & White", "A4", "Single-sided",
	                10, 10, null, false, false);
	
	        double total = calculator.calculateTotal(order);
	
	        // base = 0.20 * 10 * 10 = 20.00 ; discount (mocked) = 5.00
	        assertEquals(15.00, total, 0.01);
	        verify(discountService).calculateDiscount(eq(customer), eq(20.00));
	    }
	

	    // Test double

	    @Test(expected = IllegalStateException.class)
	    public void testCalculateTotal_PrinterUnavailable_ThrowsException() {
	        when(printerService.isPrinterAvailable(anyString(), anyString())).thenReturn(false);
	
	        customer customer = new customer("C015", "Unavailable Tester", "unavail@mail.com",
	                "0123456789", "Regular", 0);
	        printOrder order = new printOrder(customer, "Black & White", "A4", "Single-sided",
	                1, 1, null, false, false);
	
	        calculator.calculateTotal(order);
	    }
	
	    // Invalid
	    @Test(expected = IllegalArgumentException.class)
	    @FileParameters("invalid_order_data.csv")
	    public void testCalculateTotal_InvalidPagesOrCopies_ThrowsException(int numberOfPages,
	                                                                         int numberOfCopies) {
	        when(printerService.isPrinterAvailable(anyString(), anyString())).thenReturn(true);
	
	        customer customer = new customer("C016", "Invalid Tester", "invalid@mail.com",
	                "0123456789", "Regular", 0);
	        printOrder order = new printOrder(customer, "Black & White", "A4", "Single-sided",
                numberOfPages, numberOfCopies, null, false, false);

        calculator.calculateTotal(order);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateTotal_InvalidPrintingOptions_ThrowsException() {
        when(printerService.isPrinterAvailable("A2", "Black & White")).thenReturn(true);

        customer customer = new customer("C017", "Bad Options Tester", "bad@mail.com",
                "0123456789", "Regular", 0);
        printOrder order = new printOrder(customer, "Black & White", "A2", "Single-sided",
                1, 1, null, false, false);

        calculator.calculateTotal(order);
    }
}
