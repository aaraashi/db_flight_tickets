package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;


public class ReportController implements Initializable {
	
	@FXML private TextArea all2;
	
	/*
	@FXML
	private TableView<Ticket> ticket;
	@FXML
	private TableColumn<Ticket, Integer> ticketID;
	@FXML
	private TableColumn<Ticket, String> destination;
	@FXML
	private TableColumn<Ticket, String> departTime;
	@FXML
	private TableColumn<Ticket, String> arrivalTime;
	@FXML
	private TableColumn<Ticket, String> ticketNumber;
	@FXML
	private TableColumn<Ticket, Integer> seatNumber;
	
	
	private final TicketQueries ticketQueries = new TicketQueries();
	   private final ObservableList<Ticket> ticketList = 
			      FXCollections.observableArrayList();

    // stores list of Person objects that results from a database query
	public ObservableList<Ticket> getAllRecords() {
	    		   			
	    			ticketList.setAll(ticketQueries.getAllPeople());
					return ticketList; 
 		
	}
	    
	*/
	
	   private static final String URL = "jdbc:derby:newdb";
	   private static final String USERNAME = "juhee";
	   private static final String PASSWORD = "juhee";
	   


	   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		String sql = "SELECT * FROM Tickets";
		   try {
			   
			   Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			   Statement stat = conn.createStatement();
			   ResultSet rs = stat.executeQuery(sql);
		   
		   while (rs.next()) {
			   int ticketID = rs.getInt("ticketID");
			   String destination = rs.getString("destination");
			   String departTime = rs.getString("departTime");
			   String arrivalTime = rs.getString("arrivalTime");
			   String ticketNumber = rs.getString("ticketNumber");
			   int seatNumber = rs.getInt("seatNumber");
			   
			   all2.appendText("Ticket ID: " + ticketID + "\nDestination: " 
			   + destination + "\nDepart Time: " + departTime + "\nArrival Time: " 
					   + arrivalTime + "\nTicket Number: " + ticketNumber + "\nSeat Number: " + seatNumber + "\n\n\n" );
		   }   
			   conn.close();
		   
		   }
		   catch (SQLException sqlException) {
			      displayAlert(AlertType.ERROR, "Database Error", 
			         sqlException.getMessage());}
		   
		   
	}
		   
		   private void displayAlert(
				      AlertType type, String title, String message) {
				      Alert alert = new Alert(type);
				      alert.setTitle(title);
				      alert.setContentText(message);
				      alert.showAndWait();
				   }
		  /* 
		   
		ticketID.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("ticketID"));
		destination.setCellValueFactory(new PropertyValueFactory<Ticket, String>("destination"));
		departTime.setCellValueFactory(new PropertyValueFactory<Ticket, String>("departTime"));
		arrivalTime.setCellValueFactory(new PropertyValueFactory<Ticket, String>("arrivalTime"));
		ticketNumber.setCellValueFactory(new PropertyValueFactory<Ticket, String>("ticketNumber"));
		seatNumber.setCellValueFactory(new PropertyValueFactory<Ticket, Integer>("seatNumber"));
		ticket.setItems(ticketList);
  	      
	}
*/
	
}
