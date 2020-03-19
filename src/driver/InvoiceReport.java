/*************************************************************************
 *This is a driver class, it drive the function to print out the Invoice
 *Report.
 *
 * CSCE156 Fall 2018
 * Assignment 5
 * @file InvoiceReport.java
 * @author 
 * @version 1.04
 * @since November 8, 2018
 *************************************************************************/


package driver;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ceg.ext.InvoiceData;

import entities.Customer;
import entities.Invoice;
import entities.InvoiceCompByTotal;
import entities.NodeList;
import entities.Person;
import entities.Product;
import reader.DatabaseReader;

public class InvoiceReport {

	public static void main(String[] args) {
		// make database reader
		// read database data
		ArrayList<Person> personArr = new ArrayList<Person>();
		ArrayList<Customer> customerArr = new ArrayList<Customer>();
		ArrayList<Product> productArr = new ArrayList<Product>();
		ArrayList<Invoice> invoiceArr = new ArrayList<Invoice>();
		try {
			personArr = DatabaseReader.readPersons();
			customerArr = DatabaseReader.readCustomers(personArr);
			productArr = DatabaseReader.readProducts();
			invoiceArr = DatabaseReader.readInvoice(customerArr, personArr, productArr);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			InvoiceData.dbq.closeConn();
		}

		// create list of invoices
		NodeList<Invoice> sortedInvoiceList = new NodeList<Invoice>(new InvoiceCompByTotal());

		// add invoices into the magical abstract data type
		for (Invoice i : invoiceArr) {
			sortedInvoiceList.add(i);
		}

		Invoice.printExec(sortedInvoiceList);
		for (Invoice i : sortedInvoiceList) {
			i.print();
		}
	}

}
