package cs6310;

import cs6310.Exceptions.BattleLostException;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.*;
import java.util.List;

public class GamePanelForGroupBattle extends JPanel {
    private JFrame frame;
    private Image backgroundImage;
    private Image pokemonImage1;
    private Image pokemonImage2;
    private boolean showBattle;

    private boolean showAddedPlayer;
    private JTextPane battleOutputPane;

    private BattleArena battleArena;
    private Map<String, String> pokemonImages;

    private List<String> addedPokemonListForGroupA = new ArrayList<>();
    private List<Image> addedPokemonImagesForGroupA = new ArrayList<>();

    private List<String> addedPokemonListForGroupB = new ArrayList<>();

    private List<Image> addedPokemonImagesForGroupB = new ArrayList<>();

    private GroupBattleManager groupBattleManager=null;

    private String msgForCurrentBattleRound="";

    private int groundBattleIdx=0;
    private boolean initGroupBattle=false;




    public GamePanelForGroupBattle(JFrame frame) {
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
        battleOutputPane.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        battleOutputPane.setBounds(300, 150, 800, 300);

        add(battleOutputPane);
        this.initGroupManager();
        add(setSwapFreqPanel());


        JLabel groupAlabel = new JLabel("Group A");
        groupAlabel.setForeground(Color.WHITE);
        JLabel groupBlabel = new JLabel("Group B");
        groupBlabel.setForeground(Color.WHITE);

        groupAlabel.setFont(new Font("Arial", Font.BOLD, 16));
        groupBlabel.setFont(new Font("Arial", Font.BOLD, 16));


        // Set bounds for labels, positioning them above the JComboBoxes
        groupAlabel.setBounds(150, 230, 150, 30);  // Positioned above pokemonA1
        groupBlabel.setBounds(150, 330, 150, 30);  // Positioned above pokemonB1

        add(groupAlabel);
        add(groupBlabel);



        add(createBattleControlPanel());
        initializeBattleArena();
        showBattle = false;
        showAddedPlayer = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image first
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);



        int xOffset1 = 250; // Starting x offset for the first Pokémon image
            for (Image img : addedPokemonImagesForGroupA) {
                g.drawImage(img, xOffset1, 200, 60, 60, this); // Draw each added Pokémon image
                xOffset1 += 70; // Increment offset for next image
            }



            int xOffset2 = 250; // Starting x offset for the first Pokémon image
            for (Image img : addedPokemonImagesForGroupB) {
                g.drawImage(img, xOffset2, 300, 60, 60, this); // Draw each added Pokémon image
                xOffset2 += 70; // Increment offset for next image
            }

