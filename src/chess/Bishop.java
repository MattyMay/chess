package chess;

import java.io.Serializable;

class Bishop extends Piece implements Serializable {

    public Bishop(Player player) {
        super(player);
        this.pieceType = "B";
        if (this.player.equals(Player.BLACK)) {
            this.playerStr = "B";
        } else {
            this.playerStr = "W";
        }
        this.image = this.loadImage();
    }

    @Override
    public boolean isValidPath(Piece[][] board, BoardSpace from, BoardSpace to) {
        int rowDif = to.row - from.row;
        int colDif = to.col - from.col;
        int colModif;
        int rowModif;
        if (colDif < 0) {
            colModif = -1;
        } else {
            colModif = 1;
        }
        if (rowDif < 0) {
            rowModif = -1;
        } else {
            rowModif = 1;
        }

        // checks to make sure to space is not occupied by another of player's
        // pieces, and that row and col change, and that path is diagonal
        if (board[to.row][to.col] != null && board[to.row][to.col].player == this.player) {
            return false;
        } else if (rowDif == 0 || colDif == 0) {
            return false;
        } else if (Math.abs(colDif) != Math.abs(rowDif)) {
            return false;
        } else {
            // checks to see if bishop is moving past a piece
            for (int dif = Math.abs(rowDif) - 1; dif != 0; dif = dif - 1) {
                int r = from.row + dif * rowModif;
                int c = from.col + dif * colModif;
                if (board[r][c] != null) {
                    return false;
                }
            }
        }
        return true;
    }
}