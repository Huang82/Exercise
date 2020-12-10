
import javax.swing.*;

/* 象棋物件 */
public class chessObject {
    
    public static int id = 0;
    public String chessId;
    public JButton but = new JButton(new ImageIcon(".\\Image\\miss.png"));
    public String no;   // 棋名稱
    public String status;   // 棋狀態
    public int weight;  // 棋權重
    public String URL;  // 棋圖片位置
    public String playerColor;  // 棋子顏色(哪一方)
    public int x;  // 座標
    public int y;
    
    chessObject (String no, int weight, String URL, String playColor) {
        // 設定按鈕id與chessId同步
        this.but.setName(Integer.toString(id));
        chessId = but.getName();

        // 各類設定
        this.playerColor = playColor;
        this.no = no;
        this.weight = weight;
        this.URL = URL;
        this.status = chessStatus.miss.no;
        but.setContentAreaFilled(false);
        but.setBorder(null);
        id += 1;
    }
    
    public void setIcon () {
        this.but.setIcon(new ImageIcon(URL));
    }
    
    public void setIconSel() {
        this.but.setIcon(new ImageIcon(String.format(".\\Image\\%s_Sel.png", this.no)));
    }

    public void setAir() {
        this.but.setIcon(new ImageIcon(".\\Image\\air.png"));
    }
    public void setPosition (int x , int y) {
        this.x = x;
        this.y = y;
    }

    public void showPosition () {
        System.out.println(x + " " + y);
    }

}

