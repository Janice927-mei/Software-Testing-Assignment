package ApplicationCode;

public class printOrder {
    private customer customer;

    private String printType;
    private String paperSize;
    private String printingSide;

    private int numberOfPages;
    private int numberOfCopies;

    private String bindingOption;
    private boolean lamination;
    private boolean expressPrinting;

    private double basePrintingCharge;
    private double additionalServiceCharges;
    private double discountAmount;
    private double totalPrintingCharge;

    private String orderStatus;
    private String paymentStatus;

    public printOrder(customer customer, String printType, String paperSize,
                      String printingSide, int numberOfPages,
                      int numberOfCopies, String bindingOption,
                      boolean lamination, boolean expressPrinting) {

        this.customer = customer;
        this.printType = printType;
        this.paperSize = paperSize;
        this.printingSide = printingSide;
        this.numberOfPages = numberOfPages;
        this.numberOfCopies = numberOfCopies;
        this.bindingOption = bindingOption;
        this.lamination = lamination;
        this.expressPrinting = expressPrinting;

        this.orderStatus = "Pending";
        this.paymentStatus = "Unpaid";
    }

    public customer getCustomer() {
        return customer;
    }

    public String getPrintType() {
        return printType;
    }

    public String getPaperSize() {
        return paperSize;
    }

    public String getPrintingSide() {
        return printingSide;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public String getBindingOption() {
        return bindingOption;
    }

    public boolean hasLamination() {
        return lamination;
    }

    public boolean hasExpressPrinting() {
        return expressPrinting;
    }

    public double getBasePrintingCharge() {
        return basePrintingCharge;
    }

    public double getAdditionalServiceCharges() {
        return additionalServiceCharges;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getTotalPrintingCharge() {
        return totalPrintingCharge;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setBasePrintingCharge(double basePrintingCharge) {
        this.basePrintingCharge = basePrintingCharge;
    }

    public void setAdditionalServiceCharges(double additionalServiceCharges) {
        this.additionalServiceCharges = additionalServiceCharges;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setTotalPrintingCharge(double totalPrintingCharge) {
        this.totalPrintingCharge = totalPrintingCharge;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
