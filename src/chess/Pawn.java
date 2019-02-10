package chess;

import java.io.Serializable;

class Pawn extends Piece implements Serializable {
    boolean passantFlag = false;
    
    public Pawn(Player player) {
        super(player);
        this.pieceType = "P";
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
        int modif;
        int startRow;
        Pawn passantPawn = null;
        boolean canPassant = false;
        
        Piece target = board[to.row][to.col];
              
        if (this.player == Player.BLACK) {
            modif = 1;
            startRow = 1;
        } else {
            modif = -1;
            startRow = 6;
        }
        
        
        
        if (board[to.row - 1 * modif][to.col] != null   // is there a passantable pawn "behind" to space?
                && board[to.row - 1 * modif][to.col] instanceof Pawn) {
            passantPawn = (Pawn)board[to.row - 1 * modif][to.col];
            canPassant = passantPawn.passantFlag;
        }
        
        if (board[to.row][to.col] != null && board[to.row][to.col].player == this.player) {
            return false;
        } else if (rowDif == 0) {
            return false;
        } else if ((rowDif) * modif < 1) { //checks to make sure pawn moves forward
            return false;
        } else if (Math.abs((float)colDif) > 1 ) {   //checks to make sure to is on or adjacent to from column
            return false;
        } else if (from.row == startRow && Math.abs(rowDif) > 2)  {  //checks if pawn moved more than 2 rows from start row
            return false;
        } else if (rowDif == (2 * modif) && board[to.row - 1 * modif][to.col] != null) { //checks if pawn is moving past a piece
            return false;
        } else if (from.row != startRow && Math.abs(rowDif) > 1) {  // checks if pawn moved more than 1 row from from row
            return false;
        } else if (colDif == 0 && target != null) { //checks to see if to space is occupied and on same column as from
            return false;
        } else if (!canPassant && target == null && colDif != 0) { //checks if path is diagonal, to space is null, and !canPassant
            return false;
        } else {
            return true;
        }
    }
}