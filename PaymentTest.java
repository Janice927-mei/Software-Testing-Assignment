package test;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import main.payment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Its own (stub) behaviour is tested directly, and its use
 * as a Mockito test double is demonstrated for completeness.
 */
@RunWith(JUnitParamsRunner.class)
public class PaymentTest {

    @Test
    public void testMakePayment_DefaultStubImplementation_ReturnsFalse() {
        payment payment = new payment();

        assertFalse(payment.makePayment(50.00, "Credit Card"));
    }

    @Test
    @Parameters({"Credit Card", "Cash", "E-Wallet"})
    public void testMakePayment_MockedAsSuccessful_ReturnsTrue(String method) {
        payment mockPayment = mock(payment.class);
        when(mockPayment.makePayment(anyDouble(), eq(method))).thenReturn(true);

        boolean result = mockPayment.makePayment(100.00, method);

        assertTrue(result);
    }
}
