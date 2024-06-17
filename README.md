# Plane Seat Management System

This project is a simple plane seat management system implemented in Java. It allows users to buy, cancel, and search for plane seats, view the seating plan, and print ticket information along with total sales.

## Features

1. **Buy a seat:** Allows users to book a seat by providing row and column choices, and stores passenger information.
2. **Cancel a seat:** Allows users to cancel a previously booked seat.
3. **Find first available seat:** Finds the first available seat in the plane.
4. **Show seating plan:** Displays the current seating plan showing booked and available seats.
5. **Print ticket information and total sales:** Prints details of all sold tickets and the total sales amount.
6. **Search ticket:** Searches for a specific ticket by row and column and displays the passenger information.

## Usage

When you run the program, you will be presented with the following menu:


You can choose an option by entering the corresponding number.

### Buy a Seat

1. Enter the row letter (A to D).
2. Enter the column number based on the row (1-14 for rows A and D, 1-12 for rows B and C).
3. Provide passenger information: name, surname, and email.

### Cancel a Seat

1. Enter the row letter (A to D).
2. Enter the column number based on the row (1-14 for rows A and D, 1-12 for rows B and C).

### Find First Available Seat

The program will find and display the first available seat.

### Show Seating Plan

The program will display the seating plan with 'o' indicating available seats and 'x' indicating booked seats.

### Print Ticket Information and Total Sales

The program will print the details of all sold tickets and display the total sales amount.

### Search Ticket

1. Enter the row letter (A to D).
2. Enter the column number based on the row (1-14 for rows A and D, 1-12 for rows B and C).

If the seat is booked, the program will display the ticket and passenger information.

## Classes

### `Person`

This class stores passenger information:

- `name`: Passenger's first name.
- `surname`: Passenger's surname.
- `email`: Passenger's email address.

### `Ticket`

This class stores ticket information:

- `seat`: Seat identifier (row and column).
- `price`: Price of the ticket.
- `passenger`: A `Person` object containing passenger information.

#### Methods

- `print_ticket()`: Prints the ticket information.
- `save(path)`: Saves the ticket information to a file.

## How to Run

1. Make sure you have Java installed on your machine.
2. Compile the Java files:
    ```bash
    javac PlaneManagement.java
    ```
3. Run the program:
    ```bash
    java PlaneManagement
    ```

## Error Handling

The program includes error handling for invalid inputs, such as entering non-numeric values where numbers are expected or entering row letters outside the range A-D.

## Future Improvements

- Add graphical user interface (GUI).
- Save data to a database for persistent storage.
- Enhance search functionality to include more criteria.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
