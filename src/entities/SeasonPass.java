/******************************************************************************
 * This class is a subclass of Product. It is a constructor of SeasonPass.
 * It also set the SeasonPass Price, date.
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file SeasonPass.java
 * @author 
 * @version 1.1
 * @since Oct 1, 2018
 *******************************************************************************/

package entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SeasonPass extends Product {
	private String name;
	private LocalDate startDate, endDate;
	private double cost;

	public SeasonPass(String productCode, String name, String startDate, String endDate, double cost) {
		super(productCode);
		this.name = name;
		this.startDate = LocalDate.parse(startDate);
		this.endDate = LocalDate.parse(endDate);
		this.cost = cost;
	}

	public SeasonPass(Product seasonPass) {
		super(seasonPass);
		this.name = ((SeasonPass) seasonPass).name;
		this.startDate = ((SeasonPass) seasonPass).startDate;
		this.endDate = ((SeasonPass) seasonPass).endDate;
		this.cost = ((SeasonPass) seasonPass).cost;
	}

	@Override
	public String toString() {
		return "SeasonPass [" + (name != null ? "name=" + name + ", " : "")
				+ (startDate != null ? "startDate=" + startDate + ", " : "")
				+ (endDate != null ? "endDate=" + endDate + ", " : "") + "cost=" + cost + ", "
				+ (productCode != null ? "productCode=" + productCode + ", " : "") + "subtotal=" + subtotal + ", tax="
				+ tax + ", total=" + total + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = LocalDate.parse(startDate);
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = LocalDate.parse(endDate);
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
		double multiplier = 0;
		long normalDiff = ChronoUnit.DAYS.between(startDate, endDate);
		long invoiceDiff = ChronoUnit.DAYS.between(i.getInvoiceDate(), endDate);
		if (normalDiff <= invoiceDiff)
			multiplier = 1 * i.getProductArr().get(this);
		else
			multiplier = (double) invoiceDiff / (double) normalDiff * i.getProductArr().get(this);
		subtotal = cost * multiplier + (8.00 * i.getProductArr().get(this));
		// calculates tax
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
		return this.name.compareTo(((SeasonPass) o).getName());
	}
}