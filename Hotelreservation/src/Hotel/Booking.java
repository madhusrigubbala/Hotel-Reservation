package Hotel;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;

public class Booking {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "root";

    @SuppressWarnings("resource")
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            while(true){
                System.out.println();
                System.out.println("********WELCOME TO HM HOTEL*********");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                    	ReserveRoom ReserveRoom = new ReserveRoom();
                        Hotel.ReserveRoom.reserveRoom(connection, scanner);
                        break;
                    case 2:
                    	ViewReservation ViewReservations = new ViewReservation();
                        ViewReservation.viewReservations(connection);
                        break;
                    case 3:
                    	RoomNumber GetRoomNumber = new RoomNumber();
                        RoomNumber.getRoomNumber(connection, scanner);
                        break;
                    case 4:
                    	UpdateReservation UpdateReservation = new UpdateReservation();
                        Hotel.UpdateReservation.updateReservation(connection, scanner);
                        break;
                    case 5:
                    	deleteReservation DeleteReservation = new deleteReservation();
                        deleteReservation.deleteReservation(connection, scanner);
                        break;
                    case 6:
                        System.out.println("------Thank you, GoodBye!!!--------");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        


    }

}
