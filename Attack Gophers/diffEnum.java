

// 難度
public enum diffEnum {
    
    sel("請選擇難度", 0),
    easy("簡單", 1200),
    normal("普通", 900),
    difficult("困難", 600);


    public String no;
    public int speed;

    diffEnum(String no, int speed) {
        this.no = no;
        this.speed = speed;
    }

    // 取得該物件列舉
    public static diffEnum getdiffEnum(String diff) {
        for (diffEnum de : diffEnum.values()) {
            if (de.no.equals(diff)) {
                return de;
            }
        }
        // 不應該執行到這
        return null;
    }
}