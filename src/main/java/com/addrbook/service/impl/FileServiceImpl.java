package com.addrbook.service.impl;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addrbook.domain.AddressBook;
import com.addrbook.domain.AddressBook.Names;
import com.addrbook.repository.AddressBookRepository;
import com.addrbook.service.FileService;

/**
 * This interface defines methods for creating an address book, and other
 * methods to get the content of the address book.
 * 
 * @author Zainul Franciscus
 * 
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

	@Autowired
	private AddressBookRepository addressBook;

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
	@Override
	public AddressBook save(String id, String name, String phone) {

		addressBook.save(id, name, phone);
		return addressBook.getAddressBook(id);

	}

	/**
	 * @param id
	 *            of an address book recorded in this application
	 * @return an AddressBook that matches the supplied name.
	 */
	public AddressBook findAddrBook(String id) {
		return addressBook.getAddressBook(id);
	}

	/**
	 * @param addressbook
	 *            name of an address book recorded in this application
	 * @return Names object that can be used to print names in the recorded
	 *         address book.
	 * @throws IOException
	 */
	public Names names(String addrBookName){

		return findAddrBook(addrBookName).names();

	}

	/**
	 * @param addressbook1
	 *            is a name of an address book recorded in this application
	 * @param addressbook2
	 *            is a name of an address book recorded in this application
	 * @return names that are not contained in either address books.
	 * @throws IOException
	 */
	public Names diff(String file1, String file2) {
		Names names1 = names(file1);
		Names names2 = names(file2);

		return AddressBook.difference(names1, names2);

	}

	/**
	 * Check whether this application has an address book with a name that
	 * matches the supplied name.
	 * 
	 * @param name
	 *            of an address book.
	 * @return true if this name matches a recorded address book name, and false
	 *         otherwise.
	 */
	public boolean isRecorded(String addrBookName) {
		return (findAddrBook(addrBookName) != null);
	}

	/**
	 * @return a list of address book names recorded in this application.
	 */
	@Override
	public Set<String> recordedBooks() {
		return addressBook.recordedBooks();
	}

}
