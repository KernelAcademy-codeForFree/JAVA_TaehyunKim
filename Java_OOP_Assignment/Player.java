package Java_OOP_Assignment;

public class Player {
    private String nickname;
    private int money;
    private int win;
    private int lose;
    private Card[] cards; // 카드 저장
    private RankType rank;
    private int rankValue;
    private int rankKind;

    // 닉네임, 돈, 승, 패 설정
    public Player(String nickname) {
        this.nickname = nickname;
        money = 10000;
        win = 0;
        lose = 0;
    }
    public void setRank(RankType rank) {
        this.rank = rank;
    }

    public RankType getRank() {
        return rank;
    }

    public void setRankValue(int value) {
        rankValue = value;
    }

    public int getRankValue() {
        return rankValue;
    }

    public void setRankKind(int kind) {
        rankKind = kind;
    }

    public int getRankKind() {
        return rankKind;
    }

    public String getNickname() {
        return nickname;
    }

    public void receivedByDealer(Card[] cards) {
        this.cards = cards; 
    }

    public Card[] getCards() {
        return cards;
    }

    public int getMoney() {
        return money;
    }

    public int getWin() {
        return win;
    }

    // 승리시 딜러한테 상금받고 승 + 1
    public void addWin(int prize) {
        money += prize;
        win++;
    }

    // 패배시 패 + 1
    public void addLose() {
        lose++;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj instanceof Player) {
            Player p = (Player)obj;
            return nickname.equals(p.nickname);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nickname.hashCode();
    }
}