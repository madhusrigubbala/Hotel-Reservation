CREATE DATABASE hotel_db;

USE hotel_db;

CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(255),
    room_number INT,
    contact_number VARCHAR(20),
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
select * from reservations;


CREATE TABLE IF NOT EXISTS payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    reservation_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(100) NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    cvv VARCHAR(10) NOT NULL,
    expiration_date VARCHAR(10) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id)
);
select * from payments;
CREATE DATABASE user_db;
USE user_db;
drop table user_db;
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
select * from users;
