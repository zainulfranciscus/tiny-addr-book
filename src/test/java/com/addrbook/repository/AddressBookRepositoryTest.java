package com.addrbook.repository;

import com.addrbook.domain.AddressBook;
import com.addrbook.domain.AddressBook.Names;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

import static com.addrbook.domain.AddressBookTest.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class AddressBookRepositoryTest {

	private static final String ADDRESS_BOOK1 = NAME_OF_ADDRESS_BOOK;
	private AddressBookRepository addressBookRepository;

	@Before
	public void setup() {
		addressBookRepository = new AddressBookRepository();
		addressBookRepository.save(ADDRESS_BOOK1, AMY, AMY_PHONE);
	}

	@Test
	public void testShouldHave1ContactInAddressBook() {

		AddressBook addressBook = addressBookRepository.getAddressBook(ADDRESS_BOOK1);

		Names names = addressBook.names();
		assertThat(names.size(), equalTo(1));

		Iterator<String> namesIterator = addressBook.names().iterator();

		assertThat(namesIterator.next(), equalTo(AMY));
		assertThat(addressBook.phoneNumberOf(AMY), equalTo(AMY_PHONE));

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
		addressBookRepository.save(ADDRESS_BOOK1, JOHN, JOHN_PHONE);
		
		AddressBook addressBook = addressBookRepository
				.getAddressBook(ADDRESS_BOOK1);

		Names names = addressBook.names();
		assertThat(names.size(), equalTo(2));
		assertThat(addressBook.phoneNumberOf(JOHN), equalTo(JOHN_PHONE));
		assertThat(addressBook.phoneNumberOf(AMY), equalTo(AMY_PHONE));
	}

	@Test
	public void shouldReturnAddressBook1(){
		Set<String> addressBooks = addressBookRepository.recordedBooks();
		assertEquals(1,addressBooks.size());
		assertEquals(NAME_OF_ADDRESS_BOOK, addressBooks.iterator().next());
	}

}
