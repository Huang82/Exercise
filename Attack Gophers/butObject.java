
import javax.swing.ImageIcon;
import javax.swing.JButton;

/* 按鈕物件 */
public class butObject {
    
    public JButton but = new JButton();
    public int state;   //0代表沒出 1代表出來了
    public int no;
    public String URL;

    public butObject(int no) {
        this.no = no;
        this.URL = URLenum.normal.URL;
        this.state = URLenum.normal.state;
        but.setIcon(new ImageIcon(URL));
        but.setOpaque(false);
        but.setBorder(null);
        but.setContentAreaFilled(false);
    }

    // 按鈕初始化
    public void initBut() {
        this.URL = URLenum.normal.URL;
        this.state = URLenum.normal.state;
        but.setIcon(new ImageIcon(URL));
    }

    // 設置地鼠
    public void setMonster() {
        this.URL = URLenum.out.URL;
        this.state = URLenum.out.state;
        but.setIcon(new ImageIcon(URL));
    }

}
