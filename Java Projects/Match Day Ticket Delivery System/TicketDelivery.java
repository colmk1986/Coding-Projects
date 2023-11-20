package Exam;

public abstract class TicketDelivery {
    private String recipientName;
    private String recipientAddress;
    private String recipientCity;
    private String recipientCountry;
    private String recipientPostcode;
    
    private double ticketPackageWeight;
    private float costPerKgToShip;
    private long ticketPackageTrackingNumber;
    
    public TicketDelivery(String recipientName, String recipientAddress, String recipientCity,
                          String recipientCountry, String recipientPostcode, double ticketPackageWeight,
                          float costPerKgToShip, long ticketPackageTrackingNumber) {
        this.recipientName = recipientName;
        this.recipientAddress = recipientAddress;
        this.recipientCity = recipientCity;
        this.recipientCountry = recipientCountry;
        this.recipientPostcode = recipientPostcode;
        
        if (ticketPackageWeight > 0) {
            this.ticketPackageWeight = ticketPackageWeight;
        } else {
            throw new IllegalArgumentException("Ticket package weight must be positive");
        }
        
        if (costPerKgToShip > 0) {
            this.costPerKgToShip = costPerKgToShip;
        } else {
            throw new IllegalArgumentException("Cost per kg to ship must be positive");
        }
        
        this.ticketPackageTrackingNumber = ticketPackageTrackingNumber;
        
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    
    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }
    
    public String getRecipientCity() {
        return recipientCity;
    }
    
    public void setRecipientCity(String recipientCity) {
        this.recipientCity = recipientCity;
    }

    public String getRecipientCountry() {
        return recipientCountry;
    }
    
    public void setRecipientCountry(String recipientCountry) {
        this.recipientCountry = recipientCountry;
    }

    public String getRecipientPostcode() {
        return recipientPostcode;
    }
    
    public void setRecipientPostcode(String recipientPostcode) {
        this.recipientPostcode = recipientPostcode;
    }
    
    public double getTicketPackageWeight() {
        return ticketPackageWeight;
    }

    public void setTicketPackageWeight(double ticketPackageWeight) {
        if (ticketPackageWeight >= 0) {
            this.ticketPackageWeight = ticketPackageWeight;
        }
    }
    
    public float getCostPerKgToShip() {
        return costPerKgToShip;
    }
    
    public void setCostPerKgToShip(float costPerKgToShip) {
        if (costPerKgToShip >= 0) {
            this.costPerKgToShip = costPerKgToShip;
        }
    }
    
    public long getTicketPackageTrackingNumber() {
        return ticketPackageTrackingNumber;
    }

    public void setTicketPackagetrackingNumber(long ticketPackagetrackingNumber) {
        this.ticketPackageTrackingNumber = ticketPackagetrackingNumber;
    }
    
    public double calculateDeliveryCost() {
    	//return delivery cost by multiplying package weight by cost per kg to ship
        return ticketPackageWeight * costPerKgToShip;
    }
    
    @Override
    public String toString() {
        return "Recipient Name: " + recipientName + "\nRecipient Address: " + recipientAddress +
                "\nRecipient City: " + recipientCity + "\nRecipient Country: " + recipientCountry +
                "\nRecipient Postcode: " + recipientPostcode + "\nTicket Package Weight: " +
                ticketPackageWeight + "\nCost per kg to ship: " + costPerKgToShip +
                "\nTracking Number: " + ticketPackageTrackingNumber;
    }
}
