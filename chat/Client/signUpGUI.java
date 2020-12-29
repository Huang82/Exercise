package Client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class signUpGUI {
    
    public static JFrame frm = new JFrame("註冊");
    private JTextField name = new JTextField();
    private JTextField account = new JTextField();
    private JPasswordField password = new JPasswordField();

    public signUpGUI() {
        frm.setSize(300, 266);
        frm.setLocationRelativeTo(null);
        frm.setResizable(false);
        GridBagLayout g = new GridBagLayout();
        g.columnWidths = new int[] {60, 60, 60, 60, 60};
        g.rowHeights = new int[] {66, 66, 66, 66};
        frm.getContentPane().setLayout(g);
        
        // 名字row
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        frm.getContentPane().add(this.setTextField("名字"), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        frm.getContentPane().add(name, c);
        
        // 輸入帳號row
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        frm.getContentPane().add(this.setTextField("帳號："), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        frm.getContentPane().add(account, c);

        // 輸入密碼row
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        frm.getContentPane().add(this.setTextField("密碼："), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        frm.getContentPane().add(password, c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton passwordBut = new JButton("註冊完畢");
        passwordBut.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                check();

            }
        });
        frm.getContentPane().add(passwordBut, c);

        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(false);

    }

    private JTextField setTextField(String str) {
        JTextField tex = new JTextField();
        tex.setText(str);
        tex.setOpaque(false);
        tex.setEditable(false);
        tex.setBorder(null);
        tex.setFont(new Font("標楷體", 1, 14));
        return tex;
    }

    // 檢查完執行連線註冊
    private void check() {
        // 去掉頭尾空白
        String name = this.name.getText().trim();
        String account = this.account.getText().trim();
        char[] c = this.password.getPassword();
        String password = "";

        for (char ch : c) {
            password += ch;
        }

        password = password.trim();
        // 清空欄位
        this.name.setText("");
        this.account.setText("");
        this.password.setText("");

        // 判斷上述資料是否為空
        if (name.isEmpty() || account.isEmpty() || password.isEmpty()) {
            this.showMess("上述資料不可為空", "錯誤");
            return;
        }

        // 檢查名字裡是否只有中文英文數字
        if (!name.matches("[\u4E00-\u9FA5_a-zA-Z_0-9]*")) {
            this.showMess("名字不可有特殊字元", "名字錯誤");
            return;
        }

        // 檢查帳號密碼是否裡面只有英文數字
        if (!account.matches("[a-zA-Z_0-9]*") || !password.matches("[a-zA-Z_0-9]*")) {
            this.showMess("帳號密碼只能有英文及數字", "帳密錯誤");
            return;
        }

        this.socket(name, account, password);
    }

    private void socket(String name, String account, String password) {
        TSocket t = new TSocket();
        String s = t.signUpSocket(name, account, password);
        t = null;
        
        if (s.equals("success")) {
            this.showMess("創建成功", "註冊");
            frm.setVisible(false);
            loginGUI.frm.setVisible(true);
        } else if(s.equals("noName")) {
            this.showMess("創建失敗，有人與你的名字相同", "註冊");
        } else if(s.equals("noAccount")) {
            this.showMess("創建失敗，有人與你的帳號相同", "註冊");
        }
    }

    private void showMess(String mess, String title) {
        JOptionPane.showMessageDialog(null, mess, title, JOptionPane.PLAIN_MESSAGE);
    } 
}
