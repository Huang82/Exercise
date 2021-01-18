import javax.swing.ImageIcon;


public class recoveryChess {
    
    public chessObject chess1;
    public chessObject chess2;
    public String image1;       // 棋原本圖片
    public String image2;
    public String status1;      // 棋原本狀態
    public String status2;
    public String color1;       // 棋顏色方
    public String color2;
    public String no1;          // 棋原本名字
    public String no2;
    public String id1;
    public String id2;
    public String name1;
    public String name2;
    public int weight1;
    public int weight2;


    public recoveryChess(chessObject chess1){
        this.chess1 = chess1;
        this.image1 = chess1.URL;
        this.status1 = chess1.status;
    }

    public recoveryChess(chessObject chess1, chessObject chess2){
        this.chess1 = chess1;
        this.chess2 = chess2;
        this.image1 = chess1.URL;
        this.image2 = chess2.URL;
        this.status1 = chess1.status;
        this.status2 = chess2.status;
        this.color1 = chess1.playerColor;
        this.color2 = chess2.playerColor;
        this.no1 = chess1.no;
        this.no2 = chess2.no;
        this.id1 = chess1.chessId;
        this.id2 = chess2.chessId;
        this.name1 = chess1.but.getName();
        this.name2 = chess2.but.getName();
        this.weight1 = chess1.weight;
        this.weight2 = chess2.weight;
    }

    public void recovery() {

        // 如果chess2為空代表只是翻牌
        if (chess2 == null) {
            System.out.println("recoveryChess : chess2為空");
            this.chess1.but.setIcon(new ImageIcon(".\\Image\\miss.png"));
            this.chess1.status = this.status1;
            return;
        }

        // 如果吃或移動會跑來這邊
        this.chess1.URL = image1;
        this.chess2.URL = image2;
        this.chess1.status = status1;
        this.chess2.status = status2;
        this.chess1.playerColor = color1;
        this.chess2.playerColor = color2;
        this.chess1.no = no1;
        this.chess2.no = no2;
        this.chess1.chessId = id1;
        this.chess2.chessId = id2;
        this.chess1.but.setName(name1);
        this.chess2.but.setName(name2);
        this.chess1.weight = weight1;
        this.chess2.weight = weight2;
        this.chess1.setIcon();
        this.chess2.setIcon();

    }

}
