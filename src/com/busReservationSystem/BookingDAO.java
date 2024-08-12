package com.busReservationSystem;
import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public int getBookedCount(int busNo, Date date) throws SQLException {
        String query = "SELECT count(passengerName) FROM booking WHERE busNo = ? AND travelDate = ?";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        pst.setInt(1, busNo);
        pst.setDate(2, sqlDate);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public void addBooking(Booking booking) throws SQLException {
        String query = "INSERT INTO booking VALUES (?, ?, ?)";
        Connection con = DbConnection.getConnection();
        java.sql.Date sqlDate = new java.sql.Date(booking.date.getTime());
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, booking.passengerName);
        pst.setInt(2, booking.busNo);
        pst.setDate(3, sqlDate);
        pst.executeUpdate();
    }

    public void updateBooking(String oldName, Date oldDate, String newName, int newBusNo, Date newDate) throws SQLException {
        String query = "UPDATE booking SET passengerName = ?, busNo = ?, travelDate = ? WHERE passengerName = ? AND travelDate = ?";
        Connection con = DbConnection.getConnection();
        java.sql.Date newSqlDate = new java.sql.Date(newDate.getTime());
        java.sql.Date oldSqlDate = new java.sql.Date(oldDate.getTime());
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, newName);
        pst.setInt(2, newBusNo);
        pst.setDate(3, newSqlDate);
        pst.setString(4, oldName);
        pst.setDate(5, oldSqlDate);
        pst.executeUpdate();
    }

    public void cancelBooking(String passengerName, Date date) throws SQLException {
        String query = "DELETE FROM booking WHERE passengerName = ? AND travelDate = ?";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, passengerName);
        pst.setDate(2, new java.sql.Date(date.getTime()));
        pst.executeUpdate();
    }

    public List<Booking> searchBookings(String passengerName, int busNo, Date date) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM booking WHERE passengerName LIKE ? OR busNo = ? OR travelDate = ?";
        Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, "%" + passengerName + "%");
        pst.setInt(2, busNo);
        pst.setDate(3, new java.sql.Date(date.getTime()));
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Booking booking = new Booking();
            booking.passengerName = rs.getString(1);
            booking.busNo = rs.getInt(2);
            booking.date = rs.getDate(3);
            bookings.add(booking);
        }
        return bookings;
    }
}
