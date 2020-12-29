package Client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class loginGUI {
    
    public static JFrame frm = new JFrame("登入");
    private JTextField account = new JTextField();
    private JTextField password = new JTextField();

    public loginGUI() {
        frm.setSize(300, 200);
        frm.setLocationRelativeTo(null);
        frm.setResizable(false);
        GridBagLayout g = new GridBagLayout();
        g.columnWidths = new int[] {60, 60, 60, 60, 60};
        g.rowHeights = new int[] {66, 66, 66};
        frm.getContentPane().setLayout(g);
        // 輸入帳號row
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        frm.getContentPane().add(this.setTextField("帳號："), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        frm.getContentPane().add(account, c);

        // 輸入密碼row
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        frm.getContentPane().add(this.setTextField("密碼："), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        frm.getContentPane().add(password, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton loginBut = new JButton("登入");
        loginBut.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                check();
            }
        });
        frm.getContentPane().add(loginBut, c);

        c = new GridBagConstraints();
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton passwordBut = new JButton("註冊");
        passwordBut.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                frm.setVisible(false);
                signUpGUI.frm.setVisible(true);
            }
        });
        frm.getContentPane().add(passwordBut, c);

        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }
    
    // 設置文字欄
    private JTextField setTextField(String str) {
        JTextField tex = new JTextField();
        tex.setText(str);
        tex.setOpaque(false);
        tex.setEditable(false);
        tex.setBorder(null);
        tex.setFont(new Font("標楷體", 1, 14));
        return tex;
    }

    private void check() {
        // 去掉頭尾空白
        String account = this.account.getText().trim();
        String password = this.password.getText().trim();
        // 清空欄位
        this.account.setText("");
        this.password.setText("");

        // 判斷上述資料是否為空
        if (account.isEmpty() || password.isEmpty()) {
            this.showMess("請填入帳號密碼", "錯誤");
            return;
        }

        // 檢查帳號密碼是否裡面只有英文數字
        if (!account.matches("[a-zA-Z_0-9]*") || !password.matches("[a-zA-Z_0-9]*")) {
            this.showMess("帳號密碼只能有英文及數字", "帳密錯誤");
            return;
        }

        this.socket(account, password);
    }

    // 傳送資料
    private void socket(String account, String password) {
        TSocket t = new TSocket();
        String s = t.signInSocket(account, password);
        String[] arr = s.split("/");
        
        t = null;
        // arr[0]是signIn進代表登入
        if (arr[0].equals("signIn")) {
            String name = arr[1];
            // 從server傳送資料給client
            new client(new Client.ClientData(name, account, password));
            new GUI();
            frm.setVisible(false);
        } else {
            this.showMess("登入失敗\r\n請檢查帳密是否正確", "登入失敗");
        }
    }


    private void showMess(String mess, String title) {
        JOptionPane.showMessageDialog(null, mess, title, JOptionPane.PLAIN_MESSAGE);
    } 
}
