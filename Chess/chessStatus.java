
// 棋狀態
public enum chessStatus {
    miss("miss"),
    open("open"),
    air("air");

    public String no;

    chessStatus (String no) {
        this.no = no;
    }
}