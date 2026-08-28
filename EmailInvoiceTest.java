package test;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import main.email_Invoice;

/**
 * Unit tests 
 * Its own (stub) behaviour is tested directly, and its use as a Mockito
 * test double is demonstrated for completeness.
 */
public class EmailInvoiceTest {

    @Test
    public void testSendInvoice_DefaultStubImplementation_ReturnsFalse() {
        email_Invoice emailInvoice = new email_Invoice();

        boolean result = emailInvoice.sendInvoice("test@mail.com", "Invoice content");

        assertFalse(result);
    }

    @Test
    public void testSendInvoice_MockedAsSuccessful_ReturnsTrue() {
        email_Invoice mockEmailInvoice = mock(email_Invoice.class);
        when(mockEmailInvoice.sendInvoice(anyString(), anyString())).thenReturn(true);

        boolean result = mockEmailInvoice.sendInvoice("test@mail.com", "Invoice content");

        assertTrue(result);
        verify(mockEmailInvoice, times(1)).sendInvoice("test@mail.com", "Invoice content");
    }
}
