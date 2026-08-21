
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class addNewCustomer {
    public boolean addCustomer(String customerID, String name, String email, String phone, String customerType) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("customer.txt", true))) {
            String customerRecord = String.format("%s,%s,%s,%s,%s", customerID, name, email, phone, customerType);
            bw.write(customerRecord);
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to customer file: " + e.getMessage());
            return false;
        }
    }
}
