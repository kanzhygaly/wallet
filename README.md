# wallet

**wallet-proto** gRPC Service Interface (API).
**wallet-server** keeps track of a users monetary balance in the system.
**wallet-client** emulates users depositing and withdrawing funds.

## Technology stack
 - Java 8
 - gRPC
 - JPA/Hibernate
 - PostgreSQL
 - Gradle
 - JUnit 4
 - SLF4J
 
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
 > psql -U wallet_user -d wallet -a -f wallet-server/create-db.sql

## Build and Run
Build the project
- From project directory run
 > ./gradlew build

To run the Java server and client locally:
```
# Start the server (listens on port 8980)
java -jar wallet-server/build/libs/wallet-server-1.0.jar

# Run the client (connects to localhost:8980)
java -jar wallet-client/build/libs/wallet-client-1.0.jar
```