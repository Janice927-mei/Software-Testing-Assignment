package ApplicationCode;

public class customer {
    private String customerID;
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private String customerType;
    private int previousOrders;

    public customer(String customerID, String name, String emailAddress,
                    String phoneNumber, String customerType, int previousOrders) {
        this.customerID = customerID;
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.customerType = customerType;
        this.previousOrders = previousOrders;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCustomerType() {
        return customerType;
    }

    public int getPreviousOrders() {
        return previousOrders;
    }

    public void setPreviousOrders(int previousOrders) {
        this.previousOrders = previousOrders;
    }

    @Override
    public String toString() {
        return customerID + "," + name + "," + emailAddress + ","
                + phoneNumber + "," + customerType + "," + previousOrders;
    }
}
