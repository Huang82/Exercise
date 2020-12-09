
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


        

        // 翻牌
        if (co.status.equals(chessStatus.miss.no)) {
            co.status = chessStatus.open.no;
            co.setIcon();
            co.showPosition();
            // 回合方輪流
            roundColor.switchRoundColor(co);
            return;
        }

        // 下方明天寫(遊戲流程)

    }
}
