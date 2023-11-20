package Exam;

public class NextDayTicketDelivery extends TicketDelivery {
    private float perKgAdditionalFee;

    public NextDayTicketDelivery(String recipientName, String recipientAddress, String recipientCity,
            String recipientCountry, String recipientPostcode,
            double ticketPackageWeight, float costPerKgToShip, long ticketPackagetrackingNumber,
            float perKgAdditionalFee) {
    		super(recipientName, recipientAddress, recipientCity, recipientCountry, recipientPostcode,
    				ticketPackageWeight, costPerKgToShip, ticketPackagetrackingNumber);
    		
    		this.perKgAdditionalFee = perKgAdditionalFee;
}

    public float getPerKgAdditionalFee() {
        return perKgAdditionalFee;
    }

    public void setPerKgAdditionalFee(float perKgAdditionalFee) {
        this.perKgAdditionalFee = perKgAdditionalFee;
    }
    
    @Override
    public double calculateDeliveryCost() {
        return getTicketPackageWeight() * (getCostPerKgToShip() + perKgAdditionalFee);
    }
    
    @Override
    public String toString() {
        return "NextDayTicketDelivery" +
                "recipientName='" + getRecipientName() + "\n" +
                "recipientAddress='" + getRecipientAddress() + "\n" +
                "recipientCity='" + getRecipientCity() + "\n" +
                "recipientCountry='" + getRecipientCountry() + "\n" +
                "recipientPostcode='" + getRecipientPostcode() + "\n" +
                "ticketPackageWeight=" + getTicketPackageWeight() + "\n" +
                "costPerKgToShip=" + getCostPerKgToShip() + "EUR/kg\n" +
                "ticketPackageTrackingNumber=" + getTicketPackageTrackingNumber() + "\n" +
                "perKgAdditionalFee=" + perKgAdditionalFee  + "EUR\n" +
                "Delivery Cost: " + calculateDeliveryCost()+ " EUR";
               
    }
}