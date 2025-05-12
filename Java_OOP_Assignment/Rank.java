package Java_OOP_Assignment;

import java.util.HashMap;
import java.util.Map;

enum RankType {
    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIRS(3),
    TRIPLE(4),
    STRAIGHT(5),
    FLUSH(6),
    FULL_HOUSE(7),
    FOUR_CARDS(8),
    STRAIGHT_FLUSH(9);

    private final int number;

    RankType(final int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}

public class Rank {

    public static Map<String, Object> evaluate(Player player) {
        Card[] cards = player.getCards();
        int[] countValue = new int[14];
        int[] countKind = new int[5];

        for (int i = 0; i < cards.length; i++) {
            int value = cards[i].getNumber();
            int kind = cards[i].getKind();
            countValue[value]++;
            countKind[kind]++;
        }

        boolean pair = false;
        boolean two = false;
        boolean three = false;
        boolean four = false;
        boolean flush = false;
        boolean straight = false;
        int pairValue = 0;
        int twoValue = 0;
        int threeValue = 0;
        int fourValue = 0;
        int straightCount = 0;

        // straight
        for (int i = 1; i < countValue.length; i++) {
            if (countValue[i] == 1) {
                straightCount++;
                if (straightCount == 5) {
                    straight = true;
                    break;
                }
            } else {
                straightCount = 0;
            }
        }

        // (straight) a, 2, 3, 4, 5 체크
        if (countValue[13] == 1 && countValue[1] == 1 && countValue[2] == 1 && countValue[3] == 1 && countValue[4] == 1) {
            straight = true;
        }

        for (int i = 0; i < countValue.length; i++) {
            if (countValue[i] == 4) {
                four = true;
                fourValue = i;
            } else if (countValue[i] == 3) {
                three = true;
                threeValue = i;
            } else if (countValue[i] == 2)
                if (pair) {
                    two = true;
                    twoValue = i;
                } else {
                    pair = true;
                    pairValue = i;
                }
        }

        // flush는 kind 체킹 필요
        for (int i = 0; i < countKind.length; i++) {
            if (countKind[i] == 5) {
                flush = true;
                break;
            }
        }

        RankType rank;
        int rankValue;
        int rankKind;

        // 플레이어 rank 결정 시 value와 kind 저장
        if (straight && flush) {
            rank = RankType.STRAIGHT_FLUSH;
            rankValue = getHighestValue(cards);
            rankKind = getHighestKind(cards);
        }
        else if (flush) {
            rank = RankType.FLUSH;
            rankValue = getHighestValue(cards);
            rankKind = getHighestKind(cards);
        } else if (straight) {
            rank = RankType.STRAIGHT;
            rankValue = getHighestValue(cards);
            rankKind = getHighestKind(cards);
        } else if (four) {
            rank = RankType.FOUR_CARDS;
            rankValue = fourValue;
            rankKind = getHighestKind(cards);
        } else if (three && pair) {
            rank = RankType.FULL_HOUSE;
            rankValue = threeValue;
            rankKind = getHighestKind(cards);
        } else if (three) {
            rank = RankType.TRIPLE;
           rankValue = threeValue;
            rankKind = getHighestKind(cards);
        } else if (two) {
            rank = RankType.TWO_PAIRS;
            rankValue = Math.max(pairValue, twoValue);
            rankKind = getHighestKind(cards);
        } else if (pair) {
            rank = RankType.ONE_PAIR;
            rankValue = pairValue;
            rankKind = getHighestKind(cards);
        } else {
            rank = RankType.HIGH_CARD;
            Card highestCard = getHighestCard(cards);
            rankValue = highestCard.getNumber();
            rankKind = highestCard.getKind();
        }

        // rank, rankValue, rankKind 반환(동일 rank시 필요)
        Map<String, Object> evaluate = new HashMap<>();
        evaluate.put("rank", rank);
        evaluate.put("value", rankValue);
        evaluate.put("kind", rankKind);
        return evaluate;
    }

    // rankValue중 높은 수
    private static int getHighestValue(Card[] cards) {
        int highestValue = 0;
        for(int i = 0; i < cards.length; i++) {
            int value = cards[i].getNumber();
            if(value > highestValue) {
                highestValue = value;
            }
        }
        return highestValue;
    }

    // rankKind중 높은 문양
    private static int getHighestKind(Card[] cards) {
        int highestKind = 0;
        for(int i = 0; i < cards.length; i++) {
            int kind = cards[i].getKind();
            if(kind > highestKind) {
                highestKind = kind;
            }
        }
        return highestKind;
    }
    
    // High_Card일 때 가장 큰 수의 모양을 위한 메서드
    private static Card getHighestCard(Card[] cards) {
        Card highestCard = cards[0];
        for(int i = 1; i < cards.length; i++) {
            if(cards[i].getNumber() > highestCard.getNumber()) {
                highestCard = cards[i];
            } else if(cards[i].getNumber() == highestCard.getNumber()) {
                if(cards[i].getKind() > highestCard.getKind()) {
                    highestCard = cards[i];
                }
            }
        }
        return highestCard;
    }
}
