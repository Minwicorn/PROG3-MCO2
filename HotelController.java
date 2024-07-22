import java.util.ArrayList;
import java.util.List;

/**
 * The HotelController class manages operations related to hotels and rooms,
 * providing methods to add hotels, add rooms to hotels, manage reservations,
 * and perform various operations related to hotel management and booking.
 * It interacts with HotelModel for data storage and HotelView for user interface.
 */
public class HotelController {
    private List<HotelModel> hotels; // List of hotels managed by the controller
    private HotelView view; // View component for displaying messages and data

    // Default price for a room when added without specifying a price
    private static final double DEFAULT_ROOM_PRICE = 1299.0;

    /**
     * Constructor to initialize the HotelController with a list of hotels and a view.
     *
     * @param hotels List of HotelModel objects representing the hotels managed by the controller
     * @param view   HotelView object for displaying messages and data to the user
     */
    public HotelController(List<HotelModel> hotels, HotelView view) {
        this.hotels = hotels;
        this.view = view;
        this.hotels = new ArrayList<>();
    }

    // Helper Method to find a hotel by name
    private HotelModel findHotelByName(String hotelName) {
        for (HotelModel hotel : hotels) {
            if (hotel.getHotelName().equalsIgnoreCase(hotelName)) {
                return hotel;
            }
        }
        return null;
    }

    /**
     * Adds a new hotel with the specified name to the list of hotels.
     * Automatically adds a default room with room number 101 to the new hotel.
     * Displays a success message upon successful addition.
     *
     * @param hotelName Name of the hotel to be added
     */
    public void addHotel(String hotelName) {
        if (findHotelByName(hotelName) != null) {
            view.displayEnterAnother("hotel name.");
        } else {
            HotelModel newHotel = new HotelModel(hotelName);
            hotels.add(newHotel);
            view.displaySuccess("Added hotel " + hotelName);
            addRoomToHotel(hotelName, 101, 1);
            view.displayHotelDetails(newHotel);
        }
    }

    /**
     * Removes the hotel with the specified name from the list of hotels.
     * Displays a success message upon successful removal.
     *
     * @param hotelName Name of the hotel to be removed
     * @return true if the hotel was successfully removed, false otherwise
     */
    public boolean removeHotel(String hotelName) {
        HotelModel hotel = findHotelByName(hotelName);
        if (hotel != null) {
            hotels.remove(hotel);
            view.displaySuccess("Hotel removed");
            return true;
        } else {
            view.displayHotelNotFound(hotelName);
            return false;
        }
    }

    /**
     * Adds the specified number of rooms with consecutive room numbers starting from the given roomNumber
     * to the hotel with the specified name. Each room is added with the default room price.
     * Displays a success message upon successful addition of rooms.
     *
     * @param hotelName  Name of the hotel to which rooms are to be added
     * @param roomNumber Starting room number of the first room to be added
     * @param count      Number of rooms to be added
     */
    public void addRoomToHotel(String hotelName, int roomNumber, int count) {
        int successes = 0;
        
        HotelModel hotel = findHotelByName(hotelName);
        if (hotel != null) {
            Room room = hotel.getRoom(roomNumber);
            if(hotel.getRooms().size() >= 50) // Ensure the maximum is enforced
            {
                view.displayMaxRooms();
                return;
            } else if(room == null) {
                for(int i=0;i<count;i++){
                    successes += addRoomToHotel(hotelName, roomNumber+i, DEFAULT_ROOM_PRICE);
                }
                view.displaySuccess("Added " + successes + " room(s)");
                return;
            }
            else {
                view.displayEnterAnother("room number.");
            }
        }
    }

     /**
     * Adds a room with the specified room number and price to the hotel with the specified name.
     * Displays a success message upon successful addition of the room.
     *
     * @param hotelName  Name of the hotel to which the room is to be added
     * @param roomNumber Room number of the room to be added
     * @param price      Price of the room to be added
     * @return 1 if the room was successfully added, 0 otherwise
     */
    public int addRoomToHotel(String hotelName, int roomNumber, double price) {
        HotelModel hotel = findHotelByName(hotelName);
        if (hotel != null) {
            Room room = hotel.getRoom(roomNumber);
            if (room == null) {
                hotel.addRoom(new Room(roomNumber, price));
                view.displaySuccess("Room "+ roomNumber + " added");
                return 1;
            } else {
                view.displayEnterAnother("room number.");
                return 0;
            }
        } else {
            view.displayHotelNotFound(hotelName);
            return 0;
        }
    }

