package chess;

import java.io.Serializable;

class Knight extends Piece implements Serializable {

    public Knight(Player player) {
        super(player);
        this.pieceType = "Kn";
        if (this.player.equals(Player.BLACK)) {
            this.playerStr = "B";
        } else {
            this.playerStr = "W";
        }
        this.image = this.loadImage();
    }
    

    @Override
    public boolean isValidPath(Piece[][] board, BoardSpace from, BoardSpace to) {

        if (board[to.row][to.col] != null && board[to.row][to.col].player == this.player) {
            return false;
        } else if (to.row == from.row + 1 && to.col == from.col + 2) {
            return true;
        } else if (to.row == from.row + 2 && to.col == from.col + 1) {
            return true;
        } else if (to.row == from.row + 2 && to.col == from.col - 1) {
            return true;
        } else if (to.row == from.row + 1 && to.col == from.col - 2) {
            return true;
        } else if (to.row == from.row - 1 && to.col == from.col - 2) {
            return true;
        } else if (to.row == from.row - 2 && to.col == from.col - 1) {
            return true;
        } else if (to.row == from.row - 2 && to.col == from.col + 1) {
            return true;
        } else if (to.row == from.row - 1 && to.col == from.col + 2) {
            return true;
        } else
            return false;
    }

}