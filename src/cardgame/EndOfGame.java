package cardgame;

import cardgame.graphic.EndGame;

public class EndOfGame extends RuntimeException {


//    public EndOfGame() {
//    }


    public EndOfGame(String msg) {
        new EndGame(msg);
    }
}
