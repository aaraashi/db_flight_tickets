package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketQueries {
	   //private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	   //private static final String URL="jdbc:mysql://localhost:3306/books?useSSL=false";
	
	   private static final String URL = "jdbc:derby:newdb";
	   private static final String USERNAME = "juhee";
	   private static final String PASSWORD = "juhee";

	   private Connection connection; // manages connection
	   private PreparedStatement selectAllTicket;       
	   private PreparedStatement selectTicketByDestination;
	   private PreparedStatement insertNewTicket;  
	   private PreparedStatement deleteOldTicket;
	   private PreparedStatement updateOldTicket;
	   private PreparedStatement countAllTicket;  
	   // figure out delete / update statement
	   
	   // constructor
	   public TicketQueries() {
	      try {
	    	 
	         connection = 
	            DriverManager.getConnection(URL, USERNAME, PASSWORD);

	         // create query that selects all entries in the Tickets
	         selectAllTicket = connection.prepareStatement(
	            "SELECT * FROM Tickets ORDER BY TicketID");
	         	// TicketID, Destination, DepartTime, ArrivalTime, TicketNumber, SeatNumber 
	         
	         // count all queries
	         countAllTicket = connection.prepareStatement(
	        		 "SELECT * FROM Tickets ORDER BY TicketID");
	         
	         // create query that selects entries with destination
	         // that begin with the specified characters 
	         selectTicketByDestination = connection.prepareStatement(        
	            "SELECT * FROM Tickets WHERE Destination LIKE ? " + 
	            "ORDER BY TicketID");            
	         
	         // create insert that adds a new entry into the database
	         insertNewTicket = connection.prepareStatement(         
	            "INSERT INTO Tickets " +                           
	            "(Destination, DepartTime, ArrivalTime, TicketNumber, SeatNumber) " +     
	            "VALUES (?, ?, ?, ?, ?)");      
	         
	         // create delete that removes an old entry into the database
	         deleteOldTicket = connection.prepareStatement( 
	            "DELETE FROM Tickets " +
	            "WHERE TicketID = ? AND TicketNumber = ?");
	        
	         // create update that updates an old entry into the database
	         updateOldTicket = connection.prepareStatement( 
	 	         "UPDATE Tickets SET Destination = ?, DepartTime = ?, ArrivalTime = ?, " +
	 	         "TicketNumber = ?, SeatNumber = ? WHERE TicketID = ?");

	      } 
	      catch (SQLException sqlException) {
	         sqlException.printStackTrace();
	         System.exit(1);
	      } 
	   } 
	   
	   
	   // select all of the tickets in the database
	   public List<Ticket> getAllTicket() {
	      // executeQuery returns ResultSet containing matching entries
	      try (ResultSet resultSet = selectAllTicket.executeQuery()) {
	         List<Ticket> results = new ArrayList<Ticket>();
	         
	         while (resultSet.next()) {
	            //Ticket ticket = new Ticket(
	        	 results.add(new Ticket(
	 	               resultSet.getInt("TicketID"),
		               resultSet.getString("Destination"),
		               resultSet.getString("DepartTime"),
		               resultSet.getString("ArrivalTime"),
		               resultSet.getString("TicketNumber"),
		               resultSet.getInt("SeatNumber")));
	            
	            //results.add(ticket);
	         } 

	         return results;
	      }
	      catch (SQLException sqlException) {
	         sqlException.printStackTrace();         
	      }
	      
	      return null;
	   }
	   
	  // select ticket by destination
	   public List<Ticket> getTicketByDestination(String destination) {
	      try {
	    	  selectTicketByDestination.setString(1, destination); // set destination
	      }
	      catch (SQLException sqlException) {
	         sqlException.printStackTrace();
	         return null;
	      }

	      // executeQuery returns ResultSet containing matching entries
	      try (ResultSet resultSet = selectTicketByDestination.executeQuery()) {
	         List<Ticket> results = new ArrayList<Ticket>();

	         while (resultSet.next()) {
	        	 //Ticket ticket = new Ticket(
	        	 results.add(new Ticket(
	               resultSet.getInt("ticketID"),
	               resultSet.getString("Destination"),
	               resultSet.getString("DepartTime"),
	               resultSet.getString("ArrivalTime"),
	               resultSet.getString("TicketNumber"),
	               resultSet.getInt("SeatNumber")));
	               
	        	 //results.add(ticket);
	               
	         } 

	         return results;
	      }
	      catch (SQLException sqlException) {
	         sqlException.printStackTrace();
	         return null;
	      } 
	   }
	   
	   // add an entry
	   public int addTicket(String destination, String departTime, 
	      String arrivalTime, String ticketNumber, int seatNumber) {
	      
	      // insert the new entry; returns # of rows updated
	      try {
	         // set parameters 
	         insertNewTicket.setString(1, destination);
	         insertNewTicket.setString(2, departTime);
	         insertNewTicket.setString(3, arrivalTime);
	         insertNewTicket.setString(4, ticketNumber); 
	         insertNewTicket.setInt(5, seatNumber);

	         return insertNewTicket.executeUpdate();         
	      }
	      catch (SQLException sqlException) {
	         sqlException.printStackTrace();
	         return 0;
	      }
	   }
	   
	   
	   // delete an old entry 
	   public int deleteOldTicket(int ticketID, String ticketNumber) {
		
		   try {
	   		 deleteOldTicket.setInt(1,  ticketID);
	   		 deleteOldTicket.setString(2,  ticketNumber);
	         return deleteOldTicket.executeUpdate();         
	       }
	      catch (SQLException sqlException) {
	         sqlException.printStackTrace();
	         return 0;
	      }
	   }

		   
	   // update an old entry
		   public int updateOldTicket(String destination, String departTime, 
				      String arrivalTime, String ticketNumber, int seatNumber, int ticketID) {
			     
			   try {
				     updateOldTicket.setString(1, destination);
				     updateOldTicket.setString(2, departTime);
				     updateOldTicket.setString(3, arrivalTime);
				     updateOldTicket.setString(4, ticketNumber);
				     updateOldTicket.setInt(5, seatNumber);
				     updateOldTicket.setInt(6, ticketID);

				         return updateOldTicket.executeUpdate();         
				      }
				      catch (SQLException sqlException) {
				         sqlException.printStackTrace();
				         return 0;
				      }
				   } 
	   
	   // close the database connection
	   public void close() {
	      try {
	         connection.close();
	      } 
	      catch (SQLException sqlException) {
	         sqlException.printStackTrace();
	      } 
	   }

	   public int getNumberOfRows() {
		      // executeQuery returns ResultSet containing matching entries
		      try (ResultSet resultSet = countAllTicket.executeQuery()) {
		         List<Ticket> results = new ArrayList<Ticket>();
		         
		         while (resultSet.next()) {
		            results.add(new Ticket(
		               resultSet.getInt("TicketID"),
		               resultSet.getString("Destination"),
		               resultSet.getString("DepartTime"),
		               resultSet.getString("ArrivalTime"),
		               resultSet.getString("TicketNumber"),
		               resultSet.getInt("seatNumber")));
		         } 

		         
		         return 8;
		      }
		      catch (SQLException sqlException) {
		         sqlException.printStackTrace();         
		      }
		      
		      return 0;
		   }
	
	}
