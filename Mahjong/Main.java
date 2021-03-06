import java.util.ArrayList;
import java.util.Arrays;

enum MahjongId {
    /* a:萬 b:筒 c:條 */
    one("a1", "b1", "c1"),
    two("a2", "b2", "c2"),
    three("a3", "b3", "c3"),
    four("a4", "b4", "c4"),
    five("a5", "b5", "c5"),
    six("a6", "b6", "c6"),
    seven("a7", "b7", "c7"),
    eight("a8", "b8", "c8"),
    nine("a9", "b9", "c9");

    public String a;
    public String b;
    public String c;

    MahjongId(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

}

/* a:萬 b:筒 c:條 e:東 s:南 w:西 n:北 x:中 y:發 z:白*/

public class Main {
    public static void main (String[] args) {
        String[] card = {"a1", "a2", "a3", "a4", "a5", "a6", "b7", 
        "b7", "b7", "b8", "b8", "b9", "b9", "b9", "c4", "c5", "c6"};

        System.out.println(cal(card));

        String[] card1 = {"a2", "a3", "a4", "a4", "a5", "a5", "a5", 
        "a6", "c2", "c3", "c3", "c4", "c4", "c5", "c5", "c6", "c7"};

        System.out.println("card1: " + cal(card1));

        String[] card2 = {"a2", "a2", "a2", "a3", "a4", "a4", "a5", 
        "a6", "a6", "a6", "a6", "a7", "a8", "a9", "c3", "c4", "c5"};

        System.out.println("card2: " + cal(card2));

        String[] card3 = {"a1", "a2", "a3", "c3", "c2", "c3", "c3", 
        "c4", "c5", "c5", "c5", "c5", "c6", "c7", "c7", "c8", "c9"};

        System.out.println("card3: " + cal(card3));

        String[] card4 = {"a2", "a3", "a4", "b1", "b2", "b3", "b7", 
        "b8", "b9", "c1", "c1", "c3", "c4", "c5", "c7", "c8", "c9"};

        System.out.println("card4: " + cal(card4));

        String[] card5 = {"a1", "a2", "a3", "a4", "a5", "a6", "a7", 
        "a8", "a9", "c4", "c5", "c6", "x", "x", "x", "y", "y"};

        System.out.println("card5: " + cal(card5));

        String[] card6 = {"a2", "a3", "a3", "a3", "a4", "a4", "a4", 
        "a5", "a5", "a6", "a6", "b2", "b3", "b4", "b7", "b8", "b9"};

        System.out.println("card6: " + cal(card6));

        String[] card7 = {"a1", "a2", "a2", "a3", "a3", "a4", "a4", 
        "a5", "a6", "x", "x", "x", "y", "y", "e", "e", "e"};

        System.out.println("card7: " + cal(card7));

        String[] card8 = {"a1", "a2", "a3", "a4", "a5", "a6", "a7", 
        "a8", "a9", "c2", "c3", "c4", "c5", "c6", "c7", "c9", "c9"};

        System.out.println("card8: " + cal(card8));

        String[] card9 = {"a1", "a1", "a2", "a2", "a3", "a3", "a3", 
        "a3", "a4", "a4", "a5", "a5", "a6", "a6", "a6", "a7", "a8"};

        System.out.println("card9: " + cal(card9));


        String[] card10 = {"a1", "a1", "a1", "a1", "a2", "a2", "a2",
         "a2", "a5", "a5", "a5", "a5", "a7", "a7", "a7", "a3", "a3"};

         System.out.println("card10: " + cal(card10));

         String[] card11 = {"b4", "b4", "b4", "b5" , "b5"};

          System.out.println("card11: " + cal(card11));
    }
    
    public static String cal(String[] card) {
        
        ArrayList<String> a = new ArrayList<String>();
        ArrayList<String> b = new ArrayList<String>();
        ArrayList<String> c = new ArrayList<String>();
        ArrayList<String> word = new ArrayList<String>();

        for (String count : card) {
            /* 把牌堆加入各花牌堆裡 */
            if (count.charAt(0) == 'a') {
                a.add(count);
            } else if(count.charAt(0) == 'b') {
                b.add(count);
            } else if(count.charAt(0) == 'c') {
                c.add(count);
            } else {
                word.add(count);
            }

        }
        
        // 計算萬
        boolean wina = stackCal(a);
        // 計算條
        boolean winb = stackCal(b);
        // 計算筒
        boolean winc = stackCal(c);
        // 計算字
        boolean winWord = checkWord(word);
        
        // System.out.println(wina + " " + winb + " "  + winc + " " + winWord);
        if (wina && winb && winc && winWord) {
            return "你胡牌了!!";
        } else {
            return "無";
        }
    }


