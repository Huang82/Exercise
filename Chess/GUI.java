
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

    ArrayList<Integer> rangeInt = new ArrayList<Integer>();

    JButton reset = new JButton(new ImageIcon(".\\Image\\reset.png"));

    // 暫存棋(選擇)
    chessObject tchessObject = null;

    public GUI() {        
        frm.setSize(1000, 700);
    

        // init
        new roundColor();
        
        // 背景變透明以看得到背景
        background.setBounds(-15, -50, 1000, 700);
        background.setLayout(null);
        mainPan.removeAll();
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
        this.chessObjectInit();
        this.setRangeInt();
        this.setChess();

        // 置放重置鍵
        c1 = new GridBagConstraints();
        c1 = new GridBagConstraints();
        c1.gridx = 6;
        c1.gridy = 5;
        c1.gridwidth = 1;
        c1.gridheight = 1;
        c1.anchor = GridBagConstraints.CENTER;
        
        reset.setName("reset");
        reset.setContentAreaFilled(false);
        reset.setBorder(null);
        reset.addActionListener(this);
        mainPan.add(reset, c1);

        
        background.add(mainPan);
        
        frm.getContentPane().add(background);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }
    
    /* 創建將棋物件 */
    private void chessObjectInit(){
        roundColor.reset();
        for (chessWeight c : chessWeight.values()) {
            
            if (c.no.equals(chessWeight.black_1.no) || c.no.equals(chessWeight.red_1.no)) {
                // 產生兵與卒各5個
                for (int i = 0 ; i < 5 ; i++){
                    chessObject co = new chessObject(c.no, c.weight, c.URL, c.playerColor);
                    chessObject Tco = new chessObject(c.no, c.weight, c.URL, c.playerColor);
                    chessData.add(co); 
                    TchessData.add(Tco);       
                }
            } else if(c.no.equals(chessWeight.black_6.no) || c.no.equals(chessWeight.red_6.no)) {
                // 產生帥與將
                chessObject co = new chessObject(c.no, c.weight, c.URL, c.playerColor);
                chessObject Tco = new chessObject(c.no, c.weight, c.URL, c.playerColor);
                chessData.add(co);
                TchessData.add(Tco); 
            } else {
                // 產生其他的各2個
                for(int i = 0 ; i < 2 ; i++){
                    chessObject co = new chessObject(c.no, c.weight, c.URL, c.playerColor);
                    chessObject Tco = new chessObject(c.no, c.weight, c.URL, c.playerColor);
                    chessData.add(co);
                    TchessData.add(Tco); 
                }
            }
            
        }
    }
    /* 擺放象棋位置(依序) */
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
                
                chessObject co = TchessData.get(rangeInt.remove(0));
                co.setPosition(j, i);
                co.but.addActionListener(this);
                mainPan.add(co.but, c);

                /* 隨機抽取按鈕依序設置座標 */
                // int t =  (int)(TchessData.size() * Math.random());
                // System.out.println(t);
                // chessObject co = TchessData.remove(t);
                // co.setPosition(j, i);
                // co.but.addActionListener(this);
                // mainPan.add(co.but, c);
            }
        }
    }
    
    /* 隨機產生設置將棋位子的亂數 */
    private void setRangeInt() {
        rangeInt.clear();
        for (int i = 31 ; i >= 0 ; i--){
            this.rangeInt.add(i);
        }

        ArrayList<Integer> t = new ArrayList<Integer>();
        for (int i = 0 ; i < 32 ; i++) {
            int Tint = (int)(Math.random() * rangeInt.size()); 
            t.add(rangeInt.remove(Tint));
            System.out.println(t.get(i));
        }
        rangeInt = t;
    }

    /* 使用亂數來亂數設置位置賦予值 */
    private void resetChess() {
        for (int i = 0 ; i < 32 ; i++) {
            chessObject t = chessData.get(rangeInt.remove(0));
            TchessData.get(i).chessId = t.chessId;
            TchessData.get(i).no = t.no;   // 棋名稱
            TchessData.get(i).status = t.status;   // 棋狀態
            TchessData.get(i).weight = t.weight;  // 棋權重
            TchessData.get(i).URL = t.URL;  // 棋圖片位置
            TchessData.get(i).playerColor = t.playerColor;  // 棋子顏色(哪一方)
            TchessData.get(i).but.setName(t.but.getName());
            TchessData.get(i).but.setIcon(t.but.getIcon());
        }

    }

    private void resetGame() {
        roundColor.reset();
        this.setRangeInt();
        this.resetChess();
        System.out.println("重置");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        JButton but = (JButton)e.getSource();
        chessObject co = (chessObject)this.TchessData.stream()
        .filter(c -> c.chessId.equals(but.getName()))
                            .findFirst().orElse(null);
        // 是否有人贏
        boolean redWin = false;
        boolean blackWin = false;
        
        // 重置鈕
        if (but.equals(reset)) {
            this.resetGame();
            return;
        }

        System.out.println(co.playerColor + " " + co.status + " " + co.x + " " + co.y + " " + co.no);

        /* 暫存是否為null(選取棋) */
        if (tchessObject == null) {
            // 翻牌
            if (co.status.equals(chessStatus.miss.no)) {
                // 翻牌
                this.turnChrss(co);
                // 回合方輪流
                roundColor.switchRoundColor(co);
                return;
            }
            // 狀態是為空與物件與回合方不相同
            if (co.status.equals(chessStatus.air.no) || !roundColor.s.no.equals(co.playerColor)) {
                System.out.println("空/不相同");
                return;
            }

            // 選取牌存入暫存
            tchessObject = co;
            tchessObject.setIconSel();
            System.out.println("選擇牌");
            return;
        }

        // 是否與暫存相同
        if (co.equals(tchessObject)) {
            // 如果選取是相同等於不選取
            System.out.println("還原");
            // 暫存清掉
            tchessObject.setIcon();
            tchessObject = null;
            return;
        }

        // 是否為miss
        if (co.status.equals(chessStatus.miss.no)) {
            // 翻牌
            this.turnChrss(co);
            // 回合方輪流
            roundColor.switchRoundColor(co);
            // 暫存復原無選取
            tchessObject.setIcon();
            // 暫存清掉
            tchessObject = null;
            return;
        }

        // 是否為空
        if (co.status.equals(chessStatus.air.no)) {
            // 座標相差
            int x = Math.abs(co.x - tchessObject.x);
            int y = Math.abs(co.y - tchessObject.y);
            // 兩者座標加起來不超過1才可移動
            if (x + y <= 1){
                System.out.println("移動");
                // 暫存復原無選取
                tchessObject.setIcon();
                // 跟第二個選擇的做交換
                this.switchChess(co);
                tchessObject = null;
                // 回合方輪流
                roundColor.switchRoundColor(co);
                return;
            }
            System.out.println("超過移動範圍");
            return;
        }

        // 是否跟暫存方相同(相同就換選的牌)
        if (co.playerColor.equals(tchessObject.playerColor)) {
            tchessObject.setIcon();
            tchessObject = co;
            tchessObject.setIconSel();
            System.out.println("選擇牌(暫存更改)");
            return;
        }

        // 權重比大小 之前選取的權重大於等於現在選取的就吃還要現在取的權重不等於0(0:砲) 如果吃方權重為1(1:兵)
        if (tchessObject.weight >= co.weight || tchessObject.weight == 0 || tchessObject.weight == 1) {

            int x = Math.abs(tchessObject.x - co.x);
            int y = Math.abs(tchessObject.y - co.y);

            // 若被吃方選擇權重為1(1:卒)與吃方為6(6:帥)
            if (co.weight == 1 && tchessObject.weight == 6) {
                System.out.println("將(帥)無法吃兵(卒)");
                return;
            } 
            // 若被吃放選擇權重不是為6和1與吃方為1
            if (co.weight != 6 && co.weight != 1 && tchessObject.weight == 1 ) {
                System.out.println("兵(卒)無法吃除了將(帥)和卒(兵)的棋");
                return;
            }
            // 吃方為砲判斷是否可以吃
            if (x != 0 && y != 0 && tchessObject.weight == 0) {
                System.out.println("砲無法吃((x 或 y)相差兩個都等於0)");
                return;
            }
            // 吃方是否為砲(進行砲吃動作)
            if(tchessObject.weight == 0){
                // 判斷規則是否正確
                boolean s = true;
                // 若x相差為0判斷y軸
                if (x == 0) {
                    // 取最小和最大值
                    int max = Math.max(tchessObject.y, co.y);
                    int min = Math.min(tchessObject.y, co.y);
                    // stream整筆資料count為1(代表max到min之間只有一個棋子)
                    s = TchessData.stream().filter(c -> (min < c.y && c.y < max) && 
                                                (c.x == tchessObject.x) &&
                                                (c.status == chessStatus.miss.no || c.status == chessStatus.open.no))
                                                .count() == 1;
                    System.out.println(TchessData.stream().filter(c -> (min < c.y && c.y < max) && 
                    (c.status == chessStatus.miss.no || c.status == chessStatus.open.no))
                    .count() == 1);
                } else {
                    int max = Math.max(tchessObject.x, co.x);
                    int min = Math.min(tchessObject.x, co.x);
                    s = TchessData.stream().filter(c -> (min < c.x && c.x < max) && 
                                                 (c.y == tchessObject.y) &&
                                                 (c.status == chessStatus.miss.no || c.status == chessStatus.open.no))
                                                 .count() == 1;
                    System.out.println(TchessData.stream().filter(c -> (min < c.x && c.x < max) && 
                    (c.status == chessStatus.miss.no || c.status == chessStatus.open.no))
                    .count());
                }

                // 規則不對跳出
                if (!s) {
                    System.out.println("砲無法吃(規則不對)");
                    return;
                }
            }
            
            // 判斷吃的範圍有沒有超過和吃方不行為0(0:砲)
            if ((x + y) > 1 && tchessObject.weight != 0) {
                System.out.println("無法吃(範圍超過)");
                return;
            }

            // 比大小判斷 (先測試甚麼吃甚麼都可以吃)
            co.status = chessStatus.air.no;
            // 被吃那方改為空圖片
            co.setAir();
            // 吃方改為未選取圖片
            tchessObject.setIcon();
            // 交換
            this.switchChess(co);
            System.out.println(co.playerColor + " " + co.status + " " + co.x + " " + co.y + " " + co.no + " " + co.URL);
            System.out.println(tchessObject.playerColor + " " + tchessObject.status + " " + tchessObject.x + " " + tchessObject.y + " " + tchessObject.no + " " + tchessObject.URL);
                
            tchessObject = null;
            // 回合方輪流
            roundColor.switchRoundColor(co);
            System.out.println("吃");
            
        }


        /* 判斷誰贏了 */
        // 哪一方air先到16就是對方獲勝
        blackWin = TchessData.stream().filter(a -> a.status.equals(chessStatus.air.no) &&
                                         a.playerColor.equals(chessWeight.PlayerColor.red.name()))
                                         .count() == 16;
        redWin = TchessData.stream().filter(a -> a.status.equals(chessStatus.air.no) &&
                                        a.playerColor.equals(chessWeight.PlayerColor.black.name()))
                                        .count() == 16;
        
        if (redWin) {
            System.out.println("紅方獲勝");
        }

        if (blackWin) {
            System.out.println("黑方獲勝");
        }

        if (redWin || blackWin) {
            String str[] = {"重玩", "離開"};
            int a = JOptionPane.showOptionDialog(null, String.format("%s獲勝!!", redWin ? "紅方":"黑方"), "Win!!", 
                                                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, str, "exit");
            if (a == JOptionPane.CLOSED_OPTION || a == JOptionPane.YES_NO_OPTION) {
                this.resetGame();
            } else {
                System.exit(0);
            }

        }

    }

    /* 翻牌 */
    private void turnChrss(chessObject co) {
        co.status = chessStatus.open.no;
        co.setIcon();
        co.showPosition();
        System.out.println("翻牌");
    }


    /* 交換按鈕(所有資料) */
    private void switchChess(chessObject co){
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
