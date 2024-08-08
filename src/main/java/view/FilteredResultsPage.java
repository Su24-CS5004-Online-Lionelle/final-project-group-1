package view;

import controller.ACController;
import model.AdoptionCenterModel;
import model.Dog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

public class FilteredResultsPage extends JFrame {
    private List<Dog> dogs;
    private JPanel resultsPanel;
    private JComboBox<String> sortComboBox;
    private Map<String, Comparator<Dog>> sortOptions;

    public FilteredResultsPage(List<Dog> dogs) {
        this.dogs = dogs;
        setTitle("Filtered Results");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeSortOptions();
        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);
        sortComboBox = new JComboBox<>(sortOptions.keySet().toArray(new String[0]));
        sortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortDogs();
            }
        });
        topPanel.add(sortComboBox);

        JButton homeButton = new JButton("Return to Home");
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToHomePage();
            }
        });
        topPanel.add(homeButton);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        add(scrollPane, BorderLayout.CENTER);
        displayDogs();
    }

    private void initializeSortOptions() {
        sortOptions = new HashMap<>();
        sortOptions.put("Name", Comparator.comparing(Dog::getName));
        sortOptions.put("Sex", Comparator.comparing(Dog::getSex));
        sortOptions.put("Breed", Comparator.comparing(dog -> dog.getBreed().toString()));
        sortOptions.put("Age", Comparator.comparingInt(Dog::getAge));
        sortOptions.put("Weight", Comparator.comparingDouble(Dog::getWeight));
        sortOptions.put("Price", Comparator.comparingDouble(Dog::getPrice));
    }

    private void displayDogs() {
        resultsPanel.removeAll();

        for (Dog dog : dogs) {
            JPanel dogPanel = new JPanel();
            dogPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            dogPanel.setLayout(new FlowLayout());

            JLabel imageLabel = new JLabel();
            ImageIcon imageIcon = new ImageIcon("resources/dogimages/" + dog.getImage());
            int originalWidth = imageIcon.getIconWidth();
            int originalHeight = imageIcon.getIconHeight();
            int targetHeight = 100;
            double aspectRatio = (double) originalWidth / originalHeight;
            int targetWidth = (int) (targetHeight * aspectRatio);
            Image scaledImage = imageIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
            dogPanel.add(imageLabel);

            JLabel infoLabel = new JLabel("<html>Name: " + dog.getName() + "<br>Breed: " + dog.getBreed() +
                    "<br>Sex: " + dog.getSex() + "<br>Age: " + dog.getAge() +
                    "<br>Weight: " + dog.getWeight() + " pounds<br>Price: $" + dog.getPrice() +
                    "<br>Ready for Adoption: " + (dog.getIsReady() ? "Yes" : "No") + "</html>");
            dogPanel.add(infoLabel);

            JButton viewButton = new JButton("View Dog");
            viewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewDog(dog);
                }
            });
            dogPanel.add(viewButton);

            JButton addToWishlistButton = new JButton("Add to Wishlist");
            addToWishlistButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addToWishlist(dog);
                }
            });
            dogPanel.add(addToWishlistButton);

            resultsPanel.add(dogPanel);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void sortDogs() {
        String selectedOption = (String) sortComboBox.getSelectedItem();
        if (selectedOption != null && sortOptions.containsKey(selectedOption)) {
            dogs.sort(sortOptions.get(selectedOption));
        }

        displayDogs();
    }

    private void returnToHomePage() {
        this.dispose();
        AdoptionCenterModel model = new AdoptionCenterModel();
        ACController controller = new ACController(model);
        MainPage mainPage = new MainPage(controller);
        mainPage.setVisible(true);

    }

    private void viewDog(Dog dog) {
        this.dispose();
        DogPage dogPage = new DogPage();
        dogPage.setVisible(true);
    }

    private void addToWishlist(Dog dog) {
        this.dispose();
        WishlistPage wishlistPage = new WishlistPage();
        wishlistPage.setVisible(true);

    }

}
