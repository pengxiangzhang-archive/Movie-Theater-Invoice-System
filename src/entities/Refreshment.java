/******************************************************************************
 * This class is a subclass of Product. It is a constructor of Refreshment.
 * It also set the cost of it.
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file Refreshment.java

 * @version 1.1
 * @since Oct 1, 2018
 *******************************************************************************/

package entities;

public class Refreshment extends Product {
	private String name;
	private double cost;

	public Refreshment(String productCode, String name, double cost) {
		super(productCode);
		this.name = name;
		this.cost = cost;
	}

	public Refreshment(Product refreshment) {
		super(refreshment);
		this.name = ((Refreshment) refreshment).name;
		this.cost = ((Refreshment) refreshment).cost;
	}

	@Override
	public String toString() {
		return "Refreshment [" + (name != null ? "name=" + name + ", " : "") + "cost=" + cost + ", "
				+ (productCode != null ? "productCode=" + productCode + ", " : "") + "subtotal=" + subtotal + ", tax="
				+ tax + ", total=" + total + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		double multiplier = i.getProductArr().get(this);
		if (i.getPassCount() != 0)
			multiplier *= 0.95;
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
		return this.name.compareTo(((Refreshment) o).getName());
	}
}
