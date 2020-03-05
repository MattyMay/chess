package chess;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Piece implements Serializable {
    private long uniqueID;
    String pieceType;
    String playerStr;
    Player player;
    boolean hasMoved;
    // q for queen side and k for king side. only for rooks.

    // constructor for all pieces which sets the
    // color.
    public Piece(Player player) {
        this.player = player;
        this.uniqueID = Counter.INSTANCE.generate();
    }

    public ArrayList<BoardSpace> getAttackingSpaces(Piece[][] board) {
        ArrayList<BoardSpace> attackingSpaces = new ArrayList<BoardSpace>();
        BoardSpace location = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                BoardSpace bs = new BoardSpace(i, j);
                if (board[bs.row][bs.col] != null && board[bs.row][bs.col].equals(this)) {
                    location = new BoardSpace(i, j);
                    break;
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                BoardSpace to = new BoardSpace(i, j);
                try {
                    if (this.isValidPath(board, location, to)) {
                        attackingSpaces.add(to);
                    }
                } catch (IndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        return attackingSpaces;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Piece))
            return false;
        Piece piece = (Piece) o;
        return (this.uniqueID == piece.uniqueID);
    }

    public String toString() {
        return this.pieceType + "." + this.playerStr;

    }

    // *** THIS METHOD MUST FULLY DETERMINE LEGALITY OF PATH.
    // THIS INCLUDES BUT IS NOT LIMITED TO: GENERAL PATHING RULES,
    // CASTELING RULES (MINUS PERMANENT CASTELING BANS), AU PASSANT
    // LEGALITY, ETC.
    // DOES NOT NEED TO CHECK FOR CHECK***//
    protected abstract boolean isValidPath(Piece[][] board, BoardSpace from, BoardSpace to)
            throws IndexOutOfBoundsException;
}
