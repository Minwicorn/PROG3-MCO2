/**
 * Represents a executive type room in a hotel
 */
public class Executive extends Room{
    public Executive(int roomNumber, double price) {
        super(roomNumber, price);
    }

    /**
     * @return the price of a executive room
     */
    @Override
    public double getPrice() {
        return price * 1.35;
    }
}
