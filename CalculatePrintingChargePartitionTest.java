import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CalculatePrintingChargePartitionTest {

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

        // Standard valid defaults for order constraints
        when(printerServiceMock.isPrinterAvailable(anyString(), anyString())).thenReturn(true);
        when(orderMock.getCustomer()).thenReturn(customerMock);
        when(orderMock.getNumberOfPages()).thenReturn(10);
        when(orderMock.getNumberOfCopies()).thenReturn(1);
        when(orderMock.getBindingOption()).thenReturn(null);
        when(orderMock.hasLamination()).thenReturn(false);
        when(orderMock.hasExpressPrinting()).thenReturn(false);
    }

    // PARTITION: PAPER SIZE & RATE LOOKUPS

    @Test
    public void testPartition_ValidPaper_A4_BW_SingleSided() {
        when(orderMock.getPaperSize()).thenReturn("A4");
        when(orderMock.getPrintType()).thenReturn("Black & White");
        when(orderMock.getPrintingSide()).thenReturn("Single-sided");
        when(discountServiceMock.calculateDiscount(any(), anyDouble())).thenReturn(0.0);

        // Rate = 0.20 | Charge = 0.20 * 10 * 1 = 2.00
        double total = calculator.calculateTotal(orderMock);
        assertEquals(2.00, total, 0.001);
    }

    @Test
    public void testPartition_ValidPaper_A3_Colour_DoubleSided() {
        when(orderMock.getPaperSize()).thenReturn("A3");
        when(orderMock.getPrintType()).thenReturn("Colour");
        when(orderMock.getPrintingSide()).thenReturn("Double-sided");
        when(orderMock.getNumberOfCopies()).thenReturn(2);
        when(discountServiceMock.calculateDiscount(any(), anyDouble())).thenReturn(0.0);

        // Rate = 1.40 | Charge = 1.40 * 10 * 2 = 28.00
        double total = calculator.calculateTotal(orderMock);
        assertEquals(28.00, total, 0.001);
    }

    @Test
    public void testPartition_ValidPaper_A5_BW_DoubleSided() {
        when(orderMock.getPaperSize()).thenReturn("A5");
        when(orderMock.getPrintType()).thenReturn("Black & White");
        when(orderMock.getPrintingSide()).thenReturn("Double-sided");
        when(discountServiceMock.calculateDiscount(any(), anyDouble())).thenReturn(0.0);

        // Rate = 0.13 | Charge = 0.13 * 10 * 1 = 1.30
        double total = calculator.calculateTotal(orderMock);
        assertEquals(1.30, total, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPartition_InvalidPaperSize_B5() {
        when(orderMock.getPaperSize()).thenReturn("B5"); // Invalid partition
        when(orderMock.getPrintType()).thenReturn("Colour");
        when(orderMock.getPrintingSide()).thenReturn("Single-sided");

        calculator.calculateTotal(orderMock);
    }

    // PARTITION: OPTIONAL SERVICES & DISCOUNTS

    @Test
    public void testPartition_AllServicesAndDiscount() {
        when(orderMock.getPaperSize()).thenReturn("A4");
        when(orderMock.getPrintType()).thenReturn("Colour");
        when(orderMock.getPrintingSide()).thenReturn("Single-sided");
        when(orderMock.getNumberOfPages()).thenReturn(10);
        when(orderMock.getNumberOfCopies()).thenReturn(2);

        // Service Partitions
        when(orderMock.getBindingOption()).thenReturn("Spiral"); // +8.00
        when(orderMock.hasLamination()).thenReturn(true);       // +1.50 * 10 * 2 = +30.00
        when(orderMock.hasExpressPrinting()).thenReturn(true);  // +20.00

        // Base Charge: 0.80 * 10 * 2 = 16.00
        // Service Charges: 8.00 + 30.00 + 20.00 = 58.00
        // Subtotal = 74.00
        when(discountServiceMock.calculateDiscount(customerMock, 74.00)).thenReturn(10.00);

        double total = calculator.calculateTotal(orderMock);
        assertEquals(64.00, total, 0.001);

        verify(orderMock).setBasePrintingCharge(16.00);
        verify(orderMock).setAdditionalServiceCharges(58.00);
        verify(orderMock).setDiscountAmount(10.00);
        verify(orderMock).setTotalPrintingCharge(64.00);
    }

    // PARTITION: PRINTER AVAILABILITY

    @Test(expected = IllegalStateException.class)
    public void testPartition_PrinterUnavailable() {
        when(orderMock.getPaperSize()).thenReturn("A4");
        when(orderMock.getPrintType()).thenReturn("Colour");

        // Availability Partition: Unavailable
        when(printerServiceMock.isPrinterAvailable("A4", "Colour")).thenReturn(false);

        calculator.calculateTotal(orderMock);
    }
}