    /**
     * Removes the room with the specified room number from the hotel with the specified name.
     * Displays a success message upon successful removal of the room.
     *
     * @param hotelName  Name of the hotel from which the room is to be removed
     * @param roomNumber Room number of the room to be removed
     * @return true if the room was successfully removed, false otherwise
     */
    public boolean removeRoomFromHotel(String hotelName, int roomNumber) {
        HotelModel hotel = findHotelByName(hotelName);
        if (hotel != null) {
            Room room = hotel.getRoom(roomNumber);
            if (room != null && hotel.getRooms().size()>1) {
                hotel.getRooms().remove(room);
                view.displaySuccess("Room removed");
                return true;
            } else if(hotel.getRooms().size() == 1) {
                view.displayMinRooms();
                return false;
            } 
            else {
                view.displayRoomNotFound(roomNumber);
                return false;
            }
        } else {
            view.displayHotelNotFound(hotelName);
            return false;
        }
    }

    /**
     * Updates the price of all rooms in the hotel with the specified name to the new price,
     * provided that the new price is greater than 100.0 and the hotel has zero reservations.
     * Displays a success message upon successful update of room prices.
     *
     * @param hotelName Name of the hotel whose room prices are to be updated
     * @param newPrice  New price for the rooms in the hotel
     */
    public void updateRoomPrice(String hotelName, double newPrice) {
        if (newPrice < 100) {
            view.displayEnterAnother("price. Must be greater than 100.0.");
            return;
        }

        HotelModel hotel = findHotelByName(hotelName);
        if (hotel != null && hotel.getReservations().size()==0) {
            for(Room room : hotel.getRooms())
                room.setPrice(newPrice);
            view.displaySuccess("Room prices updated");
            return;
        } else if(hotel.getReservations().size()>0){
            view.displayEnterAnother("hotel. Only hotels with zero reservations can update price.");
        }
        else {
            view.displayHotelNotFound(hotelName);
        }
    }

    /**
     * Changes the name of the hotel with the oldName to the newName, ensuring that no other hotel
     * with the newName exists. Displays a success message upon successful name change.
     *
     * @param oldName Old name of the hotel to be renamed
     * @param newName New name for the hotel
     * @return true if the hotel name was successfully changed, false otherwise
     */
    public boolean changeHotelName(String oldName, String newName) {
        if (findHotelByName(newName) != null) {
            view.displayEnterAnother("new hotel name.");
            return false;
        }

        HotelModel hotel = findHotelByName(oldName);
        if (hotel != null) {
            hotel.setHotelName(newName);
            view.displaySuccess("Hotel name changed");
            return true;
        } else {
            view.displayHotelNotFound(oldName);
            return false;
        }
    }

     /**
     * Estimates the earnings for the hotel with the specified name, calculating the total earnings
     * from all booked rooms in the hotel.
     *
     * @param hotelName Name of the hotel for which earnings are to be estimated
     * @return Estimated earnings for the hotel
     */
    public double estimateEarnings(String hotelName) {
        HotelModel hotel = findHotelByName(hotelName);
        if (hotel != null) {
            return hotel.calculateEarnings();
        } else {
            view.displayHotelNotFound(hotelName);
            return 0;
        }
    }

    /**
     * Retrieves a list of rooms that are currently booked in the hotel with the specified name.
     *
     * @param hotelName Name of the hotel for which booked rooms are to be retrieved
     * @return List of booked rooms in the hotel
     */
    public List<Room> getBookedRooms(String hotelName) {
        HotelModel hotel = findHotelByName(hotelName);
        if (hotel != null) {
            List<Room> bookedRooms = new ArrayList<>();
            for (Room room : hotel.getRooms()) {
                if (room.isBooked()) {
                    bookedRooms.add(room);
                }
            }
            return bookedRooms;
        } else {
            view.displayHotelNotFound(hotelName);
            return null;
        }
    }

