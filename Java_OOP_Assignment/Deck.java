package Java_OOP_Assignment;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cardlist = new ArrayList<>();

    // 덱 구성
    Deck() {
        for (int i = Card.KIND_MAX; i > 0; i--)
            for (int j = 0; j < Card.NUM_MAX; j++)
                cardlist.add(new Card(i, j + 1));
    }   

    // 랜덤 인덱스 카드 뽑기
    public Card pick() {
        int index = (int) (Math.random() * cardlist.size());
        return pick(index);
    }

    // 중복 방지
    public Card pick(int index) {
        Card card = cardlist.get(index);
        cardlist.remove(index);
        return card;
    }

    // 덱 섞기
    public void shuffle() {
        for (int i = 0; i < cardlist.size(); i++) {
            int random = (int) (Math.random() * cardlist.size());

            Card temp = cardlist.get(i);
            cardlist.set(i, cardlist.get(random));
            cardlist.set(random, temp);
        }
    }
}