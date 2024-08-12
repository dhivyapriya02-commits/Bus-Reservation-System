package com.busReservationSystem;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BusDAO busdao = new BusDAO();
        BookingDAO bookingdao = new BookingDAO();
        Scanner scanner = new Scanner(System.in);

        try {
            busdao.displayBusInfo();

            int userOpt = 1;

            while (userOpt != 3) {
                System.out.println("Enter 1 to Book, 2 to Cancel/Reschedule, 3 to Exit");
                userOpt = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (userOpt == 1) {
                    Booking booking = new Booking();
                    if (booking.isAvailable()) {
                        bookingdao.addBooking(booking);
                        System.out.println("Your booking is confirmed");
                    } else {
                        System.out.println("Sorry. Bus is full. Try another bus or date.");
                    }
                } else if (userOpt == 2) {
                    System.out.println("Enter passenger name: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter booking date (dd-MM-yyyy): ");
                    String dateInput = scanner.nextLine();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = dateFormat.parse(dateInput);

                    System.out.println("Enter 1 to Cancel, 2 to Reschedule: ");
                    int opt = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (opt == 1) {
                        bookingdao.cancelBooking(name, date);
                        System.out.println("Booking cancelled successfully.");
                    } else if (opt == 2) {
                        System.out.println("Enter new date (dd-MM-yyyy): ");
                        String newDateInput = scanner.nextLine();
                        Date newDate = dateFormat.parse(newDateInput);

                        System.out.println("Enter new bus no: ");
                        int newBusNo = scanner.nextInt();

                        bookingdao.updateBooking(name, date, name, newBusNo, newDate);
                        System.out.println("Booking rescheduled successfully.");
                    }
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
