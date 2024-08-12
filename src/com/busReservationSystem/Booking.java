package com.busReservationSystem;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Booking {
    String passengerName;
    int busNo;
    Date date;

    Booking() throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter name of passenger: ");
        passengerName = sc.nextLine();
        while (passengerName.isEmpty()) {
            System.out.println("Passenger name cannot be empty. Enter again: ");
            passengerName = sc.nextLine();
        }

        System.out.println("Enter bus no: ");
        busNo = sc.nextInt();
        BusDAO busdao = new BusDAO();
        while (!busdao.busExists(busNo)) {
            System.out.println("Bus number is invalid. Enter a valid bus no: ");
            busNo = sc.nextInt();
        }

        System.out.println("Enter date (dd-MM-yyyy): ");
        String dateInput = sc.next();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            date = dateFormat.parse(dateInput);
            while (!isDateValid(date)) {
                System.out.println("Date is invalid or in the past. Enter a future date: ");
                dateInput = sc.next();
                date = dateFormat.parse(dateInput);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean isDateValid(Date date) {
        return date.after(new Date()); // Date must be in the future
    }

    public boolean isAvailable() throws SQLException {
        BusDAO busdao = new BusDAO();
        BookingDAO bookingdao = new BookingDAO();
        int capacity = busdao.getCapacity(busNo);
        int booked = bookingdao.getBookedCount(busNo, date);
        return booked < capacity;
    }
}
