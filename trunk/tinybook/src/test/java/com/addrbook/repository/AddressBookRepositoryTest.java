package com.addrbook.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.addrbook.domain.AddressBook.Names;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class AddressBookRepositoryTest {

	@Autowired
	private AddressBookRepository addressBookRepository;

	@Test
	public void testAddNameAndPhone() {
		addressBookRepository.save("mybook", "Billy", "1111");
		assertTrue(addressBookRepository.exist("mybook"));
		assertFalse(addressBookRepository.exist("otherbook"));

		addressBookRepository.save("mybook", "John", "2222");
		Names names = addressBookRepository.getAddressBook("mybook").names();

		Iterator<String> it = names.iterator();
		assertEquals("Billy", it.next());
		assertEquals("John", it.next());
	}

}
