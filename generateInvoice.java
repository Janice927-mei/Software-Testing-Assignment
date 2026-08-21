public class generateInvoice {
    public void printInvoice(printOrder order) {
        System.out.println("=====================================");
        System.out.println("              INVOICE                ");
        System.out.println("=====================================");
        System.out.println("Customer Name : " + order.getCustomerDetails().getName());
        System.out.println("Customer ID   : " + order.getCustomerDetails().getCustomerID());
        System.out.println("-------------------------------------");
        System.out.println("Order Details:");
        System.out.println("Paper Size    : " + order.getPaperSize());
        System.out.println("Print Type    : " + order.getPrintType());
        System.out.println("Pages/Copies  : " + order.getPages() + " pages x " + order.getCopies() + " copies");
        System.out.println("-------------------------------------");
        System.out.println("Base Charge       : RM " + String.format("%.2f", order.getBaseCharge()));
        System.out.println("Optional Services : RM " + String.format("%.2f", order.getAdditionalServiceCharges()));
        System.out.println("Discounts Applied : -RM " + String.format("%.2f", order.getDiscounts()));
        System.out.println("-------------------------------------");
        System.out.println("FINAL TOTAL       : RM " + String.format("%.2f", order.getTotalPrintingCharge()));
        System.out.println("=====================================");
    }
}
