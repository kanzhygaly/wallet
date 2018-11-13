# wallet
**wallet** ...

## Supported functionality
 - 1
 - 2
 - 3
 - 4

## Technology stack
 - 1
 - 2
 - 3
 - 4
 
## Database Setup
 - Install PostgreSQL, my local is v10.1
 - Open command line from project directory and Login to PostgreSQL server
 > psql -U postgres -h localhost
 - Create User
 > CREATE USER wallet_user WITH password 'wallet_pass';
 - Create Database
 > CREATE DATABASE wallet OWNER wallet_user;
 - Change owner of schema "public"
 > ALTER SCHEMA public OWNER TO wallet_user;
 - Logout from PostgreSQL server
 > \q
 - Create Database Data
 > psql -U wallet_user -d wallet -a -f wallet-server\create-db.sql

## Build and Run
Build the project
- From project directory run
 > ./gradlew build

- If you want to run the tests on Wallet Server, simply run
> ./gradlew :wallet-server:test

Run the server

Run the client