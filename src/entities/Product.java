/**************************************************************************
 * This class is a parent class for ParkingPass, Refreshment, SeasonPass,
 * MovieTicket.
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file Product.java

 * @version 1.1
 * @since Oct 1, 2018
 **************************************************************************/

package entities;

public abstract class Product {
	protected String productCode;
	protected double subtotal;
	protected double tax;
	protected double total;

	public Product(String productCode) {
		super();
		this.productCode = productCode;
	}

	public Product(Product product) {
		this.productCode = product.productCode;
	}

	@Override
	public String toString() {
		return "productCode=" + productCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public abstract double getCost();

	public abstract void calcSubtotalTaxTotal(Invoice invoice);
	
	public abstract int compareTo(Product o);
}