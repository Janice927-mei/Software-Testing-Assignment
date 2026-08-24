public class calculatePrintingCharge {

    private printerAvailability printerService;
    private applyDiscount discountService;

    public calculatePrintingCharge(printerAvailability printerService,
                                   applyDiscount discountService) {
        this.printerService = printerService;
        this.discountService = discountService;
    }

    public double calculateTotal(printOrder order) {

        // Check printer availability first
        boolean available = printerService.isPrinterAvailable(
                order.getPaperSize(),
                order.getPrintType()
        );

        if (!available) {
            throw new IllegalStateException(
                "Selected printer is currently unavailable."
            );
        }

        validateOrder(order);

        // Calculate base printing charge
        double rate = getBaseRate(
                order.getPaperSize(),
                order.getPrintType(),
                order.getPrintingSide()
        );

        double baseCharge = rate
                * order.getNumberOfPages()
                * order.getNumberOfCopies();

        // Calculate optional service charges
        double serviceCharge = calculateOptionalServices(order);

        double subtotal = baseCharge + serviceCharge;

        // Calculate discount
        double discountAmount = discountService.calculateDiscount(
                order.getCustomer(),
                subtotal
        );

        double total = subtotal - discountAmount;

        // Round to 2 decimal places
        total = Math.round(total * 100.0) / 100.0;

        // Store calculation results in the order
        order.setBasePrintingCharge(baseCharge);
        order.setAdditionalServiceCharges(serviceCharge);
        order.setDiscountAmount(discountAmount);
        order.setTotalPrintingCharge(total);

        return total;
    }

    private void validateOrder(printOrder order) {

        if (order.getNumberOfPages() < 1
                || order.getNumberOfPages() > 500) {
            throw new IllegalArgumentException(
                    "Number of pages must be between 1 and 500."
            );
        }

        if (order.getNumberOfCopies() < 1
                || order.getNumberOfCopies() > 1000) {
            throw new IllegalArgumentException(
                    "Number of copies must be between 1 and 1000."
            );
        }
    }

    private double getBaseRate(String paperSize,
                               String printType,
                               String printingSide) {

        if (paperSize.equalsIgnoreCase("A4")) {
            if (printType.equalsIgnoreCase("Black & White")) {
                return printingSide.equalsIgnoreCase("Single-sided")
                        ? 0.20 : 0.18;
            } else if (printType.equalsIgnoreCase("Colour")) {
                return printingSide.equalsIgnoreCase("Single-sided")
                        ? 0.80 : 0.75;
            }
        }

        if (paperSize.equalsIgnoreCase("A3")) {
            if (printType.equalsIgnoreCase("Black & White")) {
                return printingSide.equalsIgnoreCase("Single-sided")
                        ? 0.40 : 0.35;
            } else if (printType.equalsIgnoreCase("Colour")) {
                return printingSide.equalsIgnoreCase("Single-sided")
                        ? 1.50 : 1.40;
            }
        }

        if (paperSize.equalsIgnoreCase("A5")) {
            if (printType.equalsIgnoreCase("Black & White")) {
                return printingSide.equalsIgnoreCase("Single-sided")
                        ? 0.15 : 0.13;
            } else if (printType.equalsIgnoreCase("Colour")) {
                return printingSide.equalsIgnoreCase("Single-sided")
                        ? 0.60 : 0.55;
            }
        }

        throw new IllegalArgumentException("Invalid printing options.");
    }

    private double calculateOptionalServices(printOrder order) {

        double serviceCharge = 0;

        String binding = order.getBindingOption();

        if (binding != null) {
            if (binding.equalsIgnoreCase("Staple")) {
                serviceCharge += 2.00;
            } else if (binding.equalsIgnoreCase("Comb")) {
                serviceCharge += 5.00;
            } else if (binding.equalsIgnoreCase("Spiral")) {
                serviceCharge += 8.00;
            }
        }

        // Lamination is charged based on Pages × Copies
        if (order.hasLamination()) {
            serviceCharge += 1.50
                    * order.getNumberOfPages()
                    * order.getNumberOfCopies();
        }

        if (order.hasExpressPrinting()) {
            serviceCharge += 20.00;
        }

        return serviceCharge;
    }
}
