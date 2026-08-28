package main;


/**
 * Orchestrates a full order life cycle by wiring together the pricing,
 * payment, invoicing and email-notification units. This class has no
 * counterpart in the original file drop-off; it was added so that the
 * individually-specified units (CalculatePrintingCharge, Payment,
 * GenerateInvoice, EmailInvoice) have a natural integration point that
 * can be exercised by an integration test after each unit has been
 * tested in isolation.
 */
public class PrintOrderService {

    private final calculatePrintingCharge chargeCalculator;
    private final payment paymentService;
    private final generateInvoice invoiceGenerator;
    private final email_Invoice emailService;

    public PrintOrderService(calculatePrintingCharge chargeCalculator,
                              payment paymentService,
                              generateInvoice invoiceGenerator,
                              email_Invoice emailService) {
        this.chargeCalculator = chargeCalculator;
        this.paymentService = paymentService;
        this.invoiceGenerator = invoiceGenerator;
        this.emailService = emailService;
    }

    /**
     * Prices the order, attempts payment, generates the invoice and
     * (only on successful payment) emails it to the customer.
     *
     * @return the generated invoice text
     */
    public String processOrder(printOrder order, String paymentMethod) {

        double total = chargeCalculator.calculateTotal(order);

        boolean paid = paymentService.makePayment(total, paymentMethod);
        order.setPaymentStatus(paid ? "Paid" : "Unpaid");
        order.setOrderStatus(paid ? "Completed" : "Payment Failed");

        String invoice = invoiceGenerator.generate(order);

        if (paid) {
            emailService.sendInvoice(order.getCustomer().getEmailAddress(), invoice);
        }

        return invoice;
    }
}
