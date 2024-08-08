package view;

import controller.ACController;
import model.AdoptionCenterModel;
import model.Dog;
import model.Breed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MainPage class represents the main GUI window for the Dog Adoption Center application.
 * It displays breed buttons, young dogs, and provides search functionality.
 */
public class MainPage extends JFrame {
    /**
     * The controller for managing dog adoption data and operations.
     */
    private ACController controller;

    /**
     * The main panel containing all GUI components.
     */
    private JPanel mainPanel;

    /**
     * Panel for displaying breed buttons.
     */
    private JPanel breedButtonsPanel;

    /**
     * Panel for displaying young dogs.
     */
    private JPanel youngDogsPanel;

    /**
     * Panel for search functionality.
     */
    private JPanel searchPanel;

    public MainPage(ACController controller) {
        this.controller = controller;
        setTitle("D.E.J.P Dog Adoption Center");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());

        createBreedButtons();
        createYoungDogsDisplay();
        createAdvancedSearch();

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Creates and adds the advanced search panel to the main panel.
     */
    private void createAdvancedSearch() {
        searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Advanced Search"));

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton advancedButton = new JButton("Advanced");

        searchButton.addActionListener(e -> performSearch(searchField.getText()));
        advancedButton.addActionListener(e -> showAdvancedSearch());

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(advancedButton);

        mainPanel.add(searchPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates and adds the young dogs display to the main panel.
     * Shows the 4 youngest dogs available for adoption.
     */
    private void createYoungDogsDisplay() {

        youngDogsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        youngDogsPanel.setBorder(BorderFactory.createTitledBorder("Youngest Dogs"));

        // Get the 4 youngest dogs
        controller.setHomeList(); // Set the home list to youngest dogs
        List<Dog> youngDogs = controller.getHomeList().stream().limit(4).collect(Collectors.toList());

        // Create a panel for each young dog
        for (Dog dog : youngDogs) {
            JPanel dogPanel = createDogPanel(dog);
            youngDogsPanel.add(dogPanel);
        }

        mainPanel.add(youngDogsPanel, BorderLayout.CENTER);
    }

    /**
     * Creates and adds breed buttons to the main panel.
     * Each button represents a unique dog breed available for adoption.
     */
    private void createBreedButtons() {
        breedButtonsPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        breedButtonsPanel.setBorder(BorderFactory.createTitledBorder("Dog Breeds"));

        // Get up to 9 distinct breeds from adoptable dogs
        List<Breed> breeds = controller.getAdoptableDogs().stream()
                .map(Dog::getBreed)
                .distinct()
                .limit(9)
                .collect(Collectors.toList());

        // Create a button for each breed
        for (Breed breed : breeds) {
            JButton breedButton = new JButton(breed.name());
            breedButton.addActionListener(e -> showBreedInfo(breed));
            breedButtonsPanel.add(breedButton);
        }

        mainPanel.add(breedButtonsPanel, BorderLayout.NORTH);
    }

    /**
     * Creates a panel displaying information about a single dog.
     *
     * @param dog The Dog object to display information for.
     * @return A JPanel containing the dog's information and image.
     */
    private JPanel createDogPanel(Dog dog) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add dog's name
        JLabel nameLabel = new JLabel(dog.getName());
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(nameLabel, BorderLayout.NORTH);

        // Add dog's image
        ImageIcon imageIcon = new ImageIcon(dog.getImage());
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        panel.add(imageLabel, BorderLayout.CENTER);

        // Add dog's info (age and breed)
        JLabel infoLabel = new JLabel("Age: " + dog.getAge() + " weeks, Breed: " + dog.getBreed().name());
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(infoLabel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Displays detailed information about a specific dog breed.
     *
     * @param breed The Breed object to display information for.
     */
    private void showBreedInfo(Breed breed) {
        String info = "Breed: " + breed.name() + "\n" +
                "Description: " + breed.description() + "\n" +
                "Life Expectancy: " + breed.lifeMin() + "-" + breed.lifeMax() + " years\n" +
                "Male Weight: " + breed.maleWeightMin() + "-" + breed.maleWeightMax() + " pounds\n" +
                "Female Weight: " + breed.femaleWeightMin() + "-" + breed.femaleWeightMax() + " pounds\n" +
                "Hypoallergenic: " + (breed.hypoallergenic() ? "Yes" : "No");

        JOptionPane.showMessageDialog(this, info, breed.name() + " Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Performs a search based on the given query and displays the results.
     *
     * @param query The search query string.
     */
    private void performSearch(String query) {
        controller.setNameSearch(query);
        controller.setResultList();
        List<Dog> results = controller.getResultList();
        showSearchResults(results);
    }

    /**
     * Displays the advanced search dialog, allowing users to search by multiple criteria.
     */
    private void showAdvancedSearch() {
        JDialog advancedSearchDialog = new JDialog(this, "Advanced Search", true);
        advancedSearchDialog.setSize(400, 300);
        advancedSearchDialog.setLayout(new GridLayout(0, 2, 10, 10));

        // Create input fields for various search criteria
        JTextField nameField = new JTextField();
        JComboBox<String> sexComboBox = new JComboBox<>(new String[]{"Any", "Male", "Female"});
        JTextField breedField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField priceField = new JTextField();

        // Add labels and input fields to the dialog
        advancedSearchDialog.add(new JLabel("Name:"));
        advancedSearchDialog.add(nameField);
        advancedSearchDialog.add(new JLabel("Sex:"));
        advancedSearchDialog.add(sexComboBox);
        advancedSearchDialog.add(new JLabel("Breed:"));
        advancedSearchDialog.add(breedField);
        advancedSearchDialog.add(new JLabel("Age:"));
        advancedSearchDialog.add(ageField);
        advancedSearchDialog.add(new JLabel("Weight:"));
        advancedSearchDialog.add(weightField);
        advancedSearchDialog.add(new JLabel("Price:"));
        advancedSearchDialog.add(priceField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            // Apply search filters based on user input
            controller.clearSearchFilters();
            if (!nameField.getText().isEmpty()) controller.setNameSearch(nameField.getText());
            if (sexComboBox.getSelectedIndex() > 0) controller.setSexSearch((String) sexComboBox.getSelectedItem());
            if (!breedField.getText().isEmpty()) controller.setBreedSearch(breedField.getText());
            if (!ageField.getText().isEmpty()) controller.setAgeSearch(ageField.getText());
            if (!weightField.getText().isEmpty()) controller.setWeightSearch(weightField.getText());
            if (!priceField.getText().isEmpty()) controller.setPriceSearch(priceField.getText());

            // Perform search and show results
            controller.setResultList();
            List<Dog> results = controller.getResultList();
            showSearchResults(results);
            advancedSearchDialog.dispose();
        });

        advancedSearchDialog.add(searchButton);

        advancedSearchDialog.setLocationRelativeTo(this);
        advancedSearchDialog.setVisible(true);
    }

    /**
     * Displays the search results in a dialog box.
     *
     * @param results The list of Dog objects matching the search criteria.
     */
    private void showSearchResults(List<Dog> results) {
        StringBuilder resultString = new StringBuilder("Search Results:\n\n");
        for (Dog dog : results) {
            resultString.append(dog.getName()).append(" - ")
                    .append(dog.getBreed().name()).append(", ")
                    .append(dog.getAge()).append(" weeks old\n");
        }
        JOptionPane.showMessageDialog(this, resultString.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * The main method to start the application.
     * Creates the model and controller, and launches the main page.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        AdoptionCenterModel model = new AdoptionCenterModel();
        ACController controller = new ACController(model);
        // Here you would typically load your dogs into the model
        SwingUtilities.invokeLater(() -> new MainPage(controller));
    }
}