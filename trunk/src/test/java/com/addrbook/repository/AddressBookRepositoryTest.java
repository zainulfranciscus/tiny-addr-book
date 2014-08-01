package com.addrbook.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.addrbook.domain.AddressBook;
import com.addrbook.domain.AddressBook.Names;
import com.addrbook.domain.AddressBookTestData;

public class AddressBookRepositoryTest {

	private static final String ADDRESS_BOOK1 = AddressBookTestData.ADDRESS_BOOK_1;
	private AddressBookRepository addressBookRepository;

	@Before
	public void setup() {
		addressBookRepository = new AddressBookRepository();
		addressBookRepository.save(ADDRESS_BOOK1, AddressBookTestData.AMY,
				AddressBookTestData.AMY_PHONE);
	}

	@Test
	public void testShouldHave1ContactInAddressBook() {

		AddressBook addressBook = addressBookRepository
				.getAddressBook(ADDRESS_BOOK1);

		Names names = addressBook.names();
		assertThat(names.size(), equalTo(1));

		Iterator<String> namesIterator = addressBook.names().iterator();

		assertThat(namesIterator.next(), equalTo(AddressBookTestData.AMY));
		assertThat(addressBook.phoneNumberOf(AddressBookTestData.AMY),
				equalTo(AddressBookTestData.AMY_PHONE));

	}
		
	@Test
	public void testAddressBookShouldExist(){
		assertTrue(addressBookRepository.exist(ADDRESS_BOOK1));
	}
	
	@Test
	public void testAddressBookShouldNotExist(){
		assertFalse(addressBookRepository.exist("Address Book2"));
	}
	
	@Test
	public void testCanAddNewContactToAddressBook1(){
		addressBookRepository.save(ADDRESS_BOOK1, AddressBookTestData.JOHN,
				AddressBookTestData.JOHN_PHONE);
		
		AddressBook addressBook = addressBookRepository
				.getAddressBook(ADDRESS_BOOK1);

		Names names = addressBook.names();
		assertThat(names.size(), equalTo(2));
		
	
		assertThat(addressBook.phoneNumberOf(AddressBookTestData.JOHN), equalTo(AddressBookTestData.JOHN_PHONE));
		assertThat(addressBook.phoneNumberOf(AddressBookTestData.AMY), equalTo(AddressBookTestData.AMY_PHONE));
	}

}
