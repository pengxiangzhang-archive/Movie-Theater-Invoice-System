package reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.ceg.ext.InvoiceData;

import entities.*;

public class DatabaseReader {

	public static ArrayList<Person> readPersons() throws SQLException {
		ArrayList<Person> personArr = new ArrayList<Person>();
		ResultSet rs = InvoiceData.dbq.executeQuery("SELECT Persons.PersonCode, Persons.LastName, Persons.FirstName, "
				+ "Address.Street, Address.City,Address.State, Address.ZIP, Address.Country, Email.Email "
				+ "From Persons INNER JOIN Address on Persons.AddressID=Address.AddressID INNER JOIN Email "
				+ "on Persons.PersonID = Email.PersonID", null);
		while (rs.next()) {
			personArr.add(new Person(rs.getString(1), rs.getString(3), rs.getString(2),
					new Address(rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)),
					rs.getString(9)));
		}
		return personArr;
	}

	public static ArrayList<Customer> readCustomers(ArrayList<Person> personArr) throws SQLException {
		ArrayList<Customer> customerArr = new ArrayList<Customer>();
		ResultSet rs = InvoiceData.dbq.executeQuery("Select Customer.CustomerCode, Persons.PersonCode, "
				+ "Customer.Name, Address.Street, Address.City,Address.State, Address.ZIP, "
				+ "Address.Country, Customer.CustomerType from Customer INNER JOIN Address on "
				+ "Customer.AddressID=Address.AddressID INNER JOIN Persons on Customer.PersonID=Persons.PersonID",
				null);
		Person person = null;
		while (rs.next()) {
			for (Person p : personArr) {
				if (p.getPersonCode().equals(rs.getString(2)))
					person = p;
			}
			if (rs.getString(9).equals("General")) {
				customerArr.add(new General(rs.getString(1), new Person(person), rs.getString(3), new Address(
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8))));
			} else {
				customerArr.add(new Student(rs.getString(1), new Person(person), rs.getString(3), new Address(
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8))));
			}
		}
		return customerArr;
	}

	public static ArrayList<Product> readProducts() throws SQLException {
		ArrayList<Product> productArr = new ArrayList<Product>();
		Product product = null;
		ResultSet rsM = InvoiceData.dbq.executeQuery("Select Product.ProductCode, Product.ProductType, "
				+ "Product.MovieDateTime, Product.ProductName, Address.Street, Address.City, Address.State, "
				+ "Address.ZIP, Address.Country, Product.ScreenNo, Product.Price from Product INNER JOIN Address "
				+ "on Product.AddressID=Address.AddressID where Product.ProductType = 'M'", null);

		ResultSet rsS = InvoiceData.dbq.executeQuery("Select  Product.ProductCode, Product.ProductType, "
				+ "Product.ProductName, Product.SeasonPassStart, Product.SeasonPassEnd, Product.Price from Product "
				+ "where Product.ProductType = 'S'", null);

		ResultSet rsP = InvoiceData.dbq.executeQuery("Select Product.ProductCode, Product.ProductType, "
				+ "Product.Price from Product where Product.ProductType = 'P'", null);

		ResultSet rsR = InvoiceData.dbq.executeQuery("Select Product.ProductCode, Product.ProductType, "
				+ "Product.ProductName, Product.Price from Product where Product.ProductType = 'R'", null);

		while (rsM.next()) {
			product = new MovieTicket(
					rsM.getString(1), rsM.getString(3), rsM.getString(4), new Address(rsM.getString(5),
							rsM.getString(6), rsM.getString(7), rsM.getString(8), rsM.getString(9)),
					rsM.getString(10), Double.parseDouble(rsM.getString(11)));
			productArr.add(product);
		}
		while (rsS.next()) {
			product = new SeasonPass(rsS.getString(1), rsS.getString(3), rsS.getString(4), rsS.getString(5),
					Double.parseDouble(rsS.getString(6)));
			productArr.add(product);
		}
		while (rsP.next()) {
			product = new ParkingPass(rsP.getString(1), Double.parseDouble(rsP.getString(3)));
			productArr.add(product);
		}
		while (rsR.next()) {
			product = new Refreshment(rsR.getString(1), rsR.getString(3), Double.parseDouble(rsR.getString(4)));
			productArr.add(product);
		}
		return productArr;
	}

	public static ArrayList<Invoice> readInvoice(ArrayList<Customer> customerArr, ArrayList<Person> personArr,
			ArrayList<Product> productArr) throws SQLException {
		ArrayList<Invoice> invoiceArr = new ArrayList<Invoice>();
		ResultSet rs = InvoiceData.dbq.executeQuery("SELECT Invoice.InvoiceCode, Customer.CustomerCode, "
				+ "Persons.PersonCode, Invoice.Date, Product.ProductCode, InvoiceProduct.Quantity, "
				+ "InvoiceProduct.TicketID from InvoiceProduct inner join Invoice on InvoiceProduct.InvoiceID=Invoice.InvoiceID "
				+ "inner join Customer on Invoice.CustomerID=Customer.CustomerID inner join Persons on Invoice.PersonID=Persons.PersonID "
				+ "inner join Product on Product.ProductID = InvoiceProduct.ProductID", null);
		Customer tempCustomer = null;
		Person tempPerson = null;
		Product tempProduct = null;
		boolean makeANewOne = true;
		while (rs.next()) {
			for (Invoice i : invoiceArr) {
				if (i.getInvoiceCode().equals(rs.getString(1)))
					makeANewOne = false;
			}
			HashMap<Product, Integer> tempProductHashmap = new HashMap<Product, Integer>();
			if (makeANewOne) {
				for (Customer c : customerArr) {
					if (c.getCustomerCode().equals(rs.getString(2))) {
						if (c instanceof Student)
							tempCustomer = new Student(c);
						else
							tempCustomer = new General(c);
					}
				}
				for (Person p : personArr) {
					if (p.getPersonCode().equals(rs.getString(3))) {
						tempPerson = new Person(p);
					}
				}
				for (Product p : productArr) {
					if (p.getProductCode().equals(rs.getString(5))) {
						if (p instanceof ParkingPass && rs.getString(7) != null) {
							tempProduct = new ParkingPass(p);
							((ParkingPass) tempProduct).setTicketCode(rs.getString(7));
						} else if (p instanceof ParkingPass)
							tempProduct = new ParkingPass(p);
						else if (p instanceof Refreshment)
							tempProduct = new Refreshment(p);
						else if (p instanceof MovieTicket)
							tempProduct = new MovieTicket(p);
						else if (p instanceof SeasonPass)
							tempProduct = new SeasonPass(p);
						tempProductHashmap.put(tempProduct, Integer.parseInt(rs.getString(6)));
					}
				}
				if (tempCustomer == null || tempPerson == null || tempProductHashmap.isEmpty())
					System.out.println("WARNING ERROR CREATING INVOICE");
				if (tempCustomer instanceof Student)
					invoiceArr.add(new Invoice(rs.getString(1), new Student(tempCustomer), new Person(tempPerson),
							rs.getString(4), tempProductHashmap));
				else if (tempCustomer instanceof General)
					invoiceArr.add(new Invoice(rs.getString(1), new General(tempCustomer), new Person(tempPerson),
							rs.getString(4), tempProductHashmap));
				else
					System.out.println("WARNING ERROR CREATING INVOICE");
				tempProduct = null;
			} else {
				for (Product p : productArr) {
					if (p.getProductCode().equals(rs.getString(5))) {
						if (p instanceof ParkingPass && rs.getString(7) != null) {
							tempProduct = new ParkingPass(p);
							((ParkingPass) tempProduct).setTicketCode(getTicketCode(rs.getString(7)));
						} else if (p instanceof ParkingPass)
							tempProduct = new ParkingPass(p);
						else if (p instanceof Refreshment)
							tempProduct = new Refreshment(p);
						else if (p instanceof MovieTicket)
							tempProduct = new MovieTicket(p);
						else if (p instanceof SeasonPass)
							tempProduct = new SeasonPass(p);
					}
				}
				for (Invoice i : invoiceArr) {
					if (i.getInvoiceCode().equals(rs.getString(1))) {
						i.getProductArr().put(tempProduct, Integer.parseInt(rs.getString(6)));
						i.recalculate();
					}
				}
				makeANewOne = true;
			}
		}
		return invoiceArr;
	}
	
	private static String getTicketCode(String ticketID) throws SQLException {
		ResultSet rs = InvoiceData.dbq.executeQuery("SELECT Product.ProductCode FROM Product JOIN InvoiceProduct "
				+ "ON Product.ProductID = InvoiceProduct.ProductID WHERE InvoiceProduct.ProductID = " + ticketID, null);
		rs.next();
		return rs.getString(1);
	}
}
