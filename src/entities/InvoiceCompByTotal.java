/******************************************************************************
 * This class is use to compare two total fee of the invoices.
 * 
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file InvoiceCompByTotal.java

 * @version 1.1
 * @since Nov 1, 2018
 *******************************************************************************/

package entities;

import java.util.Comparator;

public class InvoiceCompByTotal implements Comparator<Invoice> {

	@Override
	/* if the second invoice is bigger, returns a positive integer
	 * if the first is bigger, returns a negative integer
	 * if they are equal, returns 0
	 */
	public int compare(Invoice inv1, Invoice inv2) {
		return (int) (inv2.getFinalTotal() - inv1.getFinalTotal());
		//if the second invoice is bigger, returns a positive integer
		//if the first is bigger, returns a negative integer
		//if they are equal, returns 0
	}

}
