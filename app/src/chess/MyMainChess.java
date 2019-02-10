package chess;
import java.io.*;
import java.util.*;


import gameNet.*;



// ***************************************************
public class MyMainChess extends GameCreator implements Serializable{   
 
  public GameNet_CoreGame createGame()
  {
      return new MyGame();
  }
  


  public static void main(String[] args) throws IOException 
  {   
    MyMainChess myMain = new MyMainChess();
    GameNet_UserInterface myUserInterface = new MyUserInterface();
    
    
    myMain.enterGame( myUserInterface);
  }// end of main
}// end of class
