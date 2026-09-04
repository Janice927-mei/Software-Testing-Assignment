package testCode;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
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
    private customer customerMock; // Declared explicitly to fix the variable resolution error

    private calculatePrintingCharge calculator;

    @Before
    public void setUp() {
        calculator = new calculatePrintingCharge(printerServiceMock, discountServiceMock);

        // Default valid behavior across tests
        when(printerServiceMock.isPrinterAvailable(anyString(), anyString())).thenReturn(true);
        when(orderMock.getCustomer()).thenReturn(customerMock);
        
        // Default valid page/copy count so validateOrder() passes unless specifically testing boundaries
        when(orderMock.getNumberOfPages()).thenReturn(10);
        when(orderMock.getNumberOfCopies()).thenReturn(1);
        
        // Default optional services
        when(orderMock.getBindingOption()).thenReturn(null);
        when(orderMock.hasLamination()).thenReturn(false);
        when(orderMock.hasExpressPrinting()).thenReturn(false);
    }

    // ==========================================
    // 1. BOUNDARY VALUE ANALYSIS (BVA) - PAGES
    // ==========================================

    @Test(expected = IllegalArgumentException.class)
    public void testPages_Boundary_0_Invalid() {
        when(orderMock.getPaperSize()).thenReturn("A4");
        when(orderMock.getPrintType()).thenReturn("Black & White");
        when(orderMock.getNumberOfPages()).thenReturn(0); // Invalid (below min)

        calculator.calculateTotal(orderMock);
    }

    @Test
    public void testPages_Boundary_1_MinValid() {
        when(orderMock.getPaperSize()).thenReturn("A4");
        when(orderMock.getPrintType()).thenReturn("Black & White");
        when(orderMock.getPrintingSide()).thenReturn("Single-sided");
        when(orderMock.getNumberOfPages()).thenReturn(1); // Min Boundary
        when(orderMock.getNumberOfCopies()).thenReturn(1);
        when(discountServiceMock.calculateDiscount(any(), anyDouble())).thenReturn(0.0);

        double total = calculator.calculateTotal(orderMock);
        assertEquals(0.20, total, 0.001);
    }

    @Test
    public void testPages_Boundary_500_MaxValid() {
        when(orderMock.getPaperSize()).thenReturn("A4");
        when(orderMock.getPrintType()).thenReturn("Black & White");
        when(orderMock.getPrintingSide()).thenReturn("Single-sided");
        when(orderMock.getNumberOfPages()).thenReturn(500); // Max Boundary
        when(orderMock.getNumberOfCopies()).thenReturn(1);
        when(discountServiceMock.calculateDiscount(any(), anyDouble())).thenReturn(0.0);

        double total = calculator.calculateTotal(orderMock);
        assertEquals(100.00, total, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPages_Boundary_501_Invalid() {
        when(orderMock.getPaperSize()).thenReturn("A4");
        when(orderMock.getPrintType()).thenReturn("Black & White");
        when(orderMock.getNumberOfPages()).thenReturn(501); // Invalid (above max)

        calculator.calculateTotal(orderMock);
    }

    // ==========================================
    // 2. BOUNDARY VALUE ANALYSIS (BVA) - COPIES
    // ==========================================

    @Test(expected = IllegalArgumentException.class)
    public void testCopies_Boundary_0_Invalid() {
        when(orderMock.getPaperSize()).thenReturn("A4");
        when(orderMock.getPrintType()).thenReturn("Black & White");
        when(orderMock.getNumberOfPages()).thenReturn(10);
        when(orderMock.getNumberOfCopies()).thenReturn(0); // Invalid (below min)

        calculator.calculateTotal(orderMock);
    }

    @Test
    public void testCopies_Boundary_1000_MaxValid() {
        when(orderMock.getPaperSize()).thenReturn("A5");
        when(orderMock.getPrintType()).thenReturn("Black & White");
        when(orderMock.getPrintingSide()).thenReturn("Single-sided");
        when(orderMock.getNumberOfPages()).thenReturn(1);
        when(orderMock.getNumberOfCopies()).thenReturn(1000); // Max Boundary
        when(discountServiceMock.calculateDiscount(any(), anyDouble())).thenReturn(0.0);

        double total = calculator.calculateTotal(orderMock);
        assertEquals(150.00, total, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopies_Boundary_1001_Invalid() {
        when(orderMock.getPaperSize()).thenReturn("A4");
        when(orderMock.getPrintType()).thenReturn("Black & White");
        when(orderMock.getNumberOfPages()).thenReturn(10);
        when(orderMock.getNumberOfCopies()).thenReturn(1001); // Invalid (above max)

        calculator.calculateTotal(orderMock);
    }

}
