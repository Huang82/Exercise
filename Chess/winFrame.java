
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class winFrame {

    public JFrame frm = new JFrame("Win!!");

    public JButton reset = new JButton("reset");
    public JButton exit = new JButton("exit");
    
    public winFrame() {
        frm.setSize(300, 300);
        frm.setLayout(new GridBagLayout());

        String arr[]= {"aaa", "bbb", "ccc", "ddd"};
        int a = JOptionPane.showOptionDialog(null, "測試", "test", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, arr, "exit");

        System.out.println(a);

        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);

    }

    public static void main(String args[]) {
        new winFrame();
    }

}

