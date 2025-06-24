# Instructions for using config.properties

## About db_init.sql

This file is used to initialize a locally hosted MySQL db

## Setting Up MySQL On Your System

In order for this project to be usable on your system, you need to locally host a db on your system, or have access to a deployed db that you can modify.

### Setting Up Local MySQL db on Linux/WSL (Ubuntu)

https://learn.microsoft.com/en-us/windows/wsl/tutorials/wsl-database

### Install MySQL Workbench for your system

This is not strictly required, but it provides a more user friendly user interface for interacting with and testing your db than the command line

### For hosting MySQL DB on WSL (Ubuntu 24.04.2) with Workbench on Windows 11

For my setup, I hosted the database within WSL and installed MySQL Workbench on Windows.  
This is not strictly necessary, but is what worked for me

## Initializing the DB

Once your DB (and Workbench if you choose to use it) are setup, you can use the init_db.sql file within the "/ReadingListProgressTracker/capstone_fh/resources" to initialize the db for this application.

### Using Workbench

1. Open MySQL Workbench and connect to the DB instance you are using
2. Navigate to the toolbar and click "File/Open SQL Script" and navigate to and choose init_db.sql
    - If this does not work, you can also use "File/New Query Tab" and copy and paste the everything within init_db.sql to the newly opened tab within workbench
3. Run everything in the script by clicking the lightningbolt icon without selecting anything within the script OR by selecting everything.
