import java.util.ArrayList;
import java.util.Arrays;


/* 製造牌堆，發牌 */
public class giveCard {
    /* a:萬 b:筒 c:條 e:東 s:南 w:西 n:北 x:中 y:發 z:白*/
    ArrayList<String> stackCard = new ArrayList<String>();
    int giveSize = 60;  // 能抽取牌數
    giveCard () {
        for (MahjongId MId : MahjongId.values()) {
            for (int i=0 ; i<3 ; i++) {
                for (int j=0 ; j<4 ; j++) {
                    if (i == 0) {
                        stackCard.add(MId.a);
                    } else if (i == 1) {
                        stackCard.add(MId.b);
                    } else if (i == 2) {
                        stackCard.add(MId.c);
                    }
                }
            }
        }
        
        String str;
        for (int i=0 ; i<7 ; i++) {
            for (int j=0 ; j<4 ; j++) {
                if (i == 0) {
                    str = "e";
                } else if (i == 1) {
                    str = "s";   
                } else if (i == 2) {
                    str = "w";   
                } else if (i == 3) {
                    str = "n";   
                } else if (i == 4) {
                    str = "x";   
                } else if (i == 5) {
                    str = "y";   
                } else {
                    str = "z";   
                }
                
                stackCard.add(str);
            }
        }
    }
    
    /* 發牌 */
    public String[] giveCardPlayer() {
        
        String[] stackPlayer = new String[16];
        
        for (int i=0 ; i<stackPlayer.length ; i++) {
            stackPlayer[i] = stackCard.remove((int)(Math.random() * (stackCard.size())));
        }
        
        Arrays.sort(stackPlayer);

        // String[] temp = {"a5", "a9", "b2", "b2", "b2" , "b3", "b4"  };

        return stackPlayer;
    }

    /* 給牌 */
    public String giveOne() {
        giveSize--;
        return stackCard.remove((int)(Math.random() * (stackCard.size())));
    }

    /* 查看剩餘牌數 */
    public int stackSize() {
        return giveSize;
    }

}
