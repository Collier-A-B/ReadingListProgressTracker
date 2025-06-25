drop database if exists progress_tracker;
create database progress_tracker;
use progress_tracker;

-- Create the users table
create table users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create the authors table
create table authors (
    author_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create the genres table
create table genres (
    genre_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create the books table
create table books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    genre_id INT NOT NULL,
    author_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    publication_date DATE,
    isbn_13 VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (genre_id) REFERENCES genres(genre_id),
    FOREIGN KEY (author_id) REFERENCES authors(author_id)
);

-- Create the users_books table to track reading progress
create table users_books (
    user_book_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    status ENUM('to_read', 'reading', 'completed') DEFAULT 'to_read',
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (user_id, book_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

/*
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
*/
-- insert some sample data into users
insert into users (username, password, is_admin) values
('john_doe', 'password123', FALSE),
('adam_collier', 'securepass456', TRUE),
('jane_smith', 'mypassword789', FALSE),
('alice_jones', 'alicepass321', FALSE);


/*  Fields in authors table:
    author_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
*/
-- insert some sample data into authors
insert into authors (name) values
('George Orwell'),
('J.K. Rowling'),
('J.R.R. Tolkien'),
('Agatha Christie'),
('Isaac Asimov'),
('Gillian Flynn'),
('Christopher Clark'),
('Lewis Carroll'),
('Aldous Huxley');

/*  Fields in genres table:
    genre_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
*/
-- insert some sample data into genres
insert into genres (name) values
('Science Fiction'),
('Fantasy'),
('Mystery'),
('Non-Fiction');

/*  Fields in books table:
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    genre_id INT NOT NULL,
    author_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    publication_date DATE,
    isbn VARCHAR(255) UNIQUE
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (genre_id) REFERENCES genres(genre_id),
    FOREIGN KEY (author_id) REFERENCES authors(author_id)
*/
-- insert some sample data into books
insert into books (genre_id, author_id, title, publication_date, isbn_13) values
(1, 1, '1984', '1949-06-08', '9791280035356'),
(2, 2, "Harry Potter and the Sorcerer\'s Stone", '1997-06-26', '9781338878929'),
(1, 3, 'The Hobbit', '1937-09-21', '9780547928227'),
(3, 4, 'And Then There Were None', '1939-11-06', '9780573014413'),
(1, 5, 'The Naked Sun', '1957-05-01', '9780553293395'),
(3, 6, 'Gone Girl', '2012-06-05', '9780307588371'),
(4, 7, 'Revolutionary Spring: Europe Aflame And The Fight For A New World, 1848-1849', '2023-06-13', '9780525575214'),
(2, 8, "Alice's Adventures in Wonderland", '1865-07-04', '9780147515872'),
(2, 2, "Harry Potter and the Chamber of Secrets", '1998-07-02', '9781338878936'),
(1, 9, 'Brave New World', '1932-08-01', '9780060850524');


/*  Fields in users_books table:
    user_book_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    status ENUM('to_read', 'reading', 'completed') DEFAULT 'to_read',
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (user_id, book_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
*/
-- insert some sample data into users_books
insert into users_books (user_id, book_id, status, start_date, end_date) values
(1, 1, 'completed', '2023-01-01', '2023-01-15'),
(1, 2, 'reading', '2023-02-01', NULL),
(2, 3, 'to_read', NULL, NULL),
(3, 4, 'completed', '2023-03-01', '2023-03-10'),
(4, 5, 'reading', '2023-04-01', NULL),
(1, 6, 'to_read', NULL, NULL),
(2, 7, 'completed', '2023-05-01', '2023-05-20'),
(3, 8, 'to_read', NULL, NULL),
(4, 9, 'reading', '2023-06-01', NULL);