package test;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;


import main.addNewCustomer;
import main.readCustomer;
import main.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(JUnitParamsRunner.class)
public class AddNewCustomerReadCustomerTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private addNewCustomer addNewCustomer;
    private readCustomer readCustomer;

    @Before
    public void setUp() throws IOException {
        File testFile = tempFolder.newFile("customer_test.txt");
        String testFilePath = testFile.getAbsolutePath();

        addNewCustomer = new addNewCustomer(testFilePath);
        readCustomer = new readCustomer(testFilePath);
    }


    // Valid cases

    @Test
    @Parameters({
            "C100, Alice Tan, alice@mail.com, 0111111111, Student, 2",
            "C101, Bob Lim, bob@mail.com, 0122222222, Corporate, 30",
            "C102, Chan Mei, chan@mail.com, 0133333333, Regular, 0"
    })
    public void testAddAndReadCustomer_ValidData(String id, String name, String email,
                                                  String phone, String type, int orders)
            throws IOException {
        customer newCustomer = new customer(id, name, email, phone, type, orders);

        addNewCustomer.addCustomer(newCustomer);
        customer retrieved = readCustomer.getCustomerByID(id);

        assertNotNull(retrieved);
        assertEquals(id, retrieved.getCustomerID());
        assertEquals(name, retrieved.getName());
        assertEquals(email, retrieved.getEmailAddress());
        assertEquals(phone, retrieved.getPhoneNumber());
        assertEquals(type, retrieved.getCustomerType());
        assertEquals(orders, retrieved.getPreviousOrders());
    }

    @Test
    public void testAddMultipleCustomers_ReadReturnsCorrectOne() throws IOException {
        addNewCustomer.addCustomer(new customer("C200", "First", "first@mail.com", "011", "Regular", 1));
        addNewCustomer.addCustomer(new customer("C201", "Second", "second@mail.com", "012", "Student", 2));
        addNewCustomer.addCustomer(new customer("C202", "Third", "third@mail.com", "013", "Corporate", 3));

        customer retrieved = readCustomer.getCustomerByID("C201");

        assertNotNull(retrieved);
        assertEquals("Second", retrieved.getName());
        assertEquals("second@mail.com", retrieved.getEmailAddress());
    }


    // Invalid 

    @Test
    public void testReadCustomer_NonExistentID_ReturnsNull() throws IOException {
       
        addNewCustomer.addCustomer(new customer("C300", "Someone", "s@mail.com", "011", "Regular", 0));

        
        customer retrieved = readCustomer.getCustomerByID("C999");

        assertNull(retrieved);
    }

    @Test
    public void testReadCustomer_EmptyFile_ReturnsNull() throws IOException {
     
        customer retrieved = readCustomer.getCustomerByID("C001");

        assertNull(retrieved);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCustomer_NullCustomer_ThrowsException() throws IOException {
        addNewCustomer.addCustomer(null);
    }
}
