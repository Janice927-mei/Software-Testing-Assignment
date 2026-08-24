import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class readCustomer {

    private static final String FILE_NAME = "customer.txt";

    public customer getCustomerByID(String customerID) throws FileNotFoundException {

        File file = new File(FILE_NAME);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");

            if (data.length >= 6 && data[0].equals(customerID)) {
                scanner.close();

                return new customer(
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    Integer.parseInt(data[5])
                );
            }
        }

        scanner.close();
        return null;
    }
}
