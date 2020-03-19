/******************************************************************************
 * This Class is use to set the person's information. This class is constructor
 * for the Person.
 * 
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file Person.java

 * @version 1.1
 * @since Oct 1, 2018
 *******************************************************************************/

package entities;

import java.util.Arrays;

public class Person {
	private String personCode, lastName, firstName;
	private Address address;
	private String emailAddresses;

	public Person(String personCode, String lastName, String firstName, Address address, String emailAddresses) {
		super();
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emailAddresses = emailAddresses;
	}

	public Person(Person person) {
		super();
		this.personCode = person.personCode;
		this.firstName = person.firstName;
		this.lastName = person.lastName;
		this.address = person.address;
		this.emailAddresses = person.emailAddresses;
	}

	@Override
	public String toString() {
		return "Person [personCode=" + personCode + ", firstName=" + firstName + ", lastName=" + lastName + ", address="
				+ address + ", emailAddresses=" + (emailAddresses != null ? Arrays.asList(emailAddresses) : null) + "]";
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(String emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((emailAddresses == null) ? 0 : emailAddresses.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((personCode == null) ? 0 : personCode.hashCode());
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
		Person other = (Person) obj;
		if(address == null) {
			if(other.address != null)
				return false;
		} else if(!address.equals(other.address))
			return false;
		if(emailAddresses == null) {
			if(other.emailAddresses != null)
				return false;
		} else if(!emailAddresses.equals(other.emailAddresses))
			return false;
		if(firstName == null) {
			if(other.firstName != null)
				return false;
		} else if(!firstName.equals(other.firstName))
			return false;
		if(lastName == null) {
			if(other.lastName != null)
				return false;
		} else if(!lastName.equals(other.lastName))
			return false;
		if(personCode == null) {
			if(other.personCode != null)
				return false;
		} else if(!personCode.equals(other.personCode))
			return false;
		return true;
	}

}
