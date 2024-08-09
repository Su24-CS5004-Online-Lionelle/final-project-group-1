package view;

import controller.ACController;
import model.Dog;

import javax.swing.*;
import java.awt.*;

public class DogPage extends JFrame {
    private Dog dog;
    private ACController controller;

    public DogPage(Dog dog, ACController controller) {
        this.dog = dog;
        this.controller = controller;
        setTitle(dog.getName() + " Details");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        createDogDetailsPanel();
        createButtonPanel();
    }

    private void createDogDetailsPanel() {
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Dog details
        detailsPanel.add(new JLabel("Name:"));
        detailsPanel.add(new JLabel(dog.getName()));
        detailsPanel.add(new JLabel("Breed:"));
        detailsPanel.add(new JLabel(dog.getBreed().name()));
        detailsPanel.add(new JLabel("Age:"));
        detailsPanel.add(new JLabel(String.valueOf(dog.getAge())));
        detailsPanel.add(new JLabel("Sex:"));
        detailsPanel.add(new JLabel(dog.getSex()));
        detailsPanel.add(new JLabel("Price:"));
        detailsPanel.add(new JLabel("$" + dog.getPrice()));

        // Breed details
        detailsPanel.add(new JLabel("Breed Description:"));
        detailsPanel.add(new JLabel(dog.getBreed().description()));
        detailsPanel.add(new JLabel("Life Expectancy:"));
        detailsPanel.add(new JLabel(dog.getBreed().lifeMin() + " - " + dog.getBreed().lifeMax() + " years"));
        detailsPanel.add(new JLabel("Male Weight:"));
        detailsPanel.add(new JLabel(dog.getBreed().maleWeightMin() + " - " + dog.getBreed().maleWeightMax() + " pounds"));
        detailsPanel.add(new JLabel("Female Weight:"));
        detailsPanel.add(new JLabel(dog.getBreed().femaleWeightMin() + " - " + dog.getBreed().femaleWeightMax() + " pounds"));
        detailsPanel.add(new JLabel("Hypoallergenic:"));
        detailsPanel.add(new JLabel(dog.getBreed().hypoallergenic() ? "Yes" : "No"));

        add(detailsPanel, BorderLayout.CENTER);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton addToWishlistButton = new JButton("Add to Wishlist");
        addToWishlistButton.addActionListener(e -> addToWishlist());
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(addToWishlistButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addToWishlist() {
        String message = controller.addToWishList(dog);
        JOptionPane.showMessageDialog(this, message);
    }
}