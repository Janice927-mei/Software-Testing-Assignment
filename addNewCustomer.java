import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class addNewCustomer {

    private static final String FILE_NAME = "customer.txt";

    public void addCustomer(customer newCustomer) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true));

        writer.println(newCustomer.toString());
        writer.close();
    }
}

