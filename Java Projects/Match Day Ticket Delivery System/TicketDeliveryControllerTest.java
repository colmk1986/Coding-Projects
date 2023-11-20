package Exam;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class TicketDeliveryControllerTest {

    public static void main(String[] args) {
        // Creating the controller object
        TicketDeliveryController controller = new TicketDeliveryController();

        
        String[] options = {"Add Ticket Delivery", "Search by Tracking Number",  "Exit",};

        
        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Select an option", "RWC Ticket Delivery App", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            
            if (choice == 0) { // Add Delivery
            	// end user adds a deliver
                controller.addDelivery();
                
            } else if (choice == 1) { // Search by Tracking Number
            	//end user searches for a delivery
                controller.searchDelivery();
                
            } else { // Exit
                break;
            }
        }
        
        // Displaying the cost per delivery report
        controller.costPerDeliveryReport();
    }
    
}
