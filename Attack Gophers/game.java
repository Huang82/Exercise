
import java.util.ArrayList;
import java.awt.event.*;

/* game多執行緒 */
public class game extends Thread {

    public diffEnum diff;
    public ArrayList<butObject> butData;
    public static int sec = 30;

    @Override
    public void run() {
        this.butData = GUI.butData;
        while (sec != 0) {
            /* 隨機亂數冒出地鼠 */
            int no = (int) (Math.random() * butData.size());
            butObject tBut = butData.stream().filter(c -> c.no == no).findFirst().orElse(null);
            tBut.setMonster();
            /* 只有選中的可以按 */
            tBut.but.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (tBut.state == URLenum.out.state) {
                        GUI.score++;
                        tBut.initBut();
                        GUI.tex2Score = String.format("得分：%s", GUI.score);
                        GUI.tex2.setText(GUI.tex2Score);
                        System.out.println(GUI.score);
                        System.out.println("打到了");
                    }
                }
            });

            // 等待時間
            try{
                Thread.sleep(diff.speed);
            } catch (Exception ex) {

            }

            tBut.but.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (tBut.state == URLenum.out.state) {
                        GUI.score++;
                        tBut.initBut();
                        GUI.tex2Score = String.format("得分：%s", GUI.score);
                        GUI.tex2.setText(GUI.tex2Score);
                        System.out.println(GUI.score);
                        System.out.println("打到了");
                    }
                }
            });
            


            // 初始化按鈕
            tBut.initBut();
        }


        // 停止多執行序
        try{
            this.interrupt();
        } catch (Exception ex) {
            System.out.println("拋出例外");
        }
        System.out.println("game停止");
        // 初始化
        this.initSec();
        GUI.game = new game();
    }

    // 設定難度
    public void setDiff(String diff) {
        this.diff = diffEnum.getdiffEnum(diff);
    }

    // 初始化秒數
    public void initSec() {
        game.sec = 30;
    }
}
