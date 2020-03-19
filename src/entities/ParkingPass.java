/******************************************************************************
 * This class is a subclass of Product. It is a constructor of ParkingPass.
 * It also set the parking fee.
 * 
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file ParkingPass.java

 * @version 1.1
 * @since Oct 1, 2018
 *******************************************************************************/

package entities;

public class ParkingPass extends Product {
	private String ticketCode = null;
	private double cost;
	private int numFree;

	public ParkingPass(String productCode, double cost, String ticketCode) {
		super(productCode);
		this.cost = cost;
		this.ticketCode = ticketCode;
	}

	public ParkingPass(Product parkingPass) {
		super(parkingPass);
		this.cost = ((ParkingPass) parkingPass).cost;
		this.ticketCode = ((ParkingPass) parkingPass).ticketCode;
	}

	public ParkingPass(String productCode, double cost) {
		super(productCode);
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "ParkingPass [" + (ticketCode != null ? "ticketCode=" + ticketCode + ", " : "") + "cost=" + cost + ", "
				+ (productCode != null ? "productCode=" + productCode + ", " : "") + "subtotal=" + subtotal + ", tax="
				+ tax + ", total=" + total + "]";
	}

	public String getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getNumFree() {
		return numFree;
	}

	public void setNumFree(int numFree) {
		this.numFree = numFree;
	}

	@Override
	public void calcSubtotalTaxTotal(Invoice i) {
		// calculates subtotal
		double multiplier = 0;
		if (ticketCode != null) {
			for (Product m : i.getProductArr().keySet()) {
				if (m.getProductCode().equals(ticketCode)) {
					if (i.getProductArr().get(m) <= i.getProductArr().get(this)) {
						multiplier = i.getProductArr().get(this) - i.getProductArr().get(m);
						numFree = i.getProductArr().get(m);
					} else {
						multiplier = 0;
						numFree = i.getProductArr().get(this);
					}
				}
			}
		} else {
			multiplier = i.getProductArr().get(this);
		}
		subtotal = cost * multiplier;
		// calculates tax
		if (i.getCustomer().isTaxable())
			tax = subtotal * .04;
		else
			tax = 0;
		// calculates total
		total = subtotal + tax;
	}

		
	
	@Override
	public int compareTo(Product o) {
		// TODO Auto-generated method stub
		return (int) (this.cost - o.getCost());
	}
}