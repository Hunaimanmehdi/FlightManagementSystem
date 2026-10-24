
import java.util.ArrayList;
import java.util.Scanner;

// Main class
public class FlightManagementSystem {

    static Scanner sc = new Scanner(System.in);

    // Store all flights and bookings
    static ArrayList<Flight> flights = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    static int nextBookingId = 1;

    // Flight class
    static class Flight {
        int flightId;
        String source;
        String destination;
        double price;
        int seatsAvailable;

        // Constructor
        Flight(int flightId, String source, String destination,
               double price, int seatsAvailable) {
            this.flightId = flightId;
            this.source = source;
            this.destination = destination;
            this.price = price;
            this.seatsAvailable = seatsAvailable;
        }

        // Display flight information
        void displayFlight() {
            System.out.println("Flight ID: " + flightId);
            System.out.println("From: " + source);
            System.out.println("To: " + destination);
            System.out.println("Ticket Price: Rs. " + price);
            System.out.println("Available Seats: " + seatsAvailable);
            System.out.println("-----------------------------");
        }
    }

    // Booking class
    static class Booking {
        int bookingId;
        String passengerName;
        int flightId;

        // Constructor
        Booking(int bookingId, String passengerName, int flightId) {
            this.bookingId = bookingId;
            this.passengerName = passengerName;
            this.flightId = flightId;
        }

        // Display booking information
        void displayBooking() {
            System.out.println("Booking ID: " + bookingId);
            System.out.println("Passenger: " + passengerName);
            System.out.println("Flight ID: " + flightId);
            System.out.println("-----------------------------");
        }
    }

    // Find a flight using its ID
    static Flight findFlight(int id) {
        for (Flight f : flights) {
            if (f.flightId == id) {
                return f;
            }
        }
        return null;
    }

    // Add a new flight
    static void addFlight() {
        System.out.print("Enter Flight ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (findFlight(id) != null) {
            System.out.println("This Flight ID already exists.");
            return;
        }

        System.out.print("Enter Source City: ");
        String source = sc.nextLine();

        System.out.print("Enter Destination City: ");
        String destination = sc.nextLine();

        if (source.trim().isEmpty() || destination.trim().isEmpty()
                || source.equalsIgnoreCase(destination)) {
            System.out.println("Enter valid and different cities.");
            return;
        }

        System.out.print("Enter Ticket Price: ");
        double price = sc.nextDouble();

        System.out.print("Enter Available Seats: ");
        int seats = sc.nextInt();

        if (price <= 0 || seats <= 0) {
            System.out.println("Price and seats must be greater than zero.");
            return;
        }

        flights.add(new Flight(id, source, destination, price, seats));

        System.out.println("Flight added successfully!");
    }

    // View all flights
    static void viewFlights() {
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
            return;
        }

        for (Flight f : flights) {
            f.displayFlight();
        }
    }

    // Search for a flight
    static void searchFlight() {
        System.out.print("Enter Flight ID to search: ");
        int id = sc.nextInt();

        Flight f = findFlight(id);

        if (f != null) {
            f.displayFlight();
        } else {
            System.out.println("Flight not found.");
        }
    }

    // Book a ticket
    static void bookTicket() {
        System.out.print("Enter Flight ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Flight f = findFlight(id);

        if (f == null) {
            System.out.println("Flight not found.");
            return;
        }

        if (f.seatsAvailable <= 0) {
            System.out.println("Sorry, no seats are available.");
            return;
        }

        System.out.print("Enter Passenger Name: ");
        String name = sc.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("Passenger name cannot be empty.");
            return;
        }

        Booking b = new Booking(nextBookingId++, name, id);
        bookings.add(b);

        // Reduce available seats after booking
        f.seatsAvailable--;

        System.out.println("Ticket booked successfully!");
        System.out.println("Your Booking ID is: " + b.bookingId);
        System.out.println("Ticket Price: Rs. " + f.price);
    }

    // View all bookings
    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking b : bookings) {
            b.displayBooking();
        }
    }

    // Cancel a booking
    static void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        int id = sc.nextInt();

        Booking selectedBooking = null;

        for (Booking b : bookings) {
            if (b.bookingId == id) {
                selectedBooking = b;
                break;
            }
        }

        if (selectedBooking == null) {
            System.out.println("Booking not found.");
            return;
        }

        // Return the seat to the flight
        Flight f = findFlight(selectedBooking.flightId);

        if (f != null) {
            f.seatsAvailable++;
        }

        bookings.remove(selectedBooking);

        System.out.println("Booking cancelled successfully!");
    }

    // Main method
    public static void main(String[] args) {

        // Sample flights for demonstration
        flights.add(new Flight(101, "Lahore", "Karachi", 15000, 50));
        flights.add(new Flight(102, "Islamabad", "Dubai", 45000, 30));
        flights.add(new Flight(103, "Multan", "Lahore", 10000, 20));

        int choice;

        do {
            System.out.println("\n===== FLIGHT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Flight");
            System.out.println("2. View All Flights");
            System.out.println("3. Search Flight");
            System.out.println("4. Book Ticket");
            System.out.println("5. View All Bookings");
            System.out.println("6. Cancel Booking");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addFlight();
                    break;

                case 2:
                    viewFlights();
                    break;

                case 3:
                    searchFlight();
                    break;

                case 4:
                    bookTicket();
                    break;

                case 5:
                    viewBookings();
                    break;

                case 6:
                    cancelBooking();
                    break;

                case 7:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 7);

        sc.close();
    }
}
