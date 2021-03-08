import java.util.Scanner;

/* Demo 1.0 */
/* 

    目前只能自己玩，自己摸自己吃碰槓聽

    已知BUG: 
        1.吃碰完後聽牌沒抽到牌(有時會這樣)
        2.吃碰完後聽牌選牌後打出兩張牌(有時會這樣)
        4.再聽牌時會亂給玩家聽牌堆(有時會這樣，找不出問題)
        3.可能還有一些牌組未能找到胡牌(待)

    目前已知不符合遊戲規則：
        1.目前只能自己摸牌，自己吃、碰、槓、聽
        2.特殊牌型，目前無法算台數
        3.槓如果取消後就無法再槓了
        4.有些吃完的牌不能打，目前都可以打
        待...

    目前玩法:
        1.打牌為輸入完整的牌
        2.有碰吃槓聽請打入代號 ex. 1.碰 就輸入1
        3.當吃或聽有複數時也是輸入代號(聽要注意的是前面為打的牌 後面為聽得牌，
          當所有的聽是你輸入的打的牌，會全部統整起來顯示) 
          ex. 聽1 a1 c2 聽2 a1 c5 就輸入1 會統整 玩家目前聽牌： c2 c5
        4.聽牌以後只能打摸到的牌
        5.胡牌遊戲自動結束
        6.牌堆總數為60張，當0張時遊戲結束
*/
public class Main {
    public static void main (String[] args) {

        /* 創建牌 */
        giveCard gc = new giveCard();
        /* 玩家牌組 */
        playerObject player = new playerObject(gc.giveCardPlayer());    
        /* 玩家是否聽牌 */
        boolean listenBool = false;
        /* 顯示聽甚麼牌 */
        boolean showListenBool = false;
        while (true) {
            listenBool = false;

            /* 玩家抽牌 */
            player.takeCard(gc.giveOne());

            /* 計算是否有碰吃槓聽*/
            String str = game.cal(player.stackCard, player.stackCard[player.stackCard.length-1]);
            /* 當有胡牌直接結束遊戲 */
            if (str.equals("你胡牌了!!")) {
                player.showCard();
                System.out.println("恭喜你胡牌了!!");
                break;
            }
            /* 顯示玩家吃碰牌堆 */
            System.out.print("您的吃碰牌堆: ");
            player.showRemoveStackCard();
            /* 當目前為聽牌時顯示出聽甚麼 */
            if (showListenBool) {
                player.showListenCrad();
            }
            /* 顯示玩家牌堆 */
            player.showCard();
            /* 玩家未聽牌可以進行動作(碰吃槓聽) */
            if (!player.getplayerListen()) {
                listenBool = player.playerAction(game.getAction(), game.getSaveCard());
                /* 如果聽了就表示玩家聽了 */
                if (listenBool) {
                    showListenBool = true;
                }
            }
            /* 玩家剛剛動作為槓抽一張牌，然後再打 */
            if (player.getAction().equals("3")) {
                System.out.println("槓完抽一張再打");
                player.takeCard(gc.giveOne());
                player.showCard();
                listenBool = player.playerAction(game.getAction(), game.getSaveCard());
                if (listenBool) {
                    showListenBool = true;
                }
            }

            /* 進行聽牌動作不用打牌(因為已經打牌過了) */
            if (!listenBool) {
                Scanner in = new Scanner(System.in);
                String playerPushCard = in.nextLine();
                player.pushCard(playerPushCard);
            }
            
            System.out.println("剩餘牌數： " + gc.giveSize);
            
            /* 能抽牌堆為0時遊戲結束 */
            if (gc.giveSize == 0) {
                System.out.println("遊戲結束，流局");
                break;
            }
        }
        
    }
}
