package com.addrbook.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.addrbook.domain.AddressBook;

/**
 * This class is responsible for accessing address book data.
 * 
 * @author Zainul Franciscus
 * 
 */
@Repository("addressBook")
public class AddressBookRepository {

	/**
	 * The application can record many address books. Each book is associated
	 * with a unique name. This map records each book with a unique name
	 */
	public Map<String, AddressBook> addressBooks = new HashMap<String, AddressBook>();

	/**
	 * Create an address book if there is no address book with the provided
	 * name, and record the provided name and phone into the address book.
	 * 
	 * @param bookname
	 *            a unique name that can be used to identify an address book
	 * @param name
	 *            of a person
	 * @param phone
	 *            of the person
	 */
	public void save(String bookname, String name, String phone) {

		AddressBook addrBook = null;

		if (exist(bookname)) {
			addrBook = getAddressBook(bookname);
		} else {
			addrBook = new AddressBook();
		}

		addrBook.add(name, phone);
		addressBooks.put(bookname, addrBook);
	}

	/**
	 * @return names of address books recorded in this application
	 */
	public Set<String> recordedBooks() {
		return addressBooks.keySet();
	}

	/**
	 * @param id
	 *            of an address book recorded in the application
	 * @return an address book that matches the provided id.
	 */
	public AddressBook getAddressBook(String id) {
		return addressBooks.get(id);
	}

	/**
	 * @param id
	 * @return true if the application has recorded an address book with the
	 *         provided id.
	 */
	public boolean exist(String id) {
		return (getAddressBook(id) != null);
	}
}
