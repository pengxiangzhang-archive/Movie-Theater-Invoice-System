/******************************************************************************
 * This class is a subclass of Product. It is a constructor of Movie Ticket.
 * It also set the Movie Ticket Price, screen No. Movie Name address.
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file MovieTicket.java

 * @version 1.1
 * @since Oct 1, 2018
 *******************************************************************************/

package entities;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class MovieTicket extends Product {
	private LocalDateTime dateTime;
	private String movieName, screenNo;
	private Address address;
	private double cost;

	public MovieTicket(String productCode, String dateTime, String movieName, Address address, String screenNo,
			double cost) {
		super(productCode);
		this.dateTime = LocalDateTime.parse(dateTime.replace(' ', 'T'));
		this.movieName = movieName;
		this.address = address;
		this.screenNo = screenNo;
		this.cost = cost;
	}

	public MovieTicket(Product movieTicket) {
		super(movieTicket);
		this.dateTime = ((MovieTicket) movieTicket).dateTime;
		this.movieName = ((MovieTicket) movieTicket).movieName;
		this.address = ((MovieTicket) movieTicket).address;
		this.screenNo = ((MovieTicket) movieTicket).screenNo;
		this.cost = ((MovieTicket) movieTicket).cost;
	}

	@Override
	public String toString() {
		return "MovieTicket [" + (dateTime != null ? "dateTime=" + dateTime + ", " : "")
				+ (movieName != null ? "movieName=" + movieName + ", " : "")
				+ (screenNo != null ? "screenNo=" + screenNo + ", " : "")
				+ (address != null ? "address=" + address + ", " : "") + "cost=" + cost + ", "
				+ (productCode != null ? "productCode=" + productCode + ", " : "") + "subtotal=" + subtotal + ", tax="
				+ tax + ", total=" + total + "]";
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = LocalDateTime.parse(dateTime.replace(' ', 'T'));
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getScreenNo() {
		return screenNo;
	}

	public void setScreenNo(String screenNo) {
		this.screenNo = screenNo;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public void calcSubtotalTaxTotal(Invoice i) {
		// calculates subtotal
		subtotal = 0;
		double multiplier = 0;
		if (dateTime.getDayOfWeek().equals(DayOfWeek.TUESDAY) || dateTime.getDayOfWeek().equals(DayOfWeek.THURSDAY))
			multiplier = i.getProductArr().get(this) * 0.93;
		else
			multiplier = i.getProductArr().get(this);
		subtotal += cost * multiplier;
		// calculates taxS
		if (i.getCustomer().isTaxable())
			tax = subtotal * .06;
		else
			tax = 0;
		// calculates total
		total = subtotal + tax;
	}

	@Override
	public int compareTo(Product o) {
		// TODO Auto-generated method stub
		return this.movieName.compareTo(((MovieTicket) o).getMovieName());
	}
}