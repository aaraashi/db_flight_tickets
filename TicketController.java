package application;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TicketController {

	   @FXML private ListView<Ticket> listView; // displays ticketIDs
	   @FXML private TextField ticketIDTextField;
	   @FXML private TextField destinationTextField;
	   @FXML private TextField departTextField;
	   @FXML private TextField arrivalTextField;
	   @FXML private TextField ticketNumTextField;
	   @FXML private TextField seatNumTextField;
	   @FXML private TextField findByDestinationTextField;
	   @FXML private Label ticketIDLabel;
	   @FXML private TextArea all;
	   


	   // interacts with the database
	   private final TicketQueries ticketQueries = new TicketQueries();

	   // stores list of Ticket objects that results from a database query
	   private final ObservableList<Ticket> ticketList = 
	      FXCollections.observableArrayList();
	   
	   // populate listView and set up listener for selection events
	   public void initialize() {
	     
		  listView.setItems(ticketList); // bind to ticketList
	      getAllEntries(); // populates ticketList, which updates listView 

	      // when ListView selection changes, display selected ticket's data
	      listView.getSelectionModel().selectedItemProperty().addListener(
	         (observableValue, oldValue, newValue) -> {
	            displayContact(newValue);
	         }
	      );     
	      
	      // check the number of row in db

		   if(ticketQueries.getNumberOfRows() > 5) {
		   System.out.println("so far so good"); // just to connect pieces 
		   
		   }
		   
		   else if(ticketQueries.getNumberOfRows() < 5) {
	       while (ticketQueries.getNumberOfRows() < 6) {
	    	  ticketQueries.addTicket("New Data", "", "", "", 0);
	       }
	       }
	      
	      

		   
	      
	   }

	   // get all the entries from the database to populate ticketList
	   private void getAllEntries() {
	      ticketList.setAll(ticketQueries.getAllTicket()); 
	      selectFirstEntry();
	   }

	   // select first item in listView
	   private void selectFirstEntry() {
	      listView.getSelectionModel().selectFirst();          
	   }

	   // display ticket information
	   private void displayContact(Ticket ticket) {
	      if (ticket != null) {
	    	  ticketIDLabel.setText(String.valueOf(ticket.getTicketID()));
	    	  destinationTextField.setText(ticket.getDestination());
	    	  departTextField.setText(ticket.getDepartTime());
	    	  arrivalTextField.setText(ticket.getArrivalTime());
	    	  ticketNumTextField.setText(ticket.getTicketNumber());
	    	  seatNumTextField.setText(String.valueOf(ticket.getSeatNumber()));
	      }
	      else {
	    	   ticketIDLabel.setText(" ");
			   destinationTextField.clear();
			   departTextField.clear();
			   arrivalTextField.clear();
			   ticketNumTextField.clear();
			   seatNumTextField.clear();
	         
	      }
	   }

	   // add a new entry
	   @FXML
	   void addEntryButtonPressed(ActionEvent event) {
	              
		  int result = ticketQueries.addTicket(
		    		  destinationTextField.getText(), departTextField.getText(), 
		    		  arrivalTextField.getText(), ticketNumTextField.getText(), Integer.parseInt(seatNumTextField.getText()));                                     
		      
	      
	      if (result == 1) {
	         displayAlert(AlertType.INFORMATION, "Entry Added", 
	            "New entry successfully added.");
	      }
	      else {
	         displayAlert(AlertType.ERROR, "Entry Not Added", 
	            "Unable to add entry.");
	      }
	      
	      getAllEntries();
	   }

	// delete an old entry
	   @FXML
	   void deleteEntryButtonPressed(ActionEvent event) {

		int result = ticketQueries.deleteOldTicket(Integer.parseInt(ticketIDLabel.getText()), ticketNumTextField.getText());                                     
	      
	      if (result == 1) {
	         displayAlert(AlertType.INFORMATION, "Entry Deleted", 
	            "The entry successfully deleted.");
	      }
	      else {
	         displayAlert(AlertType.ERROR, "Entry Not Added", 
	            "Unable to delete entry.");
	      }
	      
	      getAllEntries();
	   }

	// update an old entry
	   @FXML
	   void updateEntryButtonPressed(ActionEvent event) {
	              
		  int result = ticketQueries.updateOldTicket(
		    		  destinationTextField.getText(), departTextField.getText(), arrivalTextField.getText(), 
		    		  ticketNumTextField.getText(), Integer.parseInt(seatNumTextField.getText()), Integer.parseInt(ticketIDLabel.getText()));                                     
		      
	      if (result == 1) {
	         displayAlert(AlertType.INFORMATION, "Entry Updated", 
	            "The entry successfully updated.");
	      }
	      else {
	         displayAlert(AlertType.ERROR, "Entry Not Added", 
	            "Unable to update entry.");
	      }
	      
	      getAllEntries();
	   }
	   
	   // find entries with the specified destination (extra feature)
	   @FXML
	   void findButtonPressed(ActionEvent event) {
	      List<Ticket> Ticket = ticketQueries.getTicketByDestination(
	         findByDestinationTextField.getText() + "%");

	      if (Ticket.size() > 0) { // display all entries
	         ticketList.setAll(Ticket);
	         selectFirstEntry();
	      }
	      else {
	         displayAlert(AlertType.INFORMATION, "Lastname Not Found", 
	            "There are no entries with the specified last name.");
	      }
	   }

	   // browse all the entries
	   @FXML
	   void browseAllButtonPressed(ActionEvent event) {
	      getAllEntries();
	   }
	   

	   // display an Alert dialog
	   private void displayAlert(
	      AlertType type, String title, String message) {
	      Alert alert = new Alert(type);
	      alert.setTitle(title);
	      alert.setContentText(message);
	      alert.showAndWait();
	   }
	   
	   @FXML
	   public void displayReportButtonPressed(ActionEvent event) {
		   
		// open second window	
					try {
						Stage stage = new Stage();
						String fxmlFileName = "TicketReport.fxml";
						FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
						Parent root = loader.load();
						Scene scene = new Scene(root);
						stage.setScene(scene);
						ReportController controller = loader.<ReportController>getController();
						stage.show();
					} catch(Exception e) {
						e.printStackTrace();
					}
					
		   
		   
	   }
}