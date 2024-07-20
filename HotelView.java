import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * The `HotelView` class represents the view layer of the hotel reservation system.
 * It interacts with users through the console, displaying information and receiving input.
 */
public class HotelView {
    private HotelController controller;
    private Scanner scanner;

    /**
     * Constructs a new `HotelView` object with a default `Scanner` for user input.
     */
    public HotelView() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Sets the controller for this view to interact with the backend logic.
     *
     * @param controller The `HotelController` instance to set.
     */
    public void setController(HotelController controller) {
        this.controller = controller;
    }

    /**
     * Displays a message indicating that a hotel with the given name
     * was not found.
     *
     * @param hotelName The name of the hotel that was not found.
     */
    public void displayHotelNotFound(String hotelName) {
        System.out.println("Hotel not found: " + hotelName);
    }

    /**
     * Displays a message indicating that a room with the given number
     * was not found.
     *
     * @param roomNumber The number of the room that was not found.
     */
    public void displayRoomNotFound(int roomNumber) {
        System.out.println("Room not found: " + roomNumber);
    }

    /**
     * Displays a success message after performing an operation.
     *
     * @param interaction The type of interaction that was successful.
     */
    public void displaySuccess(String interaction) {
        System.out.println(interaction + " successfully.");
    }

     /**
     * Prompts the user to enter another item of a specified type.
     *
     * @param type The type of item the user is asked to enter.
     */
    public void displayEnterAnother(String type) {
        System.out.println("Please enter another " + type);
    }


    /**
     * Displays a message indicating that the maximum number of rooms
     * has been reached for a hotel.
     */
    public void displayMaxRooms() {
        System.out.println("Maximum number of rooms reached.");
    }

     /**
     * Displays a message indicating that the minimum number of rooms
     * has been reached for a hotel.
     */
    public void displayMinRooms() {
        System.out.println("Minimum number of rooms reached.");
    }

     /**
     * Displays a list of hotels including their names, number of rooms,
     * and number of reservations.
     *
     * @param hotels The list of `HotelModel` objects representing hotels
     *               to be displayed.
     */
    public void displayHotels(List<HotelModel> hotels) {
        if (hotels.isEmpty()) {
            System.out.println("No hotels available.");
        } else {
            System.out.println("List of Hotels:");
            for (HotelModel hotel : hotels) {
                System.out.println("Hotel Name: " + hotel.getHotelName());
                System.out.println("Rooms: " + hotel.getRooms().size());
                System.out.println("Reservations: " + hotel.getReservations().size());
                System.out.println(); // Blank line between hotels for readability
            }
        }
    }

     /**
     * Displays detailed information about a specific hotel, including
     * its name, number of rooms, and number of reservations.
     *
     * @param hotel The `HotelModel` object representing the hotel to be
     *              displayed.
     */
    public void displayHotelDetails(HotelModel hotel) {
        System.out.println("Hotel Details:");
        System.out.println("Hotel Name: " + hotel.getHotelName());
        System.out.println("Rooms: " + hotel.getRooms().size());
        System.out.println("Reservations: " + hotel.getReservations().size());
        System.out.println(); // Blank line for readability
    }

     /**
     * Displays detailed information about a specific room, including
     * its number, price, and booking status.
     *
     * @param room The `Room` object representing the room to be displayed.
     */
    public void displayRoomDetails(Room room) {
        System.out.println("Room Details:");
        System.out.println("Room Number: " + room.getRoomNumber());
        System.out.println("Price: $" + room.getPrice());
        System.out.println("Status: " + (room.isBooked() ? "Booked" : "Available"));
        System.out.println(); // Blank line for readability
    }

     /**
     * Displays a list of booked rooms, showing their room numbers and prices.
     *
     * @param bookedRooms The list of `Room` objects representing booked rooms
     *                    to be displayed.
     */
    public void displayBookedRooms(List<Room> bookedRooms) {
        if (bookedRooms != null && !bookedRooms.isEmpty()) {
            System.out.println("Booked rooms:");
            for (Room room : bookedRooms) {
                System.out.println("Room Number: " + room.getRoomNumber() + ", Price: $" + room.getPrice());
            }
        } else {
            System.out.println("No booked rooms found.");
        }
    }

