import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Main class for the Dog Adoption Wishlist GUI application.
 * This class creates the window, fetches dog data, displays the dogs, and handles interactions like adding a dog to the wishlist and saving the wishlist to a JSON file.
 */
public class WishlistPage extends JFrame {
    private List<Dog> wishlist;  // List to store the wishlist of dogs
    private JPanel mainPanel;    // Main panel for the GUI
    private JPanel dogListPanel; // Panel to display the list of dogs
    private JButton saveButton;  // Button to save the wishlist to a JSON file
    private JButton homeButton;  // Button to return to the homepage

    /**
     * Constructor to initialize the WishlistPage GUI.
     */
    public WishlistPage() {
        wishlist = new ArrayList<>();  // Initialize the wishlist
        setTitle("Dog Adoption Wishlist");  // Set the title of the window
        setSize(800, 600);  // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close the application when the window is closed
        setLocationRelativeTo(null);  // Center the window on the screen

        mainPanel = new JPanel();  // Create the main panel
        mainPanel.setLayout(new BorderLayout());  // Set the layout of the main panel

        dogListPanel = new JPanel();  // Create the dog list panel
        dogListPanel.setLayout(new BoxLayout(dogListPanel, BoxLayout.Y_AXIS));  // Set the layout of the dog list panel

        JScrollPane scrollPane = new JScrollPane(dogListPanel);  // Add a scroll pane to the dog list panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);  // Add the scroll pane to the main panel

        saveButton = new JButton("Save Wishlist to JSON");  // Create the save button
        homeButton = new JButton("Return to Homepage");  // Create the home button

        // Add an action listener to the save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveWishlistToJson();  // Save the wishlist to a JSON file when the button is clicked
            }
        });

        // Add an action listener to the home button
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement navigation to homepage
                System.out.println("Return to Homepage clicked");
            }
        });

        JPanel buttonPanel = new JPanel();  // Create a panel for the buttons
        buttonPanel.add(saveButton);  // Add the save button to the button panel
        buttonPanel.add(homeButton);  // Add the home button to the button panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);  // Add the button panel to the main panel

        add(mainPanel);  // Add the main panel to the frame
        setVisible(true);  // Make the frame visible

        fetchDogs();  // Fetch the dog data
    }

    /**
     * Simulates an API call to fetch the dog data.
     */
    private void fetchDogs() {
        // Simulate fetching dog data
        List<Dog> dogs = List.of(
                new Dog("1", "Buddy", "Male", "Labrador", 3, 30.0, "labrador.jpg", 500, true),
                new Dog("2", "Lucy", "Female", "Beagle", 5, 20.0, "beagle.jpg", 400, true)
        );

        displayDogs(dogs);  // Display the fetched dogs
    }

    /**
     * Displays the list of dogs in the GUI.
     * @param dogs List of dogs to display.
     */
    private void displayDogs(List<Dog> dogs) {
        for (Dog dog : dogs) {
            JPanel dogCard = new JPanel();  // Create a panel for each dog
            dogCard.setLayout(new BoxLayout(dogCard, BoxLayout.Y_AXIS));  // Set the layout of the dog panel
            dogCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));  // Add a border to the dog panel
            dogCard.setPreferredSize(new Dimension(200, 300));  // Set the preferred size of the dog panel

            JLabel nameLabel = new JLabel("Name: " + dog.getName());  // Create a label for the dog's name
            JLabel breedLabel = new JLabel("Breed: " + dog.getBreed());  // Create a label for the dog's breed
            JLabel ageLabel = new JLabel("Age: " + dog.getAge());  // Create a label for the dog's age
            JLabel sexLabel = new JLabel("Sex: " + dog.getSex());  // Create a label for the dog's sex
            JLabel priceLabel = new JLabel("Price: $" + dog.getPrice());  // Create a label for the dog's price
            JLabel readyLabel = new JLabel("Ready for adoption: " + (dog.isReady() ? "Yes" : "No"));  // Create a label for the dog's adoption status
            JLabel imageLabel = new JLabel(new ImageIcon("resources/dogimages/" + dog.getImage()));  // Create a label for the dog's image

            JButton addButton = new JButton("Add to Wishlist");  // Create a button to add the dog to the wishlist
            // Add an action listener to the add button
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addToWishlist(dog);  // Add the dog to the wishlist when the button is clicked
                }
            });

            // Add the labels and button to the dog panel
            dogCard.add(imageLabel);
            dogCard.add(nameLabel);
            dogCard.add(breedLabel);
            dogCard.add(ageLabel);
            dogCard.add(sexLabel);
            dogCard.add(priceLabel);
            dogCard.add(readyLabel);
            dogCard.add(addButton);

            dogListPanel.add(dogCard);  // Add the dog panel to the dog list panel
        }

        dogListPanel.revalidate();  // Refresh the dog list panel
        dogListPanel.repaint();  // Repaint the dog list panel
    }

    /**
     * Adds a dog to the wishlist if it is not already present.
     * @param dog The dog to add to the wishlist.
     */
    private void addToWishlist(Dog dog) {
        if (!wishlist.contains(dog)) {
            wishlist.add(dog);  // Add the dog to the wishlist
            JOptionPane.showMessageDialog(this, dog.getName() + " added to wishlist!");  // Show a message that the dog was added
        } else {
            JOptionPane.showMessageDialog(this, dog.getName() + " is already in the wishlist.");  // Show a message that the dog is already in the wishlist
        }
    }

    /**
     * Saves the wishlist to a JSON file.
     */
    private void saveWishlistToJson() {
        try (FileWriter file = new FileWriter("wishlist.json")) {
            file.write("[\n");
            for (int i = 0; i < wishlist.size(); i++) {
                Dog dog = wishlist.get(i);
                file.write(dog.toJson());  // Write the dog data as JSON
                if (i < wishlist.size() - 1) {
                    file.write(",\n");
                }
            }
            file.write("\n]");
            JOptionPane.showMessageDialog(this, "Wishlist saved to wishlist.json");  // Show a message that the wishlist was saved
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving wishlist: " + e.getMessage());  // Show an error message if there was a problem saving the wishlist
        }
    }

    public static void main(String[] args) {
        new WishlistPage();  // Create and display the WishlistPage GUI
    }
}

