package cs6310;

import cs6310.Exceptions.BattleLostException;

import cs6310.GroupBattleManager;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GamePanelForTeamBattle extends JPanel {
    private JFrame frame;
    private Image backgroundImage;
    private Image pokemonImageA1;
    private Image pokemonImageA2;
    private Image pokemonImageA3;
    private Image pokemonImageB1;
    private Image pokemonImageB2;
    private Image pokemonImageB3;
    private String pokemonStringA1;
    private String pokemonStringA2;
    private String pokemonStringA3;
    private String pokemonStringB1;
    private String pokemonStringB2;
    private String pokemonStringB3;
    private boolean showBattle;
    private JTextPane battleOutputPane1;
    private JTextPane battleOutputPane2;
    private JTextPane battleOutputPane3;
    private BattleArena battleArena;
    private Map<String, String> pokemonImages;
    private String[] pokeList = getPokeList();
    JButton battleButton = new JButton("Team Battle");
    JButton backButton = new JButton("Back");

    public GamePanelForTeamBattle(JFrame frame) {

        JLabel labelGroupA = new JLabel("Group A");
        JComboBox<String> pokemonA1 = new JComboBox<>(pokeList);
        JComboBox<String> pokemonA2 = new JComboBox<>(pokeList);
        JComboBox<String> pokemonA3 = new JComboBox<>(pokeList);

        JLabel labelGroupB = new JLabel("Group B");
        JComboBox<String> pokemonB1 = new JComboBox<>(pokeList);
        JComboBox<String> pokemonB2 = new JComboBox<>(pokeList);
        JComboBox<String> pokemonB3 = new JComboBox<>(pokeList);

        labelGroupA.setForeground(Color.WHITE);
        labelGroupB.setForeground(Color.WHITE);

        labelGroupA.setFont(new Font("Arial", Font.BOLD, 16));
        labelGroupB.setFont(new Font("Arial", Font.BOLD, 16));

        this.frame = frame;
        setPreferredSize(new Dimension(512, 384));
        setLayout(null);
        backgroundImage = new ImageIcon("images/dynamax-battle.png").getImage();

        initializePokemonImages();
        battleOutputPane1 = new JTextPane();
        battleOutputPane1.setEditable(false);
        battleOutputPane1.setOpaque(false);
        battleOutputPane1.setForeground(Color.GREEN);
        battleOutputPane1.setFont(new Font("Arial", Font.BOLD, 14)); 
        battleOutputPane2 = new JTextPane();
        battleOutputPane2.setEditable(false);
        battleOutputPane2.setOpaque(false);
        battleOutputPane2.setForeground(Color.GREEN);
        battleOutputPane2.setFont(new Font("Arial", Font.BOLD, 14)); 
        battleOutputPane3 = new JTextPane();
        battleOutputPane3.setEditable(false);
        battleOutputPane3.setOpaque(false);
        battleOutputPane3.setForeground(Color.GREEN);
        battleOutputPane3.setFont(new Font("Arial", Font.BOLD, 14)); 
        battleOutputPane1.setBounds(400, 150, 800, 25);
        battleOutputPane2.setBounds(400, 300, 800, 25);
        battleOutputPane3.setBounds(400, 450, 800, 25);
        add(battleOutputPane1);
        add(battleOutputPane2);
        add(battleOutputPane3);
        // Set bounds for labels, positioning them above the JComboBoxes
        labelGroupA.setBounds(150, 70, 150, 30);  // Positioned above pokemonA1
        pokemonA1.setBounds(150,100,150,30);
        pokemonA2.setBounds(150,250,150,30);
        pokemonA3.setBounds(150,400,150,30);
        labelGroupB.setBounds(900, 70, 150, 30);
        pokemonB1.setBounds(900,100,150,30);
        pokemonB2.setBounds(900,250,150,30);
        pokemonB3.setBounds(900,400,150,30);
        add(labelGroupA);
        add(pokemonA1);
        add(pokemonA2);
        add(pokemonA3);
        add(labelGroupB);
        add(pokemonB1);
        add(pokemonB2);
        add(pokemonB3);

        add(setSeedPanel());
        
        initializeBattleArena();
        showBattle = false;

        backButton.addActionListener(e -> {
            showBattle = false;
            repaint();
            ((GraphicalInterface) frame).showMainMenu();
        });
        backButton.setBounds(565,620,70,30);
        add(backButton);

        battleButton.addActionListener(e -> {
            pokemonStringA1 = (String) pokemonA1.getSelectedItem();
            pokemonStringA2 = (String) pokemonA2.getSelectedItem();
            pokemonStringA3 = (String) pokemonA3.getSelectedItem();
            pokemonStringB1 = (String) pokemonB1.getSelectedItem();
            pokemonStringB2 = (String) pokemonB2.getSelectedItem();
            pokemonStringB3 = (String) pokemonB3.getSelectedItem();
            try{
                showBattle = true;
                if (pokemonStringA1.equals(pokemonStringB1)) {
                    throw new RuntimeException("Pokemon can't battle itself");
                }
                List<String> results = performBattle(pokemonImageA1,pokemonImageB1, pokemonStringA1 , pokemonStringB1);
                new SwingWorker<Void, Void>() {
                    protected Void doInBackground() throws InterruptedException {
                        for (String res : results) {
                            Thread.sleep(1000);  // Wait for 1 second
                            SwingUtilities.invokeLater(() -> {
                                battleOutputPane1.setText(res);
                                if (res.startsWith(pokemonStringA1)) {
                                    if (res.contains("attacking")) {
                                        pokemonImageA1 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA1 + "_attack", "images/default_attack.png")).getImage();
                                    } else if (res.contains("received")) {
                                        pokemonImageA1 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA1 + "_hurt", "images/default_hurt.png")).getImage();
                                    } else if (res.contains("defend")) {
                                        pokemonImageA1 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA1 + "_defend", "images/default_defend.png")).getImage();
                                    } else if (res.contains("lost")) {
                                        pokemonImageA1 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA1 + "_lose", "images/default_lose.png")).getImage();
                                    } else if (res.contains("won")) {
                                        pokemonImageA1 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA1 + "_win", "images/default_win.png")).getImage();
                                    }
                                }
                                if (res.startsWith(pokemonStringB1)) {
                                    if (res.contains("attacking")) {
                                        pokemonImageB1 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB1 + "_attack", "images/default_attack.png")).getImage();
                                    } else if (res.contains("received")) {
                                        pokemonImageB1 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB1 + "_hurt", "images/default_hurt.png")).getImage();
                                    } else if (res.contains("defend")) {
                                        pokemonImageB1 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB1 + "_defend", "images/default_defend.png")).getImage();
                                    } else if (res.contains("won")) {
                                        pokemonImageB1 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB1 + "_win", "images/default_win.png")).getImage();
                                    } else if (res.contains("lost")) {
                                        pokemonImageB1 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB1 + "_lose", "images/default_lose.png")).getImage();
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
                battleOutputPane1.setText("Error: " + ex.getMessage());
            }
            try{
                showBattle = true;
                if (pokemonStringA2.equals(pokemonStringB2)) {
                    throw new RuntimeException("Pokemon can't battle itself");
                }
                List<String> results = performBattle(pokemonImageA2,pokemonImageB2, pokemonStringA2 , pokemonStringB2);
                new SwingWorker<Void, Void>() {
                    protected Void doInBackground() throws InterruptedException {
                        for (String res : results) {
                            Thread.sleep(500);  // Wait for 1 second
                            SwingUtilities.invokeLater(() -> {
                                battleOutputPane2.setText(res);
                                if (res.startsWith(pokemonStringA2)) {
                                    if (res.contains("attacking")) {
                                        pokemonImageA2 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA2 + "_attack", "images/default_attack.png")).getImage();
                                    } else if (res.contains("received")) {
                                        pokemonImageA2 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA2 + "_hurt", "images/default_hurt.png")).getImage();
                                    } else if (res.contains("defend")) {
                                        pokemonImageA2 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA2 + "_defend", "images/default_defend.png")).getImage();
                                    } else if (res.contains("lost")) {
                                        pokemonImageA2 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA2 + "_lose", "images/default_lose.png")).getImage();
                                    } else if (res.contains("won")) {
                                        pokemonImageA2 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA2 + "_win", "images/default_win.png")).getImage();
                                    }
                                }
                                if (res.startsWith(pokemonStringB2)) {
                                    if (res.contains("attacking")) {
                                        pokemonImageB2 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB2 + "_attack", "images/default_attack.png")).getImage();
                                    } else if (res.contains("received")) {
                                        pokemonImageB2 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB2 + "_hurt", "images/default_hurt.png")).getImage();
                                    } else if (res.contains("defend")) {
                                        pokemonImageB2 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB2 + "_defend", "images/default_defend.png")).getImage();
                                    } else if (res.contains("won")) {
                                        pokemonImageB2 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB2 + "_win", "images/default_win.png")).getImage();
                                    } else if (res.contains("lost")) {
                                        pokemonImageB2 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB2 + "_lose", "images/default_lose.png")).getImage();
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
                battleOutputPane2.setText("Error: " + ex.getMessage());
            }
            try{
                showBattle = true;
                if (pokemonStringA3.equals(pokemonStringB3)) {
                    throw new RuntimeException("Pokemon can't battle itself");
                }
                List<String> results = performBattle(pokemonImageA3,pokemonImageB3, pokemonStringA3 , pokemonStringB3);
                new SwingWorker<Void, Void>() {
                    protected Void doInBackground() throws InterruptedException {
                        for (String res : results) {
                            Thread.sleep(1000);  // Wait for 1 second
                            SwingUtilities.invokeLater(() -> {
                                battleOutputPane3.setText(res);
                                if (res.startsWith(pokemonStringA3)) {
                                    if (res.contains("attacking")) {
                                        pokemonImageA3 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA3 + "_attack", "images/default_attack.png")).getImage();
                                    } else if (res.contains("received")) {
                                        pokemonImageA3 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA3 + "_hurt", "images/default_hurt.png")).getImage();
                                    } else if (res.contains("defend")) {
                                        pokemonImageA3 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA3 + "_defend", "images/default_defend.png")).getImage();
                                    } else if (res.contains("lost")) {
                                        pokemonImageA3 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA3 + "_lose", "images/default_lose.png")).getImage();
                                    } else if (res.contains("won")) {
                                        pokemonImageA3 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringA3 + "_win", "images/default_win.png")).getImage();
                                    }
                                }
                                if (res.startsWith(pokemonStringB3)) {
                                    if (res.contains("attacking")) {
                                        pokemonImageB3 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB3 + "_attack", "images/default_attack.png")).getImage();
                                    } else if (res.contains("received")) {
                                        pokemonImageB3 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB3 + "_hurt", "images/default_hurt.png")).getImage();
                                    } else if (res.contains("defend")) {
                                        pokemonImageB3 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB3 + "_defend", "images/default_defend.png")).getImage();
                                    } else if (res.contains("won")) {
                                        pokemonImageB3 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB3 + "_win", "images/default_win.png")).getImage();
                                    } else if (res.contains("lost")) {
                                        pokemonImageB3 = new ImageIcon(pokemonImages.getOrDefault(pokemonStringB3 + "_lose", "images/default_lose.png")).getImage();
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
                battleOutputPane3.setText("Error: " + ex.getMessage());
            }
        });
        battleButton.setBounds(565,570,110,30);
        add(battleButton);


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



    private List<String> performBattle(Image pokemonImage1, Image pokemonImage2,String pokemon1, String pokemon2) throws BattleLostException, ClassNotFoundException {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image first
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

        // Then draw the battle images if needed
        if (showBattle) {
            g.drawImage(pokemonImageA1, 320, 85, 60, 60, this);
            g.drawImage(pokemonImageA2, 320, 235, 60, 60, this);
            g.drawImage(pokemonImageA3, 320, 385, 60, 60, this);
            g.drawImage(pokemonImageB1, 820, 85, 60, 60, this);
            g.drawImage(pokemonImageB2, 820, 235, 60, 60, this);
            g.drawImage(pokemonImageB3, 820, 385, 60, 60, this);
        }
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
