package Java_OOP_Assignment;

import java.util.*;

public class Game {
    private Deck deck;
    private Dealer dealer;
    private int min_players = 2;
    private int max_players = 4;
    private int round = 100;

    public Game() {
        this.deck = new Deck();
        this.dealer = new Dealer(deck);
    }

    // 게임 스타트
    public void start() {
        Player[] players = setupPlayers();
        play100Rounds(players);
    }

    // 플레이어 기본 세팅(플레이어 수, 닉네임 입력 받기)
    private Player[] setupPlayers() {
        int playerNum;
        Player[] players = null;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("플레이어 수?(2 ~ 4) >>> ");
            try {
                playerNum = scanner.nextInt();
                scanner.nextLine();
                if (min_players <= playerNum && playerNum <= max_players) {
                    players = new Player[playerNum];
                    for (int i = 0; i < playerNum; i++) {
                        System.out.println((i + 1) + "번 째 플레이어 >>> ");
                        String nickname = scanner.nextLine().trim();
                        if (nickname.isEmpty()) {
                            System.out.println("닉네임이 비어있습니다. 입력해주세요.");
                            i--;
                        } else if (nickname.length() > 20) {
                            System.out.println("닉네임은 20자안으로 입력해주세요.");
                            i--;
                        } else {
                            boolean isEquals = false;
                            for (int j = 0; j < i; j++) {
                                if (players[j].getNickname().equals(nickname)) {
                                    isEquals = true;
                                    break;
                                }
                            }
                            if (isEquals) {
                                System.out.println("이미 있는 닉네임입니다. 다른 닉네임을 입력해주세요.");
                                i--;
                            } else
                                players[i] = new Player(nickname);
                        }
                    }
                    break;
                } else System.out.println("플레이어 수는 " + min_players + "명 ~ " + max_players + "명입니다.");
            } catch (InputMismatchException ie) {
                System.out.println("숫자를 입력해주세요.");
                scanner.nextLine();
            }
        }
        scanner.close();
        return players;
    }

    // 100판 자동 돌리기
    private void play100Rounds(Player[] players) {
        for (int i = 1; i <= round; i++) {
            System.out.println("\n==== " + i + " 판 시작 ====\n");

            Deck deck = new Deck();
            deck.shuffle();
            Dealer dealer = new Dealer(deck);

            for (int j = 0; j < players.length; j++) {
                dealer.dealToPlayer(players[j]);
                Map<String, Object> evaluate = dealer.evaluateRank(players[j]);
                RankType rank = (RankType) evaluate.get("rank");
                int rankValue = (int) evaluate.get("value");
                int rankKind = (int) evaluate.get("kind");
                players[j].setRank(rank);
                players[j].setRankValue(rankValue);
                players[j].setRankKind(rankKind);
            }

            // 매판 플레이어들 카드 출력과 매판 우승자 출력
            for (int j = 0; j < players.length; j++) {
                Player player = players[j];
                System.out.println("[" + player.getNickname() + "]의 카드:");
                Card[] printCards = player.getCards();
                for (int k = 0; k < printCards.length; k++) {
                    System.out.println(printCards[k]);
                }

                System.out.println("Rank: " + player.getRank());
                System.out.println();
            }
            Player winner = dealer.Winner(Arrays.asList(players));
            if (winner != null) {
                System.out.println("우승자: " + winner.getNickname());
                System.out.println("보유 금액: " + winner.getMoney());
                System.out.println("우승자의 Rank: " + winner.getRank());
            }
        }

        List<Player> finalWinners = dealer.finalWinners(Arrays.asList(players));
        List<Player> finalLosers = dealer.finalLosers(Arrays.asList(players));

        // 최종 우승자(들)와 나머지 플레이어 내림차순 출력
        System.out.println("\n==== 최종 우승자 ====\n");
        for (int i = 0; i < finalWinners.size(); i++) {
            Player fW = finalWinners.get(i);
            System.out.println("이름: " + fW.getNickname() + "\t보유 금액: " + fW.getMoney() + "\t승수: " + fW.getWin());
        }
        System.out.println("\n==== 나머지 등수 ====\n");
        int ranking = finalWinners.size() + 1;
        int currentRank = ranking;
        int sameRank = 1;
        int preWin = -1;
        for (int i = 0; i < finalLosers.size(); i++) {
            Player fL = finalLosers.get(i);
            int win = fL.getWin();
            if (i == 0) {
                preWin = win;
            } else {
                if (win == preWin) {
                    sameRank++;
                } else {
                    currentRank += sameRank;
                    sameRank = 1;
                }
                preWin = win;
            }
            System.out.println(currentRank + "등" + "\t이름: " + fL.getNickname() + "\t보유 금액: " + fL.getMoney() + "\t승수: " + fL.getWin());
        }
    }
}