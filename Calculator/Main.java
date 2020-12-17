
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str;
        while(in.hasNext()){
            str = in.nextLine();
            calculator.stringSplitCal(str);
        }
    }
}

