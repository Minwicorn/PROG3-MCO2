/**
 * Represents a deluxe type room at the hotel
 */
public class Deluxe extends Room{
    public Deluxe(int roomNumber, double price) {
        super(roomNumber, price);
    }

    /**
     * @return the price of a deluxe room
     */
    @Override
    public double getPrice() {
        return price * 1.2;
    }
}
