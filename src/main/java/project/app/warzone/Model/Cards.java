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

        if (isValidCardType(cardType)) {
            this.cardType = cardType;
        } else {
            System.out.println("Invalid card type");
        }
    }
    public boolean isValidCardType(String cardType) {
        return List.of("BOMB", "REINFORCEMENT", "BLOCKADE", "AIRLIFT", "NEGOTIATE").contains(cardType);
    }

}
