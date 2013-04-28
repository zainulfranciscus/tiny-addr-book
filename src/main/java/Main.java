import java.io.IOException;
import java.util.Set;

import org.springframework.context.support.GenericXmlApplicationContext;

import asg.cliche.CLIException;
import asg.cliche.Command;
import asg.cliche.ShellFactory;

import com.addrbook.domain.AddressBook;
import com.addrbook.domain.AddressBook.Names;
import com.addrbook.service.FileService;

public class Main {

	private FileService fileService;

	public static void main(String[] args) throws IOException {

		ShellFactory.createConsoleShell("Address Book", "", new Main()).commandLoop();

	}

	public Main() {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:app-context.xml");
		ctx.refresh();

		fileService = ctx.getBean("fileService", FileService.class);

	}

	@Command(name = "print-asc", abbrev = "print", description = "Displays content of an address book in ascending order")
	public void printAsc(String addressbook) throws CLIException {

		AddressBook book = adressbook(addressbook);
		book.printasc();
	}

	@Command(name = "print-desc", description = "Displays content of an address book in descending order")
	public void printDesc(String addressbook) throws CLIException {

		AddressBook book = adressbook(addressbook);
		book.printdesc();
	}

	private AddressBook adressbook(String addressbook) throws CLIException {
		hasBookBeenRecorded(addressbook);
		return fileService.findAddrBook(addressbook);
	}

	@Command(name = "diff-asc", abbrev = "diff", description = "Displays names that are unique in either address books in ascending order")
	public void diffAsc(String addressbook1, String addressbook2) throws CLIException {

		Names names = diff(addressbook1, addressbook2);
		names.print();
	}

	@Command(name = "diff-desc", description = "Displays names that are unique in either address books in descending order")
	public void diffDesc(String addressbook1, String addressbook2) throws CLIException {

		Names names = diff(addressbook1, addressbook2);
		names.desc().print();
	}

	private Names diff(String addressbook1, String addressbook2) throws CLIException {
		hasBookBeenRecorded(addressbook1);
		hasBookBeenRecorded(addressbook2);
		return fileService.diff(addressbook1, addressbook2);
	}

	@Command(name = "add", description = "Record the provided name and phone into a new address book, or append to an existing one if there exist an address book with that name.")
	public void add(String addressbook, String name, String phone) {
		fileService.save(addressbook, name, phone);
	}

	@Command(name = "check", description = "A command that displays recorded address books in the application")
	public void check() {
		Set<String> books = fileService.recordedBooks();

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
		if (!fileService.isRecorded(book)) {
			throw new CLIException("Oh No, I don't have " + book + " in my record. "
					+ "You can record it using the record command. Type ?help record for instructions", null);

		}
	}

}
