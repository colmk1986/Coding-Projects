package Exam;

public class StandardTicketDelivery extends TicketDelivery {
    private double flatAdditionalFee;

    public StandardTicketDelivery(String recipientName, String recipientAddress, String recipientCity,
            String recipientCountry, String recipientPostcode,
            double ticketPackageWeight, float costPerKgToShip, long ticketPackagetrackingNumber,
            double flatAdditionalFee) {
    		super(recipientName, recipientAddress, recipientCity, recipientCountry, recipientPostcode,
    					ticketPackageWeight, costPerKgToShip, ticketPackagetrackingNumber);
    		
    		this.flatAdditionalFee = flatAdditionalFee;
}

    public double getFlatAdditionalFee() {
        return flatAdditionalFee;
    }

    public void setFlatAdditionalFee(double flatAdditionalFee) {
        this.flatAdditionalFee = flatAdditionalFee;
    }
    @Override
    public double calculateDeliveryCost() {
        return getTicketPackageWeight() * getCostPerKgToShip() + flatAdditionalFee;
    }
    
    
    @Override
    public String toString() {
        return "Standard Ticket Delivery: \n"
                + "Recipient Name: " + getRecipientName() + "\n"
                + "Recipient Address: " + getRecipientAddress() + "\n"
                + "Recipient City: " + getRecipientCity() + "\n"
                + "Recipient Country: " + getRecipientCountry() + "\n"
                + "Recipient Postcode: " + getRecipientPostcode() + "\n"
                + "Ticket Package Weight: " + getTicketPackageWeight() + " kg\n"
                + "Cost per kg to Ship: " + getCostPerKgToShip() + " EUR/kg\n"
                + "Ticket Package Tracking Number: " + getTicketPackageTrackingNumber() + "\n"
                + "Flat Additional Fee: " + flatAdditionalFee + " EUR\n"
                + "Delivery Cost: " + calculateDeliveryCost() + " EUR";
    }
}