/**
 * Class representing a Dog with various attributes.
 */
class Dog {
    private String id;
    private String name;
    private String sex;
    private String breed;
    private int age;
    private double weight;
    private String image;
    private double price;
    private boolean isReady;

    /**
     * Constructor to initialize the Dog object.
     * @param id Unique identifier for the dog.
     * @param name Name of the dog.
     * @param sex Sex of the dog.
     * @param breed Breed of the dog.
     * @param age Age of the dog.
     * @param weight Weight of the dog.
     * @param image Image file name of the dog.
     * @param price Price of the dog.
     * @param isReady Whether the dog is ready for adoption.
     */
    public Dog(String id, String name, String sex, String breed, int age, double weight, String image, double price, boolean isReady) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.image = image;
        this.price = price;
        this.isReady = isReady;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public double getPrice() {
        return price;
    }

    public boolean isReady() {
        return isReady;
    }

    public String getImage() {
        return image;
    }

    /**
     * Converts the Dog object to a JSON string.
     * @return JSON representation of the Dog object.
     */
    public String toJson() {
        return "{\n" +
                "  \"id\": \"" + id + "\",\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"sex\": \"" + sex + "\",\n" +
                "  \"breed\": \"" + breed + "\",\n" +
                "  \"age\": " + age + ",\n" +
                "  \"weight\": " + weight + ",\n" +
                "  \"image\": \"" + image + "\",\n" +
                "  \"price\": " + price + ",\n" +
                "  \"isReady\": " + isReady + "\n" +
                "}";
    }
}
