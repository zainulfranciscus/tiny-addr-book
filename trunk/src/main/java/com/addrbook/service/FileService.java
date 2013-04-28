package com.addrbook.service;

import java.io.IOException;
import java.util.Set;

import com.addrbook.domain.AddressBook;
import com.addrbook.domain.AddressBook.Names;

/**
 * This interface defines methods for creating an address book, and other
 * methods to get the content of the address book.
 * 
 * @author Zainul Franciscus
 * 
 */
public interface FileService {

	/**
	 * Create a new address book with the provided id, or append the provided
	 * name and phone number into an address book with the provided id.
	 * 
	 * @param id
	 *            a unique identified for an address book
	 * @param name
	 *            of a person
	 * @param phone
	 *            of the person
	 * @return an AddressBook with the provided name and phone number.
	 */
	AddressBook save(String id, String name, String phone);

	/**
	 * @param addressbook
	 *            name of an address book recorded in this application
	 * @return Names object that can be used to print names in the recorded
	 *         address book.
	 * @throws IOException
	 */
	Names names(String addressbook);

	/**
	 * @param addressbook1
	 *            is a name of an address book recorded in this application
	 * @param addressbook2
	 *            is a name of an address book recorded in this application
	 * @return names that are not contained in either address books.
	 * @throws IOException
	 */
	Names diff(String addressbook1, String addressbook2);

	/**
	 * @param id
	 *            of an address book recorded in this application
	 * @return an AddressBook that matches the supplied name.
	 */
	AddressBook findAddrBook(String id);

	/**
	 * Check whether this application has an address book with a name that
	 * matches the supplied name.
	 * 
	 * @param name
	 *            of an address book.
	 * @return true if this name matches a recorded address book name, and false
	 *         otherwise.
	 */
	boolean isRecorded(String name);

	/**
	 * @return a list of address book names recorded in this application.
	 */
	Set<String> recordedBooks();

}