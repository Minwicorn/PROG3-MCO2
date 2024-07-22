import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room at the hotel
 */
public class Room {
    private int roomNumber;
    private double price;
    private boolean isBooked;
    private List<Reservation> reservations;

    /**
     * Constructs a room with a room number and a price
     * 
     * @param roomNumber a unique room number
     * @param price the price of each room
     */
    public Room(int roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.isBooked = false;
        this.reservations = new ArrayList<>();
    }

    // Getter and Setter methods for roomNumber, price, and isBooked

    /**
     * @return room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber number assigned for the room
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * @return the price of the room
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price price of the room
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the availability of the room
     */
    public boolean isBooked() {
        return isBooked;
    }

    /**
     * @param isBooked the availability of the room
     */
    public void setBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    /**
     * @return list of reservations for this rooom
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * @param reservation reservation to be added
     */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        this.isBooked = true;
    }

    /**
     * @param reservation reservation to be removed
     */
    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        this.isBooked = !this.reservations.isEmpty(); // Update isBooked status
    }

    /**
     * Method to check if room is booked on a given date
     * 
     * @param date date that is being checked for availability
     * @return availability of room, true of booked, false if not
     */
    public boolean isBookedOnDate(int date) {
        for (Reservation reservation : reservations) {
            if (reservation.getCheckInDate() <= date && reservation.getCheckOutDate() > date) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if the room is booked in a given date range
     * 
     * @param checkInDate date to check into room
     * @param checkOutDate date to check out of room
     * @return availability of room, true of booked, false if not
     */
    public boolean isBooked(int checkInDate, int checkOutDate) {
        for (Reservation reservation : reservations) {
            if (checkInDate>=reservation.getCheckInDate() || checkInDate<reservation.getCheckOutDate())
                return true;
        }
        return false;
    }

    /**
     * Method to remove reservation by matching check-in and check-out dates
     * 
     * @param checkInDate date to check into room
     * @param checkOutDate date to check out of room
     */
    public void removeReservation(int checkInDate, int checkOutDate) {
        reservations.removeIf(reservation -> reservation.getCheckInDate()==checkInDate && reservation.getCheckOutDate()==checkOutDate);
        this.isBooked = !this.reservations.isEmpty(); // Update isBooked status
    }

    /**
     * Method to unbook the room
     */
    public void unbook() {
        this.reservations.clear();
        this.isBooked = false;
    }

    /**
     * Method to check if the room is available for the given date range
     * 
     * @param checkInDate date to check into room
     * @param checkOutDate date to check out of room
     * @return availability of room, true of booked, false if not
     */
     public boolean isAvailable(int checkInDate, int checkOutDate) {
        for (Reservation reservation : reservations) {
            if ((checkInDate >= reservation.getCheckInDate() && checkInDate <= reservation.getCheckOutDate()) || 
                (checkOutDate >= reservation.getCheckInDate() && checkOutDate <= reservation.getCheckOutDate()) ||
                (checkInDate < reservation.getCheckInDate() && checkOutDate > reservation.getCheckOutDate())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to get availability across the specified month
     * 
     * @return availability of room, true of booked, false if not
     */
    public List<Room> getAvailabilityAcrossMonth() {
        List<Room> availableRooms = new ArrayList<>();
    
        // Assume you have a way to fetch or mark rooms by day
        for (int day = 1; day <= 31; day++) {
            if (isAvailable(day, day)) {
                availableRooms.add(this); // Assuming 'this' room is available, add it
            }
        }
    
        return availableRooms;
    }
    
    // Optionally, you can add a method to get room name if applicable.
    public String getRoomName() {
        return "Room " + roomNumber;
    }
}