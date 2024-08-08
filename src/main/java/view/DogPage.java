package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

import model.Breed;
import model.Dog;

public class DogPage extends JPanel {

    private final JLabel nameLabel;
    private final JLabel sexLabel;
    private final JLabel ageLabel;
    private final JLabel weightLabel;
    private final JLabel imageLabel;
    private final JLabel priceLabel;
    private final JLabel readyLabel;
    private final JLabel breedNameLabel;
    private final JLabel breedDescriptionLabel;
    private final JLabel breedLifeMinLabel;
    private final JLabel breedLifeMaxLabel;
    private final JLabel breedMaleWeightMinLabel;
    private final JLabel breedMaleWeightMaxLabel;
    private final JLabel breedFemaleWeightMinLabel;
    private final JLabel breedFemaleWeightMaxLabel;
    private final JLabel breedHypoallergenicLabel;
    private final JButton backButton;
    
    public DogPage() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        nameLabel = new JLabel("Name: ");
        sexLabel = new JLabel("Sex: ");
        ageLabel = new JLabel("Age: ");
        weightLabel = new JLabel("Weight: ");
        imageLabel = new JLabel();
        priceLabel = new JLabel("Price: ");
        readyLabel = new JLabel("Ready for Adoption: ");
        breedNameLabel = new JLabel("Breed Name: ");
        breedDescriptionLabel = new JLabel("Description: ");
        breedLifeMinLabel = new JLabel("Life Expectancy Min: ");
        breedLifeMaxLabel = new JLabel("Life Expectancy Max: ");
        breedMaleWeightMinLabel = new JLabel("Male Weight Min: ");
        breedMaleWeightMaxLabel = new JLabel("Male Weight Max: ");
        breedFemaleWeightMinLabel = new JLabel("Female Weight Min: ");
        breedFemaleWeightMaxLabel = new JLabel("Female Weight Max: ");
        breedHypoallergenicLabel = new JLabel("Hypoallergenic: ");
        backButton = new JButton("Back");
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(nameLabel, gbc);
        gbc.gridy++;
        add(sexLabel, gbc);
        gbc.gridy++;
        add(ageLabel, gbc);
        gbc.gridy++;
        add(weightLabel, gbc);
        gbc.gridy++;
        add(imageLabel, gbc);
        gbc.gridy++;
        add(priceLabel, gbc);
        gbc.gridy++;
        add(readyLabel, gbc);
        gbc.gridy++;
        add(breedNameLabel, gbc);
        gbc.gridy++;
        add(breedDescriptionLabel, gbc);
        gbc.gridy++;
        add(breedLifeMinLabel, gbc);
        gbc.gridy++;
        add(breedLifeMaxLabel, gbc);
        gbc.gridy++;
        add(breedMaleWeightMinLabel, gbc);
        gbc.gridy++;
        add(breedMaleWeightMaxLabel, gbc);
        gbc.gridy++;
        add(breedFemaleWeightMinLabel, gbc);
        gbc.gridy++;
        add(breedFemaleWeightMaxLabel, gbc);
        gbc.gridy++;
        add(breedHypoallergenicLabel, gbc);
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backButton, gbc);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) getParent().getLayout()).show(getParent(), "HomePage");
            }
        });
    }

    public void displayDog(Dog dog) {
        nameLabel.setText("Name: " + dog.getName());
        sexLabel.setText("Sex: " + (dog.getSex().equals("m") ? "Male" : "Female"));
        Breed breed = dog.getBreed();
        ageLabel.setText("Age: " + dog.getAge() + " years");
        weightLabel.setText("Weight: " + dog.getWeight() + " pounds");
        priceLabel.setText("Price: $" + dog.getPrice());
        readyLabel.setText("Ready for Adoption: " + (dog.getIsReady() ? "Yes" : "No"));
        breedNameLabel.setText("Breed Name: " + breed.name());
        breedDescriptionLabel.setText("Description: " + breed.description());
        breedLifeMinLabel.setText("Life Expectancy Min: " + breed.lifeMin() + " years");
        breedLifeMaxLabel.setText("Life Expectancy Max: " + breed.lifeMax() + " years");
        breedMaleWeightMinLabel.setText("Male Weight Min: " + breed.maleWeightMin() + " pounds");
        breedMaleWeightMaxLabel.setText("Male Weight Max: " + breed.maleWeightMax() + " pounds");
        breedFemaleWeightMinLabel.setText("Female Weight Min: " + breed.femaleWeightMin() + " pounds");
        breedFemaleWeightMaxLabel.setText("Female Weight Max: " + breed.femaleWeightMax() + " pounds");
        breedHypoallergenicLabel.setText("Hypoallergenic: " + (breed.hypoallergenic() ? "Yes" : "No"));
        ImageIcon imageIcon = new ImageIcon(dog.getImage());
        Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        imageLabel.setIcon(imageIcon);
    }
}