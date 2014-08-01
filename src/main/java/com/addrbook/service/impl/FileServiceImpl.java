package com.addrbook.service.impl;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addrbook.domain.AddressBook;
import com.addrbook.domain.AddressBook.Names;
import com.addrbook.repository.AddressBookRepository;
import com.addrbook.service.FileService;


@Service("fileService")
public class FileServiceImpl implements FileService {

	@Autowired
	private AddressBookRepository addressBook;


	@Override
	public AddressBook save(String id, String name, String phone) {

		addressBook.save(id, name, phone);
		return addressBook.getAddressBook(id);

	}

	public AddressBook findAddrBook(String id) {
		return addressBook.getAddressBook(id);
	}

	public Names names(String addrBookName){

		return findAddrBook(addrBookName).names();

	}


	public Names diff(String file1, String file2) {
		Names names1 = names(file1);
		Names names2 = names(file2);

		return AddressBook.difference(names1, names2);

	}

	
	public boolean isRecorded(String addrBookName) {
		return (findAddrBook(addrBookName) != null);
	}
	
	@Override
	public Set<String> recordedBooks() {
		return addressBook.recordedBooks();
	}

}
