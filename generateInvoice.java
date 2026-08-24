public class generateInvoice {

    public String generate(printOrder order) {

        String invoice = "";

        invoice += "========== PRINTMASTER INVOICE ==========\n";

        invoice += "\n--- Customer Details ---\n";
        invoice += "Customer ID: "
                + order.getCustomer().getCustomerID() + "\n";
        invoice += "Name: "
                + order.getCustomer().getName() + "\n";
        invoice += "Email: "
                + order.getCustomer().getEmailAddress() + "\n";
        invoice += "Phone: "
                + order.getCustomer().getPhoneNumber() + "\n";

        invoice += "\n--- Print Order Details ---\n";
        invoice += "Print Type: " + order.getPrintType() + "\n";
        invoice += "Paper Size: " + order.getPaperSize() + "\n";
        invoice += "Printing Side: " + order.getPrintingSide() + "\n";
        invoice += "Number of Pages: "
                + order.getNumberOfPages() + "\n";
        invoice += "Number of Copies: "
                + order.getNumberOfCopies() + "\n";

        invoice += "\n--- Charges ---\n";
        invoice += String.format(
                "Base Printing Charge: RM%.2f%n",
                order.getBasePrintingCharge()
        );

        invoice += String.format(
                "Optional Service Charges: RM%.2f%n",
                order.getAdditionalServiceCharges()
        );

        invoice += String.format(
                "Discount: RM%.2f%n",
                order.getDiscountAmount()
        );

        invoice += String.format(
                "Total Amount Payable: RM%.2f%n",
                order.getTotalPrintingCharge()
        );

        invoice += "\n==========================================\n";

        return invoice;
    }
}
