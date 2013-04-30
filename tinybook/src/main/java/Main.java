import java.io.IOException;
import java.util.Set;

import org.springframework.context.support.GenericXmlApplicationContext;

import asg.cliche.CLIException;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.ShellFactory;

import com.addrbook.domain.AddressBook;
import com.addrbook.domain.AddressBook.Names;
import com.addrbook.service.AddressBookService;

/**
 * This class is intended to produce a shell in a console. User can add address books through the shell.
 * This class uses cliche (https://code.google.com/p/cliche/) to produce the shell.Every method that is annotated
 * with @Command can be executed through the shell by typing the a command name described in the 'name' parameter.
 * 
 * @author Zainul Franciscus
 *
 */
public class Main {

	private AddressBookService addrBookService;

	public static void main(String[] args) throws IOException {

		System.out.println("Welcome to Tiny Address Book. To begin, try adding an address book.");
		System.out.println("For example: add mybook Jerry 034065778.");
		System.out.println("Type ?list for available commands.");
		System.out.println("Type ?help followed by the command name to get a description of a command. E.g: ?help add");
		ShellFactory.createConsoleShell("Address Book", "", new Main()).commandLoop();

	}

	public Main() {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:app-context.xml");
		ctx.refresh();

		addrBookService = ctx.getBean("addressBookService", AddressBookService.class);

	}

	@Command(name = "print-asc", abbrev = "print", description = "Displays content of an address book in ascending order")
	public void printAsc(@Param(name = "book", description = "Name of an address book") String bookname)
			throws CLIException {

		AddressBook book = addressbook(bookname);
		book.printasc();
	}

	@Command(name = "print-desc", description = "Displays content of an address book in descending order")
	public void printDesc(@Param(name = "book", description = "Name of an address book") String addressbook)
			throws CLIException {

		AddressBook book = addressbook(addressbook);
		book.printdesc();
	}

	private AddressBook addressbook(String addressbook) throws CLIException {
		hasBookBeenRecorded(addressbook);
		return addrBookService.findAddrBook(addressbook);
	}

	@Command(name = "diff-asc", abbrev = "diff", description = "Displays names that are unique in either address books in ascending order")
	public void diffAsc(@Param(name = "book1", description = "Name of an address book") String addressbook1,
			@Param(name = "book2", description = "Name of another address book") String addressbook2) throws CLIException {

		Names names = diff(addressbook1, addressbook2);
		names.print();
	}

	@Command(name = "diff-desc", description = "Displays names that are unique in either address books in descending order")
	public void diffDesc(@Param(name = "book1", description = "Name of an address book") String addressbook1,
			@Param(name = "book2", description = "Name of another address book") String addressbook2) throws CLIException {

		Names names = diff(addressbook1, addressbook2);
		names.desc().print();
	}

	private Names diff(String addressbook1, String addressbook2) throws CLIException {
		hasBookBeenRecorded(addressbook1);
		hasBookBeenRecorded(addressbook2);
		return addrBookService.diff(addressbook1, addressbook2);
	}

	@Command(name = "add", description = "Record the provided name and phone into an address book.")
	public void add(@Param(name = "book", description = "Name of an address book") String addressbook,
			@Param(name = "name", description = "A name to be recorded in the addres book") String name,
			@Param(name = "phone", description = "A phone number to be recorded in the addres book") String phone) {
		addrBookService.save(addressbook, name, phone);
	}

	@Command(name = "check", description = "A command that displays recorded address books in the application")
	public void check() {
		Set<String> books = addrBookService.recordedBooks();

		if (books.size() == 0) {
			System.out.println("I have no address books in my records");
			return;
		}

		System.out.println("Sweet !! I have recorded the following address books:");

		for (String book : books) {
			System.out.println(book);
		}
	}

	private void hasBookBeenRecorded(String book) throws CLIException {
		if (!addrBookService.isRecorded(book)) {
			throw new CLIException("Oh No, I don't have " + book + " in my record. "
					+ "You can record it using the record command. Type ?help record for instructions", null);

		}
	}

}
