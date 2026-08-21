// 1) customer.java
public class customer {
    private String customerID;
    private String name;
    private String email;
    private String phone;
    private String customerType;

    public customer(String customerID, String name, String email, String phone, String customerType) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.customerType = customerType;
    }

    // Getters
    public String getCustomerID() { return customerID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getCustomerType() { return customerType; }
    
    // Setters can be added as needed...
}
