import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ApplicationCode.printerAvailability;
import ApplicationCode.applyDiscount;
import ApplicationCode.printOrder;
import ApplicationCode.calculatePrintingCharge;
import ApplicationCode.customer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CalculatePrintingChargeBVATest {

    @Mock
    private printerAvailability printerServiceMock;

    @Mock
    private applyDiscount discountServiceMock;

    @Mock
    private printOrder orderMock;

    @Mock
    private customer customerMock;

    private calculatePrintingCharge calculator;

    @Before
    public void setUp() {
        calculator = new calculatePrintingCharge(printerServiceMock, discountServiceMock);

        // Standard valid defaults so boundary tests focus on pages and copies
        when(printerServiceMock.isPrinterAvailable(anyString(), anyString())).thenReturn(true);
        when(orderMock.getCustomer()).thenReturn(customerMock);
        when(orderMock.getPaperSize()).thenReturn("A4");
        when(orderMock.getPrintType()).thenReturn("Black & White");
        when(orderMock.getPrintingSide()).thenReturn("Single-sided");
        when(orderMock.getBindingOption()).thenReturn(null);
        when(orderMock.hasLamination()).thenReturn(false);
        when(orderMock.hasExpressPrinting()).thenReturn(false);
        when(discountServiceMock.calculateDiscount(any(), anyDouble())).thenReturn(0.0);
    }

    // ==========================================
    // BVA - NUMBER OF PAGES (Valid: 1 to 500)
    // ==========================================

    @Test(expected = IllegalArgumentException.class)
    public void testPages_Boundary_0_InvalidLow() {
        when(orderMock.getNumberOfPages()).thenReturn(0);
        when(orderMock.getNumberOfCopies()).thenReturn(1);

        calculator.calculateTotal(orderMock);
    }

    @Test
    public void testPages_Boundary_1_MinValid() {
        when(orderMock.getNumberOfPages()).thenReturn(1);
        when(orderMock.getNumberOfCopies()).thenReturn(1);

        // Base Charge: 0.20 * 1 page * 1 copy = 0.20
        double total = calculator.calculateTotal(orderMock);
        assertEquals(0.20, total, 0.001);
    }

    @Test
    public void testPages_Boundary_500_MaxValid() {
        when(orderMock.getNumberOfPages()).thenReturn(500);
        when(orderMock.getNumberOfCopies()).thenReturn(1);

        // Base Charge: 0.20 * 500 pages * 1 copy = 100.00
        double total = calculator.calculateTotal(orderMock);
        assertEquals(100.00, total, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPages_Boundary_501_InvalidHigh() {
        when(orderMock.getNumberOfPages()).thenReturn(501);
        when(orderMock.getNumberOfCopies()).thenReturn(1);

        calculator.calculateTotal(orderMock);
    }

    // ==========================================
    // BVA - NUMBER OF COPIES (Valid: 1 to 1000)
    // ==========================================

    @Test(expected = IllegalArgumentException.class)
    public void testCopies_Boundary_0_InvalidLow() {
        when(orderMock.getNumberOfPages()).thenReturn(10);
        when(orderMock.getNumberOfCopies()).thenReturn(0);

        calculator.calculateTotal(orderMock);
    }

    @Test
    public void testCopies_Boundary_1_MinValid() {
        when(orderMock.getNumberOfPages()).thenReturn(10);
        when(orderMock.getNumberOfCopies()).thenReturn(1);

        // Base Charge: 0.20 * 10 pages * 1 copy = 2.00
        double total = calculator.calculateTotal(orderMock);
        assertEquals(2.00, total, 0.001);
    }

    @Test
    public void testCopies_Boundary_1000_MaxValid() {
        when(orderMock.getPaperSize()).thenReturn("A5");
        when(orderMock.getNumberOfPages()).thenReturn(1);
        when(orderMock.getNumberOfCopies()).thenReturn(1000);

        // Base Charge: 0.15 * 1 page * 1000 copies = 150.00
        double total = calculator.calculateTotal(orderMock);
        assertEquals(150.00, total, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopies_Boundary_1001_InvalidHigh() {
        when(orderMock.getNumberOfPages()).thenReturn(10);
        when(orderMock.getNumberOfCopies()).thenReturn(1001);

        calculator.calculateTotal(orderMock);
    }
}
