/*************************************************************************
 * This class is constructor for the Address.
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file Address.java

 * @version 1.1
 * @since Oct 1, 2018
 *************************************************************************/
package entities;

public class Address {
	private String street, city, state, zip, country;

	public Address(String street, String city, String state, String zip, String country) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", state=" + state + ", zip=" + zip + ", country="
				+ country + "]";
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		Address other = (Address) obj;
		if(city == null) {
			if(other.city != null)
				return false;
		} else if(!city.equals(other.city))
			return false;
		if(country == null) {
			if(other.country != null)
				return false;
		} else if(!country.equals(other.country))
			return false;
		if(state == null) {
			if(other.state != null)
				return false;
		} else if(!state.equals(other.state))
			return false;
		if(street == null) {
			if(other.street != null)
				return false;
		} else if(!street.equals(other.street))
			return false;
		if(zip == null) {
			if(other.zip != null)
				return false;
		} else if(!zip.equals(other.zip))
			return false;
		return true;
	}
}
