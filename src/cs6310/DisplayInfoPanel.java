package cs6310;

import cs6310.Exceptions.BattleLostException;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DisplayInfoPanel extends JPanel {
    private Logger LOGGER = LoggerSetup.getLogger();

    private JFrame frame;
    private Image backgroundImage;
    private Image pokemonImage1;
    private Image pokemonImage2;
    private boolean showBattle;
    private JTextPane battleOutputPane;

    private BattleArena battleArena;
    private Map<String, String> pokemonImages;

    public DisplayInfoPanel(JFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(512, 384));
        setLayout(null);

        // Load the background image and the battle images
        backgroundImage = new ImageIcon("images/displayinfo.png").getImage();


        initializePokemonImages();
        battleOutputPane = new JTextPane();
        battleOutputPane.setEditable(false);
        battleOutputPane.setOpaque(false);
        battleOutputPane.setForeground(Color.WHITE);
        battleOutputPane.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        battleOutputPane.setBounds(840, 300, 1200, 200);

        add(battleOutputPane);

        add(createDisplayInfo());
        initializeBattleArena();
        showBattle = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image first
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

        // Then draw the battle images if needed
        if (showBattle) {
            g.drawImage(pokemonImage1, 150, 300, 220, 220, this);
        }
    }

    private JPanel createDisplayInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        String[] pokeList = getPokeList();
        JComboBox<String> pokemon1 = new JComboBox<>(pokeList);
        JButton displayButton = new JButton("Display");
        JButton backButton = new JButton("Back");

        displayButton.addActionListener(e -> {
                    showBattle = true;
                    String pokemonOne = (String) pokemon1.getSelectedItem();
                    String results = performDisplayInfo(pokemonOne);
                    System.out.println("result: " + results);
                    battleOutputPane.setText(results);
                }
            );

        backButton.addActionListener(e -> {
            showBattle = false;
            pokemonImage1 = new ImageIcon("images/Charmander60.png").getImage();
            battleOutputPane.setText("");
            repaint();
            ((GraphicalInterface) frame).showMainMenu();
        });

        panel.add(pokemon1);
        panel.add(displayButton);
        panel.add(backButton);
        panel.setBounds(400, 590, 430, 30); // Set the position and size of the control panel
        return panel;
    }

    private String performDisplayInfo(String pokemon1) {
        pokemonImage1 = new ImageIcon(pokemonImages.getOrDefault(pokemon1, "images/default.png")).getImage();
        repaint();
        if (battleArena != null) {
            CustomMemoryHandler memoryHandler = new CustomMemoryHandler();
            LOGGER.addHandler(memoryHandler);
            try {
                battleArena.displayInfo(pokemon1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            LOGGER.removeHandler(memoryHandler);
            String outputString = memoryHandler.getLog();
            return outputString;
        }
        return "No battle arena set up";
    }

    private void initializePokemonImages() {
        pokemonImages = new HashMap<>();
        pokemonImages.put("Charmander", "images/Charmander.png");
        pokemonImages.put("Bulbasaur", "images/Bulbasaur.png");
        pokemonImages.put("Squirtle", "images/Squirtle.png");
        pokemonImages.put("Pikachu", "images/Pikachu.png");
        // Images for Charmander in different states
        pokemonImages.put("Charmander_attack", "images/Charmander_attack.png");
        pokemonImages.put("Charmander_hurt", "images/Charmander_hurt.png");
        pokemonImages.put("Charmander_defend", "images/Charmander_defend.png");
        pokemonImages.put("Charmander_win", "images/Charmander_win.png");
        pokemonImages.put("Charmander_lose", "images/Charmander_lose.png");

        // Images for Bulbasaur in different states
        pokemonImages.put("Bulbasaur_attack", "images/Bulbasaur_attack.png");
        pokemonImages.put("Bulbasaur_hurt", "images/Bulbasaur_hurt.png");
        pokemonImages.put("Bulbasaur_defend", "images/Bulbasaur_defend.png");
        pokemonImages.put("Bulbasaur_win", "images/Bulbasaur_win.png");
        pokemonImages.put("Bulbasaur_lose", "images/Bulbasaur_lose.png");

        // Images for Squirtle in different states
        pokemonImages.put("Squirtle_attack", "images/Squirtle_attack.png");
        pokemonImages.put("Squirtle_hurt", "images/Squirtle_hurt.png");
        pokemonImages.put("Squirtle_defend", "images/Squirtle_defend.png");
        pokemonImages.put("Squirtle_win", "images/Squirtle_win.png");
        pokemonImages.put("Squirtle_lose", "images/Squirtle_lose.png");

        // Images for Pikachu in different states
        pokemonImages.put("Pikachu_attack", "images/Pikachu_attack.png");
        pokemonImages.put("Pikachu_hurt", "images/Pikachu_hurt.png");
        pokemonImages.put("Pikachu_defend", "images/Pikachu_defend.png");
        pokemonImages.put("Pikachu_win", "images/Pikachu_win.png");
        pokemonImages.put("Pikachu_lose", "images/Pikachu_lose.png");
        // Images for Eevee in different states
        pokemonImages.put("Eevee", "images/Eevee.png");
        pokemonImages.put("Eevee_attack", "images/Eevee_attack.png");
        pokemonImages.put("Eevee_hurt", "images/Eevee_hurt.png");
        pokemonImages.put("Eevee_defend", "images/Eevee_defend.png");
        pokemonImages.put("Eevee_win", "images/Eevee_win.png");
        pokemonImages.put("Eevee_lose", "images/Eevee_lose.png");

        // Images for Gastly in different states
        pokemonImages.put("Gastly", "images/Gastly.png");
        pokemonImages.put("Gastly_attack", "images/Gastly_attack.png");
        pokemonImages.put("Gastly_hurt", "images/Gastly_hurt.png");
        pokemonImages.put("Gastly_defend", "images/Gastly_defend.png");
        pokemonImages.put("Gastly_win", "images/Gastly_win.png");
        pokemonImages.put("Gastly_lose", "images/Gastly_lose.png");

        // Images for Jigglypuff in different states
        pokemonImages.put("Jigglypuff", "images/Jigglypuff.png");
        pokemonImages.put("Jigglypuff_attack", "images/Jigglypuff_attack.png");
        pokemonImages.put("Jigglypuff_hurt", "images/Jigglypuff_hurt.png");
        pokemonImages.put("Jigglypuff_defend", "images/Jigglypuff_defend.png");
        pokemonImages.put("Jigglypuff_win", "images/Jigglypuff_win.png");
        pokemonImages.put("Jigglypuff_lose", "images/Jigglypuff_lose.png");

        // Images for Machop in different states
        pokemonImages.put("Machop", "images/Machop.png");
        pokemonImages.put("Machop_attack", "images/Machop_attack.png");
        pokemonImages.put("Machop_hurt", "images/Machop_hurt.png");
        pokemonImages.put("Machop_defend", "images/Machop_defend.png");
        pokemonImages.put("Machop_win", "images/Machop_win.png");
        pokemonImages.put("Machop_lose", "images/Machop_lose.png");
    }

    private String[] getPokeList() {
        File folder = new File("src/cs6310/Pokemon");
        String[] fileNames = folder.list();
        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                fileNames[i] = fileName.substring(0, dotIndex);
            } else {
                fileNames[i] = fileName;
            }
        }
        // Remove "Pokemon" from the list
        List<String> pokemonList = new ArrayList<>(Arrays.asList(fileNames));
        pokemonList.remove("Pokemon");
        String[] newfileNames = pokemonList.toArray(new String[0]);
        return newfileNames;
    }

    private void initializeBattleArena() {
        this.battleArena = new BattleArena();
    }
}
