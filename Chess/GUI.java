import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class GUI {
    
    JFrame frm = new JFrame();
    JLabel bel1 = new JLabel(new ImageIcon(".\\Image\\background.png"));
    JLabel bel2 = new JLabel(new ImageIcon(".\\Image\\background.png"));
    JLabel bel3 = new JLabel(new ImageIcon(".\\Image\\background.png"));
    JLabel bel4 = new JLabel(new ImageIcon(".\\Image\\background.png")); 
    JPanel mainPan = new JPanel();

    public GUI(){
        frm.setSize(1000, 700);
        frm.setLayout(new BorderLayout());
        mainPan.setPreferredSize(new Dimension(800, 600));


        frm.getContentPane().add(bel1, BorderLayout.NORTH);
        // frm.getContentPane().add(bel2, BorderLayout.SOUTH);
        // frm.getContentPane().add(bel3, BorderLayout.EAST);
        // frm.getContentPane().add(bel4, BorderLayout.WEST);
        // frm.getContentPane().add(mainPan, BorderLayout.CENTER);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }
}
