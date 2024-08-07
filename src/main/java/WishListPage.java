import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WishListPage extends JFrame {
    private List<Dog> wishlist;  // List to store the dogs in the wishlist
    private JPanel mainPanel;    // Main panel to hold all the components
    private JButton backButton;  // Button to return to the homepage
    private JButton saveButton;  // Button to save the wishlist to a JSON file

    /**
     * Constructor to initialize the WishListPage with a list of dogs.
     * 
     * @param wishlist List of Dog objects in the wishlist
     */
    public WishListPage(List<Dog> wishlist) {
        this.wishlist = wishlist;
        setTitle("Wish List");  // Set the title of the window
        setSize(800, 600);      // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set the default close operation
        setLocationRelativeTo(null);  // Center the window

        // Initialize the main panel with a vertical BoxLayout
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        addDogsToPanel();  // Add the dogs to the panel

        // Initialize the back and save buttons
        backButton = new JButton("Return to Homepage");
        saveButton = new JButton("Save to JSON File");

        // Add action listener for the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to return to the homepage
                dispose();  // Close the current window
                // new HomePage().setVisible(true); // Uncomment to open the homepage
            }
        });

        // Add action listener for the save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveWishlistToJson();  // Save the wishlist to a JSON file
            }
        });

        // Add the buttons to the main panel
        mainPanel.add(backButton);
        mainPanel.add(saveButton);

        // Add the main panel to the window
        add(new JScrollPane(mainPanel));
        setVisible(true);  // Make the window visible
    }

    /**
     * Method to add the dogs to the panel.
     */
    private void addDogsToPanel() {
        for (Dog dog : wishlist) {
            // Create a panel for each dog with a titled border
            JPanel dogPanel = new JPanel();
            dogPanel.setLayout(new BoxLayout(dogPanel, BoxLayout.Y_AXIS));
            dogPanel.setBorder(BorderFactory.createTitledBorder(dog.getName()));

            // Create labels for the dog's details
            JLabel nameLabel = new JLabel("Name: " + dog.getName());
            JLabel breedLabel = new JLabel("Breed: " + dog.getBreed().name());
            JLabel ageLabel = new JLabel("Age: " + dog.getAge());
            JLabel sexLabel = new JLabel("Sex: " + dog.getSex());
            JLabel priceLabel = new JLabel("Price: $" + dog.getPrice());
            JLabel readyLabel = new JLabel("Ready for adoption: " + dog.getIsReady());

            // Load the dog's image
            JLabel imageLabel = new JLabel(new ImageIcon(dog.getImage()));

            // Create a button to remove the dog from the wishlist
            JButton removeButton = new JButton("Remove from Wishlist");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    wishlist.remove(dog);  // Remove the dog from the wishlist
                    mainPanel.remove(dogPanel);  // Remove the dog's panel from the main panel
                    mainPanel.revalidate();  // Revalidate the main panel
                    mainPanel.repaint();  // Repaint the main panel
                }
            });

            // Add the labels and the remove button to the dog's panel
            dogPanel.add(nameLabel);
            dogPanel.add(breedLabel);
            dogPanel.add(ageLabel);
            dogPanel.add(sexLabel);
            dogPanel.add(priceLabel);
            dogPanel.add(readyLabel);
            dogPanel.add(imageLabel);
            dogPanel.add(removeButton);

            // Add the dog's panel to the main panel
            mainPanel.add(dogPanel);
        }
    }

    /**
     * Method to save the wishlist to a JSON file.
     */
    private void saveWishlistToJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Write the wishlist to a JSON file
            mapper.writeValue(new FileWriter("wishlist.json"), wishlist);
            JOptionPane.showMessageDialog(this, "Wishlist saved to wishlist.json");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving wishlist: " + e.getMessage());
        }
    }

    /**
     * Main method for testing the WishListPage.
     */
    public static void main(String[] args) {
        // Sample wishlist for testing
        ApiUtil util = new ApiUtil();
        try {
            Map<String, Breed> breeds = util.parseBreeds(util.getBreeds());
            Breed sampleBreed = breeds.get("Hokkaido");
            List<Dog> sampleWishlist = List.of(
                    new Dog("1", "Buddy", "m", sampleBreed, 3, 70.0, "1.png"),
                    new Dog("2", "Lucy", "f", sampleBreed, 5, 60.0, "2.png")
            );

            // Create and display the wishlist page
            new WishListPage(sampleWishlist);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
