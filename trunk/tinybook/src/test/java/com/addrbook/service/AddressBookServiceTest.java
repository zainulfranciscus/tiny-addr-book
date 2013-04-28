package com.addrbook.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.addrbook.domain.AddressBook;
import com.addrbook.repository.AddressBookRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class AddressBookServiceTest {

	@Autowired
	private AddressBookService fileService;

	private AddressBookRepository mockRepository;

	@Before
	public void setup() {
		mockRepository = Mockito.mock(AddressBookRepository.class);
	}

	@Test
	public void testSave() {
		ReflectionTestUtils.setField(fileService, "repository", mockRepository);
		fileService.save("mybook", "john", "04557799");
		Mockito.verify(mockRepository).getAddressBook("mybook");

	}

	@Test
	public void testFindAddrBook() {
		ReflectionTestUtils.setField(fileService, "repository", mockRepository);
		fileService.findAddrBook("mybook");
		Mockito.verify(mockRepository).getAddressBook("mybook");
	}

	@Test
	public void testNames() throws IOException {

		AddressBook addrBook = new AddressBook();
		when(mockRepository.getAddressBook("mybook")).thenReturn(addrBook);

		ReflectionTestUtils.setField(fileService, "repository", mockRepository);
		fileService.names("mybook");

		Mockito.verify(mockRepository).getAddressBook("mybook");
	}

	@Test
	public void testIsRecorded() {
		AddressBook addrBook = new AddressBook();
		when(mockRepository.getAddressBook("mybook")).thenReturn(addrBook);
		when(mockRepository.getAddressBook("otherbook")).thenReturn(null);

		ReflectionTestUtils.setField(fileService, "repository", mockRepository);
		assertTrue(fileService.isRecorded("mybook"));
		assertFalse(fileService.isRecorded("otherbook"));
	}

	@Test
	public void testRecordedBook() {
		Set<String> set = new TreeSet<String>();
		set.add("a");

		when(mockRepository.recordedBooks()).thenReturn(set);
		ReflectionTestUtils.setField(fileService, "repository", mockRepository);
		assertEquals(set, fileService.recordedBooks());

	}
}
