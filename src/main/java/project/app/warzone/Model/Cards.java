package project.app.warzone.Model;


import java.util.List;

import project.app.warzone.Model.GameEngine;
import project.app.warzone.Model.Player;

public class Cards {

    public String cardType = "";
    public List<Cards> cardsInCollection;



    /**
     * @param cardType  card type
     */
    public Cards(String cardType) {
        this.cardType = cardType;
    }

    
    /** 
     * @return String
     */
    public String getCardType() {
        return cardType;
    }

    
    /** 
     * @param cardType card type
     */
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
