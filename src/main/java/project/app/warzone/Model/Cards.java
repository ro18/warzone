package project.app.warzone.Model;


import java.util.List;

import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Player;

public class Cards {

    public String cardType = "";
    public List<Cards> cardsInCollection;



    public Cards(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        cardType = cardType.toUpperCase();

        if (cardType.equals("BOMB") || cardType.equals("REINFORCEMENT") || cardType.equals("BLOCKADE") || cardType.equals("AIRLIFT") || cardType.equals("NEGOTIATE")) {
            this.cardType = cardType;
        } else {
            System.out.println("Invalid card type");
        }
    }

}
