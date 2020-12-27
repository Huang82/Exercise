package Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    
    private JFrame frm = new JFrame("聊天");

    private static JTextArea tex = new JTextArea(); // 聊天視窗
    private static String str = "";                 // 聊天視窗裡的訊息
    
    private JTextField chat = new JTextField();
    private JButton send = new JButton("傳送");
    
    private JTextField peopleText = new JTextField();
    private DefaultTableModel dlm;
    private JTable peopleName;

    public GUI() {
        frm.setBounds(200, 200, 600, 600);
        GridBagLayout gbl = new GridBagLayout();
        frm.getContentPane().setLayout(gbl);
        gbl.columnWidths = new int[] {75, 75, 75, 75, 75, 75};
        gbl.rowHeights = new int[] {75, 75, 75, 20};

        // 聊天室人數
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        peopleText.setEditable(false);
        peopleText.setOpaque(false);
        peopleText.setBorder(null);
        peopleText.setHorizontalAlignment(JTextField.CENTER);
        peopleText.setText(String.format("聊天室目前上線人數：%d", 20));
        frm.getContentPane().add(peopleText, c);

        // 聊天室進來聊天的人清單
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 4;
        c.fill = GridBagConstraints.BOTH;
        // 清單設置
        dlm = new DefaultTableModel(new String[][] {}, new String[] {"上線清單"}){
            /**
             *
             */
            private static final long serialVersionUID = 1L;
            // 讓它無法編輯
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // table加進清單模式
        peopleName = new JTable(dlm);
        peopleName.setBackground(Color.white);
        frm.getContentPane().add(new JScrollPane(peopleName), c);

        // test
        dlm.addRow(new String[] {"Huang82"});
        dlm.addRow(new String[] {"abccddd"});

        // 對話框
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 3;
        c.fill = GridBagConstraints.BOTH;
        // 讓輸入訊息到底自動換行
        tex.setLineWrap(true);
        // 禁止編輯
        tex.setEditable(false);
        // 讓文字框有卷軸
        JScrollPane sp = new JScrollPane(tex);
        frm.getContentPane().add(sp, c);


        // 輸入欄
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        frm.getContentPane().add(chat, c);
        
        // 傳送鈕
        c = new GridBagConstraints();
        c.gridx = 5;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        frm.getContentPane().add(send, c);
        // 按下按鈕的會做出的反應
        send.addActionListener(e -> {
            client.mess = chat.getText();
            client.send();
            chat.setText("");
        });
        
        
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }


    public static void addMess(String s) {
        str += s + "\r\n";
        tex.setText(str);
    }
}
