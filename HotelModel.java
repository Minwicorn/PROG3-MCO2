import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a hotel with rooms and reservations.
 */
public class HotelModel {
    private String hotelName;
    private List<Room> rooms;
    private List<Reservation> reservations;
    private int[] dayModifier = new int[31];

    /**
     * Constructs a new HotelModel with the specified hotel name.
     * @param hotelName The name of the hotel.
     */
    public HotelModel(String hotelName) {
        this.hotelName = hotelName;
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.dayModifier = new int[31];
        for(int i=0;i<31;i++){
            dayModifier[i]=100; // initializes all days to 100% price
        }
    }

    /**
     * Retrieves the name of the hotel.
     * @return The name of the hotel.
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * Sets the name of the hotel.
     * @param hotelName The new name of the hotel.
     */
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * Retrieves the list of rooms in the hotel.
     * @return The list of rooms.
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Retrieves the list of reservations made in the hotel.
     * @return The list of reservations.
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Adds a reservation to the hotel.
     * @param reservation The reservation to add.
     */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    /**
     * Adds a room to the hotel.
     * @param room The room to add.
     */
    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    /**
     * Retrieves a room by its room number.
     * @param roomNumber The number of the room to retrieve.
     * @return The room with the specified room number, or null if not found.
     */
    public Room getRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null; // Room not found
    }

    /**
     * Removes a room from the hotel by its room number.
     * @param roomNumber The number of the room to remove.
     * @return true if the room was successfully removed, false otherwise.
     */
    public boolean removeRoom(int roomNumber) {
        Iterator<Room> iterator = rooms.iterator();
        while (iterator.hasNext()) {
            Room room = iterator.next();
            if (room.getRoomNumber() == roomNumber) {
                iterator.remove();
                return true; // Room removed
            }
        }
        return false; // Room not found
    }

    /**
     * Updates the price of a room in the hotel.
     * @param roomNumber The number of the room to update.
     * @param newPrice The new price of the room.
     * @return true if the room's price was successfully updated, false otherwise.
     */
    public boolean updateRoomPrice(int roomNumber, double newPrice) {
        Room room = getRoom(roomNumber);
        if (room != null) {
            room.setPrice(newPrice);
            return true; // Price updated
        }
        return false; // Room not found
    }

    /**
     * Calculates the total earnings from booked rooms in the hotel.
     * @return The total earnings from booked rooms.
     */
    public double calculateEarnings() {
        double earnings = 0;
        for (Reservation reservation : reservations) {
            earnings += reservation.getTotalPrice();
        }
        return earnings;
    }

    /**
     * Retrieves a list of rooms that are currently booked in the hotel.
     * @return The list of booked rooms.
     */
    public List<Room> getBookedRooms() {
        List<Room> bookedRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isBooked()) {
                bookedRooms.add(room);
            }
        }
        return bookedRooms;
    }

    /**
     * Retrieves a list of rooms that are currently available in the hotel.
     * @return The list of available rooms.
     */
    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isBooked()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    /**
     * Reserves a room in the hotel for a specified guest and date range.
     * @param roomNumber The number of the room to reserve.
     * @param guestName The name of the guest reserving the room.
     * @param checkInDate The check-in date for the reservation.
     * @param checkOutDate The check-out date for the reservation.
     * @return true if the room was successfully reserved, false otherwise.
     */
    public boolean reserveRoom(int roomNumber, String guestName, int checkInDate, int checkOutDate) {
        Room room = getRoom(roomNumber);
        if (room != null && !room.isBooked(checkInDate, checkOutDate)) {
            Reservation reservation = new Reservation(room, guestName, roomNumber, checkInDate, checkOutDate);
            room.addReservation(reservation);
            reservations.add(reservation);
            return true; // Room reserved
        }
        return false; // Room not available or already booked
    }

    /**
     * Cancels a reservation in the hotel by the room number.
     * @param roomNumber The number of the room whose reservation should be cancelled.
     * @return true if the reservation was successfully cancelled, false otherwise.
     */
    public boolean cancelReservation(int roomNumber) {
        Iterator<Reservation> reservationIterator = reservations.iterator();
        while (reservationIterator.hasNext()) {
            Reservation reservation = reservationIterator.next();
            if (reservation.getRoomNumber() == roomNumber) {
                reservationIterator.remove(); // Remove the reservation
                Room room = getRoom(roomNumber);
                if (room != null) {
                    room.removeReservation(reservation); // Unbook the room
                }
                return true; // Reservation cancelled
            }
        }
        return false; // Reservation not found
    }

    /**
     * Counts the number of available rooms in the hotel for a given date.
     * @param date The date for which availability is checked.
     * @return The number of available rooms.
     */
    public int countAvailableRooms(int date) {
        int availableCount = 0;
        for (Room room : rooms) {
            if (!room.isBookedOnDate(date)) {
                availableCount++;
            }
        }
        return availableCount;
    }

    /**
     * Counts the number of booked rooms in the hotel for a given date.
     * @param date The date for which bookings are checked.
     * @return The number of booked rooms.
     */
    public int countBookedRooms(int date) {
        int bookedCount = 0;
        for (Room room : rooms) {
            if (room.isBookedOnDate(date)) {
                bookedCount++;
            }
        }
        return bookedCount;
    }

    /**
     * Removes a reservation from the hotel by room number and dates.
     * @param roomNumber The number of the room for which the reservation should be removed.
     * @param checkInDate The check-in date of the reservation to be removed.
     * @param checkOutDate The check-out date of the reservation to be removed.
     */
    public void removeReservation(int roomNumber, int checkInDate, int checkOutDate) {
        reservations.removeIf(reservation -> reservation.getRoomNumber() == roomNumber &&
                                             reservation.getCheckInDate() == checkInDate &&
                                             reservation.getCheckOutDate() == checkOutDate);
        Room room = getRoom(roomNumber);
        if (room != null) {
            room.removeReservation(checkInDate, checkOutDate);
        }
    }

    public int[] getDayModifier(){
        return this.dayModifier;
    }

    public void setDayModifier(int day, int modifier){
        this.dayModifier[day-1] = modifier;
    }

}
