package com.addrbook.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;


import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import com.addrbook.domain.AddressBookTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.addrbook.domain.AddressBook;
import com.addrbook.domain.AddressBook.Names;
import com.addrbook.repository.AddressBookRepository;
import com.addrbook.service.impl.FileServiceImpl;


public class FileServiceTest {

	private FileService fileService = new FileServiceImpl();

	private AddressBookRepository mockRepository;

	@Before
	public void setup() {
		mockRepository = Mockito.mock(AddressBookRepository.class);
		
		
		ReflectionTestUtils.setField(fileService, "addressBook", mockRepository);
	}

	@Test
	public void testShouldReturnAnAddressBookAfterCreatingOne() {
			
		fileService.save(AddressBookTest.NAME_OF_ADDRESS_BOOK, AddressBookTest.JOHN, AddressBookTest.JOHN_PHONE);
		Mockito.verify(mockRepository).save(AddressBookTest.NAME_OF_ADDRESS_BOOK, AddressBookTest.JOHN, AddressBookTest.JOHN_PHONE);
		Mockito.verify(mockRepository).getAddressBook(AddressBookTest.NAME_OF_ADDRESS_BOOK);

	}

	@Test
	public void testShouldReturnAddressBook1() {
		
		fileService.findAddrBook(AddressBookTest.NAME_OF_ADDRESS_BOOK);
		Mockito.verify(mockRepository).getAddressBook(AddressBookTest.NAME_OF_ADDRESS_BOOK);
	}

	@Test
	public void testShouldReturnNamesInAddressBook() throws IOException {

		AddressBook addrBook = new AddressBook();
		addrBook.add(AddressBookTest.JOHN, AddressBookTest.JOHN_PHONE);
		
		when(mockRepository.getAddressBook(AddressBookTest.NAME_OF_ADDRESS_BOOK)).thenReturn(addrBook);

		ReflectionTestUtils.setField(fileService, "addressBook", mockRepository);
		
		Names names = fileService.names(AddressBookTest.NAME_OF_ADDRESS_BOOK);
		
		assertThat(names.size(), equalTo(1));
		assertThat(names.iterator().next(),equalTo(AddressBookTest.JOHN));
		assertThat(addrBook.phoneNumberOf(AddressBookTest.JOHN),equalTo( AddressBookTest.JOHN_PHONE));
		
		Mockito.verify(mockRepository).getAddressBook(AddressBookTest.NAME_OF_ADDRESS_BOOK);
	}

	@Test
	public void testIsRecorded() {
		AddressBook addrBook = new AddressBook();
		when(mockRepository.getAddressBook("mybook")).thenReturn(addrBook);
		when(mockRepository.getAddressBook("otherbook")).thenReturn(null);


		assertTrue(fileService.isRecorded("mybook"));
		assertFalse(fileService.isRecorded("otherbook"));
	}

	@Test
	public void testRecordedBook() {
		Set<String> set = new TreeSet<String>();
		set.add("a");

		when(mockRepository.recordedBooks()).thenReturn(set);
		ReflectionTestUtils.setField(fileService, "addressBook", mockRepository);
		assertEquals(set, fileService.recordedBooks());

	}
}
