Introduction
I wrote this code as a way to exercise how to write a simple application using TDD. The purpose of the application is as follow:

Create an address book that allows a user to store (between successive runs of the program) the name and phone numbers of their friends, with the following functionality:

To be able to display the list of friends and their corresponding phone numbers sorted by their name
Given another address book that may or may not contain the same friends, display the list of friends that are unique to each address book (the union of all the relative complements).
For example given:

Book1 = { “Bob”, “Mary”, “Jane” } Book2 = { “Mary”, “John”, “Jane” } The friends that are unique to each address book is: Book1 \ Book2 = { “Bob”, “John” }