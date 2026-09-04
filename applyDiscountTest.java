package testCode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ApplicationCode.customer;
import ApplicationCode.applyDiscount;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class applyDiscountTest {

    @Mock
    private customer customerMock;

    private applyDiscount discountService;

    @Before
    public void setUp() {
        discountService = new applyDiscount();
    }

    @Test
    public void testCalculateDiscount_StudentWithHighOrdersAndSubtotalOver300() {
        when(customerMock.getCustomerType()).thenReturn("Student");
        when(customerMock.getPreviousOrders()).thenReturn(25);

        // Subtotal = 400
        // Student discount: 400 * 0.90 = 360
        // Subtotal > 300 discount: 360 * 0.95 = 342
        // Previous orders > 20 discount: 342 * 0.95 = 324.90
        // Expected Discount Amount = 400 - 324.90 = 75.10
        double discount = discountService.calculateDiscount(customerMock, 400.00);

        assertEquals(75.10, discount, 0.001);
    }

    @Test
    public void testCalculateDiscount_CorporateCustomerNoExtraDiscounts() {
        when(customerMock.getCustomerType()).thenReturn("Corporate");
        when(customerMock.getPreviousOrders()).thenReturn(5);

        // Subtotal = 200 (<= 300)
        // Corporate discount: 200 * 0.85 = 170
        // Expected Discount Amount = 200 - 170 = 30.00
        double discount = discountService.calculateDiscount(customerMock, 200.00);

        assertEquals(30.00, discount, 0.001);
    }

    @Test
    public void testCalculateDiscount_RegularCustomerNoDiscounts() {
        when(customerMock.getCustomerType()).thenReturn("Regular");
        when(customerMock.getPreviousOrders()).thenReturn(10);

        double discount = discountService.calculateDiscount(customerMock, 100.00);

        assertEquals(0.00, discount, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateDiscount_NullCustomer_ThrowsException() {
        discountService.calculateDiscount(null, 100.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateDiscount_NegativeSubtotal_ThrowsException() {
        discountService.calculateDiscount(customerMock, -50.00);
    }
}
