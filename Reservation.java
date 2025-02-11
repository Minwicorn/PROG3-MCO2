/**
 * Represents a reservation made at the hotel
 */
public class Reservation {
    private String guestName;
    private int roomNumber;
    private Room room;
    private int checkInDate;
    private int checkOutDate;
    private double totalPrice;
    private String discountCode;
    

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
                ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + ", totalPrice=" + totalPrice +
                ", discountCode=" + discountCode + "]";
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
    public void calculateTotalPrice(HotelModel hotel) {
        double price=0;

        for(int i=this.checkInDate; i<checkOutDate; i++){
            int[] dates = hotel.getDayModifier();
            price += room.getPrice() * ((double)(dates[i-1]/100.0));
        }

        setTotalPrice(price);
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

    /**
     * Method to apply a discount based on the provided discount code
     * @param discountCode the discount code to apply
     */
    public void applyDiscount(String discountCode) {
        this.discountCode = discountCode; // Convert to uppercase for case-sensitive matching
    
        switch (this.discountCode) {
            case "I_WORK_HERE":
                totalPrice *= 0.90; // Apply a 10% discount
                break;
            case "STAY4_GET1":
                if (calculateNumberOfNights() >= 5) {
                    double pricePerNight = room.getPrice(); // Assuming the price per night is fixed
                    totalPrice -= pricePerNight; // Subtract the price of the first night
                } else {
                    // Display a message dialog indicating the discount is not applicable
                    javax.swing.JOptionPane.showMessageDialog(null, 
                        "The STAY4_GET1 discount is not applicable for stays less than 5 nights.", 
                        "Discount Not Applicable", 
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case "PAYDAY":
                if ((checkInDate <= 15 && checkOutDate > 15) || (checkInDate <= 30 && checkOutDate > 30)) {
                    totalPrice *= 0.93; // Apply a 7% discount
                } else {
                    // Display a message dialog indicating the discount is not applicable
                    javax.swing.JOptionPane.showMessageDialog(null, 
                        "The PAYDAY discount is only applicable for stays that include the 15th or the end of the month.", 
                        "Discount Not Applicable", 
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            default:
                // Invalid or unsupported discount code
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Invalid or unsupported discount code.", 
                    "Error", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
    
}
