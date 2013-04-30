package com.addrbook.domain;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

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

		AddressBook addrBook = new AddressBook();
		addrBook.add("John", "11");
		addrBook.add("bruce", "22");

		AddressBook addrBook2 = new AddressBook();
		addrBook2.add("Abigail", "33");
		addrBook2.add("bruce", "22");

		Names diff = AddressBook.difference(addrBook.names(), addrBook2.names());
		Iterator<String> it = diff.iterator();

		assertEquals("Abigail", it.next());
		assertEquals("John", it.next());
	}

	@Test
	public void testDiffWithSubSets() {

		AddressBook addrBook = new AddressBook();
		addrBook.add("Briar", "11");
		addrBook.add("Mark", "22");
		addrBook.add("Gabriel", "44");
		addrBook.add("John", "55");

		AddressBook addrBook2 = new AddressBook();
		addrBook2.add("Briar", "33");
		addrBook2.add("Mark", "99");

		Names diff = AddressBook.difference(addrBook.names(), addrBook2.names());
		Iterator<String> it = diff.iterator();

		assertEquals("Gabriel", it.next());
		assertEquals("John", it.next());

	}

	@Test
	public void testChineseNames() {
		AddressBook addrBook = new AddressBook();
		addrBook.add("末农科院", "444333");
		addrBook.add("Amy", "777888");
		addrBook.add("的二", "777666");

		Names names = addrBook.names();
		Iterator<String> it = names.iterator();
		assertEquals("Amy", it.next());
		assertEquals("末农科院", it.next());
		assertEquals("的二", it.next());
	}

	@Test
	public void testDiffChineseNames() {

		AddressBook addrBook = new AddressBook();
		addrBook.add("反", "11");
		addrBook.add("我", "22");
		addrBook.add("丽", "44");

		AddressBook addrBook2 = new AddressBook();
		addrBook2.add("反", "33");
		addrBook2.add("我", "99");
		addrBook2.add("与李", "00");
		Names diff = AddressBook.difference(addrBook.names(), addrBook2.names());
		Iterator<String> it = diff.iterator();

		assertEquals("与李", it.next());
		assertEquals("丽", it.next());

	}

	@Test(expected = NullPointerException.class)
	public void testEmptyNames() {
		AddressBook addrBook = new AddressBook();
		addrBook.add("", "");

		Iterator<String> it = addrBook.names().iterator();
		String name = it.next();
		assertEquals("", name);
		assertEquals("", addrBook.phone(name));

		AddressBook addrBook2 = new AddressBook();

		addrBook2.add(null, "");

		Iterator<String> it2 = addrBook2.names().iterator();
		String name2 = it2.next();

		assertEquals(null, name2);
		assertEquals("", addrBook.phone(name2));
	}

}
