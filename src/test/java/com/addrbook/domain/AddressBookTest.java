package com.addrbook.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.addrbook.domain.AddressBook.Names;

public class AddressBookTest {

	public static final String ABIGAIL = "Abigail";
	public static final String AMY_PHONE = "2222";
	public static final String JOHN_PHONE = "1111";
	public static final String AMY = "Amy";
	public static final String JOHN = "John";
	public static final String NAME_OF_ADDRESS_BOOK = "Address Book";

	private AddressBook firstAddressBook = new AddressBook();
	private AddressBook secondAddressBook = new AddressBook();
	private AddressBook emptyBook = new AddressBook();

	@Before
	public void setup() {

		firstAddressBook.add(JOHN, JOHN_PHONE);
		firstAddressBook.add(AMY, AMY_PHONE);

		secondAddressBook.add(ABIGAIL, "");
		secondAddressBook.add(AMY, AMY_PHONE);
	}

	@Test
	public void testThereShouldBe2Contacts() {

		Names names = firstAddressBook.names();
		assertThat(names.size(), equalTo(2));

		Iterator<String> it = names.iterator();
		assertEquals(AMY, it.next());
		assertEquals(JOHN, it.next());
	}

	@Test
	public void shouldReturnJohnPhoneNumber(){
		assertEquals(JOHN_PHONE, firstAddressBook.phoneNumberOf(JOHN));
	}

	@Test
	public void namesShouldBeSortedDescendingly() {

		Names names = firstAddressBook.names().sortedDescendingly();
		Iterator<String> it = names.iterator();
		assertEquals(JOHN, it.next());
		assertEquals(AMY, it.next());

	}
	
	@Test
	public void namesShouldBeSortedAscendingly(){
		Names names = firstAddressBook.names().sortedAscendingly();
		Iterator<String> it = names.iterator();
		assertEquals(AMY, it.next());
		assertEquals(JOHN, it.next());
	}

	@Test
	public void thereShouldBe2UniqueNames() {

		Names uniqueNames = AddressBook.findUniqueNames(firstAddressBook, secondAddressBook);

		assertThat(uniqueNames.size(), equalTo(2));

		Iterator<String> it = uniqueNames.iterator();
		assertEquals(ABIGAIL, it.next());
		assertEquals(JOHN, it.next());
	}
	
	@Test
	public void shouldContentFromTheFirstAddressBook(){
		Names uniqueNames = AddressBook.findUniqueNames(firstAddressBook, emptyBook);

		assertThat(uniqueNames.size(), equalTo(2));

		Iterator<String> it = uniqueNames.iterator();
		assertEquals(AMY, it.next());
		assertEquals(JOHN, it.next());
	}

}
