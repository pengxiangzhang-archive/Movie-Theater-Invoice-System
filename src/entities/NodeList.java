/******************************************************************************
 * This class is use to create the ADT.
 * 
 * 
 * CSCE156 Fall 2018
 * Assignment 5
 * @file NodeLise.java

 * @version 1.1
 * @since Nov 1, 2018
 *******************************************************************************/

package entities;

import java.util.Comparator;
import java.util.Iterator;

public class NodeList<T> implements Iterable<T> {
	private Node<T> start;
	private int size;
	private Comparator<T> comp;

	public NodeList(Comparator<T> comp) {
		this.comp = comp;
		this.start = null;
		this.size = 0;
	}

	public boolean add(T o) {
		if(start == null) {
			start = new Node<T>(o);
			size++;
			return true;
		} else if(size == 1) {
			if(comp.compare(start.getObject(), o) <= 0) {
				// if the number is smaller, add it to the end
				start.setNext(new Node<T>(o));
				size++;
				return true;
			} else {
				// if the number is bigger, move the start node over and add to the beginning
				Node<T> temp = new Node<T>(o);
				temp.setNext(start);
				start = temp;
				size++;
				return true;
			}
		} else {
			if(comp.compare(start.getObject(), o) <= 0) {
				// if new number is smaller loop until new number is bigger
				Node<T> current;
				Node<T> previous = null;
				for(current = start; current.getNext() != null; current = current.getNext()) {
					// if new number is bigger, insert it into the list
					if(comp.compare(current.getObject(), o) >= 0) {
						if(previous != null) {
							previous.setNext(new Node<T>(o));
							previous.getNext().setNext(current);
						} else {
							Node<T> temp = new Node<T>(o);
							temp.setNext(start);
							start = temp;
						}
						size++;
						return true;
					}
					previous = current;
				}
				// if new number is the smallest, add it to the end
				if(comp.compare(current.getObject(), o) >= 0) {
					previous.setNext(new Node<T>(o));
					previous.getNext().setNext(current);
				} else
					current.setNext(new Node<T>(o));
				size++;
				return true;
			} else {
				// if the number is bigger, move the start node over and add to the beginning
				Node<T> temp = new Node<T>(o);
				temp.setNext(start);
				start = temp;
				size++;
				return true;
			}
		}
	}

	public void clear() {
		start = null;
		size = 0;
	}

	public boolean contains(T o) {
		// make sure T has an overridden equals() and hashcode() method
		for(T obj : this) {
			if(obj.equals(o))
				return true;
		}
		return false;
	}

	public T get(int index) {
		if(index > size)
			throw new IndexOutOfBoundsException();
		for(T st : this) {
			index--;
			if(index == 0)
				return st;
		}
		return null;
	}

	public boolean isEmpty() {
		if(size == 0)
			return true;
		return false;
	}

	public boolean remove(T o) {
		Node<T> current;
		Node<T> previous = null;
		// make sure T has an overridden equals() and hashcode() method
		if(start.getObject().equals(o)) {
			start = start.getNext();
			size--;
			return true;
		}
		for(current = start; current != null; current = current.getNext()) {
			if(current.getObject().equals(o)) {
				previous.setNext(current.getNext());
				size--;
				return true;
			}
			previous = current;
		}
		return false;
	}

	public T remove(int index) {
		Node<T> current;
		Node<T> previous = null;
		T obj;
		if(index > size)
			throw new IndexOutOfBoundsException();
		if(index == 1) {
			obj = start.getObject();
			start = start.getNext();
			size--;
			return obj;
		}
		for(current = start; current != null; current = current.getNext()) {
			index--;
			if(index == 0) {
				obj = current.getObject();
				previous.setNext(current.getNext());
				size--;
				return obj;
			}
			previous = current;
		}
		return null;
	}

	public int size() {
		return size;
	}

	public void printList() {
		for(T obj : this) {
			System.out.println(obj.toString());
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new IteratorObject();
	}

	public class IteratorObject implements Iterator<T> {
		Node<T> current = start;

		@Override
		// Checks if the next element exists
		public boolean hasNext() {
			return current != null;
		}

		@Override
		// moves the cursor/iterator to next element
		public T next() {
			T obj = current.getObject();
			current = current.getNext();
			return obj;
		}
	}
}
