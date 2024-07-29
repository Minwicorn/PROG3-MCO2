import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class HotelView {

    private JFrame mainFrame;
    private JButton createHotelBtn, listHotelBtn, manageHotelBtn, simulateBookingBtn;
    private HotelController controller;
    private JTable hotelsTable;
    private JLabel roomNumberLabel;
    private JLabel roomTypeLabel;
    private JLabel priceLabel;
    private JLabel statusLabel;
    private JTable bookedRoomsTable;

    public void setController(HotelController controller) {
        this.controller = controller;
    }

    public HotelView() {
        
        mainFrame = new JFrame("Hotel Reservation System");
        mainFrame.setSize(600, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(10, 10));

        // Panel for tables
        JPanel tablePanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Initialize the hotels table
        hotelsTable = new JTable(new DefaultTableModel(new Object[]{"Hotel Name", "Room Number", "Type", "Price", "Status"}, 0));
        JScrollPane scrollPane = new JScrollPane(hotelsTable);
        tablePanel.add(scrollPane);

        mainFrame.add(tablePanel, BorderLayout.CENTER);

        // Panel for labels
        JPanel labelPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        roomNumberLabel = new JLabel("Room Number: ");
        roomTypeLabel = new JLabel("Room Type: ");
        priceLabel = new JLabel("Price: ");
        statusLabel = new JLabel("Status: ");
        labelPanel.add(roomNumberLabel);
        labelPanel.add(roomTypeLabel);
        labelPanel.add(priceLabel);
        labelPanel.add(statusLabel);

        mainFrame.add(labelPanel, BorderLayout.SOUTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        createHotelBtn = new JButton("Create Hotel");
        listHotelBtn = new JButton("List Hotels");
        manageHotelBtn = new JButton("Manage Hotel");
        simulateBookingBtn = new JButton("Simulate Booking");

        createHotelBtn.setFocusable(false);
        listHotelBtn.setFocusable(false);
        manageHotelBtn.setFocusable(false);
        simulateBookingBtn.setFocusable(false);

        buttonPanel.add(createHotelBtn);
        buttonPanel.add(listHotelBtn);
        buttonPanel.add(manageHotelBtn);
        buttonPanel.add(simulateBookingBtn);

        mainFrame.add(buttonPanel, BorderLayout.NORTH);

        mainFrame.setVisible(true);

        // Add action listeners to the buttons
        createHotelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHotel();
            }
        });

        listHotelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showListsHotelMenu();
            }
        });

        manageHotelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showManageHotelMenu();
            }
        });

        simulateBookingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSimulateBookingMenu();
            }
        });
    }

    private void showListsHotelMenu(){
         // Create a new JFrame for Show Lists Hotel menu
         JFrame manageFrame = new JFrame("Lists Hotel");
         manageFrame.setSize(400, 400);
         manageFrame.setLayout(new GridLayout(3, 1));
 
         JButton showHotelDetailsBtn = new JButton("Show Hotel Details");
         JButton estimateEarningBtn = new JButton("Estimate Earnings");
         JButton showReservationDetailsBtn = new JButton("Show Reservation Details");
    
         manageFrame.add(showHotelDetailsBtn);
         manageFrame.add(estimateEarningBtn);
         manageFrame.add(showReservationDetailsBtn);

         manageFrame.setVisible(true);


         showHotelDetailsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getHotelDetails();
            }
        });

        estimateEarningBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                estimateEarnings();
            }
        });

        showReservationDetailsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReservationDetails();
            }
        });
    }

    private void showManageHotelMenu() {
        // Create a new JFrame for Manage Hotel menu
        JFrame manageFrame = new JFrame("Manage Hotel");
        manageFrame.setSize(400, 400);
        manageFrame.setLayout(new GridLayout(7, 1));

        JButton changeNameBtn = new JButton("Change Hotel Name");
        JButton addRoomBtn = new JButton("Add a Room");
        JButton removeRoomBtn = new JButton("Remove a Room");
        JButton updateRoomPriceBtn = new JButton("Update Room Price");
        JButton modifyPriceDayBtn = new JButton("Modify Price for a Day");
        JButton modifyPriceRangeBtn = new JButton("Modify Price for a Range of Days");
        JButton removeHotelBtn = new JButton("Remove a Hotel");

        manageFrame.add(changeNameBtn);
        manageFrame.add(addRoomBtn);
        manageFrame.add(removeRoomBtn);
        manageFrame.add(updateRoomPriceBtn);
        manageFrame.add(modifyPriceDayBtn);
        manageFrame.add(modifyPriceRangeBtn);
        manageFrame.add(removeHotelBtn);

        manageFrame.setVisible(true);

        changeNameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeHotelName();
            }
        });

        addRoomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoomToHotel();
            }
        });

        removeRoomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeRoomFromHotel();
            }
        });

        updateRoomPriceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRoomPrice();
            }
        });

        modifyPriceDayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyDatePrice();
            }
        });

        modifyPriceRangeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyDatePriceRange();
            }
        });

        removeHotelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeHotel();
            }
        });
    }

    private void showSimulateBookingMenu() {
        // Create a new JFrame for Simulate Booking menu
        JFrame bookingFrame = new JFrame("Simulate Booking");
        bookingFrame.setSize(400, 400);
        bookingFrame.setLayout(new GridLayout(6, 1));

        JButton makeReservationBtn = new JButton("Make a Reservation");
        JButton cancelReservationBtn = new JButton("Cancel a Reservation");
        JButton listBookedRoomsBtn = new JButton("List Booked Rooms");
        JButton listAvailableRoomsBtn = new JButton("List Available Rooms");
        JButton showRoomCountsBtn = new JButton("Show Room Counts for a Date");
        JButton showRoomInfoMonthBtn = new JButton("Show Room Info Across Month");

        bookingFrame.add(makeReservationBtn);
        bookingFrame.add(cancelReservationBtn);
        bookingFrame.add(listBookedRoomsBtn);
        bookingFrame.add(listAvailableRoomsBtn);
        bookingFrame.add(showRoomCountsBtn);
        bookingFrame.add(showRoomInfoMonthBtn);

        bookingFrame.setVisible(true);

        makeReservationBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeReservation();
            }
        });

        cancelReservationBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelReservation();
            }
        });

        listBookedRoomsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listBookedRooms();
            }
        });

        listAvailableRoomsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listAvailableRooms();
            }
        });

        showRoomCountsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRoomCountsForDate();
            }
        });

        showRoomInfoMonthBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRoomInfoAcrossMonth();
            }
        });
    }

    /**
     * METHOD FUNCTIONALITIES:
     */

     /**
     * Adds a new hotel to the system based on user input.
     */
    private void addHotel() {
        String hotelName = JOptionPane.showInputDialog(mainFrame, "Enter hotel name:");
        if (hotelName != null && !hotelName.trim().isEmpty()) {
            controller.addHotel(hotelName.trim());
            JOptionPane.showMessageDialog(mainFrame, "Hotel added: " + hotelName);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Hotel name cannot be empty.");
        }
    }


    private void getHotelDetails() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            displayHotelDetails(hotel);
        }
    }

    private void estimateEarnings() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            double earnings = controller.estimateEarnings(hotel.getHotelName());
            JOptionPane.showMessageDialog(mainFrame, "Estimated earnings for hotel " + hotel.getHotelName() + ": $" + earnings);
        }
    }

    /**
     * Displays detailed reservation information including hotel name, room number,
     * guest name, check-in date, check-out date, total price, and price per night.
     */
    private void showReservationDetails() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            Room room = validateRoomNumber(hotel, "Enter room number:");
            if (room != null) {
                int checkInDate = getValidDate("Enter check-in date (1-31):");
                int checkOutDate = getValidDate("Enter check-out date (1-31):");
                while (checkOutDate < checkInDate) {
                    JOptionPane.showMessageDialog(mainFrame, "Check-out date must be after check-in date.");
                    checkOutDate = getValidDate("Enter check-out date (1-31):");
                }
                controller.getReservationDetails(hotel.getHotelName(), room.getRoomNumber(), checkInDate, checkOutDate);
            }
        }
    }
    /**
     * Changes the name of a specified hotel based on user input.
     */
    private void changeHotelName() {
        HotelModel hotel = validateHotelName("Enter current hotel name:");
        if (hotel != null) {
            String newHotelName = JOptionPane.showInputDialog(mainFrame, "Enter new hotel name:");
            if (newHotelName != null && !newHotelName.trim().isEmpty()) {
                controller.changeHotelName(hotel.getHotelName(), newHotelName.trim());
                JOptionPane.showMessageDialog(mainFrame, "Hotel name changed to: " + newHotelName);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "New hotel name cannot be empty.");
            }
        }
    }

    // Method to add rooms to a hotel
    private void addRoomToHotel() {
        String hotelName = JOptionPane.showInputDialog(mainFrame, "Enter hotel name:");
        if (hotelName == null || hotelName.trim().isEmpty()) {
            displayError("Hotel name cannot be empty.");
            return;
        }

        String roomNumberStr = JOptionPane.showInputDialog(mainFrame, "Enter room number:");
        if (roomNumberStr == null || roomNumberStr.trim().isEmpty()) {
            displayError("Room number cannot be empty.");
            return;
        }

        String roomType = JOptionPane.showInputDialog(mainFrame, "Enter room type:");
        if (roomType == null || roomType.trim().isEmpty()) {
            displayError("Room type cannot be empty.");
            return;
        }
        else if (!(roomType.equals("Standard")) && !(roomType.equals("Deluxe")) && !(roomType.equals("Executive"))) {
            displayError("Enter a valid room type.");
            return;
        }

        int roomNumber;
        try {
            roomNumber = Integer.parseInt(roomNumberStr);
        } catch (NumberFormatException e) {
            displayError("Invalid room number.");
            return;
        }

        String countStr = JOptionPane.showInputDialog(mainFrame, "Enter number of rooms to add:");
        if (countStr == null || countStr.trim().isEmpty()) {
            displayError("Number of rooms cannot be empty.");
            return;
        }

        int count;
        try {
            count = Integer.parseInt(countStr);
        } catch (NumberFormatException e) {
            displayError("Invalid number of rooms.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to add " + count + " room(s) to hotel " + hotelName + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            controller.addRoomToHotel(hotelName, roomNumber, roomType, count);
        }
    }

    private void removeRoomFromHotel() {
        String hotelName = JOptionPane.showInputDialog(mainFrame, "Enter hotel name:");
        if (hotelName == null) return; // User cancelled
        HotelModel hotel = controller.findHotelByName(hotelName);
        if (hotel == null) {
            displayError("Hotel not found.");
            return;
        }

        String roomNumberStr = JOptionPane.showInputDialog(mainFrame, "Enter room number:");
        if (roomNumberStr == null) return; // User cancelled

        int roomNumber;
        try {
            roomNumber = Integer.parseInt(roomNumberStr);
        } catch (NumberFormatException e) {
            displayError("Invalid room number.");
            return;
        }

        Room room = hotel.getRoomByNumber(roomNumber);
        if (room == null) {
            displayError("Room not found.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to remove room number " + room.getRoomNumber() + " from hotel: " + hotel.getHotelName() + "?",
                "Confirm Removal", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean check = controller.removeRoomFromHotel(hotel.getHotelName(), room.getRoomNumber());
            if (check) {
                JOptionPane.showMessageDialog(null, "Removed room number " + room.getRoomNumber() + " from hotel: " + hotel.getHotelName());
            } else {
                displayError("Failed to remove room.");
            }
        }
    }

    /**
     * Updates the price of rooms in a specified hotel based on user input.
     */
    private void updateRoomPrice() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            Room room = validateRoomNumber(hotel, "Enter room number:");
            if (room != null) {
                double newPrice = getValidDouble("Enter new room price:");
                controller.updateRoomPrice(hotel.getHotelName(), room.getRoomNumber(), newPrice);
                JOptionPane.showMessageDialog(mainFrame, "Room price updated: " + room.getRoomNumber() + " - $" + newPrice);
            }
        }
    }

    private void modifyDatePrice() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            Room room = validateRoomNumber(hotel, "Enter room number:");
            if (room != null) {
                int date = getValidDate("Enter date (1-31):");
                double newPrice = getValidDouble("Enter new price for date " + date + ":");
                controller.modifyPriceForADay(hotel.getHotelName(), room.getRoomNumber(), date, newPrice);
                JOptionPane.showMessageDialog(mainFrame, "Price updated for date " + date + ": $" + newPrice);
            }
        }
    }

    private void modifyDatePriceRange() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            Room room = validateRoomNumber(hotel, "Enter room number:");
            if (room != null) {
                int startDate = getValidDate("Enter start date (1-31):");
                int endDate = getValidDate("Enter end date (1-31):");
                while (endDate < startDate) {
                    JOptionPane.showMessageDialog(mainFrame, "End date must be after start date.");
                    endDate = getValidDate("Enter end date (1-31):");
                }
                double newPrice = getValidDouble("Enter new price for range " + startDate + " to " + endDate + ":");
                controller.modifyPriceForRange(hotel.getHotelName(), room.getRoomNumber(), startDate, endDate, newPrice);
                JOptionPane.showMessageDialog(mainFrame, "Price updated for dates " + startDate + " to " + endDate + ": $" + newPrice);
            }
        }
    }

    /**
     * Removes a hotel from the system based on user input.
     */
    private void removeHotel() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            controller.removeHotel(hotel.getHotelName());
            JOptionPane.showMessageDialog(mainFrame, "Hotel removed: " + hotel.getHotelName());
        }
    }

     /**
     * Makes a reservation for a room in a specified hotel based on user input.
     */
    private void makeReservation() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            Room room = validateRoomNumber(hotel, "Enter room number:");
            if (room != null) {
                int checkInDate = getValidDate("Enter check-in date (1-31):");
                int checkOutDate = getValidDate("Enter check-out date (1-31):");
                while (checkOutDate < checkInDate) {
                    JOptionPane.showMessageDialog(mainFrame, "Check-out date must be after check-in date.");
                    checkOutDate = getValidDate("Enter check-out date (1-31):");
                }
                String customerName = JOptionPane.showInputDialog(mainFrame, "Enter customer name:");
                String discountCode = JOptionPane.showInputDialog(null, "Enter discount code (if any):");
                if (customerName != null && !customerName.trim().isEmpty()) {
                    controller.makeReservation(hotel.getHotelName(), room.getRoomNumber(), checkInDate, checkOutDate, customerName.trim(), discountCode);
                    JOptionPane.showMessageDialog(mainFrame, "Reservation made for " + customerName);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Customer name cannot be empty.");
                }
            }
        }
    }

    /**
     * Cancels a reservation for a room in a specified hotel based on user input.
     */
    private void cancelReservation() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            Room room = validateRoomNumber(hotel, "Enter room number:");
            if (room != null) {
                int checkInDate = getValidDate("Enter check-in date (1-31):");
                int checkOutDate = getValidDate("Enter check-out date (1-31):");
                while (checkOutDate < checkInDate) {
                    JOptionPane.showMessageDialog(mainFrame, "Check-out date must be after check-in date.");
                    checkOutDate = getValidDate("Enter check-out date (1-31):");
                }
                controller.cancelReservation(hotel.getHotelName(), room.getRoomNumber(), checkInDate, checkOutDate);
                JOptionPane.showMessageDialog(mainFrame, "Reservation cancelled.");
            }
        }
    }

    /**
     * Lists all rooms that are currently booked in a specified hotel based on user input.
     */
    private void listBookedRooms() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            int date = getValidDate("Enter date (1-31):");
            List<Room> bookedRooms = controller.getBookedRooms(hotel.getHotelName(), date);
            StringBuilder sb = new StringBuilder("Booked rooms on date " + date + ":\n");
            for (Room room : bookedRooms) {
                sb.append("Room ").append(room.getRoomNumber()).append(": ").append("\n");
            }
            JOptionPane.showMessageDialog(mainFrame, sb.toString());
        }
    }


    /**
     * Lists all rooms that are currently available in a specified hotel based on user input.
     */
    private void listAvailableRooms() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            int date = getValidDate("Enter date (1-31):");
            List<Room> availableRooms = controller.getAvailableRooms(hotel.getHotelName(), date);
            StringBuilder sb = new StringBuilder("Available rooms on date " + date + ":\n");
            for (Room room : availableRooms) {
                sb.append("Room ").append(room.getRoomNumber()).append(": ").append("\n");
            }
            JOptionPane.showMessageDialog(mainFrame, sb.toString());
        }
    }

     /**
     * Displays the count of available and booked rooms for a specific date
     * in the context of a specific hotel.
     */
    private void showRoomCountsForDate() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            int date = getValidDate("Enter date (1-31):");
            controller.showRoomCountsForDate(hotel.getHotelName(), date);
        }
    }

     /**
     * Shows detailed information about a specific room for the entire month,
     * including availability status for each day.
     */
    private void showRoomInfoAcrossMonth() {
        HotelModel hotel = validateHotelName("Enter hotel name:");
        if (hotel != null) {
            int month = getValidMonth("Enter month (1-12):");
            controller.showRoomInfoAcrossMonth(hotel.getHotelName(), month);
        }
    }

    /**
     * Utility method to validate the existence of a hotel based on user input.
     *
     * @param prompt The prompt message to display when requesting input.
     * @return The validated `HotelModel` object corresponding to the hotel name entered by the user.
     */
    private HotelModel validateHotelName(String message) {
        String hotelName = JOptionPane.showInputDialog(mainFrame, message);
        if (hotelName == null || hotelName.trim().isEmpty()) {
            return null; // User cancelled or entered empty value
        }
        HotelModel hotel = controller.findHotelByName(hotelName.trim());
        if (hotel == null) {
            JOptionPane.showMessageDialog(mainFrame, "Hotel not found.");
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
    private Room validateRoomNumber(HotelModel hotel, String message) {
        String roomNumberStr = JOptionPane.showInputDialog(mainFrame, message);
        if (roomNumberStr == null || roomNumberStr.trim().isEmpty()) {
            return null; // User cancelled or entered empty value
        }
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(roomNumberStr.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid room number.");
            return null;
        }
        Room room = hotel.getRoomByNumber(roomNumber);
        if (room == null) {
            JOptionPane.showMessageDialog(mainFrame, "Room not found.");
        }
        return room;
    }
    
    private int getValidDate(String message) {
        while (true) {
            String dateStr = JOptionPane.showInputDialog(mainFrame, message);
            if (dateStr == null || dateStr.trim().isEmpty()) {
                return -1; // User cancelled or entered empty value
            }
            try {
                int date = Integer.parseInt(dateStr.trim());
                if (date >= 1 && date <= 31) {
                    return date;
                }
            } catch (NumberFormatException e) {
                // Ignore and prompt again
            }
            JOptionPane.showMessageDialog(mainFrame, "Please enter a valid date between 1 and 31.");
        }
    }
    

    private int getValidMonth(String message) {
        String monthStr;
        int month;
        while (true) {
            monthStr = JOptionPane.showInputDialog(mainFrame, message);
            try {
                month = Integer.parseInt(monthStr);
                if (month >= 1 && month <= 12) {
                    return month;
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Month must be between 1 and 12.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid month.");
            }
        }
    }

    private double getValidDouble(String message) {
        String doubleStr;
        double value;
        while (true) {
            doubleStr = JOptionPane.showInputDialog(mainFrame, message);
            try {
                value = Double.parseDouble(doubleStr);
                return value;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid number.");
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
        JOptionPane.showMessageDialog(mainFrame,
                "Hotel Details:\n" +
                        "Hotel Name: " + hotel.getHotelName() + "\n" +
                        "Rooms: " + hotel.getRooms().size() + "\n" +
                        "Reservations: " + hotel.getReservations().size(),
                "Hotel Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayReservationDetails(Reservation reservation, HotelModel hotel, Room room, int checkInDate, int checkOutDate) {
        JOptionPane.showMessageDialog(mainFrame,
                "Reservation details for room number: " + room.getRoomNumber() + " in hotel: " + hotel.getHotelName() + "\n" +
                        "Room type: " + room.getRoomType() + "\n" +
                        "Check-in date: " + checkInDate + ", Check-out date: " + checkOutDate + "\n" +
                        "Total price: $" + reservation.getTotalPrice(),
                "Reservation Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Prompts the user to confirm changes by entering 'Y' for yes or 'N' for no.
     *
     * @return `true` if the user confirms changes, `false` otherwise.
     */
    private boolean confirmChanges() {
        int response = JOptionPane.showConfirmDialog(mainFrame,
                "Do you want to proceed with the changes?",
                "Confirm Changes",
                JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }

    private String getDiscountCode() {
        return JOptionPane.showInputDialog(mainFrame,
                "Enter discount code (or press Enter to skip):",
                "Discount Code",
                JOptionPane.PLAIN_MESSAGE);
    }

    public void displayHotels(List<HotelModel> hotels) {
        DefaultTableModel model = (DefaultTableModel) hotelsTable.getModel();
        model.setRowCount(0); // Clear existing rows
    
        if (hotels.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No hotels available.");
        } else {
            for (HotelModel hotel : hotels) {
                model.addRow(new Object[]{
                    hotel.getHotelName(),
                    hotel.getRooms().size(),
                    hotel.getReservations().size()
                });
            }
        }
    }

    public void displayRoomDetails(Room room) {
        roomNumberLabel.setText("Room Number: " + room.getRoomNumber());
        roomTypeLabel.setText("Room type: " + room.getRoomType());
        priceLabel.setText("Price: $" + room.getPrice());
        statusLabel.setText("Status: " + (room.isBooked() ? "Booked" : "Available"));
    }

    public void displayBookedRooms(List<Room> bookedRooms) {
        DefaultTableModel model = (DefaultTableModel) bookedRoomsTable.getModel();
        model.setRowCount(0); // Clear existing rows
    
        if (bookedRooms.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No booked rooms found.");
        } else {
            for (Room room : bookedRooms) {
                model.addRow(new Object[]{
                    room.getRoomNumber(),
                    room.getPrice()
                });
            }
        }
    }
    
    public boolean areAnyRoomsAvailable() {
        for (HotelModel hotel : controller.getHotels()) {
            if (!hotel.getRooms().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /*
     * DISPLAY MESSAGES:
     */
    
     // Method to prompt the user to enter another value
     public void displayEnterAnother(String field) {
        JOptionPane.showMessageDialog(mainFrame, "Please enter another " + field);
    }

     // Method to display a success message
     public void displaySuccess(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    // Method to display an error message for hotel not found
    public void displayHotelNotFound(String hotelName) {
        JOptionPane.showMessageDialog(null, "Hotel not found: " + hotelName, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Method to display a message when the maximum number of rooms is reached
    public void displayMaxRooms() {
        JOptionPane.showMessageDialog(null, "Maximum number of rooms reached.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Method to display a message when the minimum number of rooms is reached
    public void displayMinRooms() {
        JOptionPane.showMessageDialog(null, "Minimum number of rooms reached.", "Error", JOptionPane.ERROR_MESSAGE);
    }

     // Method to display an error message for room not found
    public void displayRoomNotFound(int roomNumber) {
        JOptionPane.showMessageDialog(null, "Room not found: " + roomNumber, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Method to display error messages
    private void displayError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
