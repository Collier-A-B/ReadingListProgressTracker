drop database if exists progress_tracker;
create database progress_tracker;
use progress_tracker;

-- Create the users table
create table users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Create the books table
create table books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL UNIQUE,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    publication_date DATE,
    isbn VARCHAR(20) UNIQUE
);

-- Create the users_books table to track reading progress
create table users_books (
    user_book_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    status ENUM('to_read', 'reading', 'completed') DEFAULT 'to_read',
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

-- insert some sample data into users
insert into users (username, password) values
('john_doe', 'password123'),
('jane_smith', 'securepassword'),
('alice_jones', 'mypassword');

-- insert some sample data into books
insert into books (title, author, genre, publication_date, isbn) values
('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', '1925-04-10', '9780743273565'),
('To Kill a Mockingbird', 'Harper Lee', 'Fiction', '1960-07-11', '9780061120084'),
('1984', 'George Orwell', 'Dystopian', '1949-06-08', '9780451524935'),
('Pride and Prejudice', 'Jane Austen', 'Romance', '1813-01-28', '9780141040349');