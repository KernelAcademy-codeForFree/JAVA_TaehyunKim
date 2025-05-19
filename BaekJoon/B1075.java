import java.util.Scanner;

public class B1075 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int f = scanner.nextInt();
        int i = (n / 100) * 100;
        int a = 0;

        while(i % f != 0) {
            i++;
            a++;
        }
        System.out.printf("%02d", a);
    }
}