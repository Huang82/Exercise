
import java.awt.FlowLayout;

import javax.swing.*;


/* 抬頭顯示行動方 */
public class roundColor {

    public static JPanel pan = new JPanel();
    public static status s = status.miss; 
    private static JLabel lb = new JLabel(new ImageIcon(status.miss.URL));

    public roundColor(){
        pan.setLayout(new FlowLayout(FlowLayout.CENTER));
        pan.setOpaque(false);
        lb.setOpaque(false);
        pan.add(lb);
    }

    // 回合方輪流
    public static void switchRoundColor(chessObject co) {
        switch(roundColor.s) {
            case miss:
                if (co.playerColor.equals(chessWeight.PlayerColor.black.name())) {
                    lb.setIcon(new ImageIcon(status.red.URL));
                    roundColor.s = roundColor.status.red; 
                } else {
                    lb.setIcon(new ImageIcon(status.black.URL));
                    roundColor.s = roundColor.status.black;
                }
                break;
            case black:
                lb.setIcon(new ImageIcon(status.red.URL));
                roundColor.s = roundColor.status.red; 
                break;
            case red:
                lb.setIcon(new ImageIcon(status.black.URL));
                roundColor.s = roundColor.status.black;
                break;
        }
    }

    static enum status{
        miss("miss", ".\\Image\\miss_.png"),
        red("red" , ".\\Image\\red.png"), 
        black("black", ".\\Image\\black.png");

        public String no;
        public String URL;

        status (String no, String URL) {
            this.no = no;
            this.URL = URL;
        }
    }
}
