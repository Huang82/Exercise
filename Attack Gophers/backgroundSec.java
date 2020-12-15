
/* 倒數秒數多執行緒 */
public class backgroundSec extends Thread {
    
    public static String tex1Sec = String.format("剩餘秒數：%s", game.sec);

    @Override
    public void run() {
        while(game.sec != 0) {

            // 等待1秒
            try {
                Thread.sleep(1000);
            } catch(Exception ex) {

            }

            game.sec--;
            tex1Sec = String.format("剩餘秒數：%s", game.sec);
            GUI.tex1.setText(tex1Sec);
        }
        System.out.println("時間已到");
        try{
            this.interrupt();
        } catch (Exception ex) {
            System.out.println("拋出例外");
        }
        // 初始化
        GUI.bs = new backgroundSec();
        // 遊戲結束 開始按鈕可以按
        GUI.but.setEnabled(true);
    }


}
