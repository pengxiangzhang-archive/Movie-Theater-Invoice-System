/******************************************************************************
 * This class extends Customer. It set the fee for Customer.
 * 
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file General.java

 * @version 1.1
 * @since Oct 1, 2018
 *******************************************************************************/

package entities;

public class General extends Customer {
	public final double discountPercent = 0.00;
	public final boolean taxable = true;
	public final double fee = 0.00;

	public General(String customerCode, Person primaryContact, String name, Address address) {
		super(customerCode, primaryContact, name, address);
	}

	public General(Customer tempCustomer) {
		super(tempCustomer);
	}

	@Override
	public String toString() {
		return "General [discountPercent=" + discountPercent + ", taxable=" + taxable + ", fee=" + fee + ", "
				+ (primaryContact != null ? "primaryContact=" + primaryContact + ", " : "")
				+ (customerCode != null ? "customerCode=" + customerCode + ", " : "")
				+ (name != null ? "name=" + name + ", " : "") + (address != null ? "address=" + address : "") + "]";
	}

	@Override
	public double getDiscountPercent() {
		return discountPercent;
	}

	@Override
	public boolean isTaxable() {
		return taxable;
	}

	@Override
	public double getFees() {
		return fee;
	}
}
