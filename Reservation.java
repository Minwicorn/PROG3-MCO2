public class Reservation {
    private String guestName;
    private int roomNumber;
    private Room room;
    private int checkInDate;
    private int checkOutDate;
    private double totalPrice;

    /** 
     * Constructs a reservation with a room, guest name, room number, check in date and check out date
     * 
     * @param room the room assigned for the reservation
     * @param guestName the name of the guest who booked the reservation
     * @param roomNumber the number of the room that was booked
     * @param checkInDate the date to the check in
     * @param checkOutDate the date to check out
     */ 
    public Reservation(Room room, String guestName, int roomNumber, int checkInDate, int checkOutDate) {
        this.room = room;
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    /**
     * @return check in date
     */
    public int getCheckInDate() {
        return checkInDate;
    }

    /**
     * @param checkInDate the date to check in
     */
    public void setCheckInDate(int checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * @return check out date
     */
    public int getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * @param checkOutDate date to check out
     */
    public void setCheckOutDate(int checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /**
     * @return guest's name
     */
    public String getGuestName() {
        return guestName;
    }

    /**
     * @return room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * @return room booked for reservation
     */
    public Room getRoom() {
        return room;
    }

    /**
     *  overrides the toString method by returning reservation details
     */
    @Override
    public String toString() {
        return "Reservation [guestName=" + guestName + ", roomNumber=" + roomNumber +
                ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + "]";
    }

    /**
     * @return total price of the reservation
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the total price of the reservation
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Method to calculate total price based on room price and duration 
    */ 
    public void calculateTotalPrice() {
        double roomPrice = room.getPrice(); // Assuming Room class has getPrice() method
        int numberOfNights = calculateNumberOfNights();
        totalPrice = roomPrice * numberOfNights;
    }

    /**
     * Method to calculate the number of nights between check-in and check-out dates
     * @return total number of nights
     */
    private int calculateNumberOfNights() {
        return checkOutDate - checkInDate + 1;
    }

    /**
     * Method to calculate price per night
     * @return price of each night
     */
    public double getPricePerNight() {
        int numberOfNights = calculateNumberOfNights();
        if (numberOfNights > 0) {
            return totalPrice / numberOfNights;
        } else {
            return 0.0; // Handle division by zero gracefully, if needed
        }
    }

}