    public static boolean stackCal(ArrayList<String> arr) {

        // for (String str : arr) {
        //     System.out.print(str + " ");
        // }
        // System.out.println();
        /* 一四七、二五八、三六九牌堆預設為0 */
        int stack[] = new int[3];
        boolean win = true;
        boolean cg = false; // 槓檢查路線的boolean

        /* list大小為0 return true */
        if (arr.size() == 0) {
            return win;
        }

        for (int i=0 ; i<arr.size() ; i++) {
            if (arr.get(i).charAt(1) == '1' || arr.get(i).charAt(1) == '4' || arr.get(i).charAt(1) == '7') {
                stack[0]++;
            } else if(arr.get(i).charAt(1) == '2' || arr.get(i).charAt(1) == '5' || arr.get(i).charAt(1) == '8') {
                stack[1]++;
            } else if(arr.get(i).charAt(1) == '3' || arr.get(i).charAt(1) == '6' || arr.get(i).charAt(1) == '9') {
                stack[2]++;
            }
        }

        Arrays.sort(stack);
        // System.out.println(stack[0] + " " + stack[1] + " " + stack[2]);
        if ((stack[1] == stack[0] && stack[1] == stack[2]) || (stack[0]%3 == 0 && stack[1]%3 == 0 && stack[2]%3 == 0)) {
            /* 做深層檢查 */
            win = deepCheck(arr);

        } else {
            /* 拿掉一隻眼一一做檢查 */
            for (int i=0 ; i<arr.size() ; i++) {
                ArrayList<String> t = new ArrayList<String>(arr);
                /* 先拿掉眼 */
                String str = t.remove(i);
                

                ArrayList<String> tempArrG = new ArrayList<String>(arr);
                /* 檢查是否是槓 */
                if (tempArrG.remove(str) && tempArrG.remove(str) && tempArrG.remove(str) && tempArrG.remove(str)) {
                    /* 刪除4次加回來 */
                    tempArrG.add(str);
                    tempArrG.add(str);    
                    tempArrG.add(str);
                    tempArrG.add(str);

                    cg = checkG(tempArrG);

                    if (cg) {
                        break;
                    }
                }


                // System.out.println(str + "   " + t.indexOf(str));
                if (t.indexOf(str) != -1) {
                    t.remove(str);
                    /* 再做檢查 */
                    win = deepCheck(t);
                    if (win) {
                        break;
                    }
                } 
                

                if (i == arr.size() -1) {
                    return false;
                }
            }

        } 
        // System.out.println("win: " + win + "  cg: " + cg);
        return (win || cg)?true:false;
    }

    public static boolean deepCheck(ArrayList<String> arr) {

        // for (String str : arr) {
        //     System.out.print(str + " ");
        // }
        // System.out.println();
        /* 先檢查刻 */
        for (int i=0 ; i<arr.size()-2 ; i++) {
            String t = arr.get(i);
            int index = arr.indexOf(t);
            if (arr.get(index).equals(arr.get(index+1)) && arr.get(index).equals(arr.get(index+2))) {
                /* 做更深層計算(可能性的方法) */
                boolean ddc = doubleDeepCheck(new ArrayList<String>(arr));
                arr.remove(index);
                arr.remove(index);
                arr.remove(index);
                

                if (arr.size() == 0 || ddc) {
                    return true;
                } else {
                    return deepCheck(arr);
                }
            }
        }

        /* 在檢查順 */

        for (int i=0 ; i<arr.size()-2 ; i++) {
            String t = arr.get(i);
            int index = arr.indexOf(t);
            String s1 = arr.get(index).charAt(0) + "" + (char)(arr.get(index).charAt(1)+1);
            String s2 = arr.get(index).charAt(0) + "" + (char)(arr.get(index).charAt(1)+2);
            if (arr.indexOf(s1) != -1 && arr.indexOf(s2) != -1) {
                arr.remove(index);
                arr.remove(arr.indexOf(s1));
                arr.remove(arr.indexOf(s2));
                if (arr.size() == 0) {
                    return true;
                } else {
                    return deepCheck(arr);
                }
            }
        }

        return false;
    }

    public static boolean doubleDeepCheck(ArrayList<String> arr) {

        /* 檢查順子 */
        for (int i=0 ; i<arr.size()-2 ; i++) {
            String t = arr.get(i);
            int index = arr.indexOf(t);
            String s1 = arr.get(index).charAt(0) + "" + (char)(arr.get(index).charAt(1)+1);
            String s2 = arr.get(index).charAt(0) + "" + (char)(arr.get(index).charAt(1)+2);
            if (arr.indexOf(s1) != -1 && arr.indexOf(s2) != -1) {
                arr.remove(index);
                arr.remove(arr.indexOf(s1));
                arr.remove(arr.indexOf(s2));
                if (arr.size() == 0) {
                    return true;
                } else {
                    return deepCheck(arr);
                }
            }
        }

        return false;
    }
    
    /* 檢查槓 */
    public static boolean checkG(ArrayList<String> arr) {

        for (int i=0 ; i<arr.size() ; i++) {
            String t = arr.get(i);
            int index = arr.indexOf(t);
            if (index != -1 && (arr.get(index+1).equals(t) && arr.get(index+2).equals(t) && arr.get(index+3).equals(t))) {
                
                // for (String s : arr) {
                //     System.out.print(s + " ");
                // }
                // System.out.println();

                arr.remove(index);
                arr.remove(index);
                arr.remove(index);
                arr.remove(index);
    
                if (arr.size() == 0) {
                    return true;
                } else if (arr.size() <= 2) {
                    return false;
                } else {
                    return stackCal(arr);
                }

            }
        }

        return false;
    }

    /* 檢查字 */
    public static boolean checkWord(ArrayList<String> arr) {

        // for (String str : arr) {
        //     System.out.print(str + " ");
        // }
        // System.out.println();

        if (arr.size() == 0) {
            return true;
        }

        for (int i=0 ; i<arr.size()-2 ; i++) {
            String t = arr.get(i);
            int index = arr.indexOf(t);
            if (arr.get(index).equals(arr.get(index+1)) && arr.get(index).equals(arr.get(index+2))) {
                arr.remove(index);
                arr.remove(index);
                arr.remove(index);
                if (arr.size() == 0) {
                    return true;
                } else {
                    return checkWord(arr);
                }
            }
        }
        /* 檢查眼是否相同 */
        if (arr.size() == 2 && (arr.get(0).equals(arr.get(1)))) {
            return true;
        }

        return false;
    }

}