package Exam;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class TicketDeliveryController {
    private ArrayList<TicketDelivery> deliveries;

    public TicketDeliveryController() {
        deliveries = new ArrayList<TicketDelivery>();
    }

    public void addDelivery() {
    	//String array to hold drop down options
        String[] deliveryTypes = {"Next Day", "Standard"};
        //JOptionPane menu to request delivery type
        String selectedDeliveryType = (String) JOptionPane.showInputDialog(null, "Select a delivery type:", "Add Delivery", JOptionPane.PLAIN_MESSAGE, null, deliveryTypes, deliveryTypes[0]);

        //Request delivery details
        String recipientName = JOptionPane.showInputDialog(null, "Enter recipient name:");
        String recipientAddress = JOptionPane.showInputDialog(null, "Enter recipient address:");
        String recipientCity = JOptionPane.showInputDialog(null, "Enter recipient city:");
        String recipientCountry = JOptionPane.showInputDialog(null, "Enter recipient country:");
        String recipientPostcode = JOptionPane.showInputDialog(null, "Enter recipient postcode:");
        double packageWeight = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter package weight (in kg):"));
        float costPerKg = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter cost per kg (in EUR):"));
        long packageTrackingNumber = Long.parseLong(JOptionPane.showInputDialog(null, "Enter package tracking number:"));
        double additionalFee = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter additional fee (in EUR):"));

        TicketDelivery delivery = null;
        if (selectedDeliveryType.equals("Next Day")) {
            float perKgAdditionalFee = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter per kg additional fee (in EUR):"));
            delivery = new NextDayTicketDelivery(recipientName, recipientAddress, recipientCity, recipientCountry, recipientPostcode, packageWeight, costPerKg, packageTrackingNumber, perKgAdditionalFee);
        } else if (selectedDeliveryType.equals("Standard")) {
            delivery = new StandardTicketDelivery(recipientName, recipientAddress, recipientCity, recipientCountry, recipientPostcode, packageWeight, costPerKg, packageTrackingNumber, additionalFee);
        }

        deliveries.add(delivery);
        displayDeliveryDetails(delivery);
    }

    void displayDeliveryDetails(TicketDelivery delivery) {
        JOptionPane.showMessageDialog(null, "Delivery added:\n" + delivery.toString(), "Delivery Details", JOptionPane.PLAIN_MESSAGE);
    }
    
    
    private int getIndex(String myDeliveryTrackingNumber) {
        for (int i = 0; i < deliveries.size(); i++) {
            if (Long.toString(deliveries.get(i).getTicketPackageTrackingNumber()).equals(myDeliveryTrackingNumber)) {
                return i;
            }
        }
        return -1;
    }
    
    public void searchDelivery() {
        String trackingNumber = JOptionPane.showInputDialog("Please enter the delivery tracking number:");
        int index = getIndex(trackingNumber);
        
        if (index != -1) {
            TicketDelivery delivery = deliveries.get(index);
            JOptionPane.showMessageDialog(null, delivery.toString(), "Delivery Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Delivery not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void costPerDeliveryReport() {
        String report = "Cost per delivery report:\n\n";
        for (TicketDelivery delivery : deliveries) {
            report += delivery.getRecipientName() + ": " + delivery.calculateDeliveryCost() + "\n";
        }
        JOptionPane.showMessageDialog(null, report, "Cost per delivery report", JOptionPane.INFORMATION_MESSAGE);
    }


}

