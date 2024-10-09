import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;

public class WhacAMole {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Whac-A-Mole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[] buttons = new JButton[9];
    ImageIcon stoneIcon;
    ImageIcon moleIcon;

    JButton currMoleTile;
    JButton currStoneTile; 

    Random random = new Random();
    Timer setMoleTimer;
    Timer setStoneTimer;

    int score;

    WhacAMole() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Monospaced", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score : 0");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        frame.add(boardPanel);

        // stoneIcon = new ImageIcon(getClass().getResource("./stone.png"));
        Image stoneImg = new ImageIcon(getClass().getResource("./stone.png")).getImage();
        stoneIcon = new ImageIcon(stoneImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("./Mole.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        score = 0;

        for(int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            buttons[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            // tile.setIcon(stoneIcon);

            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if(tile == currMoleTile) {
                        score += 10;
                        textLabel.setText("Score: " + score);
                    }
                    else if(tile == currStoneTile) {
                        textLabel.setText("Game Over: " + score);
                        setMoleTimer.stop();
                        setStoneTimer.stop();

                        for(int i = 0; i < 9; i++) {
                            buttons[i].setEnabled(false);
                        }
                    }
                }
            
            });
        }
        
        setMoleTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }
                int num = random.nextInt(9);
                JButton tile = buttons[num];
                
                if(currStoneTile == tile) return;

                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });

        setStoneTimer = new Timer(700, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(currStoneTile != null) {
                    currStoneTile.setIcon(null);
                    currStoneTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = buttons[num];

                if(currStoneTile == tile) return;

                currStoneTile = tile;
                currStoneTile.setIcon(stoneIcon);

            }
        });
        
        setStoneTimer.start();
        setMoleTimer.start();
        
        

        frame.setVisible(true);
    }
}
