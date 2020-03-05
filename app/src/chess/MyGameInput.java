package chess;

import java.io.Serializable;

public class MyGameInput implements Serializable {
    static final int CONNECTING = 0;
    static final int MOVE_PIECE = 1;
    static final int RESIGN = 2;
    
    String sendersName;
    int cmd = CONNECTING;
    BoardSpace from = null;
    BoardSpace to = null;
    
    public void setFrom(int row, int col) {
        this.from = new BoardSpace(row, col);
        if (this.from == null) {
            this.from = new BoardSpace(-1, -1);
        }
    }
    
    public void setTo(int row, int col) {
        this.to = new BoardSpace(row, col);
        if (this.to == null) {
            this.to = new BoardSpace(-1, -1);
        }
    }
    
    public void setCmd(int cmd) {
        if (cmd < 0 || cmd > 2)
            return;
        this.cmd = cmd;
        return;
    }

    public void setName(String playerName) {
        this.sendersName = playerName;
        
    }
    

}
