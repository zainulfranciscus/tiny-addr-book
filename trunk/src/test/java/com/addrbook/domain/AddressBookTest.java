package com.addrbook.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.addrbook.domain.AddressBook.Names;

public class AddressBookTest {

	private AddressBook addrBook = new AddressBook();
	private AddressBook addrBook2 = new AddressBook();
	private AddressBook emptyBook = new AddressBook();

	@Before
	public void setup() {

		addrBook.add(AddressBookTestData.JOHN, AddressBookTestData.JOHN_PHONE);
		addrBook.add(AddressBookTestData.AMY, AddressBookTestData.AMY_PHONE);

		addrBook2.add(AddressBookTestData.ABIGAIL, "");
		addrBook2.add(AddressBookTestData.AMY, AddressBookTestData.AMY_PHONE);
	}

	@Test
	public void testThereShouldBe2Contacts() {

		Names names = addrBook.names();
		assertThat(names.size(), equalTo(2));

		Iterator<String> it = names.iterator();
		assertEquals(AddressBookTestData.AMY, it.next());
		assertEquals(AddressBookTestData.JOHN, it.next());

		assertEquals(AddressBookTestData.JOHN_PHONE, addrBook.phoneNumberOf(AddressBookTestData.JOHN));
		assertEquals(AddressBookTestData.AMY_PHONE, addrBook.phoneNumberOf(AddressBookTestData.AMY));
	}

	@Test
	public void testNamesCanBeSortedDescendingly() {

		Names names = addrBook.names().sortedDescendingly();
		Iterator<String> it = names.iterator();
		assertEquals(AddressBookTestData.JOHN, it.next());
		assertEquals(AddressBookTestData.AMY, it.next());

	}
	
	@Test
	public void testNamesCanBeSortedAscendingly(){
		Names names = addrBook.names().sortedAscendingly();
		Iterator<String> it = names.iterator();
		assertEquals(AddressBookTestData.AMY, it.next());
		assertEquals(AddressBookTestData.JOHN, it.next());
	}

	@Test
	public void testThereShouldBe2UniqueNames() {

		Names uniqueNames = AddressBook.findUniqueNames(addrBook, addrBook2);

		assertThat(uniqueNames.size(), equalTo(2));

		Iterator<String> it = uniqueNames.iterator();
		assertEquals(AddressBookTestData.ABIGAIL, it.next());
		assertEquals(AddressBookTestData.JOHN, it.next());
	}
	
	@Test
	public void testUniqueNamesWithEmptyAddrBook(){
		Names uniqueNames = AddressBook.findUniqueNames(addrBook, emptyBook);

		assertThat(uniqueNames.size(), equalTo(2));

		Iterator<String> it = uniqueNames.iterator();
		assertEquals(AddressBookTestData.AMY, it.next());
		assertEquals(AddressBookTestData.JOHN, it.next());
	}

}
