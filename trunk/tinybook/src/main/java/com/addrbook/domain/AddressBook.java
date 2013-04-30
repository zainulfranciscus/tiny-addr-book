package com.addrbook.domain;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class stores names and phones
 * 
 * @author Zainul Franciscus
 * 
 */
public class AddressBook {

	/**
	 * A map that stores names and phone number in ascending order
	 */
	private Map<String, String> namesAndPhones = new TreeMap<String, String>(NameComparator.ascOrder());

	/**
	 * Add the provided name and phone in an address book
	 * 
	 * @param name
	 * @param phone
	 */
	public void add(String name, String phone) {
		checkNotNull(name,"name must not be null");
		namesAndPhones.put(name, phone);
	}

	/**
	 * names in this address book are wrapped in a Names object. The intention
	 * is to provide clients methods to print the names in asc or descending
	 * order.
	 * 
	 * @return
	 */
	public Names names() {
		return new Names(namesAndPhones.keySet());

	}

	/**
	 * Retrieve a phone number associated with the provided name.
	 * 
	 * @param name
	 *            recorded in this address book
	 * @return
	 */
	public String phone(String name) {
		return namesAndPhones.get(name);
	}

	/**
	 * print names in this address book in descending order.
	 */
	public void printdesc() {

		print(names().desc());
	}

	/**
	 * print names in this address book in ascending oder
	 */
	public void printasc() {
		print(names());
	}

	/**
	 * print names into a console.
	 * 
	 * @param names
	 */
	public void print(Names names) {
		for (String name : names.names) {
			System.out.println(name + " " + phone(name));
		}
	}

	/**
	 * Produce names that are unique in either names.
	 * 
	 * @param n1
	 * @param n2
	 * @return names that are unique in both list.
	 */
	public static Names difference(Names n1, Names n2) {
		Set<String> uniqueNames = Sets.symmetricDifference(n1.names, n2.names);
		return new Names(uniqueNames, NameComparator.ascOrder());

	}

	/**
	 * This class stores a set of names recorded in an address book. The
	 * intention of creating this class is to provide useful methods for
	 * printing names, either in ascending or descending order.
	 * 
	 * @author Zainul Franciscus
	 * 
	 */
	public static class Names {
		Set<String> names;

		public Names() {
		}

		public Names(Set<String> set) {
			names = set;
		}

		public Names(Set<String> set, Ordering<String> order) {
			names = new TreeSet<String>(order);
			names.addAll(set);
		}

		/**
		 * @return a Names object that has a list of names sorted descendingly.
		 */
		public Names desc() {
			return new Names(names, NameComparator.descOrder());
		}

		/**
		 * Print names into a console
		 */
		public void print() {
			for (String name : names) {
				System.out.println(name);
			}

		}

		/**
		 * @return an Iterator that can be used to iterate names in this class.
		 */
		public Iterator<String> iterator() {
			return names.iterator();
		}

	}

}
