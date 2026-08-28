public class applyDiscount {

    public double calculateDiscount(customer customer, double subtotal) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        if (subtotal < 0) {
            throw new IllegalArgumentException("Subtotal cannot be negative.");
        }    	
        
        double finalAmount = subtotal;

        // Student discount
        if (customer.getCustomerType().equalsIgnoreCase("Student")) {
            finalAmount = finalAmount * 0.90;
        }

        // Corporate customer discount
        if (customer.getCustomerType().equalsIgnoreCase("Corporate")) {
            finalAmount = finalAmount * 0.85;
        }

        // Additional 5% if subtotal exceeds RM300
        if (subtotal > 300) {
            finalAmount = finalAmount * 0.95;
        }

        // Additional 5% for existing customer with more than 20 orders
        if (customer.getPreviousOrders() > 20) {
            finalAmount = finalAmount * 0.95;
        }

        return subtotal - finalAmount;
    }
}
