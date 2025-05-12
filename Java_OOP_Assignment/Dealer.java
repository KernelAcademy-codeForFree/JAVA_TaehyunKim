package Java_OOP_Assignment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Dealer {
    private static final int HAND_SET = 5;
    private static final int PRIZE = 100;
    Deck deck;

    Dealer(Deck deck) {
        this.deck = deck;
        deck.shuffle();
    }

    private Card[] pickFive() {
        Card[] handSet = new Card[HAND_SET];
        for (int i = 0; i < HAND_SET; i++) {
            handSet[i] = deck.pick();
        }
        return handSet;
    }

    public void dealToPlayer(Player player) {
        Card[] cards = pickFive();             // 5개 뽑기
        player.receivedByDealer(cards);        // 카드 뿌림
    }

    Map<String, Object> evaluateRank(Player player) {
        return Rank.evaluate(player); // 랭크 판단
    }

    public Player Winner(List<Player> players) {
        Player winner = null;
        RankType bestRank = null;
        int bestRankValue = -1;
        int bestRankKind = -1;

        // Rank클래스에서 저장한 rank결정 시 높은 수와 높은 모양 받아옴
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            RankType playerRank = player.getRank();
            int rankValue = player.getRankValue();
            int rankKind = player.getRankKind();

            // 위에서 받아온 값으로 Rank가 같을 때 숫자 비교, 숫자가 같을 때 모양 비교
            if (winner == null) {
                winner = player;
                bestRank = playerRank;
                bestRankValue = rankValue;
                bestRankKind = rankKind;
            } else {
                if (playerRank.getNumber() > bestRank.getNumber()) {
                    winner = player;
                    bestRank = playerRank;
                    bestRankValue = rankValue;
                    bestRankKind = rankKind;
                } else if (playerRank.getNumber() == bestRank.getNumber()) {
                    if(rankValue > bestRankValue) {
                        winner = player;
                        bestRankValue = rankValue;
                        bestRankKind = rankKind;
                    } else if(rankValue == bestRankValue) {
                        if(rankKind > bestRankKind) {
                            winner = player;
                            bestRankKind = rankKind;
                        }
                    }
                }
            }
        }

        // 승자에게 상금 전달 및 패자 결정
        if (winner != null) {
            winner.addWin(PRIZE);

            for (int i = 0; i < players.size(); i++) {
                Player loser = players.get(i);
                if (loser != winner) {
                    loser.addLose();
                }
            }
        }
        return winner;
    }

    // 최종 우승자(동점자 고려)
    public List<Player> finalWinners(List<Player> players) {
        List<Player> finalWinners = new ArrayList<>();
        int finalWin = 0;

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (player.getWin() > finalWin) {
                finalWin = player.getWin();
                finalWinners.clear();
                finalWinners.add(player);
            } else if (player.getWin() == finalWin) {
                finalWinners.add(player);
            }
        }
        return finalWinners;
    }

    // 최종 우승자 외 나머지
    public List<Player> finalLosers(List<Player> players) {
        List<Player> finalWinners = finalWinners(players);
        List<Player> finalLosers = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (!finalWinners.contains(player)) {
                finalLosers.add(player);
            }
        }
        // 나머지 사람들 내림차순 정렬
        finalLosers.sort(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o2.getWin() - o1.getWin();
            }
        });
        return finalLosers;
    }
}