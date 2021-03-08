import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/* 玩家資料 */
public class playerObject {

    String[] stackCard;                                             // 玩家牌堆
    String action = "0";                                            // 玩家剛剛的動作0:打牌 1:碰 2:吃 3:槓 4:聽
    ArrayList<String> removeStackCard = new ArrayList<String>();    // 玩家吃碰槓牌堆
    ArrayList<String> listenStack = new ArrayList<String>();        // 聽牌堆
    boolean listenBool = false;                                     // 是否聽牌

    playerObject (String[] stackCard) {
        this.stackCard = stackCard;
    }

    /* 顯示玩家牌堆 */
    public void showCard() {
        System.out.println("您的牌: ");
        for (int i=0 ; i<stackCard.length ; i++) {
            if (i == (stackCard.length-1)) {
                System.out.print("您拿到的牌： " + stackCard[i]);
            } else {
                System.out.print(stackCard[i] + " ");
            }
        }
        System.out.println();
    }

    /* 玩家打牌 */
    public void pushCard(String card) {
        Scanner in = new Scanner(System.in);
        while (true) {
            action = "0";
            /* 未聽牌可以自由打牌 */
            if (!this.listenBool) {
                for (int i=0 ; i<stackCard.length ; i++) {
                    /* 尋找玩家打的牌，找到之後拿最後面的牌往那張牌的位置打然後刪除最後一個位置 */
                    if (stackCard[i].equals(card)) {
                        stackCard[i] = stackCard[stackCard.length-1];
                        stackCard = Arrays.copyOf(stackCard, (stackCard.length-1));
                        Arrays.sort(stackCard);
                        return;
                    }
                    if (i == (stackCard.length - 1)) {
                        System.out.println("找不到牌，請打正確好嗎");
                    }
                }
            } else {
                /* 聽牌以後只能打最後一張牌 */
                if (card.equals(this.stackCard[stackCard.length-1])) {
                    stackCard = Arrays.copyOf(stackCard, (stackCard.length-1));
                    Arrays.sort(stackCard);
                    return;
                }
                System.out.println("你已經聽了，只能打摸進來的那張牌，請重新輸入牌號");
            }
                card = in.nextLine();
        }
    }

    /* 玩家抽牌 */
    public void takeCard(String card) {
        stackCard = Arrays.copyOf(stackCard, (stackCard.length+1));
        stackCard[stackCard.length-1] = card;
    }

