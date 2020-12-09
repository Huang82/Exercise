
/* 象棋權重 */
public enum chessWeight {

    red_0("red_0", 0, ".\\Image\\red_0.png", PlayerColor.red.name()), // 砲
    red_1("red_1", 1, ".\\Image\\red_1.png", PlayerColor.red.name()), // 兵
    red_2("red_2", 2, ".\\Image\\red_2.png", PlayerColor.red.name()), // 馬
    red_3("red_3", 3, ".\\Image\\red_3.png", PlayerColor.red.name()), // 車
    red_4("red_4", 4, ".\\Image\\red_4.png", PlayerColor.red.name()), // 象
    red_5("red_5", 5, ".\\Image\\red_5.png", PlayerColor.red.name()), // 士
    red_6("red_6", 6, ".\\Image\\red_6.png", PlayerColor.red.name()), // 帥
    black_0("black_0", 0, ".\\Image\\black_0.png", PlayerColor.black.name()), 
    black_1("black_1", 1, ".\\Image\\black_1.png", PlayerColor.black.name()),
    black_2("black_2", 2, ".\\Image\\black_2.png", PlayerColor.black.name()), 
    black_3("black_3", 3, ".\\Image\\black_3.png", PlayerColor.black.name()),
    black_4("black_4", 4, ".\\Image\\black_4.png", PlayerColor.black.name()), 
    black_5("black_5", 5, ".\\Image\\black_5.png", PlayerColor.black.name()),
    black_6("black_6", 6, ".\\Image\\black_6.png", PlayerColor.black.name());

    public String no;
    public int weight;
    public String URL;
    public String playerColor;

    chessWeight(String no, int weight, String URL, String playerColor) {
        this.no = no;
        this.weight = weight;
        this.URL = URL;
        this.playerColor = playerColor;
    }

    public static enum PlayerColor {
        red, black;
    }

}