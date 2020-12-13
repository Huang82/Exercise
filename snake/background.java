import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class background {
    
    public JLabel lab = new JLabel();
    public String no;
    public int x;
    public int y;

    background (int x, int y, String no, String Image) {
        this.x = x;
        this.y = y;
        this.no = no;
        lab.setIcon(new ImageIcon(Image));
    }

    public void setStatus(String no , String Image) {
        this.no = no;
        lab.setIcon(new ImageIcon(Image));
    }

    public void reBackground() {
        this.no = ImageEnum.black.no;
        this.lab.setIcon(new ImageIcon(ImageEnum.black.URL));
    }
}
