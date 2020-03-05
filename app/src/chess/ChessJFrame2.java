package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChessJFrame2 extends JFrame {
    Image offScreenImage = null;
    String ipInfo;
    String dialogue;
    
    MyBoardPanel boardPanel;
    JPanel infoPanel;
    JTextField ipInfoField;
    JTextField dialogueField;
    JButton resignButton;
    
    ChessJFrame2() {
        this.setSize(700, 800);
        this.setResizable(false);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.boardPanel = new MyBoardPanel();
        this.infoPanel = new JPanel();
        this.ipInfoField = new JTextField();
        this.dialogueField = new JTextField();
        this.resignButton = new JButton("Resign");
        
        this.infoPanel.add(this.ipInfoField);
        this.infoPanel.add(this.dialogueField);
        this.infoPanel.add(this.resignButton);
        
        this.getContentPane().add(this.infoPanel);
        this.getContentPane().add(this.boardPanel);
        this.setVisible(true);
        
        Insets insets = this.getInsets();
        int left = insets.left;
        int right = insets.right;
        int top = insets.top;
        int bottom = insets.bottom;
        int boardWidth = this.getWidth() - left - right;
        int boardHeight = this.getHeight() - top - bottom;
        int square_width = boardWidth / 8;
        int boarderRemainder = boardWidth % 8;
        int boardTop = boardHeight - 8 * square_width;
        
        this.boardPanel.setSize(boardWidth, boardHeight);
        this.boardPanel.setLocation(boarderRemainder / 2, boardTop );
        this.boardPanel.setBackground(Color.GRAY);
        
        this.infoPanel.setSize(boardWidth, boardTop);
        this.infoPanel.setLocation(0, 0);
        this.infoPanel.setBackground(Color.BLACK);
        
        
    }
    
//    public void paint(Graphics screen) {
//        super.paint(screen);
//        
//        Insets insets = this.getInsets();
//        int left = insets.left;
//        int right = insets.right;
//        int top = insets.top;
//        int bottom = insets.bottom;
//        int imageWidth = this.getWidth() - left - right;
//        int imageHeight = this.getHeight() - top - bottom;
//        int square_width = imageWidth / 8;
//        int boarderRemainder = imageWidth % 8;
//        
//        this.offScreenImage = createImage(imageWidth, imageHeight);
//        Graphics g = this.offScreenImage.getGraphics();
//
//        
//        int boardTop = imageHeight - 8 * square_width;
//        
//        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, imageWidth, imageHeight);
//        
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                int x = (boarderRemainder / 2) + j * square_width;
//                int y = boardTop + i * square_width;
//                if ((i + j) % 2 == 1) {
//                    g.setColor(Color.darkGray);
//                    g.fillRect(x, y, square_width, square_width);
//                } else {
//                    g.setColor(Color.gray);
//                    g.fillRect(x, y, square_width, square_width);
//                }
//            }
//        }
//        
//        
//        screen.drawImage(this.offScreenImage, insets.left, insets.top, this);
//        
//    }
    public static void main(String[] args) {
        ChessJFrame2 c = new ChessJFrame2();
    }
}
