package com.addrbook.domain;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import com.addrbook.domain.AddressBook.Names;

public class AddressBookTest {

	@Test
	public void testAdd() {

		AddressBook addrBook = new AddressBook();
		addrBook.add("John", "1111");
		addrBook.add("Amy", "2222");

		Names names = addrBook.names();
		Iterator<String> it = names.iterator();
		assertEquals("Amy", it.next());
		assertEquals("John", it.next());

		assertEquals("1111", addrBook.phone("John"));
		assertEquals("2222", addrBook.phone("Amy"));
	}

	@Test
	public void testDesc() {
		AddressBook addrBook = new AddressBook();
		addrBook.add("Kim", "2222");
		addrBook.add("Garry", "1111");
		addrBook.add("Mary", "3333");

		Names names = addrBook.names().desc();
		Iterator<String> it = names.iterator();
		assertEquals("Mary", it.next());
		assertEquals("Kim", it.next());
		assertEquals("Garry", it.next());

	}

	@Test
	public void testNames() {
		AddressBook addrBook = new AddressBook();
		addrBook.add("mark", "2222");
		addrBook.add("Julie", "1111");
		addrBook.add("Adrian", "3333");

		Names names = addrBook.names();
		Iterator<String> it = names.iterator();
		assertEquals("Adrian", it.next());
		assertEquals("Julie", it.next());
		assertEquals("mark", it.next());
	}

	@Test
	public void testDifference() {

		Set<String> set = new TreeSet<String>();
		set.add("John");
		set.add("bruce");
		Names names1 = new Names(set);

		Set<String> set2 = new TreeSet<String>();
		set2.add("Abigail");
		set2.add("bruce");
		Names names2 = new Names(set2);

		Names diff = AddressBook.difference(names1, names2);
		Iterator<String> it = diff.iterator();

		assertEquals("Abigail", it.next());
		assertEquals("John", it.next());
	}

	@Test
	public void testDiffWithSubSets() {

		Set<String> set = new TreeSet<String>();
		set.add("Briar");
		set.add("Mark");
		set.add("Gabriel");
		set.add("John");
		Names names1 = new Names(set);

		Set<String> set2 = new TreeSet<String>();
		set2.add("Gabriel");
		set2.add("Mark");
		Names names2 = new Names(set2);

		Names diff = AddressBook.difference(names1, names2);
		Iterator<String> it = diff.iterator();

		assertEquals("Briar", it.next());
		assertEquals("John", it.next());

	}

}
