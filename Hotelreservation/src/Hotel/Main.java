package Hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class Main {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/user_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static final String HOTEL_DB_URL = "jdbc:mysql://localhost:3306/hotel_db";
	private static int reservationId;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection userConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Connection hotelConnection = DriverManager.getConnection(HOTEL_DB_URL, DB_USER, DB_PASSWORD);

            createUsersTable(userConnection);

            Scanner scanner = new Scanner(System.in);
            System.out.println("********Welcome to HM Hotel********* \n Signup SignIn App");

            while (true) {
                System.out.println("1. Sign Up");
                System.out.println("2. Sign In");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        signUp(userConnection, scanner);
                        break;
                    case 2:
                        signIn(userConnection, hotelConnection, scanner);
                        break;
                    case 3:
                        System.out.println("Thank You. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred. Exiting SignUpSignInApp.");
        }
    }

    private static void createUsersTable(Connection connection) throws Exception {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(255) NOT NULL," +
                "password VARCHAR(255) NOT NULL)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.execute();
        }
    }

    private static void signUp(Connection connection, Scanner scanner) throws Exception {
        System.out.println("Sign Up");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userExists(connection, username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        String insertUserSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        }

        System.out.println("Registration successful!");
    }

    private static void signIn(Connection userConnection, Connection hotelConnection, Scanner scanner) throws Exception {
        System.out.println("Sign In");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (validateUser(userConnection, username, password)) {
            System.out.println("Login successful!");
            hotelMain(hotelConnection, scanner);
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static boolean userExists(Connection connection, String username) throws Exception {
        String checkUserSQL = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkUserSQL)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private static boolean validateUser(Connection connection, String username, String password) throws Exception {
        String validateUserSQL = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(validateUserSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private static void hotelMain(Connection connection, Scanner scanner) throws Exception {
        while (true) {
            System.out.println();
            System.out.println("********WELCOME TO HM*********");
            System.out.println("1. Reserve a room");
            System.out.println("2. View Reservations");
            System.out.println("3. Get Room Number");
            System.out.println("4. Update Reservations");
            System.out.println("5. Delete Reservations");
            System.out.println("6. Pay for Reservation");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    ReserveRoom.reserveRoom(connection, scanner);
                    break;
                case 2:
                    ViewReservation.viewReservations(connection);
                    break;
                case 3:
                    RoomNumber.getRoomNumber(connection, scanner);
                    break;
                case 4:
                    UpdateReservation.updateReservation(connection, scanner);
                    break;
                case 5:
                    deleteReservation.deleteReservation(connection, scanner);
                    break;
                case 6:
                    payForReservation(connection, scanner);
                    break;
                case 7:
                    System.out.println("------Thank you, GoodBye!!!--------");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    private static void reserveRoom(Connection connection, Scanner scanner) {
        // Implement room reservation logic including payment integration here
        // Prompt user for payment details and process the payment securely
    }

    private static void viewReservations(Connection connection) {
        // Implement logic to view reservations
    }

    private static void getRoomNumber(Connection connection, Scanner scanner) {
        // Implement logic to get room number
    }

    private static void updateReservation(Connection connection, Scanner scanner) {
        // Implement logic to update reservations
    }

    private static void deleteReservation(Connection connection, Scanner scanner) {
        // Implement logic to delete reservations
    }
    private static void payForReservation(Connection connection, Scanner scanner) {
        System.out.println("Payment Processing");
        System.out.print("Enter payment amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter payment method (Credit/Debit Card, Online Banking, Digital Wallet, etc.):");
        String paymentMethod = scanner.nextLine();

        System.out.println("Enter payment details:");
        System.out.print("Card Number / Bank Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("CVV / Security Code: ");
        String cvv = scanner.nextLine();
        System.out.print("Expiration Date (MM/YYYY): ");
        String expirationDate = scanner.nextLine();

        // Assuming you have a payment gateway API to process payments
        boolean paymentSuccessful = processPayment(amount, paymentMethod, accountNumber, cvv, expirationDate);

        if (paymentSuccessful) {
            System.out.println("Payment successful!");
            // Update reservation status in the database to indicate payment completed
            // For example, you might have a reservations table where you can update the payment status column
            try {
                String updateSQL = "UPDATE reservations SET payment_status = 'Paid' WHERE reservation_id = ?";
                PreparedStatement statement = connection.prepareStatement(updateSQL);
                // Assuming reservation_id is the primary key of the reservations table
                // You would need to retrieve this value when making the reservation
                statement.setInt(1, reservationId); // Replace reservationId with the actual reservation ID
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Reservation payment status updated successfully.");
                } else {
                    System.out.println("Failed to update reservation payment status.");
                }
            } catch (SQLException e) {
                System.out.println("Error updating reservation payment status: " + e.getMessage());
            }
        } else {
            System.out.println("Payment failed. Please try again or choose another payment method.");
        }
    }

    private static boolean processPayment(double amount, String paymentMethod, String accountNumber, String cvv, String expirationDate) {
       
        return true;
    }
    


	}

