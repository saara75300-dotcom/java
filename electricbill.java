```java
import java.util.Scanner;

public class electricbill1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter previous reading: ");
        int prev = sc.nextInt();

        System.out.print("Enter current reading: ");
        int cur = sc.nextInt();
        sc.nextLine(); 
        int units = cur - prev;
        if (units < 0) {
            System.out.println("Error: Current reading must be greater than previous reading.");
            return;
        }

        System.out.print("Enter type (domestic/commercial): ");
        String type = sc.nextLine();

        double bill = 0;

        if (type.equalsIgnoreCase("domestic")) {
            if (units <= 100) {
                bill = units * 1;
            } else if (units <= 200) {
                bill = units * 2.5;
            } else if (units <= 500) {
                bill = units * 4;
            } else {
                bill = units * 6;
            }
        } else if (type.equalsIgnoreCase("commercial")) {
            if (units <= 100) {
                bill = units * 2;
            } else if (units <= 200) {
                bill = units * 4.5;
            } else if (units <= 500) {
                bill = units * 6;
            } else {
                bill = units * 7;
            }
        } else {
            System.out.println("Invalid type entered.");
            return;
        }
        System.out.println("\n--- Electricity Bill ---");
        System.out.println("Name: " + name);
        System.out.println("Previous Reading: " + prev);
        System.out.println("Current Reading: " + cur);
        System.out.println("Units Consumed: " + units);
        System.out.println("Connection Type: " + type);
        System.out.println("Total Bill: ₹" + bill);
    }
}