   /**
     * Retrieves a list of rooms that are currently available (not booked) in the hotel with the specified name.
     *
     * @param hotelName Name of the hotel for which available rooms are to be retrieved
     * @return List of available rooms in the hotel
     */
    public List<Room> getAvailableRooms(String hotelName) {
        HotelModel hotel = findHotelByName(hotelName);
        if (hotel != null) {
            List<Room> availableRooms = new ArrayList<>();
            for (Room room : hotel.getRooms()) {
                if (!room.isBooked()) {
                    availableRooms.add(room);
                }
            }
            return availableRooms;
        } else {
            view.displayHotelNotFound(hotelName);
            return null;
        }
    }

     /**
     * Retrieves the count of available rooms in the hotel with the specified name for the given date range.
     *
     * @param hotelName   Name of the hotel for which available rooms count is to be retrieved
     * @param checkInDate Check-in date for the reservation
     * @param checkOutDate Check-out date for the reservation
     * @return Count of available rooms in the hotel for the specified date range
     */
    public int getAvailableRoomsCount(String hotelName, int checkInDate, int checkOutDate) {
        HotelModel hotel = getHotel(hotelName);
        if (hotel != null) {
            int count = 0;
            for (Room room : hotel.getRooms()) {
                if (room.isAvailable(checkInDate, checkOutDate)) {
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

     /**
     * Retrieves the count of booked rooms in the hotel with the specified name for the given date range.
     *
     * @param hotelName   Name of the hotel for which booked rooms count is to be retrieved
     * @param checkInDate Check-in date for the reservation
     * @param checkOutDate Check-out date for the reservation
     * @return Count of booked rooms in the hotel for the specified date range
     */
    public int getBookedRoomsCount(String hotelName, int checkInDate, int checkOutDate) {
        HotelModel hotel = getHotel(hotelName);
        if (hotel != null) {
            int count = 0;
            for (Room room : hotel.getRooms()) {
                if (!room.isAvailable(checkInDate, checkOutDate)) {
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

    /**
     * Makes a reservation for the specified guest in the room with the given roomNumber
     * at the hotel with the specified hotelName for the provided check-in and check-out dates.
     * Displays a success message upon successful reservation.
     *
     * @param hotelName   Name of the hotel for which reservation is to be made
     * @param roomNumber  Room number of the room for which reservation is to be made
     * @param guestName   Name of the guest making the reservation
     * @param checkInDate Check-in date for the reservation
     * @param checkOutDate Check-out date for the reservation
     */
    public void makeReservation(String hotelName, int roomNumber, String guestName, int checkInDate, int checkOutDate) {
        HotelModel hotel = findHotelByName(hotelName);
        if (hotel != null) {
            Room room = hotel.getRoom(roomNumber);
            if (room != null) {
            if (isOverlappingReservation(room, checkInDate, checkOutDate)) {
                view.displayEnterAnother("date range. The room is already booked for the selected dates.");
            } else {
                Reservation reservation = new Reservation(room, guestName, roomNumber, checkInDate, checkOutDate);
                room.addReservation(reservation);
                hotel.addReservation(reservation);
                view.displaySuccess("Reservation made");
            }
        } else {
            view.displayRoomNotFound(roomNumber);
        }
    } else {
        view.displayHotelNotFound(hotelName);
    }
}

// Helper method to check for overlapping reservations
private boolean isOverlappingReservation(Room room, int checkInDate, int checkOutDate) {
    for (Reservation existingReservation : room.getReservations()) {
        int existingCheckIn = existingReservation.getCheckInDate();
        int existingCheckOut = existingReservation.getCheckOutDate();

        // Check for overlap
        if ((checkInDate >= existingCheckIn && checkInDate <= existingCheckOut) || 
            (checkOutDate >= existingCheckIn && checkOutDate <= existingCheckOut) ||
            (checkInDate < existingCheckIn && checkOutDate > existingCheckOut)) {
            return true;
        }
    }
    return false;
}

    /**
     * Cancels the reservation for the specified room in the hotel with the specified hotelName
     * for the provided check-in and check-out dates.
     * Displays a success message upon successful cancellation.
     *
     * @param hotelName   Name of the hotel for which reservation is to be cancelled
     * @param roomNumber  Room number of the room for which reservation is to be cancelled
     * @param checkInDate Check-in date for the reservation
     * @param checkOutDate Check-out date for the reservation
     */
    public void cancelReservation(String hotelName, int roomNumber, int checkInDate, int checkOutDate) {
        HotelModel hotel = findHotelByName(hotelName);
        if (hotel != null) {
            Room room = hotel.getRoom(roomNumber);
            if (room != null) {
                if (room.isBooked(checkInDate, checkOutDate)) {
                    hotel.removeReservation(roomNumber, checkInDate, checkOutDate);
                    view.displaySuccess("Reservation cancelled");
                } else {
                    view.displayEnterAnother("reservation. The specified dates do not match any reservation.");
                }
            } else {
                view.displayRoomNotFound(roomNumber);
            }
        } else {
            view.displayHotelNotFound(hotelName);
        }
    }

    /**
     * Retrieves the list of hotels managed by the controller.
     *
     * @return List of HotelModel objects representing the hotels managed by the controller
     */
    public List<HotelModel> getHotels() {
        return hotels;
    }

     /**
     * Retrieves the availability details of the room with the specified roomNumber in the hotel with the specified hotelName
     * across the month, providing a list of Room objects representing the availability status for each day.
     *
     * @param hotelName  Name of the hotel for which room availability is to be retrieved
     * @param roomNumber Room number of the room for which availability is to be retrieved
     * @return List of Room objects representing the availability status across the month
     */
    public List<Room> getRoomAvailabilityAcrossMonth(String hotelName, int roomNumber) {
        HotelModel hotel = getHotel(hotelName);
        if (hotel != null) {
            Room room = hotel.getRoom(roomNumber);
            if (room != null) {
                return room.getAvailabilityAcrossMonth(); // Ensure Room class has this method
            }
        }
        return new ArrayList<>();
    }

     /**
     * Retrieves the HotelModel object representing the hotel with the specified name.
     *
     * @param hotelName Name of the hotel to be retrieved
     * @return HotelModel object representing the hotel with the specified name,
     *         or null if no such hotel exists
     */
    public HotelModel getHotel(String hotelName) {
        HotelModel hotel = findHotelByName(hotelName);
        if (hotel != null) {
            return hotel;
        } else {
            view.displayHotelNotFound(hotelName);
            return null;
        }
    }

   /**
     * Retrieves the reservation details for the specified room in the hotel with the specified hotelName,
     * for the provided check-in and check-out dates.
     *
     * @param hotelName   Name of the hotel for which reservation details are to be retrieved
     * @param roomNumber  Room number of the room for which reservation details are to be retrieved
     * @param checkInDate Check-in date for the reservation
     * @param checkOutDate Check-out date for the reservation
     * @return Reservation object representing the reservation details,
     *         or null if no such reservation exists
     */
    public Reservation getReservationDetails(String hotelName, int roomNumber, int checkInDate, int checkOutDate) {
        HotelModel hotel = getHotel(hotelName);
        if (hotel == null) {
            return null;
        }
    
        Room room = hotel.getRoom(roomNumber);
        if (room == null) {
            return null;
        }
    
        for (Reservation reservation : hotel.getReservations()) {
            if (reservation.getRoom() != null && // Null check
                reservation.getRoom().getRoomNumber() == roomNumber &&
                reservation.getCheckInDate() == checkInDate &&
                reservation.getCheckOutDate() == checkOutDate) {
                return reservation;
            }
        }
        return null;
    }
    
    /**
     * Method to set a percent modifier for a specific date
     * @param day day in which price is being modified
     * @param percent percent from 50% to 150% on price to modify
     */
    public void datePriceModifier(HotelModel hotel, int day, int percent){
        hotel.setDayModifier(day, percent);
    }

    /**
     * Method to set a percent modifier for a date range
     * @param day1 first day in which price is being modified
     * @param day2 last day in which price is being modified
     * @param percent percent from 50% to 150% on price to modify
     */
    public void datePriceModifier(HotelModel hotel, int day1, int day2, int percent){
        for(int i=day1;i<=day2;i++){
            datePriceModifier(hotel,i,percent);
        }
    }
}