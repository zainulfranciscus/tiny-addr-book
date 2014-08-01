package com.addrbook.service;

import java.util.Set;

import com.addrbook.domain.AddressBook;
import com.addrbook.domain.AddressBook.Names;


public interface FileService {

	
	AddressBook save(String id, String name, String phone);

	
	Names names(String addressbook);

	
	Names diff(String addressbook1, String addressbook2);

	
	AddressBook findAddrBook(String id);

	
	boolean isRecorded(String name);

	
	Set<String> recordedBooks();

}