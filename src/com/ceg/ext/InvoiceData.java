/*************************************************************************
 *This file is a API to update file to the database. Include method like
 *remove, update, select, etc.
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file InvoiceData.java
 * @author 
 * @version 1.0
 * @since November 1, 2018
 *************************************************************************/

package com.ceg.ext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sql.DatabaseQuerier;

/*
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 * 15 methods in total, add more if required.
 * Do not change any method signatures or the package name.
 * 
 */

public class InvoiceData {

	public static DatabaseQuerier dbq = new DatabaseQuerier();

	/**
	 * 1. Method that removes every person record from the database
	 * 
	 */
	public static void removeAllPersons() {
		dbq.executeUpdate("SET foreign_key_checks = 0", null);
		dbq.executeUpdate("Delete From Persons", null);
		dbq.executeUpdate("Delete From Email", null);
		dbq.executeUpdate("DELETE FROM Address WHERE Type = \"Persons\"", null);
		dbq.executeUpdate("SET foreign_key_checks = 1", null);
	}

	/**
	 * 2. Method to add a person record to the database with the provided data.
	 * 
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city,
			String state, String zip, String country) {
		dbq.executeUpdate(
				"INSERT INTO Address(`Type`,`Street`, `City`, `State`, `Zip`, `Country`) " + "VALUES(\"Persons\", \""
						+ street + "\", \"" + city + "\", \"" + state + "\", \"" + zip + "\", \"" + country + "\")",
				null);
		dbq.executeUpdate("INSERT INTO Persons(PersonCode, FirstName, LastName, AddressID) " + "VALUES (\"" + personCode
				+ "\", \"" + firstName + "\", \"" + lastName + "\", LAST_INSERT_ID())", null);
	}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		ArrayList<String> inputStr = new ArrayList<String>();
		int personID = 9999;
		inputStr.add(personCode);
		try {
			ResultSet rs = dbq.executeQuery("SELECT PersonID FROM Persons WHERE PersonCode = ?", inputStr);
			while (rs.next()) {
				personID = rs.getInt("PersonID");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		inputStr.clear();
		dbq.executeUpdate("INSERT INTO Email(PersonID, Email) VALUES(\"" + personID + "\", \"" + email + "\")", null);
	}

	/**
	 * 4. Method that removes every customer record from the database
	 * 
	 * 
	 */
	public static void removeAllCustomers() {
		dbq.executeUpdate("SET foreign_key_checks = 0", null);
		dbq.executeUpdate("Delete From Customer", null);
		dbq.executeUpdate("DELETE FROM Address WHERE Type = \"Customer\"", null);
		dbq.executeUpdate("SET foreign_key_checks = 1", null);
	}

	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,
			String name, String street, String city, String state, String zip, String country) {
		dbq.executeUpdate("INSERT INTO Address(`Type`,Street, City, State, Zip, Country) " + "VALUES(\"Customer\", \""
				+ street + "\", \"" + city + "\", \"" + state + "\", \"" + zip + "\", \"" + country + "\")", null);
		ArrayList<String> inputStr = new ArrayList<String>();
		int output = 9999;
		inputStr.add(primaryContactPersonCode);
		try {
			ResultSet rs = dbq.executeQuery("SELECT PersonID FROM Persons WHERE PersonCode = ?", inputStr);
			while (rs.next()) {
				output = rs.getInt("PersonID");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbq.executeUpdate(
				"INSERT INTO Customer(CustomerCode, CustomerType, PersonID, Name, AddressID)" + "VALUES(\""
						+ customerCode + "\", \"" + customerType + "\", " + output + ", \"" + name + "\", LAST_INSERT_ID())",
				null);
	}

	/**
	 * 5. Removes all product records from the database
	 */
	public static void removeAllProducts() {
		dbq.executeUpdate("SET foreign_key_checks = 0", null);
		dbq.executeUpdate("Delete From Product", null);
		dbq.executeUpdate("DELETE FROM Address WHERE Type = \"Product\"", null);
		dbq.executeUpdate("SET foreign_key_checks = 1", null);
	}

	/**
	 * 6. Adds an movieTicket record to the database with the provided data.
	 */
	public static void addMovieTicket(String productCode, String dateTime, String movieName, String street, String city,
			String state, String zip, String country, String screenNo, double pricePerUnit) {
		dbq.executeUpdate(
				"INSERT INTO Address(`Type`,`Street`, `City`, `State`, `Zip`, `Country`) " + "VALUES(\"Product\", \""
						+ street + "\", \"" + city + "\", \"" + state + "\", \"" + zip + "\", \"" + country + "\")",
				null);
		dbq.executeUpdate(
				"INSERT INTO Product(MovieDateTime, ProductCode, ProductType, ProductName, SeasonPassStart, SeasonPassEnd, AddressID, ScreenNo, Price) "
						+ "VALUES(\"" + dateTime + "\",\"" + productCode + "\", \"M\", \"" + movieName
						+ "\", NULL, Null, LAST_INSERT_ID(), \"" + screenNo + "\", " + pricePerUnit + ")",
				null);
	}

	/**
	 * 7. Adds a seasonPass record to the database with the provided data.
	 */
	public static void addSeasonPass(String productCode, String name, String seasonStartDate, String seasonEndDate,
			double cost) {
		dbq.executeUpdate(
				"INSERT INTO Product(MovieDateTime, ProductCode, ProductType, ProductName, SeasonPassStart, SeasonPassEnd, AddressID, ScreenNo, Price) "
						+ "VALUES(NULL,\"" + productCode + "\", \"S\", \"" + name + "\", \"" + seasonStartDate
						+ "\", \"" + seasonEndDate + "\", Null, Null, " + cost + ")",
				null);

	}

	/**
	 * 8. Adds a ParkingPass record to the database with the provided data.
	 */
	public static void addParkingPass(String productCode, double parkingFee) {
		dbq.executeUpdate(
				"INSERT INTO Product(MovieDateTime, ProductCode, ProductType, ProductName, SeasonPassStart, SeasonPassEnd, AddressID, ScreenNo, Price) "
						+ "VALUES(NULL, \"" + productCode + "\", \"P\", Null, Null, Null, Null, Null, "
						+ parkingFee + ")",
				null);
	}

	/**
	 * 9. Adds a refreshment record to the database with the provided data.
	 */
	public static void addRefreshment(String productCode, String name, double cost) {
		dbq.executeUpdate(
				"INSERT INTO Product(MovieDateTime,ProductCode, ProductType, ProductName, SeasonPassStart, SeasonPassEnd, AddressID, ScreenNo, Price) "
						+ "VALUES(NULL, \"" + productCode + "\", \"R\", \"" + name + "\", Null, Null, Null, Null, "
						+ cost + ")",
				null);
	}

	/**
	 * 10. Removes all invoice records from the database
	 */
	public static void removeAllInvoices() {
		dbq.executeUpdate("SET foreign_key_checks = 0", null);
		dbq.executeUpdate("Delete From Invoice", null);
		dbq.executeUpdate("Delete From InvoiceProduct", null);
		dbq.executeUpdate("SET foreign_key_checks = 1", null);
	}

	/**
	 * 11. Adds an invoice record to the database with the given data.
	 */
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode, String invoiceDate) {
		ArrayList<String> inputStr = new ArrayList<String>();
		int customerOutput = 9999;
		inputStr.add(customerCode);
		try {
			ResultSet rs = dbq.executeQuery("SELECT CustomerID FROM Customer WHERE CustomerCode = ?", inputStr);
			while (rs.next()) {
				customerOutput = rs.getInt("CustomerID");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		inputStr.clear();
		int salesPersonOutput = 9999;
		inputStr.add(salesPersonCode);
		try {
			ResultSet rs = dbq.executeQuery("SELECT PersonID FROM Persons WHERE PersonCode = ?", inputStr);
			while (rs.next()) {
				salesPersonOutput = rs.getInt("PersonID");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbq.executeUpdate("INSERT INTO Invoice(InvoiceCode, CustomerID, PersonID, Date) " + "VALUES(\"" + invoiceCode
				+ "\", \"" + customerOutput + "\", \"" + salesPersonOutput + "\", \"" + invoiceDate + "\")", null);
	}

	/**
	 * 12. Adds a particular movieticket (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with the
	 * given number of units
	 */

	public static void addMovieTicketToInvoice(String invoiceCode, String productCode, int quantity) {
		ArrayList<String> inputStr = new ArrayList<String>();
		int invoiceOutput = 9999;
		inputStr.add(invoiceCode);
		try {
			ResultSet rs = dbq.executeQuery("SELECT InvoiceID FROM Invoice WHERE InvoiceCode = ?", inputStr);
			while (rs.next()) {
				invoiceOutput = rs.getInt("InvoiceID");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		inputStr.clear();
		int productOutput = 9999;
		inputStr.add(productCode);
		try {
			ResultSet rs = dbq.executeQuery("SELECT ProductID FROM Product WHERE ProductCode = ?", inputStr);
			while (rs.next()) {
				productOutput = rs.getInt("ProductID");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbq.executeUpdate("INSERT INTO InvoiceProduct(InvoiceID, ProductID, Quantity) VALUES( \"" + invoiceOutput + "\",\""
				+ productOutput + "\",\"" + quantity + "\")", null);
	}

	/*
	 * 13. Adds a particular seasonpass (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with the
	 * given begin/end dates
	 */
	public static void addSeasonPassToInvoice(String invoiceCode, String productCode, int quantity) {
		ArrayList<String> inputStr = new ArrayList<String>();
		int invoiceOutput = 9999;
		inputStr.add(invoiceCode);
		try {
			ResultSet rs = dbq.executeQuery("SELECT InvoiceID FROM Invoice WHERE InvoiceCode = ?", inputStr);
			while (rs.next()) {
				invoiceOutput = rs.getInt("InvoiceID");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		inputStr.clear();
		int productOutput = 9999;
		inputStr.add(productCode);
		try {
			ResultSet rs = dbq.executeQuery("SELECT ProductID FROM Product WHERE ProductCode = ?", inputStr);
			while (rs.next()) {
				productOutput = rs.getInt("ProductID");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbq.executeUpdate("INSERT INTO InvoiceProduct(InvoiceID, ProductID, Quantity) VALUES( \"" + invoiceOutput + "\",\""
				+ productOutput + "\",\"" + quantity + "\")", null);
	}

	/**
	 * 14. Adds a particular ParkingPass (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with the
	 * given number of quantity. NOTE: ticketCode may be null
	 */
	public static void addParkingPassToInvoice(String invoiceCode, String productCode, int quantity,
			String ticketCode) {
		ArrayList<String> inputStr = new ArrayList<String>();
		int invoiceOutput = 9999;
		inputStr.add(invoiceCode);
		try {
			ResultSet rs = dbq.executeQuery("SELECT InvoiceID FROM Invoice WHERE InvoiceCode = ?", inputStr);
			while (rs.next()) {
				invoiceOutput = rs.getInt("InvoiceID");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		inputStr.clear();
		int productOutput = 9999;
		inputStr.add(productCode);
		try {
			ResultSet rs = dbq.executeQuery("SELECT ProductID FROM Product WHERE ProductCode = ?", inputStr);
			while (rs.next()) {
				productOutput = rs.getInt("ProductID");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (ticketCode != null) {
			inputStr.clear();
			int ticketOutput = 9999;
			inputStr.add(ticketCode);
			try {
				ResultSet rs = dbq.executeQuery("SELECT ProductID FROM Product WHERE ProductCode = ?", inputStr);
				while (rs.next()) {
					ticketOutput = rs.getInt("ProductID");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			dbq.executeUpdate("INSERT INTO InvoiceProduct(InvoiceID, ProductID, Quantity, TicketID) VALUES( \""
					+ invoiceOutput + "\",\"" + productOutput + "\",\"" + quantity + "\", " + ticketOutput + ")", null);
		} else {
			dbq.executeUpdate("INSERT INTO InvoiceProduct(InvoiceID, ProductID, Quantity) VALUES( \"" + invoiceOutput
					+ "\",\"" + productOutput + "\",\"" + quantity + "\")", null);
		}
	}

	/**
	 * 15. Adds a particular refreshment (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with the
	 * given number of quantity.
	 */
	public static void addRefreshmentToInvoice(String invoiceCode, String productCode, int quantity) {

		ArrayList<String> inputStr = new ArrayList<String>();
		int invoiceOutput = 9999;
		inputStr.add(invoiceCode);
		try {
			ResultSet rs = dbq.executeQuery("SELECT InvoiceID FROM Invoice WHERE InvoiceCode = ?", inputStr);
			while (rs.next()) {
				invoiceOutput = rs.getInt("InvoiceID");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		inputStr.clear();
		int productOutput = 9999;
		inputStr.add(productCode);
		try {
			ResultSet rs = dbq.executeQuery("SELECT ProductID FROM Product WHERE ProductCode = ?", inputStr);
			while (rs.next()) {
				productOutput = rs.getInt("ProductID");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbq.executeUpdate("INSERT INTO InvoiceProduct(InvoiceID, ProductID, Quantity) VALUES( \"" + invoiceOutput + "\",\""
				+ productOutput + "\",\"" + quantity + "\")", null);
	}

}
