package chess;

import java.io.Serializable;

public class MyGameOutput implements Serializable{
    MyGame myGame = null;
    boolean isClientConnected;
    String hostIPinfo;
    int clientIndex;
    
    public MyGameOutput(MyGame myGame, boolean isClientConnected, String hostIPinfo, int clientIndex) {
        this.myGame = myGame;
        this.isClientConnected = isClientConnected;
        this.hostIPinfo = hostIPinfo;
        this.clientIndex = clientIndex;
    }
}
