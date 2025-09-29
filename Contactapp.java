```java
import java.util.ArrayList;
import java.util.Scanner;
public class Contactapp {
static class Contact {
String name;
String phone;
Contact(String name, String phone) {
this.name = name;
this.phone = phone;
}
public String toString() {
return name + " - " + phone;
}
}
public static void main(String[] args) {
ArrayList<Contact> contacts = new ArrayList<>();
Scanner sc = new Scanner(System.in);
int choice = 0;
while (choice != 6) {
System.out.println("\n--- Contact Management ---");
System.out.println("1. Add Contact");
System.out.println("2. View Contacts");
System.out.println("3. Update Contact");
System.out.println("4. Delete Contact");
System.out.println("5. Search Contact");
System.out.println("6. Exit");
System.out.print("Enter your choice: ");
choice = sc.nextInt();
sc.nextLine();
if (choice == 1) {
System.out.print("Enter contact name: ");
String name = sc.nextLine();
System.out.print("Enter your number: ");
String phone = sc.nextLine();
contacts.add(new Contact(name, phone));
System.out.println("Contact added!");
}
else if (choice == 2) {
if (contacts.isEmpty()) {
System.out.println("No contacts found.");
} else {
for (int i = 0; i < contacts.size(); i++) {
System.out.println((i + 1) + ". " + contacts.get(i));
}
}
}
else if (choice == 3) {
System.out.print("Enter contact number to update: ");
int idx = sc.nextInt();
sc.nextLine();
if (idx > 0 && idx <= contacts.size()) {
Contact c = contacts.get(idx - 1);
System.out.print("Enter new name: ");
c.name = sc.nextLine();
System.out.print("Enter new phone number: ");
c.phone = sc.nextLine();
System.out.println("Contact updated!");
} else {
System.out.println("Invalid number.");
}
}
else if (choice == 4) {
System.out.print("Enter contact number to delete: ");
int idx = sc.nextInt();
sc.nextLine();
if (idx > 0 && idx <= contacts.size()) {
contacts.remove(idx - 1);
System.out.println("Contact deleted!");
} else {
System.out.println("Invalid number.");
}
}
else if (choice == 5) {
System.out.print("Enter name to search: ");
String name = sc.nextLine();
boolean found = false;
for (Contact c : contacts) {
if (c.name.equalsIgnoreCase(name)) {
System.out.println("Contact found: " + c);
found = true;
break;
}
}
if (!found) {
System.out.println("Contact not found.");
}
}
else if (choice == 6) {
System.out.println("Exiting... Goodbye!");
}
else {
System.out.println("Invalid choice!");
}
}
sc.close();
}
}
