package com.busReservationSystem;
import java.sql.*;

public class BusDAO {
    public void displayBusInfo() throws SQLException {
        Connection con = DbConnection.getConnection();
        String query = "SELECT * FROM Bus";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            System.out.println("Bus No: " + rs.getInt(1));
            System.out.println("AC: " + (rs.getInt(2) == 0 ? "No" : "Yes"));
            System.out.println("Capacity: " + rs.getInt(3));
            System.out.println("------------------------------------------------------------");
        }
    }

    public int getCapacity(int busNo) throws SQLException {
        Connection con = DbConnection.getConnection();
        String query = "SELECT capacity FROM bus WHERE busNo = " + busNo;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        rs.next();
        return rs.getInt(1);
    }

    public boolean busExists(int busNo) throws SQLException {
        Connection con = DbConnection.getConnection();
        String query = "SELECT 1 FROM bus WHERE busNo = " + busNo;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs.next();
    }
}
