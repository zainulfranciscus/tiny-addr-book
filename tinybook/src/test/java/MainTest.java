import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import asg.cliche.CLIException;

import com.addrbook.domain.AddressBook;
import com.addrbook.domain.AddressBook.Names;
import com.addrbook.service.impl.AddressBookServiceImpl;

public class MainTest {

	private AddressBookServiceImpl mockService;

	@Before
	public void setUp() {
		mockService = Mockito.mock(AddressBookServiceImpl.class);
		Mockito.reset(mockService);
	}

	@Test
	public void testPrintAsc() throws CLIException {

		Main main = new Main();
		AddressBook addrBook = new AddressBook();

		when(mockService.findAddrBook("mybook")).thenReturn(addrBook);
		when(mockService.isRecorded("mybook")).thenReturn(true);
		ReflectionTestUtils.setField(main, "addrBookService", mockService);
		main.printAsc("mybook");

		Mockito.verify(mockService).findAddrBook("mybook");
		Mockito.verify(mockService).isRecorded("mybook");

	}

	@Test
	public void testPrintDesc() throws CLIException {

		Main main = new Main();
		when(mockService.findAddrBook("mybook")).thenReturn(new AddressBook());
		when(mockService.isRecorded("mybook")).thenReturn(true);
		ReflectionTestUtils.setField(main, "addrBookService", mockService);
		main.printDesc("mybook");

		Mockito.verify(mockService).findAddrBook("mybook");
		Mockito.verify(mockService).isRecorded("mybook");

	}

	@Test
	public void testDiffAsc() throws CLIException {

		Main main = new Main();

		AddressBook addrBook = new AddressBook();
		addrBook.add("John", "1111");
		addrBook.add("Amy", "2222");

		Names names = addrBook.names();

		when(mockService.isRecorded("mybook")).thenReturn(true);
		when(mockService.isRecorded("otherbook")).thenReturn(true);
		when(mockService.diff("mybook", "otherbook")).thenReturn(names);
		ReflectionTestUtils.setField(main, "addrBookService", mockService);
		main.diffAsc("mybook", "otherbook");

		Mockito.verify(mockService).isRecorded("mybook");
		Mockito.verify(mockService).isRecorded("otherbook");
		Mockito.verify(mockService).diff("mybook", "otherbook");
	}

	@Test
	public void testDiffDesc() throws CLIException {

		Main main = new Main();

		AddressBook addrBook = new AddressBook();
		addrBook.add("John", "1111");
		addrBook.add("Amy", "2222");

		Names names = addrBook.names();

		when(mockService.isRecorded("mybook")).thenReturn(true);
		when(mockService.isRecorded("otherbook")).thenReturn(true);
		when(mockService.diff("mybook", "otherbook")).thenReturn(names);
		ReflectionTestUtils.setField(main, "addrBookService", mockService);
		main.diffDesc("mybook", "otherbook");

		Mockito.verify(mockService).isRecorded("mybook");
		Mockito.verify(mockService).isRecorded("otherbook");
		Mockito.verify(mockService).diff("mybook", "otherbook");
	}
	
	@Test
	public void testAdd(){
		Main main = new Main();
		ReflectionTestUtils.setField(main, "addrBookService", mockService);
		main.add("book1", "adrian", "111000");
		
		Mockito.verify(mockService).save("book1", "adrian", "111000");
	}
	
	@Test
	public void testCheck(){
		Main main = new Main();
		when(mockService.recordedBooks()).thenReturn(new HashSet<String>());
		ReflectionTestUtils.setField(main, "addrBookService", mockService);
		main.check();
		
		Mockito.verify(mockService).recordedBooks();
		
	}
		
}
