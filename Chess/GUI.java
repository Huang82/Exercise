
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.ArrayList;

public class GUI implements ActionListener{

    JFrame frm = new JFrame("Chess");

    // 更換JPanel背景
    JPanel background = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            ImageIcon icon = new ImageIcon(".\\Image\\background.png");
            Image img = icon.getImage();
            g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());
        }
    };

    JPanel mainPan = new JPanel();

    ArrayList<chessObject> chessData = new ArrayList<chessObject>();
    ArrayList<chessObject> TchessData = new ArrayList<chessObject>();

    // 暫存棋(選擇)
    chessObject tchessObject = null;

    public GUI() {        
        frm.setSize(1000, 700);
        background.setBounds(-15, -50, 1000, 700);
        background.setLayout(null);
        
        // init
        new roundColor();

        // 背景變透明以看得到背景
        mainPan.setOpaque(false);
        mainPan.setLayout(new GridBagLayout());
        mainPan.setBounds(background.getBounds());
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 3;
        c1.gridy = 0;
        c1.gridwidth = 2;
        c1.gridheight = 1;
        c1.anchor = GridBagConstraints.CENTER;
        mainPan.add(roundColor.pan, c1);

        // 象棋初始化
        chessObjectInit();
        setChess();

        background.add(mainPan);

        frm.getContentPane().add(background);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }

    private void chessObjectInit(){
        chessObject.id = 0;
        chessData.clear();
        TchessData.clear();
        for (chessWeight c : chessWeight.values()) {

            if (c.no.equals(chessWeight.black_1.no) || c.no.equals(chessWeight.red_1.no)) {
                // 產生兵與卒各5個
                for (int i = 0 ; i < 5 ; i++){
                    chessObject co = new chessObject(c.no, c.weight, c.URL, c.playerColor);
                    chessData.add(co); 
                    TchessData.add(co);       
                }
            } else if(c.no.equals(chessWeight.black_6.no) || c.no.equals(chessWeight.red_6.no)) {
                // 產生帥與將
                chessObject co = new chessObject(c.no, c.weight, c.URL, c.playerColor);
                chessData.add(co);
                TchessData.add(co); 
            } else {
                // 產生其他的各2個
                for(int i = 0 ; i < 2 ; i++){
                    chessObject co = new chessObject(c.no, c.weight, c.URL, c.playerColor);
                    chessData.add(co);
                    TchessData.add(co); 
                }
            }

        }
    }

    private void setChess(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                /* GridBagConstraints 設定 */
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = j;
                c.gridy = 1 + i;
                c.gridwidth = 1;
                c.gridheight = 1;
                c.anchor = GridBagConstraints.CENTER;
                /* 隨機抽取按鈕依序設置座標 */
                int t =  (int)(TchessData.size() * Math.random());
                System.out.println(t);
                chessObject co = TchessData.remove(t);
                co.setPosition(j, i);
                co.but.addActionListener(this);
                mainPan.add(co.but, c);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        JButton but = (JButton)e.getSource();
        chessObject co = (chessObject)this.chessData.stream()
                            .filter(c -> c.chessId.equals(but.getName()))
                            .findFirst().orElse(null);
        // 是否有人贏
        boolean redWin = false;
        boolean blackWin = false;
        
        System.out.println(co.playerColor + " " + co.status + " " + co.x + " " + co.y + " " + co.no);

        /* 暫存是否為null(選取棋) */
        if (tchessObject == null) {
            // 翻牌
            if (co.status.equals(chessStatus.miss.no)) {
                // 翻牌
                co.status = chessStatus.open.no;
                co.setIcon();
                co.showPosition();
                System.out.println("翻牌");
                // 回合方輪流
                roundColor.switchRoundColor(co);
                return;
            }
            // 狀態是為空與物件與回合方不相同
            if (co.status.equals(chessStatus.air.no) || !roundColor.s.no.equals(co.playerColor)) {
                System.out.println("空/不相同");
                return;
            }

            // 選取牌
            tchessObject = (chessObject)this.chessData.stream()
                            .filter(c -> c.equals(co))
                            .findFirst().orElse(null);;
            tchessObject.setIconSel();
            System.out.println("選擇牌");
            return;
        }

        // 是否與暫存相同
        if (co.equals(tchessObject)) {
            // 選取還原
            System.out.println("還原");
            tchessObject.setIcon();
            tchessObject = null;
            return;
        }

        // 是否為miss
        if (co.status.equals(chessStatus.miss.no)) {
            // 翻牌
            co.status = chessStatus.open.no;
            co.setIcon();
            co.showPosition();
            System.out.println("翻牌");
            // 回合方輪流
            roundColor.switchRoundColor(co);
            tchessObject.setIcon();
            tchessObject = null;
            return;
        }

        // 是否為空
        if (co.status.equals(chessStatus.air.no)) {
            System.out.println("移動");
            tchessObject.setIcon();
            switchChess(co);
            tchessObject = null;
            // 回合方輪流
            roundColor.switchRoundColor(co);
            return;
        }

        // 是否跟暫存方相同
        if (co.playerColor.equals(tchessObject.playerColor)) {
            tchessObject.setIcon();
            tchessObject = (chessObject)this.chessData.stream()
                            .filter(c -> c.equals(co))
                            .findFirst().orElse(null);;;
            tchessObject.setIconSel();
            System.out.println("選擇牌(暫存更改)");
            return;
        }

        // 比大小判斷 (先測試甚麼吃甚麼都可以吃)
        co.status = chessStatus.air.no;
        co.setAir();
        tchessObject.setIcon();
        this.switchChess(co);
        System.out.println(co.playerColor + " " + co.status + " " + co.x + " " + co.y + " " + co.no + " " + co.URL);
        System.out.println(tchessObject.playerColor + " " + tchessObject.status + " " + tchessObject.x + " " + tchessObject.y + " " + tchessObject.no + " " + tchessObject.URL);
        
        tchessObject = null;
        // 回合方輪流
        roundColor.switchRoundColor(co);
        System.out.println("吃");


        /* 判斷誰贏了 */
        // 哪一方air先到16就是對方獲勝
        blackWin = chessData.stream().filter(a -> a.status.equals(chessStatus.air.no) &&
                                         a.playerColor.equals(chessWeight.PlayerColor.red.name()))
                                         .count() == 16;
        redWin = chessData.stream().filter(a -> a.status.equals(chessStatus.air.no) &&
                                        a.playerColor.equals(chessWeight.PlayerColor.black.name()))
                                        .count() == 16;
        
        if (redWin) {
            System.out.println("紅方獲勝");
        }

        if (blackWin) {
            System.out.println("黑方獲勝");
        }

    }

    // 交換按鈕(所有資料)
    public void switchChess(chessObject co){
        System.out.println(co.playerColor + " " + co.status + " " + co.x + " " + co.y + " " + co.no);
        System.out.println(tchessObject.playerColor + " " + tchessObject.status + " " + tchessObject.x + " " + tchessObject.y + " " + tchessObject.no);
        // 暫存
        chessObject t = new chessObject("", 0, "", "");
        t.chessId = co.chessId;
        t.no = co.no;   // 棋名稱
        t.status = co.status;   // 棋狀態
        t.weight = co.weight;  // 棋權重
        t.URL = co.URL;  // 棋圖片位置
        t.playerColor = co.playerColor;  // 棋子顏色(哪一方)
        t.but.setName(co.but.getName());
        t.but.setIcon(co.but.getIcon());
        System.out.println(" t : " + t.playerColor + " " + t.status + " " + t.x + " " + t.y + " " + t.no);
        // 交換
        co.chessId = tchessObject.chessId;
        co.no = tchessObject.no;   // 棋名稱
        co.status = tchessObject.status;   // 棋狀態
        co.weight = tchessObject.weight;  // 棋權重
        co.URL = tchessObject.URL;  // 棋圖片位置
        co.playerColor = tchessObject.playerColor;  // 棋子顏色(哪一方)
        co.but.setName(tchessObject.but.getName());
        co.but.setIcon(tchessObject.but.getIcon());

        tchessObject.chessId = t.chessId;
        tchessObject.no = t.no;   // 棋名稱
        tchessObject.status = t.status;   // 棋狀態
        tchessObject.weight = t.weight;  // 棋權重
        tchessObject.URL = t.URL;  // 棋圖片位置
        tchessObject.playerColor = t.playerColor;  // 棋子顏色(哪一方)
        tchessObject.but.setName(t.but.getName());
        tchessObject.but.setIcon(t.but.getIcon());
    }
}
