package cs6310;

import cs6310.Exceptions.BattleLostException;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePanel extends JPanel {
    private JFrame frame;
    private Image backgroundImage;
    private Image pokemonImage1;
    private Image pokemonImage2;
    private boolean showBattle;
    private JTextPane battleOutputPane;

    private BattleArena battleArena;
    private Map<String, String> pokemonImages;

    public GamePanel(JFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(512, 384));
        setLayout(null);

        // Load the background image and the battle images
        backgroundImage = new ImageIcon("images/dynamax-battle.png").getImage();


        initializePokemonImages();
        battleOutputPane = new JTextPane();
        battleOutputPane.setEditable(false);
        battleOutputPane.setOpaque(false);
        battleOutputPane.setForeground(Color.GREEN);
        battleOutputPane.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        battleOutputPane.setBounds(400, 450, 800, 100);

        add(battleOutputPane);
        add(setSeedPanel());

        add(createBattleControlPanel());
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
            g.drawImage(pokemonImage1, 460, 500, 60, 60, this);
            g.drawImage(pokemonImage2, 690, 500, 60, 60, this);
        }
    }

    private JPanel createBattleControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        String[] pokeList = getPokeList();
        JComboBox<String> pokemon1 = new JComboBox<>(pokeList);
        JComboBox<String> pokemon2 = new JComboBox<>(pokeList);
        JButton battleButton = new JButton("Battle");
        JButton backButton = new JButton("Back");

        battleButton.addActionListener(e -> {
            try {
                showBattle = true;
                String pokemonOne = (String) pokemon1.getSelectedItem();
                String pokemonTwo = (String) pokemon2.getSelectedItem();
                if (pokemonOne.equals(pokemonTwo)) {
                    throw new RuntimeException("Pokemon can't battle itself");
                }
                List<String> results = performBattle(pokemonOne, pokemonTwo);

                new SwingWorker<Void, Void>() {
                    protected Void doInBackground() throws InterruptedException {
                        for (String res : results) {
                            Thread.sleep(500);
                            SwingUtilities.invokeLater(() -> {
                                battleOutputPane.setText(res);
                                if (res.startsWith(pokemonOne)) {
                                    if (res.contains("attacking")) {
                                        pokemonImage1 = new ImageIcon(pokemonImages.getOrDefault(pokemonOne + "_attack", "images/default_attack.png")).getImage();
                                    } else if (res.contains("received")) {
                                        pokemonImage1 = new ImageIcon(pokemonImages.getOrDefault(pokemonOne + "_hurt", "images/default_hurt.png")).getImage();
                                    } else if (res.contains("defend")) {
                                        pokemonImage1 = new ImageIcon(pokemonImages.getOrDefault(pokemonOne + "_defend", "images/default_defend.png")).getImage();
                                    } else if (res.contains("lost")) {
                                        pokemonImage1 = new ImageIcon(pokemonImages.getOrDefault(pokemonOne + "_lose", "images/default_lose.png")).getImage();
                                    } else if (res.contains("won")) {
                                        pokemonImage1 = new ImageIcon(pokemonImages.getOrDefault(pokemonOne + "_win", "images/default_win.png")).getImage();
                                    }
                                }
                                if (res.startsWith(pokemonTwo)) {
                                    if (res.contains("attacking")) {
                                        pokemonImage2 = new ImageIcon(pokemonImages.getOrDefault(pokemonTwo + "_attack", "images/default_attack.png")).getImage();
                                    } else if (res.contains("received")) {
                                        pokemonImage2 = new ImageIcon(pokemonImages.getOrDefault(pokemonTwo + "_hurt", "images/default_hurt.png")).getImage();
                                    } else if (res.contains("defend")) {
                                        pokemonImage2 = new ImageIcon(pokemonImages.getOrDefault(pokemonTwo + "_defend", "images/default_defend.png")).getImage();
                                    } else if (res.contains("won")) {
                                        pokemonImage2 = new ImageIcon(pokemonImages.getOrDefault(pokemonTwo + "_win", "images/default_win.png")).getImage();
                                    } else if (res.contains("lost")) {
                                        pokemonImage2 = new ImageIcon(pokemonImages.getOrDefault(pokemonTwo + "_lose", "images/default_lose.png")).getImage();
                                    }
                                }
                                repaint();
                            });
                        }
                        return null;
                    }
                }.execute();


            } catch (BattleLostException | ClassNotFoundException | RuntimeException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Battle Error", JOptionPane.ERROR_MESSAGE);
                battleOutputPane.setText("Error: " + ex.getMessage());
            }
        });

        backButton.addActionListener(e -> {
            showBattle = false;
            pokemonImage1 = new ImageIcon("images/Charmander60.png").getImage();
            battleOutputPane.setText("");
            repaint();
            ((GraphicalInterface) frame).showMainMenu();
        });

        panel.add(pokemon1);
        panel.add(pokemon2);
        panel.add(battleButton);
        panel.add(backButton);
        panel.setBounds(400, 590, 430, 30); // Set the position and size of the control panel
        return panel;
    }

    private JPanel setSeedPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        String[] seedList = {"20","15","10","5","1"};
        JComboBox<String> seedPicker = new JComboBox<>(seedList);
        JLabel labelSeed = new JLabel("Set seed as");
        JButton setSeed = new JButton("Go");


        setSeed.addActionListener(e -> {
            String selectedSeed = (String) seedPicker.getSelectedItem();

            SeedManager.setSeed(Integer.valueOf(selectedSeed));

        });
        panel.add(labelSeed);
        panel.add(seedPicker);
        panel.add(setSeed);
        panel.setBounds(500, 10, 200, 40);
        return panel;
    }

    private List<String> performBattle(String pokemon1, String pokemon2) throws BattleLostException, ClassNotFoundException {
        pokemonImage1 = new ImageIcon(pokemonImages.getOrDefault(pokemon1, "images/default.png")).getImage();
        pokemonImage2 = new ImageIcon(pokemonImages.getOrDefault(pokemon2, "images/default.png")).getImage();
        repaint();
        List<String> outputString = new ArrayList<>();
        if (battleArena != null) {
            PrintStream stdout = System.out;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));
            try {
                battleArena.battle(pokemon1, pokemon2);
            } catch (BattleLostException e) {
                String winner = (pokemon1.equals(e.getLosingPokemonName())) ? pokemon2 : pokemon1;
                String loser = (pokemon1.equals(e.getLosingPokemonName())) ? pokemon1 : pokemon2;
                System.out.println(loser + " has lost");
                System.out.println(winner + " has won the battle");
            }
            System.setOut(stdout);
            String[] outputLines = outputStream.toString().split("\n");
            for (String line : outputLines) {
                outputString.add(line);
            }
            return outputString;
        }
        outputString.add("No battle arena set up");
        return outputString;
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
            if(fileName=="Pokemon")
                continue;
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                fileNames[i] = fileName.substring(0, dotIndex);
            } else {
                fileNames[i] = fileName;
            }
        }
        List<String> filteredList = new ArrayList<>();
        for (String fileName : fileNames) {
            if (!"Pokemon".equals(fileName)) {
                filteredList.add(fileName);
            }
        }

        return filteredList.toArray(new String[0]);
    }

    private void initializeBattleArena() {
        this.battleArena = new BattleArena();
    }
}