    /* 玩家是否吃碰槓聽牌 */
    public boolean playerAction(ArrayList<String> action, ArrayList<ArrayList<String>> saveCard) {
        /* 1:碰 2:吃 3:槓 4.聽 */
        /* 聽牌不用打牌(true，因為已經打過牌了) */
        if (action.size() == 0) {
            return false;
        }
        Scanner in = new Scanner(System.in);

        ArrayList<String> number = new ArrayList<String>();
        ArrayList<String> removeCard = new ArrayList<String>();

        /* 存入可執行的動作 */
        System.out.print("可進行動作:");
        if (action.indexOf("碰") != -1) {
            System.out.print("1.碰 ");
            number.add("1");
        } 

        if (action.indexOf("吃1") != -1) {
            System.out.print("2.吃 ");
            number.add("2");
        }  

        if (action.indexOf("槓") != -1) {
            System.out.print("3.槓 ");
            number.add("3");
        } 

        if (action.indexOf("聽1") != -1) {
            System.out.print("4.聽 ");
            number.add("4");
        }

        System.out.println("0.取消");
        number.add("0");
        System.out.println("請輸入代號：");

        while (true) {

            String str = in.nextLine();
            boolean listenActionBool = false;   // 再測碰吃完後是否有聽

            if (number.indexOf(str) != -1) {

                int index = 1;      // 吃、聽會用到(因為可能有複數)
                String card;        // 動作與index加起來使用

                switch(str) {
                    /* 碰 */
                    case "1":
                        /* 尋找action裡面的碰並顯示出來 */
                        for (int i=0 ; i<action.size() ; i++) {
                            if (action.get(i).equals("碰")) {
                                System.out.print("碰:");
                                for (String s : saveCard.get(i)) {
                                    System.out.print(s + " ");
                                    removeCard.add(s);
                                }
                                removeCard(removeCard);
                                System.out.println();
                                listenActionBool = this.reCheck();
                                this.showCard();
                                break;
                            }
                        }
                        /* 紀錄剛剛的動作 */
                        this.action = "1";
                        break;
                    /* 吃 */
                    case "2":
                        String eat = "吃";
                        index = 1;
                        card = eat + Integer.toString(index);
                        /* 尋找action裡面的吃並全部顯示出來讓玩家選擇 */
                        for (int i=0 ; i <action.size() ; i++) {
                            if (action.get(i).equals(card)) {
                                System.out.print(action.get(i) + ": ");
                                for (String s : saveCard.get(i)) {
                                    System.out.print(s + " ");
                                }
                                System.out.println();
                                index++;
                                card = eat + Integer.toString(index);
                            }
                        }
                        /* 如果只有一組吃直接吃 */
                        if (index <= 2) {
                            card = eat + Integer.toString(index-1);
                            for (String s : saveCard.get(action.indexOf("吃1"))) {
                                removeCard.add(s);
                            }
                            this.removeCard(removeCard);
                        } else {
                            /* 兩組以上跑出選單讓玩家選擇 */
                            System.out.println("0.取消");
                            System.out.println("請選擇要吃得牌：");
                            while (true) {
                                boolean br = false;         // 如果選擇了動作就跳出迴圈
                                String sel = in.nextLine();
                                String c = "吃" + sel;
                                for (int i=0 ; i<action.size() ; i++) {
                                    if (action.get(i).equals(c)) {
                                        for (String s : saveCard.get(action.indexOf(c))) {
                                            removeCard.add(s);
                                        }
                                        removeCard(removeCard);
                                        br = true;
                                        break;
                                    }

                                    if (c.equals("吃0")) {
                                        br = true;
                                        break;
                                    }
                                }
                                if (br) {
                                    break;
                                }
                                System.out.println("沒有此吃牌代號，請重打");
                            }
                        }
                        listenActionBool = this.reCheck();
                        this.showCard();
                        /* 紀錄剛剛的動作 */
                        this.action = "2";
                        break;
                    /* 槓 */
                    case "3":
                    /* 尋找action裡面的槓並顯示出來 */
                    for (int i=0 ; i<action.size() ; i++) {
                        if (action.get(i).equals("槓")) {
                            System.out.print("槓:");
                            for (String s : saveCard.get(i)) {
                                System.out.print(s + " ");
                                removeCard.add(s);
                            }
                            removeCard(removeCard);
                            System.out.println();
                            listenActionBool = this.reCheck();
                            break;
                        }
                    }
                    /* 紀錄剛剛的動作 */
                    this.action = "3";
                    break;
                    /* 聽 */
                    case "4":
                        String listen = "聽";
                        index = 1;
                        card = listen + Integer.toString(index);
                        /* 尋找action裡面所有的聽，並顯示出來讓玩家選擇 */
                        for (int i=0 ; i<action.size() ; i++) {
                            if (action.get(i).equals(card)) {
                                System.out.print(action.get(i) + ": ");
                                for (String s : saveCard.get(i)) {
                                    System.out.print(s + " ");
                                }
                                index++;
                                card = listen + Integer.toString(index);
                            }
                        }
                        System.out.println("0.取消");
                        System.out.println("請選擇要聽得牌：");
                        while (true) {
                            boolean br = false;
                            String sel = in.nextLine();
                            String c = "聽" + sel;
                            for (int i=0 ; i<action.size() ; i++) {
                                if (action.get(i).equals(c)) {
                                    removeCard.add(saveCard.get(action.indexOf(c)).get(0));
                                               
                                    for (int j=0 ; j<saveCard.size() ; j++) {
                                        if (saveCard.get(j).get(0).equals(removeCard.get(0))) {
                                            this.listenStack.add(saveCard.get(j).get(1));
                                        }
                                    }

                                    this.listenBool = true;
                                    removeCard(removeCard);
                                    br = true;
                                    break;
                                }

                                if (c.equals("聽0")) {
                                    this.showCard();
                                    return false;
                                }
                            }
                            if (br) {
                                break;
                            }
                            System.out.println("沒有此聽牌代號，請重打");
                        }
                        /* 紀錄剛剛的動作 */
                        this.action = "4";
                        return true;
                        
                    /* 取消 */
                    case "0":
                        this.showCard();
                        break;
                }

                /* 吃碰後聽牌回傳true */
                if (listenActionBool) {
                    this.showCard();
                    /* 紀錄剛剛的動作 */
                    this.action = "4";
                    return true;
                }

                return false;
            }

            System.out.println("沒有此代號");
        }
    }

    /* 顯示吃碰牌堆 */
    public void showRemoveStackCard() {

        if (removeStackCard.size() == 0) {
            return;
        }

        for (String str : removeStackCard) {
            System.out.print(str + " ");
        }
        System.out.println();
    }

    /* 傳送是否聽牌 */
    public boolean getplayerListen() {
        return listenBool;
    }

    /* 顯示聽的牌 */
    public void showListenCrad() {
        System.out.print("您聽的牌有： ");
        for (String str : this.listenStack) {
            System.out.print(str + " ");
        }
        System.out.println();
    }

    /* 回傳剛剛玩家的動作 */
    public String getAction() {
        return this.action;
    }

    /* 從牌堆刪除牌(吃碰槓聽) */
    private void removeCard(ArrayList<String> arr) {
        
        
        for (String rm : arr) {
            for (int i=0 ; i<stackCard.length ; i++) {
                if (stackCard[i].equals(rm)) {
                    /* list裡面如果有超過3個以上就不是聽 */
                    if (arr.size() >= 3) {
                        removeStackCard.add(stackCard[i]);
                    }
                    stackCard[i] = stackCard[stackCard.length-1];
                    stackCard = Arrays.copyOf(stackCard, (stackCard.length-1));
                    Arrays.sort(stackCard);
                    break;
                }
            }
        }

    }

    /* 吃碰牌完，重新檢查是否有聽(只檢查聽) */
    private boolean reCheck() {

        game.cal(this.stackCard, this.stackCard[this.stackCard.length-1]);

        ArrayList<String> actionArr = game.getAction();

        if (actionArr.indexOf("聽1") != -1) {
            this.showCard();
            return this.playerAction(game.playerAction, game.saveCard);
        }

        return false;
    }

}
