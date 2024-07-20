import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        // Initialize the list of hotels
        List<HotelModel> hotels = new ArrayList<>();

        // Create the view first
        HotelView view = new HotelView();

        // Create the controller
        HotelController controller = new HotelController(hotels, view);

        // Set the controller in the view
        view.setController(controller);

        // Start the user interface
        view.start();
    }
}
