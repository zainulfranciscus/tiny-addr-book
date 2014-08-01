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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.addrbook.domain.AddressBook;
import com.addrbook.domain.AddressBook.Names;
import com.addrbook.domain.AddressBookTestData;
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
			
		fileService.save(AddressBookTestData.ADDRESS_BOOK_1, AddressBookTestData.JOHN, AddressBookTestData.JOHN_PHONE);
		Mockito.verify(mockRepository).save(AddressBookTestData.ADDRESS_BOOK_1, AddressBookTestData.JOHN, AddressBookTestData.JOHN_PHONE);
		Mockito.verify(mockRepository).getAddressBook(AddressBookTestData.ADDRESS_BOOK_1);

	}

	@Test
	public void testShouldReturnAddressBook1() {
		
		fileService.findAddrBook(AddressBookTestData.ADDRESS_BOOK_1);
		Mockito.verify(mockRepository).getAddressBook(AddressBookTestData.ADDRESS_BOOK_1);
	}

	@Test
	public void testShouldReturnNamesInAddressBook() throws IOException {

		AddressBook addrBook = new AddressBook();
		addrBook.add(AddressBookTestData.JOHN, AddressBookTestData.JOHN_PHONE);
		
		when(mockRepository.getAddressBook(AddressBookTestData.ADDRESS_BOOK_1)).thenReturn(addrBook);

		ReflectionTestUtils.setField(fileService, "addressBook", mockRepository);
		
		Names names = fileService.names(AddressBookTestData.ADDRESS_BOOK_1);
		
		assertThat(names.size(), equalTo(1));
		assertThat(names.iterator().next(),equalTo(AddressBookTestData.JOHN));
		assertThat(addrBook.phoneNumberOf(AddressBookTestData.JOHN),equalTo( AddressBookTestData.JOHN_PHONE));
		
		Mockito.verify(mockRepository).getAddressBook(AddressBookTestData.ADDRESS_BOOK_1);
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
