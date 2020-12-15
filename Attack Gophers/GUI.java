
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class GUI implements ActionListener{
    
    private JFrame frm = new JFrame("Attack Gophers!!");
    public static backgroundSec bs = new backgroundSec();
    public static game game = new game();
    private JPanel mainPan = new JPanel();
    private JPanel upPan = new JPanel();
    private JPanel downPan = new JPanel();
    /* upPan裡的物件 */
    private JComboBox<String> box = new JComboBox<String>();
    public static JTextField tex1 = new JTextField();
    public static JTextField tex2 = new JTextField();
    public static JButton but = new JButton("開始");
    public static int score = 0;
    public static String tex2Score = String.format("得分：%s", score);
    /* dowPan裡的物件 */
    public static ArrayList<butObject> butData = new ArrayList<butObject>();



    public GUI() {
        frm.setSize(613, 660);
        frm.setResizable(false);
        mainPan.setBounds(0, 0, frm.getSize().width, frm.getSize().height);
        mainPan.setLayout(new BoxLayout(mainPan, BoxLayout.Y_AXIS));
        mainPan.setBorder(null);
        
        /* upPan 裡內容 */
        upPan.setLayout(new FlowLayout(1, 50 , 0));

        // 下拉式選單設定
        box.addItem(diffEnum.sel.no);
        box.addItem(diffEnum.easy.no);
        box.addItem(diffEnum.normal.no);
        box.addItem(diffEnum.difficult.no);
        upPan.add(box);

        // 秒數文字設定
        tex1.setOpaque(false);
        tex1.setBorder(null);
        tex1.setFont(new Font("標楷體", Font.BOLD, 16));
        tex1.setText(backgroundSec.tex1Sec);
        upPan.add(tex1);

        // 分數文字設定
        tex2.setOpaque(false);
        tex2.setBorder(null);
        tex2.setPreferredSize(new Dimension(100, 30));
        tex2.setFont(new Font("標楷體", Font.BOLD, 16));
        tex2.setText(tex2Score);
        upPan.add(tex2);

        // 開始按鈕設定
        but.addActionListener(this);
        upPan.add(but);
        
        /* downPan 裡內容 */
        downPan.setLayout(new GridBagLayout());

        for (int i = 0 ; i < 3 ; i++) {
            for (int j = 0 ; j < 3 ; j++) {
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = j;
                c.gridy = i;
                butObject b = new butObject((i * 3) + j);
                b.but.addActionListener(this);
                butData.add(b);
                downPan.add(b.but, c);
            }
        }


        
        mainPan.add(upPan);
        mainPan.add(downPan);
        frm.getContentPane().add(mainPan);
        frm.setVisible(true);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = ((JButton)e.getSource()).getText();
        
        // 倒數秒數如果沒有啟動的話進來
        if(!bs.isAlive()){
        
            if (!str.equals("開始")) {
                System.out.println("請按開始");
                return;
            }
            
            if (box.getSelectedItem().equals(diffEnum.sel.no)) {
                System.out.println("請選擇難度在按開始");
                return;
            }
            
            // 啟動多執行序(倒數秒數)
            System.out.println("啟動");
            // 分數初始化
            score = 0;
            tex2Score = String.format("得分：%s", score);
            tex2.setText(tex2Score);
            // 開始了 開始按鈕就無法按
            but.setEnabled(false);
            // 秒數和game物件初始化
            bs.start();
            game.setDiff((String)box.getSelectedItem());
            game.start();
            return;

        }

        // butObject but = butData.stream().filter(c -> c.but.equals((JButton)e.getSource())).findFirst().orElse(null);
        
        // if (but.state == URLenum.out.state) {
        //     this.score++;
        //     but.initBut();
        //     this.tex2Score = String.format("得分：%s", score);
        //     tex2.setText(tex2Score);
        //     System.out.println(score);
        //     System.out.println("打到了");
        // }
        

    }

}
