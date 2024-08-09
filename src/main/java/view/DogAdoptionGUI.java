package view;

import controller.ACController;
import model.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DogAdoptionGUI extends JFrame {
    private ACController controller;
    private JPanel mainPanel, searchPanel, resultsPanel, wishlistPanel;
    private JTextField nameField, ageField, weightField, priceField;
    private JComboBox<String> sexComboBox;
    private JComboBox<String> breedComboBox;
    private JButton searchButton, clearButton, showWishlistButton;
    private JPanel dogGridPanel;
    private JScrollPane scrollPane;

    public DogAdoptionGUI(ACController controller) {
        this.controller = controller;

        setTitle("Dog Adoption Program");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel(new BorderLayout());
        createSearchPanel();
        createResultsPanel();
        createWishlistPanel();

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(resultsPanel, BorderLayout.CENTER);
        mainPanel.add(wishlistPanel, BorderLayout.SOUTH);
        add(mainPanel);

        updateDogList(controller.getAdoptableDogs());
        setVisible(true);
    }

    private void createSearchPanel() {
        searchPanel = new JPanel(new GridLayout(4, 2));

        nameField = new JTextField(20);
        ageField = new JTextField(5);
        weightField = new JTextField(5);
        priceField = new JTextField(10);
        sexComboBox = new JComboBox<>(new String[]{"Any", "Male", "Female"});
        breedComboBox = new JComboBox<>(getBreedList());
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");

        searchPanel.add(new JLabel("Name:"));
        searchPanel.add(nameField);
        searchPanel.add(new JLabel("Sex:"));
        searchPanel.add(sexComboBox);
        searchPanel.add(new JLabel("Breed:"));
        searchPanel.add(breedComboBox);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        searchButton.addActionListener(e -> performSearch());
        clearButton.addActionListener(e -> clearSearch());
    }

    private void createResultsPanel() {
        resultsPanel = new JPanel(new BorderLayout());
        dogGridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        scrollPane = new JScrollPane(dogGridPanel);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createWishlistPanel() {
        wishlistPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        showWishlistButton = new JButton("Show Wishlist");
        showWishlistButton.addActionListener(e -> showWishlist());
        wishlistPanel.add(showWishlistButton);
    }

    private String[] getBreedList() {
        Set<String> breeds = new HashSet<>();

        breeds.add("Any");
        for (Dog dog : controller.getAdoptableDogs()) {
            breeds.add(dog.getBreed().name());
        }
        String[] breedArray = breeds.toArray(new String[0]);
        Arrays.sort(breedArray);

        return breedArray;
    }

    private void performSearch() {

        List<Dog> allDogs = controller.getAdoptableDogs();
        ACFilterPlanner planner = new ACFilterPlanner(allDogs);

        boolean nameOn = !nameField.getText().isEmpty();
        boolean ageOn = !ageField.getText().isEmpty();
        boolean sexOn = sexComboBox.getSelectedIndex() > 0;
        boolean breedOn = breedComboBox.getSelectedIndex() > 0;
        boolean weightOn = !weightField.getText().isEmpty();
        boolean priceOn = !priceField.getText().isEmpty();

        String nameFilter = nameField.getText();
        String ageFilter = ageField.getText();
        String sexFilter = sexOn ? sexComboBox.getSelectedItem().toString().substring(0, 1).toLowerCase() : null;
        String breedFilter = breedOn ? breedComboBox.getSelectedItem().toString() : null;
        String weightFilter = weightField.getText();
        String priceFilter = priceField.getText();

        try {
            Stream<Dog> filteredDogs = planner.filter(
                nameOn, nameFilter,
                sexOn, sexFilter,
                breedOn, breedFilter,
                ageOn, ageFilter,
                weightOn, weightFilter,
                priceOn, priceFilter,
                "name", true
            );

            List<Dog> resultList = filteredDogs.collect(Collectors.toList());
            updateDogList(resultList);

            if (resultList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No dogs match the search criteria.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, resultList.size() + " dogs found matching the search criteria.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearSearch() {
        nameField.setText("");
        ageField.setText("");
        sexComboBox.setSelectedIndex(0);
        breedComboBox.setSelectedIndex(0);
        weightField.setText("");
        priceField.setText("");
        controller.clearSearchFilters();

        updateDogList(controller.getAdoptableDogs());
    }

    private void updateDogList(List<Dog> dogs) {
        dogGridPanel.removeAll();

        for (Dog dog : dogs) {
            JPanel dogPanel = createDogPanel(dog);
            dogGridPanel.add(dogPanel);
        }

        dogGridPanel.revalidate();
        dogGridPanel.repaint();
    }
    private JPanel createDogPanel(Dog dog) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ImageIcon imageIcon = new ImageIcon(dog.getImage());
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        panel.add(imageLabel, BorderLayout.CENTER);

        JLabel infoLabel = new JLabel("<html>" + dog.getName() + "<br>" +
                                    (dog.getSex().equals("m") ? "Male" : "Female") + "<br>" +
                                    dog.getBreed().name() + "<br>" + dog.getWeight() + " pounds<br>" +
                                    dog.getAge() + " years<br>$" + dog.getPrice() + "</html>");
        panel.add(infoLabel, BorderLayout.SOUTH);

        JButton wishlistButton = new JButton(controller.getWishList().contains(dog) ? "Remove from Wishlist" : "Add to Wishlist");
        wishlistButton.addActionListener(e -> toggleWishlist(dog, wishlistButton));
        panel.add(wishlistButton, BorderLayout.NORTH);
        return panel;
    }

    private void toggleWishlist(Dog dog, JButton button) {

        if (controller.getWishList().contains(dog)) {
            controller.removeFromWishList(dog.getID());
            button.setText("Add to Wishlist");
        } else {
            controller.addToWishList(dog);
            button.setText("Remove from Wishlist");
        }
    }

    private void showWishlist() {
        List<Dog> wishlist = controller.getWishList();

        JDialog wishlistDialog = new JDialog(this, "Wishlist", true);
        wishlistDialog.setSize(400, 300);
        JPanel wishlistPanel = new JPanel(new BorderLayout());
        JPanel dogsPanel = new JPanel(new GridLayout(0, 1));

        for (Dog dog : wishlist) {
            JPanel dogPanel = createDogPanel(dog);
            dogsPanel.add(dogPanel);
        }

        JScrollPane scrollPane = new JScrollPane(dogsPanel);
        wishlistPanel.add(scrollPane, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save Wishlist");
        saveButton.addActionListener(e -> saveWishlist());
        wishlistPanel.add(saveButton, BorderLayout.SOUTH);
        wishlistDialog.add(wishlistPanel);
        wishlistDialog.setVisible(true);
    }

    private void saveWishlist() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".json")) {
                filePath += ".json";
            }

            controller.saveList(filePath);
            JOptionPane.showMessageDialog(this, "Wishlist saved successfully to " + filePath);
        }
    }

    public static void main(String[] args) {
        AdoptionCenterModel model = new AdoptionCenterModel();
        ACController controller = new ACController(model);
        ApiUtil util = new ApiUtil();
        Map<String, Breed> breeds;
        try {
            breeds = util.parseBreeds(util.getBreeds());
            Dog dog1 = new Dog("1", "Alexander", "M", breeds.get("Caucasian Shepherd Dog"), 24, 12.0, "1.png");
            Dog dog2 = new Dog("2", "Bella", "F", breeds.get("Bouvier des Flandres"), 3, 65.0, "2.png", 1200.00, true);
            Dog dog3 = new Dog("3", "Charlie", "M", breeds.get("Hanoverian Scenthound"), 5, 70.0, "3.png", 1500.00, true);
            Dog dog4 = new Dog("4", "Daisy", "F", breeds.get("Tibetan Spaniel"), 4, 60.0, "4.png", 1300.00, true);
            Dog dog5 = new Dog("5", "Ella", "F", breeds.get("Border Collie"), 2, 25.0, "5.png", 800.00, true);
            Dog dog6 = new Dog("6", "Finn", "M", breeds.get("Curly-Coated Retriever"), 6, 50.0, "6.png", 900.00, true);
            Dog dog7 = new Dog("7", "Ginger", "F", breeds.get("Skye Terrier"), 7, 55.0, "7.png", 1100.00, true);
            Dog dog8 = new Dog("8", "Hunter", "M", breeds.get("Hokkaido"), 3, 85.0, "8.png", 1700.00, true);
            Dog dog9 = new Dog("9", "Ivy", "F", breeds.get("Hokkaido"), 4, 45.0, "9.png", 1400.00, true);
            Dog dog10 = new Dog("10", "Jack", "M", breeds.get("Japanese Terrier"), 5, 75.0, "10.png", 1600.00, true);
            Dog dog11 = new Dog("11", "Kira", "F", breeds.get("Caucasian Shepherd Dog"), 6, 65.0, "11.png", 1200.00, true);
            Dog dog12 = new Dog("12", "Leo", "M", breeds.get("Bouvier des Flandres"), 2, 20.0, "12.png", 700.00, true);
            Dog dog13 = new Dog("13", "Molly", "F", breeds.get("Hanoverian Scenthound"), 7, 15.0, "13.png", 900.00, true);
            Dog dog14 = new Dog("14", "Nala", "F", breeds.get("Tibetan Spaniel"), 5, 140.0, "14.png", 0, false);
            Dog dog15 = new Dog("15", "Oscar", "M", breeds.get("Border Collie"), 3, 8.0, "15.png", 600.00, true);
            Dog dog16 = new Dog("16", "Penny", "F", breeds.get("Curly-Coated Retriever"), 4, 5.0, "16.png", 800.00, true);
            Dog dog17 = new Dog("17", "Quinn", "F", breeds.get("Skye Terrier"), 6, 12.0, "17.png", 1000.00, true);
            Dog dog18 = new Dog("18", "Rocky", "M", breeds.get("Tibetan Spaniel"), 5, 60.0, "18.png", 1300.00, true);
            Dog dog19 = new Dog("19", "Sophie", "F", breeds.get("Hokkaido"), 7, 7.0, "19.png", 850.00, true);
            Dog dog20 = new Dog("20", "Toby", "M", breeds.get("Japanese Terrier"), 2, 30.0, "20.png", 1100.00, true);
            Dog dog21 = new Dog("21", "Ursula", "F", breeds.get("Caucasian Shepherd Dog"), 3, 70.0, "21.png", 1500.00, true);
            Dog dog22 = new Dog("22", "Victor", "M", breeds.get("Bouvier des Flandres"), 6, 80.0, "22.png", 1700.00, true);
            Dog dog23 = new Dog("23", "Wendy", "F", breeds.get("Hanoverian Scenthound"), 4, 65.0, "23.png");
            Dog dog24 = new Dog("24", "Xander", "M", breeds.get("Tibetan Spaniel"), 5, 50.0, "24.png", 1600.00, true);
            Dog dog25 = new Dog("25", "Yara", "F", breeds.get("Border Collie"), 6, 20.0, "25.png", 1000.00, true);
            Dog dog26 = new Dog("26", "Zane", "M", breeds.get("Curly-Coated Retriever"), 3, 55.0, "26.png", 1300.00, true);
            Dog dog27 = new Dog("27", "Amber", "F", breeds.get("Skye Terrier"), 4, 60.0, "27.png", 1500.00, true);
            Dog dog28 = new Dog("28", "Ben", "M", breeds.get("Caucasian Shepherd Dog"), 2, 90.0, "28.png", 1800.00, true);
            Dog dog29 = new Dog("29", "Cleo", "F", breeds.get("Hokkaido"), 5, 50.0, "29.png", 1200.00, true);
            Dog dog30 = new Dog("30", "Duke", "M", breeds.get("Japanese Terrier"), 6, 40.0, "30.png", 1300.00, true);
            model.addDog(dog1);
            model.addDog(dog2);
            model.addDog(dog3);
            model.addDog(dog4);
            model.addDog(dog5);
            model.addDog(dog6);
            model.addDog(dog7);
            model.addDog(dog8);
            model.addDog(dog9);
            model.addDog(dog10);
            model.addDog(dog11);
            model.addDog(dog12);
            model.addDog(dog13);
            model.addDog(dog14);
            model.addDog(dog15);
            model.addDog(dog16);
            model.addDog(dog17);
            model.addDog(dog18);
            model.addDog(dog19);
            model.addDog(dog20);
            model.addDog(dog21);
            model.addDog(dog22);
            model.addDog(dog23);
            model.addDog(dog24);
            model.addDog(dog25);
            model.addDog(dog26);
            model.addDog(dog27);
            model.addDog(dog28);
            model.addDog(dog29);
            model.addDog(dog30);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new DogAdoptionGUI(controller));
    }
}
