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
    /** The controller for managing dog adoption data and operations. */
    private ACController controller;

    /** The main panel containing all GUI components. */
    private JPanel mainPanel;

    /** Panel for displaying breed buttons. */
    private JPanel breedButtonsPanel;

    /** Panel for displaying young dogs. */
    private JPanel youngDogsPanel;

    /** Panel for search functionality. */
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

    private void createAdvancedSearch() {
    }

    private void createYoungDogsDisplay() {
    }

    private void createBreedButtons() {
    }
