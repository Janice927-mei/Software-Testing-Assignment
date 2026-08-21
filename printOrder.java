public class printOrder {
    private customer customerDetails;
    private String printType; 
    private String paperSize; 
    private String printingSide;
    private int pages;
    private int copies;
    
    private String bindingOption;
    private boolean lamination;
    private boolean expressPrinting;
    private double baseCharge;
    private double additionalServiceCharges;
    private double discounts;
    private double totalPrintingCharge;
    private String orderStatus; 
    private String paymentStatus; 

    public printOrder(customer cust, String type, String size, String side, int pages, int copies) {
        this.customerDetails = cust;
        this.printType = type;
        this.paperSize = size;
        this.printingSide = side;
        this.pages = pages;
        this.copies = copies;
        this.orderStatus = "Pending";
        this.paymentStatus = "Unpaid";
    }

    public customer getCustomerDetails() { return customerDetails; }
    public String getPrintType() { return printType; }
    public String getPaperSize() { return paperSize; }
    public String getPrintingSide() { return printingSide; }
    public int getPages() { return pages; }
    public int getCopies() { return copies; }
    
    public void setCharges(double base, double additional, double discount, double total) {
        this.baseCharge = base;
        this.additionalServiceCharges = additional;
        this.discounts = discount;
        this.totalPrintingCharge = total;
    }
    
    // Additional standard getters/setters omitted for brevity...
}
