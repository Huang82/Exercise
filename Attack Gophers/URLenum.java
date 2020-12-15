
/* 該按鈕有無出現地鼠 */
public enum URLenum {

    normal("no", ".\\Image\\air.png", 0),
    out("yes", ".\\Image\\monster.png", 1);

    public String no;
    public String URL;
    public int state;

    URLenum(String no, String URL, int state) {
        this.no = no;
        this.URL = URL;
        this.state = state;
    }

}