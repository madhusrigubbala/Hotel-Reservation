package Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ReserveRoom {

    public static void reserveRoom(Connection connection, Scanner scanner) {
        try {
            System.out.print("Enter guest name: ");
            String guestName = scanner.next();
            scanner.nextLine();
            System.out.print("Enter room number: ");
            int roomNumber = scanner.nextInt();
            System.out.print("Enter contact number: ");
            String contactNumber = scanner.next();

            if (isRoomBooked(connection, roomNumber)) {
                System.out.println("Sorry, the room is already booked.");
                return;
            }

            String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, guestName);
                preparedStatement.setInt(2, roomNumber);
                preparedStatement.setString(3, contactNumber);

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Reservation successful!");
                } else {
                    System.out.println("Reservation failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isRoomBooked(Connection connection, int roomNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservations WHERE room_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
}
