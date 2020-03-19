/******************************************************************************
 * This class is a constructor for customer. And it abstract to General Class
 * 
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file Customer.java

 * @version 1.1
 * @since Oct 1, 2018
 *******************************************************************************/

package entities;

public abstract class Customer {
	protected Person primaryContact;
	protected String customerCode, name;
	protected Address address;

	public Customer(String customerCode, Person primaryContact, String name, Address address) {
		super();
		this.customerCode = customerCode;
		this.primaryContact = primaryContact;
		this.name = name;
		this.address = address;
	}

	public Customer(Customer customer) {
		super();
		this.customerCode = customer.customerCode;
		this.primaryContact = customer.primaryContact;
		this.name = customer.name;
		this.address = customer.address;
	}

	@Override
	public abstract String toString();

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Person getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(Person primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((customerCode == null) ? 0 : customerCode.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((primaryContact == null) ? 0 : primaryContact.hashCode());
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
		Customer other = (Customer) obj;
		if(address == null) {
			if(other.address != null)
				return false;
		} else if(!address.equals(other.address))
			return false;
		if(customerCode == null) {
			if(other.customerCode != null)
				return false;
		} else if(!customerCode.equals(other.customerCode))
			return false;
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.equals(other.name))
			return false;
		if(primaryContact == null) {
			if(other.primaryContact != null)
				return false;
		} else if(!primaryContact.equals(other.primaryContact))
			return false;
		return true;
	}

	public abstract double getDiscountPercent();

	public abstract boolean isTaxable();

	public abstract double getFees();
}
