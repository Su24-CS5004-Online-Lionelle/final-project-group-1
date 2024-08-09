package view;
 
import controller.ACController;
import model.Dog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 
public class WishlistPage extends JFrame {
    private ACController controller;
    private JPanel mainPanel;
    private JPanel dogListPanel;
    private JButton saveButton;
    private JButton homeButton;
 
    public WishlistPage(ACController controller) {
        this.controller = controller;
        setTitle("Dog Adoption Wishlist");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
 
        mainPanel = new JPanel(new BorderLayout());
 
        dogListPanel = new JPanel();
        dogListPanel.setLayout(new BoxLayout(dogListPanel, BoxLayout.Y_AXIS));
 
        JScrollPane scrollPane = new JScrollPane(dogListPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
 
        saveButton = new JButton("Save Wishlist to JSON");
        homeButton = new JButton("Return to Homepage");
 
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveWishlistToJson();
            }
        });
 
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToHomePage();
            }
        });
 
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(homeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
 
        add(mainPanel);
 
        displayWishlist();
    }
 
    private void displayWishlist() {
        dogListPanel.removeAll();
        List<Dog> wishlist = controller.getWishList();
 
        for (Dog dog : wishlist) {
            JPanel dogCard = createDogPanel(dog);
            dogListPanel.add(dogCard);
        }
 
        dogListPanel.revalidate();
        dogListPanel.repaint();
    }
 
    private JPanel createDogPanel(Dog dog) {
        JPanel dogCard = new JPanel();
        dogCard.setLayout(new BoxLayout(dogCard, BoxLayout.Y_AXIS));
        dogCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dogCard.setPreferredSize(new Dimension(200, 300));
 
        JLabel nameLabel = new JLabel("Name: " + dog.getName());
        JLabel breedLabel = new JLabel("Breed: " + dog.getBreed());
        JLabel ageLabel = new JLabel("Age: " + dog.getAge());
        JLabel sexLabel = new JLabel("Sex: " + dog.getSex());
        JLabel priceLabel = new JLabel("Price: $" + dog.getPrice());
        JLabel imageLabel = new JLabel(new ImageIcon(dog.getImage()));
 
        JButton removeButton = new JButton("Remove from Wishlist");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFromWishlist(dog);
            }
        });
 
        dogCard.add(imageLabel);
        dogCard.add(nameLabel);
        dogCard.add(breedLabel);
        dogCard.add(ageLabel);
        dogCard.add(sexLabel);
        dogCard.add(priceLabel);
        dogCard.add(removeButton);
 
        return dogCard;
    }
 
    private void removeFromWishlist(Dog dog) {
        String message = controller.removeFromWishList(dog.getID());
        JOptionPane.showMessageDialog(this, message);
        displayWishlist();
    }
 
    private void saveWishlistToJson() {
        controller.saveList("wishlist.json");
        JOptionPane.showMessageDialog(this, "Wishlist saved to wishlist.json");
    }
 
    private void returnToHomePage() {
        this.dispose();
        MainPage mainPage = new MainPage(controller);
        mainPage.setVisible(true);
    }
}