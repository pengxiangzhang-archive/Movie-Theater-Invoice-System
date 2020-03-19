/******************************************************************************
 * This class is a constructor for invoice. It used to generate the Invoice.
 * 
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file Invoice.java

 * @version 1.1
 * @since Oct 1, 2018
 *******************************************************************************/

package entities;

import java.time.LocalDate;
import java.util.HashMap;

public class Invoice {
	private String invoiceCode;
	private Customer customer;
	private Person salesperson;
	private LocalDate invoiceDate;
	private HashMap<Product, Integer> productArr;

	private double subtotal;
	private double fees;
	private double taxes;
	private double discount;
	private double finalTotal;
	private int passCount; // total number of passes/tickets in the productArr

	public Invoice(String invoiceCode, Customer customer, Person salesperson, String invoiceDate,
			HashMap<Product, Integer> productArr) {
		super();
		this.invoiceCode = invoiceCode;
		this.customer = customer;
		this.salesperson = salesperson;
		this.invoiceDate = LocalDate.parse(invoiceDate);
		this.productArr = productArr;
		calcPassCount();
		calcSubtotalTaxesTotal();
		calcFees();
		calcDiscount();
		calcFinalTotal();
	}

	public Invoice(Invoice invoice) {
		this.invoiceCode = invoice.invoiceCode;
		this.customer = invoice.customer;
		this.salesperson = invoice.salesperson;
		this.salesperson = invoice.salesperson;
		this.invoiceDate = invoice.invoiceDate;
		this.productArr = invoice.productArr;
	}

	@Override
	public String toString() {
		return "Invoice [" + (invoiceCode != null ? "invoiceCode=" + invoiceCode + ", " : "")
				+ (customer != null ? "customer=" + customer + ", " : "")
				+ (salesperson != null ? "salesperson=" + salesperson + ", " : "")
				+ (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", " : "")
				+ (productArr != null ? "productArr=" + productArr + ", " : "") + "subtotal=" + subtotal + ", fees="
				+ fees + ", taxes=" + taxes + ", discount=" + discount + ", finalTotal=" + finalTotal + ", passCount="
				+ passCount + "]";
	}
	
