
import java.util.Stack;

public class calculator {

    public static Stack<String> num = new Stack<String>();
    public static Stack<String> cal = new Stack<String>();
    public static int index = 0;

    public static void stringSplitCal(String str) {
        String[] arr = str.split(" ");
        num.clear();
        cal.clear();

        for (index = 0 ; index < arr.length ; index++) {
            String a = arr[index];
            if (a.matches("[0-9]*")) {
                num.add(a);
                if (cal.contains("*") || cal.contains("/") || cal.contains("%")) {
                    // 遇到* / 先計算
                    calMD(num, cal);
                }
            } else {
                if(a.equals("(")) {
                    // 呼叫函數
                    num.add(Double.toString(calInclude(arr)));
                    // 如果算完括號裡面出來index超過的話直接跳出
                    if (index >= arr.length) {
                        break;
                    }

                    if (cal.contains("*") || cal.contains("/") || cal.contains("%")) {
                        System.out.println("近來");
                        calMD(num, cal);
                    }
                    a = arr[index];
                    cal.add(a);
                    
                }else { 
                    cal.add(a);
                }
            }
            System.out.println(num);
            System.out.println(cal);
        }


        checkMD(num, cal);
        
        System.out.println(calAll(num, cal));
        
    }
    
    private static void checkMD(Stack<String> Tnum, Stack<String> Tcal) {
        if (Tcal.contains("*") || Tcal.contains("/") || Tcal.contains("%")) {
            System.out.println("近來");
            calMD(Tnum, Tcal);
        }
    }

    // 遇到乘除先計算
    private static void calMD(Stack<String> Tnum, Stack<String> Tcal) {
        double num2 = Double.parseDouble(Tnum.pop());
        double num1 = Double.parseDouble(Tnum.pop());
        double Ans;

        char c = Tcal.pop().charAt(0);

        if (c == '*') {
            Ans = num1 * num2;
        } else if (c == '/') {
            Ans = num1 / num2;
        } else {
            Ans = num1 % num2;
        }

        Tnum.add(Double.toString(Ans));
    }

    // 遇到前括號近來此函數做處理(把括號裡的運算做完)
    private static double calInclude(String[] arr) {
        Stack<String> num1 = new Stack<String>();
        Stack<String> cal1 = new Stack<String>();
        for (index = index + 1 ; index < arr.length ; index++) {
            String a = arr[index];
            if (a.matches("[0-9]*")) {
                num1.add(a);
                checkMD(num1, cal1);
            } else {
                if(a.equals("(")) {
                    // 呼叫函數
                    num1.add(Double.toString(calInclude(arr)));

                    
                    if (index <= arr.length - 1) {
                        a = arr[index];
                        if (a.equals(")")) { 
                            break;
                        }
                        cal1.add(a);
                        continue;   
                    }
    
                    checkMD(num1, cal1);

                }else if (a.equals(")")) {
                    break;
                }else {
                    cal1.add(a);
                }
            }
            System.out.println("include!!");
            System.out.println(num1);
            System.out.println(cal1);
        }
        // 跳出右括號
        index++;
        checkMD(num1, cal1);
        return calAll(num1, cal1);
    }

    // 最後只會剩下減和加而已(最後加減)
    private static double calAll(Stack<String> Tnum, Stack<String> Tcal) {
        try {
            while (true) {
                System.out.println(Tnum);
                System.out.println(Tcal);
                if (Tnum.size() == 1) {
                    break;
                }

                double Ans;
                double num1 = Double.parseDouble(Tnum.remove(0));
                double num2 = Double.parseDouble(Tnum.remove(0));
                char c = Tcal.remove(0).charAt(0);
        
                if (c == '+') {
                    Ans = num1 + num2;
                } else {
                    Ans = num1 - num2;
                } 

                Tnum.add(0, Double.toString(Ans));
            }
            return Double.parseDouble(Tnum.pop());
        } catch (Exception ex){
            System.out.println("無值跳出");
            return Double.parseDouble(Tnum.pop());
        }

    }
}