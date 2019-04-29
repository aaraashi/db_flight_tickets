package application;



public class Ticket {

	   private int ticketID;
	   private String destination;
	   private String departTime;
	   private String arrivalTime;
	   private String ticketNumber;
	   private int seatNumber;

	   // constructor
	   public Ticket() {}

	   // constructor
	   public Ticket(int ticketID, String destination, String departTime, 
			   String arrivalTime, String ticketNumber, int seatNumber) {
		this.ticketID = ticketID;
		this.destination = destination;
		this.departTime = departTime;
		this.arrivalTime = arrivalTime;
		this.ticketNumber = ticketNumber;
		this.seatNumber = seatNumber;
	   }


	   public int getTicketID() {
		   return ticketID;
	   }

	   public void setTicketID(int ticketID) {
		   this.ticketID = ticketID;
	   }

	   public String getDestination() {
		   return destination;
	   }

	   public void setDestination(String destination) {
		   this.destination = destination;
	   }

	   public String getDepartTime() {
		   return departTime;
	   }

	   public void setDepartTime(String departTime) {
		   this.departTime = departTime;
	   }
	   
	   public String getArrivalTime() {
		   return arrivalTime;
	   }

	   public void setArrivalTime(String arrivalTime) {
		   this.arrivalTime = arrivalTime;
	   }

	   public String getTicketNumber() {
		   return ticketNumber;
	   }

	   public void setTicketNumber(String ticketNumber) {
		   this.ticketNumber = ticketNumber;
	   }

	   public int getSeatNumber() {
		   return seatNumber;
	   }

	   public void setSeatNumber(int seatNumber) {
		   this.seatNumber = seatNumber;
	   }

	// returns the string representation of the Ticket
	   @Override
	   public String toString() 
	      {return getTicketID() + ", " + getDestination();}
	
}



