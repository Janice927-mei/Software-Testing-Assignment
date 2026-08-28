import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class addNewCustomer {

    private static final String DEFAULT_FILE_NAME = "customer.txt";

    private final String fileName;

    public addNewCustomer() {
        this(DEFAULT_FILE_NAME);
    }

    public addNewCustomer(String fileName) {
        this.fileName = fileName;
    }
    public void addCustomer(customer newCustomer) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true));

        writer.println(newCustomer.toString());
        writer.close();
    }
}

