package Hotel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class RoomNumber {
	 public static void getRoomNumber(Connection connection, Scanner scanner) {
	        try {
	            System.out.print("Enter reservation ID: ");
	            int reservationId = scanner.nextInt();
	            System.out.print("Enter guest name: ");
	            String guestName = scanner.next();

	            String sql = "SELECT room_number FROM reservations " +
	                    "WHERE reservation_id = " + reservationId +
	                    " AND guest_name = '" + guestName + "'";

	            try (Statement statement = connection.createStatement();
	                 ResultSet resultSet = statement.executeQuery(sql)) {

	                if (resultSet.next()) {
	                    int roomNumber = resultSet.getInt("room_number");
	                    System.out.println("Room number for Reservation ID " + reservationId +
	                            " and Guest " + guestName + " is: " + roomNumber);
	                } else {
	                    System.out.println("Reservation not found for the given ID and guest name.");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
