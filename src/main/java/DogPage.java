import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.border.EmptyBorder;

public class DogPage extends JPanel {

    private final JLabel nameLabel;
    private final JLabel sexLabel;
    private final JLabel breedLabel;
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
        GridBagConstraints gbc = new GridBagConstraints();
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        nameLabel = new JLabel("Name: ");
        sexLabel = new JLabel("Sex: ");
        breedLabel = new JLabel("Breed: ");
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
    }
}
