package Java_OOP_Assignment;

public class Card {
    public static final int KIND_MAX = 4;
    public static final int NUM_MAX = 13;

    private int kind;
    private int number;

    Card(int kind, int number) {
        this.kind = kind;
        this.number = number;
    }

    public String toString() {
        String[] kinds = {"", "CLOVER", "HEART", "DIAMOND", "SPADE"};
        String[] numbers = {"", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        return "kind : " + kinds[this.kind] + ", number : " + numbers[this.number];
    }

    public int getNumber() {
        return number;
    }

    public int getKind() {
        return kind;
    }
}