        // Set the color for the text
        g.setColor(Color.WHITE);
        // Set the font (optional)
        g.setFont(new Font("SansSerif", Font.BOLD, 12));
        // Draw the text at position (150, 230)


    }

    private JPanel setSwapFreqPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] freqList = {"1","2","3"};
        JComboBox<String> freqPicker = new JComboBox<>(freqList);
        JLabel labelFreq1 = new JLabel("Set the swap frequency to one swap every");
        JButton freqButton = new JButton("Set");
        JLabel labelFreq2 = new JLabel("battle rounds.");

        freqButton.addActionListener(e -> {
            String selectedSwapFreq = (String) freqPicker.getSelectedItem();
            this.groundBattleIdx=0;
            this.initGroupBattle=false;
            if(selectedSwapFreq !="" && selectedSwapFreq !=null){

                this.battleOutputPane.setText( "");
                this.groupBattleManager.setSwapFreq(Integer.valueOf(selectedSwapFreq));
            }
        });
        panel.add(labelFreq1);
        panel.add(freqPicker);
        panel.add(labelFreq2);
        panel.add(freqButton);
        panel.setBounds(10, 10, 500, 50);
        return panel;
    }



    private JPanel createBattleControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        String[] pokeList = getPokeList();
        JLabel groupAlabel = new JLabel("Group A");
        JComboBox<String> pokemonInGroupA = new JComboBox<>(pokeList);

        JLabel groupBlabel = new JLabel("Group B");
        JComboBox<String> pokemonInGroupB = new JComboBox<>(pokeList);

        JButton addButtonForGroupA = new JButton("Add");
        JButton addButtonForGroupB = new JButton("Add");
        JButton grpBattleButton = new JButton("Group Battle");
        JButton backButton = new JButton("Back");
        add(setSeedPanel());



        // Modify this to add the selected Pokémon to a list
        addButtonForGroupA.addActionListener(e -> {
            this.groundBattleIdx=0;
            String selectedPokemon = (String) pokemonInGroupA.getSelectedItem();
            this.initGroupBattle=false;
            this.battleOutputPane.setText( "");
            if (!addedPokemonListForGroupA.contains(selectedPokemon) && !addedPokemonListForGroupB.contains(selectedPokemon)) { // Prevent adding duplicates
                addedPokemonListForGroupA.add(selectedPokemon);
                addedPokemonImagesForGroupA.add(new ImageIcon(pokemonImages.getOrDefault(selectedPokemon, "images/default.png")).getImage());
                showAddedPlayer = true;
                repaint();
            }
        });

        addButtonForGroupB.addActionListener(e -> {

            this.groundBattleIdx=0;
            String selectedPokemon = (String) pokemonInGroupB.getSelectedItem();
            this.initGroupBattle=false;
            this.battleOutputPane.setText( "");
            if (!addedPokemonListForGroupB.contains(selectedPokemon) && !addedPokemonListForGroupA.contains(selectedPokemon)) { // Prevent adding duplicates
                addedPokemonListForGroupB.add(selectedPokemon);
                addedPokemonImagesForGroupB.add(new ImageIcon(pokemonImages.getOrDefault(selectedPokemon, "images/default.png")).getImage());
                showAddedPlayer = true;
                repaint();
            }
        });


        grpBattleButton.addActionListener(e -> {

             if(addedPokemonListForGroupA.size() != addedPokemonListForGroupB.size()){
                 battleOutputPane.setText("Both group must have the same size");
                 return;
             }else{
                 battleOutputPane.setText("");
             }



                showBattle = true;
                if(this.groupBattleManager !=null && !this.initGroupBattle){
                    this.initGroupManager();
                    this.groupBattleManager.setPokemonGroupAName("Group A");
                    this.groupBattleManager.setPokemonGroupAList(addedPokemonListForGroupA.toArray(new String[addedPokemonListForGroupA.size()]));
                    this.groupBattleManager.setPokemonGroupBName("Group B");
                    this.groupBattleManager.setPokemonGroupBList(addedPokemonListForGroupB.toArray(new String[addedPokemonListForGroupB.size()]));
                    this.groupBattleManager.groupBattle();
                    this.initGroupBattle=true;
                }

                if(this.initGroupBattle){
                    this.groupBattleWalkThroughOnPanel();

                    this.groundBattleIdx+=1;


                }

            repaint();
        });

        backButton.addActionListener(e -> {
            showBattle = false;
            showAddedPlayer = false;
            addedPokemonListForGroupA.clear();
            addedPokemonImagesForGroupA.clear();

            addedPokemonListForGroupB.clear();
            addedPokemonImagesForGroupB.clear();

            pokemonImage1 = new ImageIcon("images/Charmander60.png").getImage();
            battleOutputPane.setText("");
            this.groundBattleIdx=0;
            repaint();
            ((GraphicalInterface) frame).showMainMenu();
        });

        panel.add(groupAlabel);
        panel.add(pokemonInGroupA);
        panel.add(addButtonForGroupA);
        panel.add(groupBlabel);
        panel.add(pokemonInGroupB);
        panel.add(addButtonForGroupB);
        panel.add(grpBattleButton);
        panel.add(backButton);
        panel.setBounds(400, 590, 700, 40); // Set the position and size of the control panel
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
        panel.setBounds(800, 10, 200, 40);
        return panel;
    }

    private void groupBattleWalkThroughOnPanel(){

        ArrayList<ArrayList<BattlePairRecord>> groupBattleRecordsList = this.groupBattleManager.getBattlePairRecords();

        if(this.groundBattleIdx>=groupBattleRecordsList.size()){
            this.battleOutputPane.setText( this.groupBattleManager.getFinalMsg());
                    return;
                }


        ArrayList<BattlePairRecord> battlePairRecordList=groupBattleRecordsList.get(this.groundBattleIdx);


                    this.msgForCurrentBattleRound = "";

                    for (BattlePairRecord battle : battlePairRecordList) {

                        this.msgForCurrentBattleRound += battle.getBattleLogInfo();
                        this.msgForCurrentBattleRound += "\n";

                    }

                    this.battleOutputPane.setText( this.msgForCurrentBattleRound);

    }

    //found pokemon in group A

    /*
    private Image addPokemonImage(String pokemonName,String action){
        switch(action){
            case "attacking":
                return new ImageIcon(pokemonImages.getOrDefault(pokemonName + "_attack", "images/default_attack.png")).getImage();

            case "received":
                return new ImageIcon(pokemonImages.getOrDefault(pokemonName + "_hurt", "images/default_hurt.png")).getImage();

            case "defend":
                return new ImageIcon(pokemonImages.getOrDefault(pokemonName + "_defend", "images/default_defend.png")).getImage();

            case "lost":
                return new ImageIcon(pokemonImages.getOrDefault(pokemonName + "_lose", "images/default_lose.png")).getImage();

            case "win":
                return new ImageIcon(pokemonImages.getOrDefault(pokemonName + "_win", "images/default_win.png")).getImage();



        }

        return new ImageIcon(pokemonImages.getOrDefault(pokemonName, "images/default.png")).getImage();

    }
*/
    private void initGroupManager(){
        int seed = SeedManager.getCurrentSeed();
        this.groupBattleManager=new GroupBattleManager(seed);
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
