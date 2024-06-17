import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.File;
public class PlaneManagement {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int[][] all_seats = new int[4][];// Every seat is assigned value 0 at the start.
        all_seats[0] = new int[14];
        all_seats[1] = new int[12];
        all_seats[2] = new int[12];
        all_seats[3] = new int[14];
        int[][] tickets = new int[4][];//to record booked  tickets,all values are assigned 0 at the start.
        tickets[0] = new int[14];
        tickets[1] = new int[12];
        tickets[2] = new int[12];
        tickets[3] = new int[14];
        Person[][] passengerInfo; // to store passenger information.
        passengerInfo = new Person[all_seats.length][all_seats[0].length];
        System.out.println("Welcome to the plane management System ");
        System.out.println("******************************");
        System.out.println("*       Menu options         *");
        System.out.println("******************************");
        System.out.println("1) Buy a seat");
        System.out.println("2) Cancel a seat");
        System.out.println("3) Find first available seat");
        System.out.println("4) Show seating plan");
        System.out.println("5) Print ticket information and total sales");
        System.out.println("6) Search ticket");
        System.out.println("0) Quit");
        System.out.println("******************************");

        // Getting the option user chose
        int user_choice = -1;
        do {
            try {
                System.out.println("Please enter an option number:");
                user_choice = input.nextInt();
                if (user_choice >= 0 && user_choice <= 6) { /*This makke code easier to understand and it shows that each if works independetly and may cause a differnet action*/
                    if (user_choice == 1) {
                        buy_seat(input, all_seats, tickets,passengerInfo);
                    }
                    if (user_choice == 2) {
                        cancel_seat(input, all_seats, tickets);
                    }
                    if (user_choice == 3) {
                        find_first_available(all_seats);
                    }
                    if (user_choice == 4) {
                        show_seating_plan(all_seats);
                    }
                    if (user_choice == 5) {
                        print_ticket_info(tickets);
                    }
                    if (user_choice == 6) {
                        search_ticket(input, all_seats, tickets,passengerInfo);
                    }
                } else {
                    System.out.println("Enter a number between 0 to 6 only");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter only a number!");
                input.next(); // Clear the invalid input from the scanner
            }
        } while (user_choice != 0);
        System.out.println("Exiting the program.");
    }
    /*purpose - to determine price of seat according to the column number
     variable----> price-price of the seat */
    public static int seat_price(int column_choice) {
        int price;
        if (column_choice >= 0 && column_choice <= 4) { //this way it more clear which price apply to which range.I used only if to avoid overlapping.
            price = 200;
        }
        if (column_choice >= 5 && column_choice <= 8) {
            price = 150;
        }
        if (column_choice >= 9 && column_choice <= 14) {
            price = 180;
        }
        return price;
    }

    /* purpose- To convert user input row_choice to row_index(using switch)
    variables----> row_choice- user entered row letter
                   row_index- row number that matches with row letter*/
    public static int row_index_convertor(String row_choice) {
        int row_index = -1;
        switch (row_choice) {
            case "A":
                row_index = 0;
                break;
            case "B":
                row_index = 1;
                break;
            case "C":
                row_index = 2;
                break;
            case "D":
                row_index = 3;
                break;
        }
        return row_index;

    }

