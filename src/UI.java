import java.awt.BorderLayout;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * <h1>Jokes for Fun Application</h1> The UI class extends JFrame and provides a
 * graphical user interface for a joke viewing application. It includes
 * functionalities like selecting joke categories, displaying jokes, and
 * refreshing jokes.
 * <p>
 * This class is responsible for creating and managing the UI components such as
 * buttons, labels, combo boxes, and text areas. It interacts with the
 * AppService which is a handler of all classes.
 * </p>
 * <p>
 * Copyright 2023 Meisam Amjad @author amjadm@miamioh.edu
 * </p>
 */
public class UI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> categoriesComboBox;
    private JLabel selectedCategoryLabel;
    private JTextArea jokeText;
    private JButton nextJokeButton;
    private JButton refreshJokesButton;
    private AppService appService;

    private int index1 = 0;
    private int index2 = 5;

    public UI() {
        createUI();
    }

    // ---------------------------------------------------
    /*
     * Initializes the main frame of the application. Sets the title, size, and
     * default close operation. It also disables resizing and sets the layout to
     * null for absolute positioning of components.
     */
    private void setupFrame() {
        setTitle("Jokes for Fun");
        setSize(450, 350);
        this.setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // to be able to put component anywhere on the frame
        this.setLayout(null);
    }

    /*
     * Initializes and configures the combo box for selecting joke categories.
     * The combo box is populated with predefined categories and is set to
     * trigger an action when a category is selected.
     */
    private void setupComboBox() {
        // Drop down for joke categories
        String[] categories = { "Misc", "Programming" };
        categoriesComboBox = new JComboBox<>(categories);
        categoriesComboBox.setBounds(10, 60, 300, 20);
        categoriesComboBox.addActionListener(e -> onCategorySelected());
    }

    /*
     * Handles the event when a new category is selected from the combo box.
     * Updates the label to display the selected category and triggers the joke
     * display mechanism.
     */
    private void onCategorySelected() {
        String selectedCategory = (String) categoriesComboBox.getSelectedItem();
        selectedCategoryLabel.setText("Selected Category: " + selectedCategory);
        displayJoke();
    }

    /*
     * Initializes and positions the label used to display the currently
     * selected joke category. Sets the initial text to display the first
     * selected category from the combo box.
     */
    private void setupLabel() {
        // Label to display selected category
        selectedCategoryLabel = new JLabel();
        selectedCategoryLabel.setBounds(15, 25, 300, 20);
        selectedCategoryLabel.setText(
                "Selected Category: " + categoriesComboBox.getSelectedItem());
    }

    /*
     * Initializes the 'Next Joke' and 'Refresh Jokes' buttons with their
     * respective positions, sizes, and action listeners. These buttons are used
     * to navigate between jokes and refresh the joke list.
     */
    private void setupButtons() {
        // Next Joke button
        nextJokeButton = new JButton("Next Joke");
        nextJokeButton.setBounds(320, 60, 100, 20);
        nextJokeButton.addActionListener(e -> displayJoke());

        // Fetch new Jokes
        refreshJokesButton = new JButton("Refresh Jokes");
        refreshJokesButton.setBounds(150, 250, 150, 20);
        refreshJokesButton.addActionListener(e -> refreshJokes());
    }

    /*
     * Initializes the text area used for displaying jokes. Sets its position,
     * size, and wrapping properties. The text area is made non-editable.
     */
    private void setupTextArea() {
        // Label to display a joke
        jokeText = new JTextArea();
        jokeText.setBounds(15, 120, 420, 100);
        jokeText.setWrapStyleWord(true);
        jokeText.setLineWrap(true);
        jokeText.setEditable(false);
        // jokeLabel.setFocusable(false);
        // jokeLabel.setBackground(null);
        // jokeLabel.setBorder(null);
        // jokeText.setText("");
    }

    /*
     * Initializes the AppService responsible for handling jokes. It sets up the
     * service with the required file and size. Catches and prints exceptions if
     * any occur during initialization.
     */
    private void setupAppService() {
        try {
            appService = new AppService("temp.dat", 600);
        } catch (Exception e) {
            e.printStackTrace();
        }

        refreshJokes();
    }

    /*
     * Central method for setting up the entire user interface. It sequentially
     * calls other setup methods to initialize frame, combo box, label, buttons,
     * and text area. Finally, adds all components to the frame and sets the
     * look and feel of the UI.
     */
    private void createUI() {
        setupFrame();

        setupComboBox();

        setupLabel();

        setupButtons();

        setupTextArea();

        // Adding components to the frame
        add(categoriesComboBox);
        add(selectedCategoryLabel);
        add(jokeText);
        add(nextJokeButton);
        add(refreshJokesButton);

        // Set a professional and nice look
        setLookAndFeel();
    }

    /*
     * Handles the refreshing of jokes. Displays a progress dialog with a
     * progress bar while jokes are being fetched and updated. Manages the
     * process in a separate thread to keep the UI responsive.
     */
    private void refreshJokes() {
        JDialog progressDialog = new JDialog(this, "Loading...", true);
        progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        progressDialog.setSize(200, 60);
        progressDialog.setLocationRelativeTo(this);
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressDialog.add(BorderLayout.CENTER, progressBar);

        AppService.index = 0;

        new Thread(() -> {
            try {
                updateBarAndJokes(progressBar);
                progressDialog.dispose(); // Close the dialog
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        progressDialog.setVisible(true);

        displayJoke(); // shows a new joke after refreshing jokes
    }

    /*
     * Updates the progress bar and fetches jokes and updates the file.
     * 
     * @param progressBar for displaying the progress
     */
    private void updateBarAndJokes(JProgressBar progressBar) {
        for (int i = 0; i < 100; i += 10) {
            progressBar.setValue(i + 10); // updating progress bar
            fetchJokes(AppService.index); // Updating joke at index
        }
    }

    /*
     * Fetches a joke and save them from the AppService based on the order
     * parameter. Differentiates between 'Misc' and 'Programming' jokes. Handles
     * exceptions that might occur during the fetching process.
     */
    private void fetchJokes(int order) {
        try {
            if (order < 5) {
                appService.saveJoke("Misc");
            } else {
                appService.saveJoke("Programming");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Displays a joke in the text area based on the currently selected
     * category. Manages indices to ensure a sequential and cyclic display of
     * jokes for each category. Handles exceptions in reading joke data.
     */
    private void displayJoke() {
        // Emptying the text and label outputs
        selectedCategoryLabel.setText("");
        jokeText.setText("");

        // Getting the selected category
        String selectedCategory = (String) categoriesComboBox.getSelectedItem();
        selectedCategoryLabel.setText("Selected Category: " + selectedCategory);

        int fileIdx = 0;
        if (selectedCategory.equals("Misc")) {
            fileIdx = index1; // moving index1 in the range of [0-4]
            index1 = ((index1 + 1) % 5);
        } else {
            fileIdx = index2; // moving index2 in the range of [5-9]
            index2 = 5 + ((index2 + 1) % 5);
        }
        try {
            showResult(Arrays.copyOfRange(appService.read(fileIdx), 15,
                    584));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Gets a new joke and fixes its format and displays it on the text area.
     * 
     * @param joke a bytes of a joke string
     */
    private void showResult(byte[] joke) {
        String j = new String(joke).replace("\\n", "\n").replace("\\\"",
                "\"");
        jokeText.setText(j);
    }

    /*
     * Sets the look and feel of the application's user interface to match the
     * system's look and feel. Catches and prints exceptions if the setting
     * fails.
     */
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initiates the UI setup and makes the frame visible. It also initializes
     * the AppService required for fetching jokes and working with files.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UI jokesGui = new UI();
            // jokesGui.pack();
            jokesGui.setVisible(true);
            jokesGui.setupAppService();
        });
    }
}
