

public enum ImageEnum {
    
    black("black", ".\\Image\\black.png"),
    head("head", ".\\Image\\head.png"),
    body("body", ".\\Image\\body.png"),
    money("money", ".\\Image\\money.png");
    public String no;
    public String URL;

    ImageEnum(String no, String URL) {
        this.no = no;
        this.URL = URL;
    }

}
