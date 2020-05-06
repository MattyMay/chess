package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

import javax.swing.JPanel;

public class MyBoardPanel extends JPanel implements MouseListener, MouseMotionListener {
    int width = this.getWidth();
    int height = this.getHeight();
    int squareLength = width / 8;
    boolean movingPiece = false;

    MyUserInterface myUserInterface;

    public void setMyUserInterface(MyUserInterface myUserInterface) {
        this.myUserInterface = myUserInterface;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {

        this.width = this.getWidth();
        this.height = this.getHeight();
        this.squareLength = width / 8;
        super.paintComponent(g);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int x = j * this.squareLength;
                int y = i * this.squareLength;
                int square_width = this.squareLength;
                int square_height = this.squareLength;
                if ((i + j) % 2 == 1) {
                    g.setColor(Color.gray);
                    g.fillRect(x, y, square_width, square_height);
                } else {
                    g.setColor(Color.lightGray);
                    g.fillRect(x, y, square_width, square_height);
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isActivePlayer()) {
            int col = e.getX() / this.squareLength;
            int row = e.getY() / this.squareLength;
            this.myUserInterface.myGameInput.setCmd(1);
            this.myUserInterface.myGameInput.setFrom(row, col);

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isActivePlayer()) {
            int col = e.getX() / this.squareLength;
            int row = e.getY() / this.squareLength;
            this.myUserInterface.myGameInput.setTo(row, col);
            this.myUserInterface.myGamePlayer.sendMessage(this.myUserInterface.myGameInput);
        }
    }

    public boolean isActivePlayer() {
        if (this.myUserInterface.clientIndex == 0 &&
                this.myUserInterface.activePlayer.equals("White")) {
            return true;
        } else if (this.myUserInterface.clientIndex == 1 &&
                this.myUserInterface.activePlayer.equals("Black")) {
            return true;
        } else return false;
    }

}
