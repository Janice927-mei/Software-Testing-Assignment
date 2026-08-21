import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class readCustomer {
    public customer getCustomerByID(String customerID) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("customer.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5 && data[0].equals(customerID)) {
                    return new customer(data[0], data[1], data[2], data[3], data[4]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading customer file: " + e.getMessage());
        }
        return null; // Customer not found
    }
}
