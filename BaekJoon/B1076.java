import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class B1076 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> colorlist = new ArrayList<>(Arrays.asList(
                "black", "brown", "red", "orange", "yellow", "green",
                "blue",  "violet", "grey", "white"));

        String first = scanner.next();
        String second = scanner.next();
        String third = scanner.next();

        int fi = colorlist.indexOf(first);
        int se = colorlist.indexOf(second);
        long th = colorlist.indexOf(third);
        th = (long)Math.pow(10, th);
        long result = (fi * 10 + se) * th;
        System.out.println(result);
    }
}