     /**
     * Displays a list of available rooms, showing their room numbers and prices.
     *
     * @param availableRooms The list of `Room` objects representing available rooms
     *                       to be displayed.
     */
    public void displayAvailableRooms(List<Room> availableRooms) {
        if (availableRooms != null && !availableRooms.isEmpty()) {
            System.out.println("Available rooms:");
            for (Room room : availableRooms) {
                System.out.println("Room Number: " + room.getRoomNumber() + ", Price: $" + room.getPrice());
            }
        } else {
            System.out.println("No available rooms found.");
        }
    }

     /**
     * Displays the count of available and booked rooms for a specific date
     * in the context of a specific hotel.
     */
    private void showRoomCountsForDate() {
        HotelModel hotel = validateHotelName("Enter hotel name: ");
        int date = 0;
        
        while (date<1 || date>31) {
            System.out.print("Enter date (1-31) to check room counts: ");
            date = scanner.nextInt();
            if(date<1 || date>31)
                displayEnterAnother("date within the specified range.");
        }

        int availableCount = controller.getAvailableRoomsCount(hotel.getHotelName(), date, date);
        int bookedCount = controller.getBookedRoomsCount(hotel.getHotelName(), date, date);

        System.out.println("On " + date + ":");
        System.out.println("Available rooms: " + availableCount);
        System.out.println("Booked rooms: " + bookedCount);
    }

     /**
     * Shows detailed information about a specific room for the entire month,
     * including availability status for each day.
     */
    private void showRoomInfoAcrossMonth() {
        HotelModel hotel = validateHotelName("Enter hotel name: ");
        Room room = validateRoomNumber(hotel, "Enter room number: ");

        // Display room details
        displayRoomDetails(room);

        // Display availability across the month
        displayMonthlyAvailability(room);
    }

     /**
     * Displays the availability status of a room across a month,
     * showing whether the room is available or booked for each day.
     *
     * @param room The `Room` object for which availability is displayed.
     */
    private void displayMonthlyAvailability(Room room) {
        System.out.println("Availability for " + room.getRoomName() + ":");
        
        for(int day=1;day<=31;day++)
        {
            String status = room.isAvailable(day, day) ? "Available" : "Booked";
            System.out.println("Day " + day + ": " + status);
        }
        
    }

    /**
     * Prompts the user to confirm changes by entering 'Y' for yes or 'N' for no.
     *
     * @return `true` if the user confirms changes, `false` otherwise.
     */
    private boolean confirmChanges() {
        char c;
        do{
            System.out.print("Confirm changes? (Y/N) ");
            c = scanner.next().charAt(0);
        } while(c != 'Y' && c != 'N');

        if(c == 'Y')
                return true;
            else
                return false;
    }
    /**
     * Displays detailed reservation information including hotel name, room number,
     * guest name, check-in date, check-out date, total price, and price per night.
     */
    public void showReservationDetails() {
        HotelModel hotel = validateHotelName("Enter hotel name: ");
        Room room = validateRoomNumber(hotel, "Enter room number: ");
        int checkInDate;
        int checkOutDate;
    
        do {
            System.out.print("Enter check-in date (1-31): ");
            checkInDate = scanner.nextInt();
            if (checkInDate < 1 || checkInDate > 31)
                displayEnterAnother("date within the specified range.");
        } while (checkInDate < 1 || checkInDate > 31);
    
        do {
            System.out.print("Enter check-out date (1-31): ");
            checkOutDate = scanner.nextInt();
            if (checkOutDate < 1 || checkOutDate > 31)
                displayEnterAnother("date within the specified range.");
            else if (checkOutDate < checkInDate)
                displayEnterAnother("date.\nCheck-out date must be after check-in date.");
        } while (checkOutDate < 1 || checkOutDate > 31 || checkOutDate < checkInDate);
    
        // Retrieve reservation details from controller
        Reservation reservation = controller.getReservationDetails(hotel.getHotelName(), room.getRoomNumber(), checkInDate, checkOutDate);
    
        if (reservation != null) {
            // Calculate and set total price
            reservation.calculateTotalPrice();
    
            System.out.println("\nReservation Details:");
            System.out.println("Hotel Name: " + hotel.getHotelName());
            System.out.println("Room Number: " + room.getRoomNumber());
            System.out.println("Guest Name: " + reservation.getGuestName());
            System.out.println("Check-in Date: " + checkInDate);
            System.out.println("Check-out Date: " + checkOutDate);
            System.out.println("Total Price: $" + reservation.getTotalPrice());
            System.out.println("Price per night: $" + reservation.getPricePerNight());
        } else {
            System.out.println("No reservation found for room number " + room.getRoomNumber() + " in hotel: " + hotel.getHotelName() +
                    " from " + checkInDate + " to " + checkOutDate);
        }
    } 

