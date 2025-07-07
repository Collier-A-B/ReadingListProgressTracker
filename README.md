# README

## About This Project

This is my submission for Cognixia Future Horizons Capstone Project

For this project, I created an application that allows users to create their own  
reading list from the books available in the application

This is a terminal based application (text-only UI). Any outputs will be printed to the terminal, and the user will be prompted for input through the terminal.

## Setup

### Hard Requirements

The following are required in order to run/modify this project

- Java 17+
- MySQL DB (locally hosted)
- Maven
- IDE/TextEditor
- DB initialization script (sample one is provided in "/capstone_fh/src/test/resources/init_db.sql" from the project root directory)

### Soft Requirements (recommendations)

The following are not strictly required to run/modify this project, but are what I used in when developing/testing this project

- MySQL Workbench (for testing db interactions)
- VSCode (Other IDEs should work as well)

## Using the application

This application has two major modes that can be accessed,  
depending on the user's privileges:

1. User:  grants access to the user's reading list and the available options  
for editing and viewing it
2. Admin: grants access to administrator options for interacting with the database

***NOTE: when logged in as an administrator, you cannot access your reading list***

### Login Page

This is the "page" that is first displayed in the console.

The main options are

1. Login as user: allows user to access their reading list (available to all users)
2. Login as admin: allows user to write to database tables other than users_books (available only to admin users)
3. Create user account: allows user to create account with user privileges (cannot create admin account)
4. Exit Program

### User Mode

This is the "page" that is displayed when a user logs in as a user

The main functionalities available in user mode are

1. Display books in your reading list
2. Display books not added to your reading list
3. Display all books available in application
4. Add book to your list
5. Remove book from your list
6. Update status of book in your list
7. Logout of application

### Admin Mode

This is the "page" that is displayed when a user logs in as an admin

The main functionalities available in admin mode are

1. Add
2. Update
3. delete

This applies for authors, genres, books tables in database
