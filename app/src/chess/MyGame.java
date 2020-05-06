package chess;

import java.io.Serializable;
import java.util.ArrayList;

import gameNet.GameNet_CoreGame;

public class MyGame extends GameNet_CoreGame implements Serializable {
    ChessGame chessGame = new ChessGame();
    boolean isClientConnected;
    String hostIPinfo;
   
    private ArrayList<String> clients = new ArrayList<String>();
    
    @Override
    public Object process(Object inputObj) {
        MyGameInput myGameInput = (MyGameInput)inputObj;
        switch (myGameInput.cmd) {
        case MyGameInput.MOVE_PIECE:          
            chessGame.movePiece(myGameInput.from, myGameInput.to);
            break;
        case MyGameInput.RESIGN:
            chessGame.resign();
            break;
        case MyGameInput.CONNECTING:
            if (clients.size() == 0) {
                clients.add(myGameInput.sendersName);
                hostIPinfo = "You are host. Waiting for client to connect...";
                isClientConnected = false;
            } else if (clients.size() == 1) {
                clients.add(myGameInput.sendersName);
                hostIPinfo = clients.get(0) + " is host. " + clients.get(1) + " is client.";
                isClientConnected = true;
            }
            break;    
        }
        return new MyGameOutput(this, this.isClientConnected, this.hostIPinfo, clients.indexOf(myGameInput.sendersName));
    }

}