	public static void printExec(NodeList<Invoice> invoiceArr) {
		System.out.println(makeLine(35, '='));
		System.out.println("    EXECUTIVE  SUMMARY  REPORT (Sorted by Invoice Total)");
		System.out.println(makeLine(35, '='));
		System.out.println(String.format("%-10s %-30s %-10s %-20s %11s %11s %11s %11s %11s", "Invoice", "Customer",
				"Type", "Salesperson", "Subtotal", "Fees", "Taxes", "Discount", "Total"));
		double totalSubtotal = 0, totalFees = 0, totalTaxes = 0, totalDiscount = 0, totalTotal = 0;
		String t;
		for (Invoice i : invoiceArr) {
			totalSubtotal += i.getSubtotal();
			totalFees += i.getFees();
			totalTaxes += i.getTaxes();
			totalDiscount += i.getDiscount();
			totalTotal += i.getFinalTotal();
			if (i.getCustomer() instanceof Student)
				t = "[Student]";
			else
				t = "[General]";
			System.out.println(String.format("%-10s %-30s %-10s %-20s $%10.2f $%10.2f $%10.2f $%10.2f $%10.2f",
					i.getInvoiceCode(), i.getCustomer().getName(), t,
					i.getSalesperson().getLastName() + ", " + i.getSalesperson().getFirstName(), i.getSubtotal(),
					i.getFees(), i.getTaxes(), i.getDiscount(), i.getFinalTotal()));
		}
		System.out.println(makeLine(133, '='));
		System.out.println(String.format("%-73s $%10.2f $%10.2f $%10.2f $%10.2f $%10.2f", "Total", totalSubtotal,
				totalFees, totalTaxes, totalDiscount, totalTotal));
		// End of first section
		System.out.println("\n\n\nINDIVIDUAL INVOICE REPORT DETAILS");

	}
	public void print() {
		System.out.println(makeLine(35, '='));
		String t;
		if(customer instanceof Student)
			t = "[Student]";
		else
			t = "[General]";
		System.out.println("Invoice" + "     " + invoiceCode);
		System.out.println(
				makeLine(35, '=') + "\nSalesperson: " + salesperson.getLastName() + ", " + salesperson.getFirstName());
		System.out.println("Customer Info:");
		System.out.println("  " + customer.getName() + " (" + customer.getCustomerCode() + ")");
		System.out.println("  " + t);
		System.out.println(
				"  " + customer.getPrimaryContact().getFirstName() + " " + customer.getPrimaryContact().getLastName());
		System.out.println("  " + customer.getAddress().getStreet());
		System.out.println("  " + customer.getAddress().getCity() + " " + customer.getAddress().getState() + " "
				+ customer.getAddress().getZip() + " " + customer.getAddress().getCountry());
		System.out.println(makeLine(35, '-'));
		System.out.println(String.format("%-8s %-60s %9s %9s %9s", "Code", "Item", "SubTotal", "Tax", "Total"));

		for(Product p : productArr.keySet()) {
			if(p instanceof ParkingPass || p instanceof Refreshment)
				System.out.println(String.format("%-8s %-60s $%8.2f $%8.2f $%8.2f", p.getProductCode(),
						convertToString(p), p.getSubtotal(), p.getTax(), p.getTotal()));
			else {
				System.out.println(String.format("%-8s %-60s $%8.2f $%8.2f $%8.2f", p.getProductCode(),
						convertToString(p).split(";")[0], p.getSubtotal(), p.getTax(), p.getTotal()));
				System.out.println("        " + convertToString(p).split(";")[1]);
			}
		}
		System.out.println(makeLine(70, ' ') + makeLine(29, '='));
		System.out.println(String.format("%-69s $%8.2f $%8.2f $%8.2f", "SUB-TOTALS", subtotal, taxes,
				finalTotal + discount - (taxes + fees)));
		if(customer instanceof Student) {
			System.out.println(String.format("%-89s $%8.2f", "DISCOUNT (8%  STUDENT)", discount * -1));
			System.out.println(String.format("%-89s $%8.2f", "ADDITIONAL FEE (Student)", customer.getFees()));
			System.out.println(String.format("%-89s $%8.2f", "TOTAL", finalTotal));
		} else {
			System.out.println(String.format("%-89s $%8.2f", "TOTAL", finalTotal));
		}
		System.out.println("\n              Thankyou for your purchase!\n\n");
	}

	/*
	 * Returns a String that represents the product as a String (better suited for
	 * invoices than Product.toString())
	 */
	private String convertToString(Product p) {
		StringBuilder output = new StringBuilder("");
		if(p instanceof MovieTicket)
			output.append("Movie Ticket \'" + ((MovieTicket) p).getMovieName() + "\' @ "
					+ ((MovieTicket) p).getAddress().getStreet() + "; " + ((MovieTicket) p).getDateTime().toString()
					+ "(" + productArr.get(p) + " units @ $" + p.getCost() + "/unit)");
		else if(p instanceof SeasonPass)
			output.append("Season Pass - " + ((SeasonPass) p).getName() + ";" + " (" + productArr.get(p) + " units @ $"
					+ p.getCost() + "/unit)");
		else if(p instanceof ParkingPass && ((ParkingPass) p).getTicketCode() != null)
			output.append("Parking Pass " + ((ParkingPass) p).getTicketCode() + " (" + productArr.get(p) + " units @ $"
					+ p.getCost() + " with " + ((ParkingPass) p).getNumFree() + " free)");
		else if(p instanceof ParkingPass)
			output.append("Parking Pass " + " (" + productArr.get(p) + " units @ $" + p.getCost() + " with "
					+ ((ParkingPass) p).getNumFree() + " free)");
		else if(p instanceof Refreshment)
			output.append(((Refreshment) p).getName() + " (" + productArr.get(p) + " units @ $" + p.getCost()
					+ "/unit with 5% off)");
		return output.toString();
	}

