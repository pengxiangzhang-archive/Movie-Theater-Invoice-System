/******************************************************************************
 * This class is a subclass of Customer which student is a part of customer
 * It set the special benefit for student.
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file Student.java
 * @author 
 * @version 1.1
 * @since Oct 1, 2018
 *******************************************************************************/

package entities;

public class Student extends Customer {
	public final double discountPercent = 0.08;
	public final boolean taxable = false;
	public final double fee = 6.75;

	public Student(String customerCode, Person primaryContact, String name, Address address) {
		super(customerCode, primaryContact, name, address);
	}

	public Student(Customer student) {
		super(student);
	}

	@Override
	public String toString() {
		return "Student [discountPercent=" + discountPercent + ", taxable=" + taxable + ", fee=" + fee + ", "
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
