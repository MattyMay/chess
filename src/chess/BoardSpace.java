package chess;

import java.io.Serializable;

/* Mostly just used for inputing moves in a logical manner */
public class BoardSpace implements Serializable {

    // int row;
    // char col;
    int row;
    int col;
    // char[] validChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    public BoardSpace(int row, int column) {
        // this.col = column;
        // this.row = row;
        // this.rowIndex =
        // for (int i = 0; i < 8; i++) {
        // if (validChars[i] == column) {
        // this.colIndex = i;
        // }
        // }
        this.col = column;
        this.row = row;

    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BoardSpace))
            return false;
        BoardSpace bs = (BoardSpace) o;
        return (this.row == bs.row && this.col == bs.col);
    }
}
