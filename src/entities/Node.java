/******************************************************************************
 * This class is use set the node for the link list.
 * 
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file Node.java

 * @version 1.1
 * @since Nov 1, 2018
 *******************************************************************************/

package entities;

public class Node<T> {
	private Node<T> next;
	private T o;

	public Node(T o) {
		this.o = o;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public Node<T> getNext() {
		return this.next;
	}

	public T getObject() {
		return this.o;
	}
}
