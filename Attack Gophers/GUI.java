
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GUI implements ActionListener{
    
    private JFrame frm = new JFrame("Attack Gophers!!");
    private JPanel mainPan = new JPanel();

    public GUI() {
        frm.setSize(800, 800);
        mainPan.setBounds(0, 0, frm.getSize().width, frm.getSize().height);
        mainPan.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        for (int i = 0 ; i < 3 ; i++) {
            for (int j = 0 ; j < 3 ; j++) {
                JButton but = new JButton("test");
                c.gridx = j;
                c.gridy = i;
                but.addActionListener(this);
                mainPan.add(but, c);
            }
        }




        frm.getContentPane().add(mainPan);
        frm.setVisible(true);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("TEST");
    }
}
