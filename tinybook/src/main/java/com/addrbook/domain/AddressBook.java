package com.addrbook.domain;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

/**
 * This class stores names and phones
 * 
 * @author Zainul Franciscus
 * 
 */
public class AddressBook {

	private Map<String, String> namesAndPhones;

	/**
	 * The constructor of this class
	 */
	public AddressBook() {

		File dbDir = Files.createTempDir();
		File dbFile = new File(dbDir, dbDir.getName());

		DB db = DBMaker.newFileDB(dbFile).deleteFilesAfterClose().closeOnJvmShutdown().writeAheadLogDisable()
				.cacheSize(10).make();

		/**
		 * Data in this map is stored in the disk. The intention is to allow
		 * anyone to create big address books (> 5 gb);
		 */
		namesAndPhones = db.createTreeMap("AddressBook", 10, true, false, null, null, NameComparator.ascOrder());
	}

	/**
	 * Add the provided name and phone in an address book
	 * 
	 * @param name
	 * @param phone
	 */
	public void add(String name, String phone) {
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
	 * 
	 * 
	 * @param n1 of a names
	 * @param n2
	 * @return names that are unique in both list.
	 */
	public static Names difference(Names n1, Names n2) {
		Set<String> uniqueNames = Sets.symmetricDifference(n1.names, n2.names);
		return new Names(uniqueNames, NameComparator.ascOrder());

	}

	/**
	 * This class stores a set of names recorded in an address book. The
	 * intention of creating this class is to provide a client useful method to
	 * print names, either in ascending or descending order.
	 * 
	 * @author Zainul Franciscus
	 * 
	 */
	public static class Names {
		Set<String> names;

		public Names(Set<String> n) {

			names = n;
		}
		

		/**
		 * @param n
		 *            a set of names
		 * @param nc
		 *            used to sort the provided set
		 */
		public Names(Set<String> n, Ordering<String> nc) {
			names = new TreeSet<String>(nc);
			names.addAll(n);
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

		/**
		 * @return the number of names in this class
		 */
		public int size() {
			return names.size();
		}

	}

}
