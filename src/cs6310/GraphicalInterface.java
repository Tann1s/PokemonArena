package cs6310;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class GraphicalInterface extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public GraphicalInterface() {
        initializeUI();
        setResizable(false);
        pack();
        setSize(new Dimension(1200, 700));
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GraphicalInterface().setVisible(true));
    }

    private void initializeUI() {
        setTitle("Pokemon Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        getContentPane().add(cardPanel, BorderLayout.CENTER);

        // Create panels
        JPanel mainMenu = createMainMenuPanel();
        cardPanel.add(mainMenu, "Main Menu");


        GamePanel battlePanel = new GamePanel(this);
        cardPanel.add(battlePanel, "Battle Panel");

        GamePanelForTournament tournamentPanel = new GamePanelForTournament(this);
        cardPanel.add(tournamentPanel, "Tournament Panel");

        GamePanelForTeamBattle teamBattlePanel = new GamePanelForTeamBattle(this); // Assuming similar constructor requirements
        cardPanel.add(teamBattlePanel, "Team Battle Panel");

        GamePanelForGroupBattle groupBattlePanel = new GamePanelForGroupBattle(this); // Assuming similar constructor requirements
        cardPanel.add(groupBattlePanel, "Group Battle Panel");

        DisplayInfoPanel displayInfoPanel = new DisplayInfoPanel(this); // Assuming similar constructor requirements
        cardPanel.add(displayInfoPanel, "Display Info Panel");

        pack();
        setVisible(true);
    }



    private JPanel createMainMenuPanel() {
        ImageIcon backgroundIcon = new ImageIcon("images/city.jpeg");
        JLabel background = new JLabel(backgroundIcon);
        background.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.insets = new Insets(2, 50, 2, 50);  // Further reduced padding

        JButton newGameButton = createButton("Battle", "Battle Panel");
        background.add(newGameButton, constraints);

        JButton tournamentButton = createButton("Tournament", "Tournament Panel");
        background.add(tournamentButton, constraints);

        JButton gameBattleButton = createButton("Team Battle", "Team Battle Panel");
        background.add(gameBattleButton, constraints);

        JButton groupBattleButton = createButton("Group Battle", "Group Battle Panel");
        background.add(groupBattleButton, constraints);

        JButton displayInfoButton = createButton("Display Info", "Display Info Panel");
        background.add(displayInfoButton,constraints);

        JButton exitButton = createButton("Exit", null);
        exitButton.addActionListener(e -> dispose());
        background.add(exitButton, constraints);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(background, BorderLayout.CENTER);
        return panel;
    }

    public void showMainMenu() {
        cardLayout.show(cardPanel, "Main Menu");
    }

    private JButton createButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Serif", Font.BOLD, 24));
        button.setBorder(new RoundedBorder(10, 2, new Color(0, 0, 255, 100)));

        button.setPreferredSize(new Dimension(150, 40));

        if (cardName != null) {
            button.addActionListener(e -> cardLayout.show(cardPanel, cardName));
        }

        return button;
    }

    public class RoundedBorder implements Border {
        private int radius;
        private int thickness;
        private Color color;

        RoundedBorder(int radius, int thickness, Color color) {
            this.radius = radius;
            this.thickness = thickness;
            this.color = color;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness, thickness, thickness, thickness);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));

            int adjust = thickness / 2;
            g2d.drawRoundRect(x + adjust, y + adjust, width - thickness - 1, height - thickness - 1, radius, radius);
        }
    }
}