    /**
     * Starts the hotel reservation system and displays a dynamic menu based on the
     * existence of hotels and user input choices. Allows users to create hotels,
     * view hotel details, manage hotels, simulate bookings, and exit the system.
     */
    public void start() {
        while (true) {
            System.out.println("\nHotel Reservation System");

            // Dynamic menu based on hotel existence
            if (controller.getHotels().isEmpty()) {
                System.out.println("1. Create hotel");
                System.out.println("2. List Hotels");
            } 
            else { 
                System.out.println("\n1. Create hotel");
                System.out.println("2. View Hotel");
                System.out.println("3. Manage Hotel");
                System.out.println("4. Simulate Booking");
                System.out.println("5. Exit");
            }

            System.out.print("Enter your choice: ");
            int choice = getValidatedIntInput("Enter your choice: ", 1, 5);
            scanner.nextLine(); // consume newline

            switch(choice) {
                case 1:
                    addHotel();
                    break;
                case 2:
                case 3:
                case 4:
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }

            // View Hotel
            if(choice == 2) {
                System.out.println("\n1. Show hotel details");
                System.out.println("2. Estimate earnings for a hotel");
                System.out.println("3. Show reservation details");
                System.out.println("4. Back to main menu");

                System.out.print("Enter your choice: ");
                int choice2 = getValidatedIntInput("Enter your choice: ", 1, 4);
                scanner.nextLine(); // consume newline

                switch(choice2) {
                    case 1:
                        if (!controller.getHotels().isEmpty()) {
                            getHotelDetails();
                        } else {
                            System.out.println("No hotels available. Please add a hotel first.");
                        }
                        break;
                    case 2:
                        if (!controller.getHotels().isEmpty()) {
                            estimateEarnings();
                        } else {
                            System.out.println("No hotels available. Please add a hotel first.");
                        }
                        break;
                    case 3:
                        if (areAnyRoomsAvailable()) {
                            showReservationDetails(); // Call the new method
                        } else {
                            System.out.println("No rooms available. Please add a room first.");
                        }
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            } 
            
            // Manage Hotel
            else if(choice==3) {
                System.out.println("\n1. Change hotel name");
                System.out.println("2. Add a room to a hotel");
                System.out.println("3. Remove a room from a hotel");
                System.out.println("4. Update room price");
                System.out.println("5. Remove a hotel");
                System.out.println("6. List hotels");
                System.out.println("7. Back to main menu");

                System.out.print("Enter your choice: ");
                int choice3 = getValidatedIntInput("Enter your choice: ", 1, 7);
                scanner.nextLine(); // consume newline

                switch(choice3) {
                    case 1:
                        if (!controller.getHotels().isEmpty()) {
                            changeHotelName();
                        } else {
                            System.out.println("No hotels available. Please add a hotel first.");
                        }
                        break;
                    case 2:
                        if (controller.getHotels().isEmpty()) {
                            System.out.println("No hotels available. Please add a hotel first.");
                        } else {
                            addRoomToHotel();
                        }
                        break;
                    case 3:
                        if (areAnyRoomsAvailable()) {
                            removeRoomFromHotel();
                        } else {
                            System.out.println("No rooms available to remove. Please add a room first.");
                        }
                        break;
                    case 4:
                        if (areAnyRoomsAvailable()) {
                            updateRoomPrice();
                        } else {
                            System.out.println("No rooms available. Please add a room first.");
                        }
                        break;
                    case 5:
                        if (controller.getHotels().isEmpty()) {
                            listHotels();
                        } else {
                            removeHotel();
                        }
                        break;
                    case 6:
                        listHotels();
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            } // Simulate Booking
            else if(choice==4) {
                System.out.println("\n1. Make a reservation");
                System.out.println("2. Cancel a reservation");
                System.out.println("3. List booked rooms in a hotel");
                System.out.println("4. List available rooms in a hotel");
                System.out.println("5. Show room counts for a date");
                System.out.println("6. Show selected room info across the month");
                System.out.println("7. Back to main menu");

                System.out.print("Enter your choice: ");
                int choice4 = getValidatedIntInput("Enter your choice: ", 1, 7);
                scanner.nextLine(); // consume newline

                switch(choice4) {
                    case 1:
                        if (areAnyRoomsAvailable()) {
                            makeReservation();
                        } else {
                            System.out.println("No rooms available. Please add a room first.");
                        }
                        break;
                    case 2:
                        if (areAnyRoomsAvailable()) {
                            cancelReservation();
                        } else {
                            System.out.println("No rooms available. Please add a room first.");
                        }
                        break;
                    case 3:
                        if (areAnyRoomsAvailable()) {
                            listBookedRooms();
                        } else {
                            System.out.println("No rooms available. Please add a room first.");
                        }
                        break;
                    case 4:
                        if (areAnyRoomsAvailable()) {
                            listAvailableRooms();
                        } else {
                            System.out.println("No rooms available. Please add a room first.");
                        }
                        break;
                    case 5:
                        if (!controller.getHotels().isEmpty()) {
                            showRoomCountsForDate(); // Call the new method
                        } else {
                            System.out.println("No hotels available. Please add a hotel first.");
                        }
                        break;
                    case 6:
                        if (areAnyRoomsAvailable()) {
                            showRoomInfoAcrossMonth();
                        } else {
                            System.out.println("No rooms available. Please add a room first.");
                        }
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        }
    }
    
    /**
     * Checks if any rooms are available in any hotel managed by the system.
     *
     * @return `true` if there are rooms available in any hotel, `false` otherwise.
     */
    private boolean areAnyRoomsAvailable() {
        for (HotelModel hotel : controller.getHotels()) {
            if (!hotel.getRooms().isEmpty()) {
                return true;
            }
        }
        return false;
    }    

    /**
     * Utility method to validate the existence of a hotel based on user input.
     *
     * @param prompt The prompt message to display when requesting input.
     * @return The validated `HotelModel` object corresponding to the hotel name entered by the user.
     */
    private HotelModel validateHotelName(String prompt) {
        HotelModel hotel;
        while (true) {
            System.out.print(prompt);
            String hotelName = scanner.nextLine();
            hotel = controller.getHotel(hotelName);
            if (hotel == null) {
                displayEnterAnother("hotel name.");
            } else {
                break;
            }
        }
        return hotel;
    }

   /**
     * Utility method to validate the existence of a room in a specified hotel based on user input.
     *
     * @param hotel The `HotelModel` object representing the hotel in which to validate the room.
     * @param prompt The prompt message to display when requesting input.
     * @return The validated `Room` object corresponding to the room number entered by the user.
     */
    private Room validateRoomNumber(HotelModel hotel, String prompt) {
        Room room;
        while (true) {
            System.out.print(prompt);
            int roomNumber = scanner.nextInt();
            scanner.nextLine(); // consume newline
            room = hotel.getRoom(roomNumber);
            if (room == null) {
                displayRoomNotFound(roomNumber);
                displayEnterAnother("room number");
            } else {
                break;
            }
        }
        return room;
    }

    /**
     * Utility method to get validated integer input within a specified range from the user.
     *
     * @param prompt The prompt message to display when requesting input.
     * @param minValue The minimum allowed integer value (inclusive).
     * @param maxValue The maximum allowed integer value (inclusive).
     * @return The validated integer input from the user within the specified range.
     */
    private int getValidatedIntInput(String prompt, int minValue, int maxValue) {
        int input = -1;
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.print(prompt);
                input = scanner.nextInt();
                
                if (input < minValue || input > maxValue) {
                    throw new InputMismatchException();
                }
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
            }
        }

        return input;
    }

    /**
     * Utility method to get validated double input from the user.
     *
     * @param prompt The prompt message to display when requesting input.
     * @return The validated double input from the user.
     */
    private double getValidatedDoubleInput(String prompt) {
        double input = -1;
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.print(prompt);
                input = scanner.nextDouble();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }

        return input;
    }

     /**
     * Displays detailed information about a specific hotel based on user input.
     */
    private void getHotelDetails(){
        HotelModel hotel = validateHotelName("Enter hotel name: ");
        displayHotelDetails(hotel);
    }

     /**
     * Adds a new hotel to the system based on user input.
     */
    private void addHotel() {
        System.out.print("Enter hotel name: ");
        String hotelName = scanner.nextLine();
        controller.addHotel(hotelName);
    }

     /**
     * Removes a hotel from the system based on user input.
     */
    private void removeHotel() {
        HotelModel hotel = validateHotelName("Enter hotel name to remove: ");
        boolean confirm = confirmChanges();
        if(confirm){
            boolean check = controller.removeHotel(hotel.getHotelName());
            if(check)
                System.out.println("Removed hotel: " + hotel.getHotelName());
        }
    }

     /**
     * Adds rooms to a specified hotel based on user input.
     */
    private void addRoomToHotel() {
        HotelModel hotel = validateHotelName("Enter hotel name: ");
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        System.out.print("Enter number of rooms to add: ");
        int count = scanner.nextInt();
        scanner.nextLine(); // consume newline
        boolean confirm = confirmChanges();
        if(confirm)
            controller.addRoomToHotel(hotel.getHotelName(), roomNumber, count);
    }

     /**
     * Removes a room from a specified hotel based on user input.
     */
    private void removeRoomFromHotel() {
        HotelModel hotel = validateHotelName("Enter hotel name: ");
        Room room = validateRoomNumber(hotel, "Enter room number to remove: ");
        boolean confirm = confirmChanges();
        if(confirm){
            boolean check = controller.removeRoomFromHotel(hotel.getHotelName(), room.getRoomNumber());
            if(check)
                System.out.println("Removed room number " + room.getRoomNumber() + " from hotel: " + hotel.getHotelName());
        }
    }

    /**
     * Updates the price of rooms in a specified hotel based on user input.
     */
    private void updateRoomPrice() {
        HotelModel hotel = validateHotelName("Enter hotel name: ");
        System.out.print("Enter new room price: ");
        double newPrice = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        boolean confirm = confirmChanges();
        if(confirm)
            controller.updateRoomPrice(hotel.getHotelName(), newPrice);
    }

    /**
     * Changes the name of a specified hotel based on user input.
     */
    private void changeHotelName() {
        HotelModel hotel = validateHotelName("Enter old hotel name: ");
        System.out.print("Enter new hotel name: ");
        String newName = scanner.nextLine();
        String oldName = hotel.getHotelName();
        boolean confirm = confirmChanges();
        if(confirm){
            boolean check = controller.changeHotelName(hotel.getHotelName(), newName);
            if(check){
                System.out.println("Changed hotel name from " + oldName + " to " + newName);
                hotel = controller.getHotel(newName);
                if (hotel != null) {
                    displayHotelDetails(hotel);
                }
            }
        }
    }

     /**
     * Estimates the earnings of a specified hotel based on user input.
     */
    private void estimateEarnings() {
        HotelModel hotel = validateHotelName("Enter hotel name: ");
        double earnings = controller.estimateEarnings(hotel.getHotelName());
        System.out.println("Estimated earnings for hotel " + hotel.getHotelName() + ": $" + earnings);
    }

    /**
     * Lists all rooms that are currently booked in a specified hotel based on user input.
     */
    private void listBookedRooms() {
        HotelModel hotel = validateHotelName("Enter hotel name: ");
        List<Room> bookedRooms = controller.getBookedRooms(hotel.getHotelName());
        displayBookedRooms(bookedRooms);
    }

    /**
     * Lists all rooms that are currently available in a specified hotel based on user input.
     */
    private void listAvailableRooms() {
        HotelModel hotel = validateHotelName("Enter hotel name: ");
        List<Room> availableRooms = controller.getAvailableRooms(hotel.getHotelName());
        displayAvailableRooms(availableRooms);
    }

      /**
     * Makes a reservation for a room in a specified hotel based on user input.
     */
    private void makeReservation() {
        HotelModel hotel = validateHotelName("Enter hotel name: ");
        Room room = validateRoomNumber(hotel, "Enter room number: ");
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();
        int checkInDate;
        int checkOutDate;

        do{
            System.out.print("Enter check-in date (1-31): ");
            checkInDate = scanner.nextInt();
            if(checkInDate<1 || checkInDate>31)
                displayEnterAnother("date within the specified range.");
        }
        while (checkInDate<1 || checkInDate>31);

        do{
            System.out.print("Enter check-out date (1-31): ");
            checkOutDate = scanner.nextInt();
            if(checkOutDate<1 || checkOutDate>31)
                displayEnterAnother("date within the specified range.");
            else if(checkOutDate<checkInDate)
                displayEnterAnother("date.\nCheck-out date must be after check-in date.");
        }
        while (checkOutDate<1 || checkOutDate>31 || checkOutDate<checkInDate);

        controller.makeReservation(hotel.getHotelName(), room.getRoomNumber(), guestName, checkInDate, checkOutDate);
    }

    /**
     * Cancels a reservation for a room in a specified hotel based on user input.
     */
    private void cancelReservation() {
        HotelModel hotel = validateHotelName("Enter hotel name: ");
        Room room = validateRoomNumber(hotel, "Enter room number: ");
        int checkInDate;
        int checkOutDate;

        do{
            System.out.print("Enter check-in date (1-31): ");
            checkInDate = scanner.nextInt();
            if(checkInDate<1 || checkInDate>31)
                displayEnterAnother("date within the specified range.");
        }
        while (checkInDate<1 || checkInDate>31);

        do{
            System.out.print("Enter check-out date (1-31): ");
            checkOutDate = scanner.nextInt();
            if(checkOutDate<1 || checkOutDate>31)
                displayEnterAnother("date within the specified range.");
            else if(checkOutDate<checkInDate)
                displayEnterAnother("date.\nCheck-out date must be after check-in date.");
        }
        while (checkOutDate<1 || checkOutDate>31 || checkOutDate<checkInDate);

        controller.cancelReservation(hotel.getHotelName(), room.getRoomNumber(), checkInDate, checkOutDate);
        System.out.println("Cancelled reservation for room number " + room.getRoomNumber() + " in hotel: " + hotel.getHotelName() +
                           " from " + checkInDate + " to " + checkOutDate);
        displayRoomDetails(room);
    }

    /**
     * Lists all hotels currently managed by the system.
     */
    private void listHotels() {
        List<HotelModel> hotels = controller.getHotels();
        displayHotels(hotels);
    }
}