    /* purpose - To get row and column of the seat according to user preference and book the seat,
     display user information and ticket information,
       recording ticket price in the ticket array
       variables----> row_correct- to control while loop
                      row_choice- user entered row letter
                      column_correct - to control while loop
                      columnLimit - maximum number user can input for a column*/
    public static void buy_seat(Scanner input, int[][] all_seats, int[][] tickets,Person[][] passengerInfo) {
        // getting input for row choice and validate
        boolean row_correct = false;
        String row_choice = "";
        while (!row_correct) {
            System.out.println("Enter a row letter you want to book (A to D):");
            row_choice = input.next().toUpperCase();
            String regex = "[A-D]"; //this is not a variablr this is a regular espression
            if (row_choice.matches(regex)) {
                row_correct = true;
            } else {
                System.out.println("Please enter a letter between A to D only");
            }
        }
        int row_index = row_index_convertor(row_choice);//calling the method to get row_index
        // getting input for column choice and validate
        boolean column_correct = false;
        int column_choice = -1;
        while (!column_correct) {
            try {
                int columnLimit = (row_choice.equals("A") || row_choice.equals("D")) ? 14 : 12;//ternary conditional operator, a handy way to replace if else in one line
                System.out.println("Enter a column number between 1-" + columnLimit + ": ");
                column_choice = input.nextInt();
                if (column_choice <= columnLimit && column_choice >= 1) {
                    if (all_seats[row_index][column_choice - 1] == 0) {
                        all_seats[row_index][column_choice - 1] = 1; // Mark seat as booked
                        System.out.println("Seat booked");
                        System.out.println("Enter passenger's name:");
                        String name = input.next();
                        System.out.println("Enter passenger's surname:");
                        String surname = input.next();
                        System.out.println("Enter passenger's email:");
                        String email = input.next();

                        String seat = row_choice + column_choice;
                        int price = seat_price(column_choice);
                        tickets[row_index][column_choice - 1] = price;
                        Person passenger = new Person(name, surname, email);
                        passengerInfo[row_index][column_choice - 1] = passenger;
                        Ticket ticket = new Ticket( seat, price, passenger);
                        ticket.print_ticket();

                        String path=seat+".txt";
                        ticket.save(path);
                    } else {
                        System.out.println("Seat already booked! Sorry!");
                        break;
                    }
                    column_correct = true;
                } else {
                    System.out.println("Please enter a number between 1 to " + columnLimit + " only!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a number only");
                input.next(); // Clear the invalid input from the scanner
            }
        }
    }

    /* purpose-To get the row letter and column number from the user and cancel the seat,
       remove the ticket price from ticket array
    variables-----> row_correct-it is used to control the while loop
                    cancel_row_letter-It store the row letter user entered
                    cancel_row_index-convert row_choice to an integer number using a switch
     */public static void cancel_seat(Scanner input, int[][] all_seats, int[][] tickets) {
        boolean row_correct = false;
        String cancel_row_letter = "";
        int cancel_row_index = -1;
        while (!row_correct) {
            System.out.println("Please enter the row letter you want to cancel (A to D):");
            cancel_row_letter = input.next().toUpperCase();
            String regex = "[A-D]";
            if (cancel_row_letter.matches(regex)) {
                row_correct = true;
            } else {
                System.out.println("Please enter a letter between A to D only");
            }
        }

        cancel_row_index = row_index_convertor(cancel_row_letter);//calling the method to get cancel_row_index

        // Prompt for column choice and validate
        boolean column_correct = false;
        int cancel_column_number = -1;
        String seat_cancel = "";
        while (!column_correct) {
            try {
                int columnLimit = (cancel_row_letter.equals("A") || cancel_row_letter.equals("D")) ? 14 : 12;
                System.out.println("Enter the seat number between 1 to " + columnLimit + ":");
                cancel_column_number = input.nextInt();
                if (cancel_column_number <= columnLimit && cancel_column_number >= 1) {
                    if (all_seats[cancel_row_index][cancel_column_number - 1] == 0) {
                        System.out.println("Seat is not booked");
                    } else {
                        all_seats[cancel_row_index][cancel_column_number - 1] = 0; // Mark seat as available
                        tickets[cancel_row_index][cancel_column_number - 1] = 0;
                        System.out.println("Seat booking cancelled!");
                    }
                    column_correct = true;
                } else {
                    System.out.println("Enter a number 1 to " + columnLimit + " only!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a number only!");
                input.next(); // Clear the invalid input from the scanner
            }
        }
        seat_cancel = cancel_row_letter + cancel_column_number;//will delete the created file if user cancel the ticket.
        String file_name = seat_cancel + ".txt";
        File file = new File(file_name);
        if (file.exists()) {
            file.delete();
        }
    }
    /* purpose- To find the first seat which is still available.
    variables-----> first_found_row:stores the index of the row where the first available seat is found.
                    first_found_column:stores the index of the column where the first available seat is found.
                    first_found_row_letter:convert first_found_row to letter representation using switch
     */
    public static void find_first_available(int[][] all_seats) {
        int first_found_row=-1;
        int first_found_column = -1;
        String first_found_row_letter = "";
        for (int i = 0; i < all_seats.length; i++) {
            for (int j = 0; j < all_seats[i].length; j++) {
                if (all_seats[i][j] == 0) {
                    first_found_row = i;
                    first_found_column = j;
                    break;
                }
            }
            if (first_found_row != -1) {
                break;
            }
        }
        switch (first_found_row) {
            case 0:
                first_found_row_letter = ("A");
                break;
            case 1:
                first_found_row_letter = ("B");
                break;
            case 2:
                first_found_row_letter = ("C");
                break;
            case 3:
                first_found_row_letter = ("D");
                break;
        }
        if (first_found_row != -1) {
            System.out.println("First seat found at" + " " + first_found_row_letter + " " + (first_found_column + 1));
        } else {
            System.out.println("Sorry! Every seat is booked.");
        }
    }

    /* purpose-To show seat plan and print x if the seat is booked or print o if the seat is still available
       variables------> row - current row being iterated over
                        col -  current seat in the row. */
    public static void show_seating_plan(int[][] all_seats) {
        for (int[] row : all_seats) {
            for (int col : row) {
                if (col == 0) {
                    System.out.print("o ");
                }
                if (col == 1) {
                    System.out.print("x ");
                }
            }
            System.out.println();
        }
    }
    /* purpose-To show which seats were sold during the session and give the total amount of sales.
       variables------> row - current row being iterated over.
                        col -  current seat in the row.
                        Column_Number-To get the right  column because col starts with 0.
                        Row_Letter-To determine the Row letter based on the row number
                        total_Sales-To store the total sale value*/
    public static void print_ticket_info(int[][] tickets) {
        int total_Sales = 0;
        for (int row = 0; row < tickets.length; row++) {
            char Row_Letter = (char) ('A' + row);
            for (int col = 0; col < tickets[row].length; col++) {
                if (tickets[row][col] != 0) {
                    int Column_Number = col + 1;
                    System.out.println("Ticket information for seat " + Row_Letter + Column_Number + ": Price £" + tickets[row][col]);
                    total_Sales += tickets[row][col];
                }
            }
        }
        System.out.println("Total sales:  £" + total_Sales);
    }
    /* purpose-To show the ticket and passenger information of a booked seat or say it is still available .
       variables------> row_correct- to control the while loop
                        search_row_letter-to store the row letter that user want to search
                        search_row_index-to store the column number user want to search
                        column_correct-to control the while loop
                        search_column_number-to store the column number user want to search
                        columnLimit - maximum number user can input for a column
                        foundTicket-it shows ticket found during the search
                        passengerName-stores passenger name
                        passengerSurname-stores passenger surname
                        passengerEmail-stores passenger email
                        */
    public static void search_ticket(Scanner input, int[][] all_seats, int[][] tickets, Person[][] passengerInfo) {
        boolean row_correct = false;
        String search_row_letter = "";
        int search_row_index = -1;

        while (!row_correct) {
            System.out.println("Enter the row letter of the seat you want to search (A to D):");
            search_row_letter = input.next().toUpperCase();
            String regex = "[A-D]";
            if (search_row_letter.matches(regex)) {
                row_correct = true;
            } else {
                System.out.println("Please enter a letter between A to D only");
            }
        }
        search_row_index = row_index_convertor(search_row_letter);

        boolean column_correct = false;
        int search_column_number = -1;

        while (!column_correct) {
            try {
                System.out.println("Enter the seat number you want to search:");
                search_column_number = input.nextInt();

                int columnLimit = (search_row_letter.equals("A") || search_row_letter.equals("D")) ? 14 : 12;
                if (search_column_number <= columnLimit && search_column_number > 0) {
                    Ticket foundTicket;
                    if (all_seats[search_row_index][search_column_number - 1] == 0) {
                        System.out.println("This seat is available.");
                        break;
                    } else {
                        String passengerName = passengerInfo[search_row_index][search_column_number - 1].getName();
                        String passengerSurname = passengerInfo[search_row_index][search_column_number - 1].getSurname();
                        String passengerEmail = passengerInfo[search_row_index][search_column_number - 1].getEmail();

                        foundTicket = new Ticket(search_row_letter + search_column_number, tickets[search_row_index][search_column_number - 1],
                                new Person(passengerName, passengerSurname, passengerEmail));
                    }
                    foundTicket.print_ticket();
                    System.out.println("Passenger Information:");
                    System.out.println("Name: " + foundTicket.getPassenger().getName());
                    System.out.println("Surname: " + foundTicket.getPassenger().getSurname());
                    System.out.println("Email: " + foundTicket.getPassenger().getEmail());
                    column_correct = true;
                } else {
                    System.out.println("Invalid seat number. Please enter a number between 1 to " + columnLimit + " only!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a number only!");
                input.next();
            }
        }
    }
}