import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class game {
    public static void main (String[] args) {
        String[] card = {"a4", "a4", "a5", "a5", "b2", "b2", "b5", 
        "b5", "b5", "c3", "c3", "c4", "c5", "c6", "c7", "b2", "a5"};

        System.out.println(cal(card, "c5"));
    }
    
    public static ArrayList<String> playerAction = new ArrayList<String>();   // 可以提醒玩家可做的動作
    public static ArrayList<ArrayList<String>> saveCard = new ArrayList<ArrayList<String>>();   // 可以儲存玩家如果可以吃碰槓聽牌的牌

    public static boolean eatBool = false;    // 是否可吃牌
    public static boolean bunoBool = false;   // 是否可碰牌
    public static boolean gBool = false;      // 是否可槓牌
    public static boolean listenBool = false; // 是否可聽牌
    public static int eye = 0;                    // 眼有幾個，如果超過2個就是沒有聽牌，最少一個

    

    public static String cal(String[] card, String getCard) {
        
        /* 初始化 */
        playerAction = new ArrayList<String>();
        saveCard = new ArrayList<ArrayList<String>>();

        eatBool = false;
        bunoBool = false;
        gBool = false;
        listenBool = false;
        eye = 0;

        ArrayList<String> a = new ArrayList<String>();
        ArrayList<String> b = new ArrayList<String>();
        ArrayList<String> c = new ArrayList<String>();
        ArrayList<String> word = new ArrayList<String>();
        
        ArrayList<String> cardStack = new ArrayList<String>();

        for (String str : card) {
            cardStack.add(str);
        }
        /* 原本有的，如果要測試注意這是未拿取牌前的牌 */
        // cardStack.add(getCard);

        Collections.sort(cardStack);

        for (String count : cardStack) {
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
        boolean wina = stackCal(a, getCard);
        // 計算條
        boolean winb = stackCal(b, getCard);
        // 計算筒
        boolean winc = stackCal(c, getCard);
        // 計算字
        boolean winWord = checkWord(word, getCard);
        
        // System.out.println(wina + " " + winb + " "  + winc + " " + winWord);
        // System.out.println(eye);

        /* 尋找聽牌 */
        if ((!wina || !winb || !winc || !winWord)) {
            String[] temp = Arrays.copyOf(card, (card.length));
            Arrays.sort(temp);
            checkListen(temp);
            // showAction();
        }



        if (wina && winb && winc && winWord && eye <= 1) {
            return "你胡牌了!!";
        } else {
            return "";
        }
    }


    public static boolean stackCal(ArrayList<String> arr, String getCard) {

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
        } else if (arr.size() == 2) {
            eye++;
            return arr.get(0).equals(arr.get(1))?true:false;
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


        ArrayList<String> tempArr = new ArrayList<String>(arr);
        /* 檢查是否可以吃 */
        eatBool = checkEat(tempArr, getCard);
        
        tempArr = new ArrayList<String>(arr);
        /* 檢查是否可以碰 */
        bunoBool = checkbuno(tempArr, getCard);

        tempArr = new ArrayList<String>(arr);
        /* 檢查是否可以槓 */
        gBool = checkGun(tempArr, getCard);

        /* 檢查裡面是否有槓 */
        boolean gunbool = false;
        for (int i=0 ; i<arr.size() ; i++) {
            ArrayList<String> temp = new ArrayList<String>(arr);
            String str = temp.get(i);
            if (temp.remove(str) && temp.remove(str) && temp.remove(str) && temp.remove(str)) {
                gunbool = true;
            }
        }

        Arrays.sort(stack);
        // System.out.println(stack[0] + " " + stack[1] + " " + stack[2]);
        if ((stack[1] == stack[0] && stack[1] == stack[2]) || (stack[0]%3 == 0 && stack[1]%3 == 0 && stack[2]%3 == 0) || ((stack[0]+stack[1]+stack[2])%3 == 0 && !gunbool)) {
            /* 做深層檢查 */
            win = deepCheck(arr, getCard);

        } else {
            /* 拿掉一隻眼一一做檢查 */
            for (int i=0 ; i<arr.size() ; i++) {
                ArrayList<String> t = new ArrayList<String>(arr);
                /* 先拿掉眼1 */
                String str = t.remove(i);
                

                ArrayList<String> tempArrG = new ArrayList<String>(arr);
                /* 檢查是否是槓 */
                if (tempArrG.remove(str) && tempArrG.remove(str) && tempArrG.remove(str) && tempArrG.remove(str)) {


                    /* 再分支檢查，如果刪除後後面兩隻是不同的就檢查 */
                    if (tempArrG.size() >= 2) {
                        if (!(tempArrG.get(0).equals(tempArrG.get(1)))){
                            /* 刪除4次加回來 */
                            tempArrG.add(str);
                            tempArrG.add(str);    
                            tempArrG.add(str);
                            tempArrG.add(str);
                            cg = deepCheck(tempArrG, getCard);
                        }
                    }

                    if (cg) {
                        break;
                    }

                    /* 刪除4次加回來 */
                    tempArrG.add(str);
                    tempArrG.add(str);    
                    tempArrG.add(str);
                    tempArrG.add(str);

                    cg = checkG(tempArrG, getCard);

                    if (cg) {
                        break;
                    }
                }


                // System.out.println(str + "   " + t.indexOf(str));
                if (t.indexOf(str) != -1) {
                    /* 眼2 */
                    t.remove(str);
                    /* 再做檢查 */
                    win = deepCheck(t, getCard);
                    if (win) {
                        eye++;
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

    public static boolean deepCheck(ArrayList<String> arr, String getCard) {

        // for (String str : arr) {
        //     System.out.print(str + " ");
        // }
        // System.out.println();

        
        /* 先檢查碰 */
        for (int i=0 ; i<arr.size()-2 ; i++) {
            String t = arr.get(i);
            int index = arr.indexOf(t);

            if (arr.get(index).equals(arr.get(index+1)) && arr.get(index).equals(arr.get(index+2))) {
                /* 做更深層計算(可能性的方法) */
                boolean ddc = doubleDeepCheck(new ArrayList<String>(arr), getCard);
                arr.remove(index);
                arr.remove(index);
                arr.remove(index);
                

                if (arr.size() == 0 || ddc) {
                    return true;
                } else {
                    return deepCheck(arr, getCard);
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
                    return deepCheck(arr, getCard);
                }
            }
        }

        return false;
    }

    public static boolean doubleDeepCheck(ArrayList<String> arr, String getCard) {

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
                    return deepCheck(arr, getCard);
                }
            }
        }

        return false;
    }
    
    /* 檢查槓 */
    public static boolean checkG(ArrayList<String> arr, String getCard) {

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
                } else if (arr.size() <= 1) {
                    return false;
                } else if (arr.get(0).length() == 1){
                    return checkWord(arr, getCard);
                } else {
                    return stackCal(arr, getCard);
                }

            }
        }

        return false;
    }

    /* 檢查字 */
    public static boolean checkWord(ArrayList<String> arr, String getCard) {

        // for (String str : arr) {
        //     System.out.print(str + " ");
        // }
        // System.out.println();

        if (arr.size() == 0) {
            return true;
        } else if (arr.size() == 2) {
            eye++;
            return arr.get(0).equals(arr.get(1))?true:false;
        }

        ArrayList<String> temp = new ArrayList<String>(arr);
        /* 檢查是否可以碰 */
        bunoBool = checkbuno(temp, getCard);

        temp = new ArrayList<String>(arr);
        /* 檢查是否可以槓 */
        gBool = checkGun(temp, getCard);

        for (int i=0 ; i<arr.size()-2 ; i++) {
            String t = arr.get(i);
            int index = arr.indexOf(t);
            
            
            /* 檢查字槓 */
            ArrayList<String> tempArr = new ArrayList<String>(arr);
            if (tempArr.remove(t) && tempArr.remove(t) && tempArr.remove(t) && tempArr.remove(t)) {
                tempArr.add(t);
                tempArr.add(t);
                tempArr.add(t);
                tempArr.add(t);
                boolean cg = checkG(tempArr, getCard);
                
                if (cg) {
                    return true;
                }
            }

            /* 檢查字碰 */
            if (arr.get(index).equals(arr.get(index+1)) && arr.get(index).equals(arr.get(index+2))) {
                arr.remove(index);
                arr.remove(index);
                arr.remove(index);
                if (arr.size() == 0) {
                    return true;
                } else {
                    return checkWord(arr, getCard);
                }
            }
        }
        /* 檢查眼是否相同 */
        if (arr.size() == 2 && (arr.get(0).equals(arr.get(1)))) {
            eye++;
            return true;
        }

        return false;
    }

//          -----------------------------------檢查區---------------------------------------

    /* 檢查是否可不可以吃 */
    public static boolean checkEat (ArrayList<String> arr, String getCard) {

        boolean eb = false;     // 查看是否可以吃
        int count = 1;          // 序號
        /* 檢查順子 */
        for (int i=0 ; i<arr.size()-2 ; i++) {
            String eat1 = arr.get(i);
            int index = arr.indexOf(eat1);
            String eat2 = arr.get(index).charAt(0) + "" + (char)(arr.get(index).charAt(1)+1);
            String eat3 = arr.get(index).charAt(0) + "" + (char)(arr.get(index).charAt(1)+2);
            if (arr.indexOf(eat2) != -1 && arr.indexOf(eat3) != -1) {

                 /* 查看是否有無有得到的牌(吃) */
                 if (eat1.equals(getCard) || eat2.equals(getCard)|| eat3.equals(getCard)) {
                    ArrayList<String> tempArr = new ArrayList<String>();
                    tempArr.add(eat1);
                    tempArr.add(eat2);
                    tempArr.add(eat3);
                    if (!saveCard.contains(tempArr)) {
                        saveCard.add(tempArr);
                        playerAction.add("吃" + Integer.toString(count));
                        count++;
                        eb = true;
                    }
                }
            }
        }

        return eb;
    }

    /*  檢查是否可不可以碰 */
    public static boolean checkbuno (ArrayList<String> arr, String getCard) {

        boolean bb = false;     //查看這組是否有碰

        if (arr.remove(getCard) && arr.remove(getCard) && arr.remove(getCard)) {
            ArrayList<String> tempArr = new ArrayList<String>();
            tempArr.add(getCard);
            tempArr.add(getCard);
            tempArr.add(getCard);
            if (!saveCard.contains(tempArr)) {
                saveCard.add(tempArr);
                playerAction.add("碰");
                bb = true;
            }

        }
        

        return bb;

    }

    /* 檢查可不可以槓 */
    public static boolean checkGun (ArrayList<String> arr, String getCard) {

        boolean bg = false;

        if (arr.remove(getCard) && arr.remove(getCard) && arr.remove(getCard) && arr.remove(getCard)) {
            ArrayList<String> tempArr = new ArrayList<String>();
            tempArr.add(getCard);
            tempArr.add(getCard);
            tempArr.add(getCard);
            tempArr.add(getCard);
            if (!saveCard.contains(tempArr)) {
                saveCard.add(tempArr);
                playerAction.add("槓");
                bg = true;
            }
        }
        

        return bg;

    }

    /* 檢查是否有聽牌 */
    public static boolean checkListen (String[] card) {
        
        int count = 1;      // 幾組聽牌
        boolean bl = false;
        String str;     // 字牌(顏色)
        String[] copy = Arrays.copyOf(card, card.length);
            /* 刪除一張牌在探索每一種可能會胡牌的可能性 */
            for (int i=0 ; i<copy.length ; i++) {
                String[] temp = Arrays.copyOf(copy, copy.length);
                String recoveryCard = temp[i];
                /* 探索每一種可能會胡牌的可能性 */
                for (int k=0 ; k<10 ; k++) {

                    /* 前三次檢查數字牌 */
                    if (k <= 2) {

                        if (k == 0) {
                            str = "a";
                        } else if (k == 1) {
                            str = "b";
                        } else {
                            str = "c";
                        }

                        for (int j=1 ; j<=9 ; j++) {
                            String linstenCard = str + Integer.toString(j);    // 聽的牌(尋找)
                            temp[i] = linstenCard;
                            boolean cl = checkListen.cal(temp);

                            // System.out.println(recoveryCard + "   " + linstenCard);

                            // for (String s : temp) {
                            //     System.out.print(s + " ");
                            // }
                            // System.out.println();

                            if (cl) {
                                ArrayList<String> tempArr = new ArrayList<String>();
                                tempArr.add(recoveryCard);  // 需要打出的牌
                                tempArr.add(linstenCard);   // 聽的牌
                                if (!saveCard.contains(tempArr)){
                                    playerAction.add("聽" + Integer.toString(count));
                                    saveCard.add(tempArr);
                                    count++;
                                }
                            }
                        }
                        temp[i] = recoveryCard;    
                    } else {
                        /* a:萬 b:筒 c:條 e:東 s:南 w:西 n:北 x:中 y:發 z:白*/
                        if (k == 3) {
                            str = "e";
                        } else if (k == 4) {
                            str = "s";   
                        } else if (k == 5) {
                            str = "w";   
                        } else if (k == 6) {
                            str = "n";   
                        } else if (k == 7) {
                            str = "x";   
                        } else if (k == 8) {
                            str = "y";   
                        } else {
                            str = "z";   
                        }

                            String linstenCard = str;    // 聽的牌(尋找)
                            temp[i] = linstenCard;
                            boolean cl = checkListen.cal(temp);

                            // System.out.println(recoveryCard + "   " + linstenCard);

                            // for (String s : temp) {
                            //     System.out.print(s + " ");
                            // }
                            // System.out.println();

                            if (cl) {
                                ArrayList<String> tempArr = new ArrayList<String>();
                                tempArr.add(recoveryCard);  // 需要打出的牌
                                tempArr.add(linstenCard);   // 聽的牌
                                if (!saveCard.contains(tempArr)){
                                    playerAction.add("聽" + Integer.toString(count));
                                    saveCard.add(tempArr);
                                    count++;
                                }
                            }
                        
                        temp[i] = recoveryCard;   

                    }
                }
            }

        return bl;

    }

    public static void showAction() {
        if (playerAction.size() != 0){
            for (int i=0 ; i<playerAction.size() ; i++) {
                System.out.print(playerAction.get(i) + ": ");
                for (int j=0 ; j<saveCard.get(i).size() ; j++) {
                    System.out.print(saveCard.get(i).get(j) + " ");
                }
            }
            System.out.println();
        }
    }

    public static ArrayList<String> getAction() {
        return playerAction;
    }

    public static ArrayList<ArrayList<String>> getSaveCard() {
        return saveCard;
    }

}