	private static String makeLine(int size, char c) {
		StringBuilder output = new StringBuilder();
		for(int i = 0; i < size; i++)
			output.append(c);
		return output.toString();
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomerCode(Customer customer) {
		this.customer = customer;
	}

	public Person getSalesperson() {
		return salesperson;
	}

	public void setSalesperson(Person salesperson) {
		this.salesperson = salesperson;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = LocalDate.parse(invoiceDate);
	}

	public HashMap<Product, Integer> getProductArr() {
		return productArr;
	}

	public void setProductArr(HashMap<Product, Integer> productArr) {
		this.productArr = productArr;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public double getFees() {
		return fees;
	}

	public double getTaxes() {
		return taxes;
	}

	public double getDiscount() {
		return discount;
	}

	public double getFinalTotal() {
		return finalTotal;
	}

	public int getPassCount() {
		return passCount;
	}

	private void calcPassCount() {
		int passCount = 0;
		for(Product p : productArr.keySet()) {
			if(p instanceof MovieTicket || p instanceof SeasonPass)
				passCount += productArr.get(p);
		}
		this.passCount = passCount;
	}

	private void calcSubtotalTaxesTotal() {
		for(Product p : productArr.keySet()) {
			p.calcSubtotalTaxTotal(this);
			subtotal += p.getSubtotal();
			taxes += p.getTax();
		}
	}

	private void calcFees() {
		this.fees = customer.getFees();
	}

	private void calcDiscount() {
		discount = subtotal * customer.getDiscountPercent();
	}

	private void calcFinalTotal() {
		finalTotal = (subtotal + taxes + fees) - discount;
	}

	public void recalculate() {
		passCount = 0;
		subtotal = 0;
		taxes = 0;
		fees = 0;
		discount = 0;
		finalTotal = 0;
		calcPassCount();
		calcSubtotalTaxesTotal();
		calcFees();
		calcDiscount();
		calcFinalTotal();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		long temp;
		temp = Double.doubleToLongBits(discount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(fees);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(finalTotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((invoiceCode == null) ? 0 : invoiceCode.hashCode());
		result = prime * result + ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
		result = prime * result + passCount;
		result = prime * result + ((productArr == null) ? 0 : productArr.hashCode());
		result = prime * result + ((salesperson == null) ? 0 : salesperson.hashCode());
		temp = Double.doubleToLongBits(subtotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(taxes);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		if(customer == null) {
			if(other.customer != null)
				return false;
		} else if(!customer.equals(other.customer))
			return false;
		if(Double.doubleToLongBits(discount) != Double.doubleToLongBits(other.discount))
			return false;
		if(Double.doubleToLongBits(fees) != Double.doubleToLongBits(other.fees))
			return false;
		if(Double.doubleToLongBits(finalTotal) != Double.doubleToLongBits(other.finalTotal))
			return false;
		if(invoiceCode == null) {
			if(other.invoiceCode != null)
				return false;
		} else if(!invoiceCode.equals(other.invoiceCode))
			return false;
		if(invoiceDate == null) {
			if(other.invoiceDate != null)
				return false;
		} else if(!invoiceDate.equals(other.invoiceDate))
			return false;
		if(passCount != other.passCount)
			return false;
		if(productArr == null) {
			if(other.productArr != null)
				return false;
		} else if(!productArr.equals(other.productArr))
			return false;
		if(salesperson == null) {
			if(other.salesperson != null)
				return false;
		} else if(!salesperson.equals(other.salesperson))
			return false;
		if(Double.doubleToLongBits(subtotal) != Double.doubleToLongBits(other.subtotal))
			return false;
		if(Double.doubleToLongBits(taxes) != Double.doubleToLongBits(other.taxes))
			return false;
		return true;
	}

}
