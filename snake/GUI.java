import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


import java.util.ArrayList;

public class GUI {

    public JFrame frm = new JFrame();
    public JPanel mainPan = new JPanel();
    public ArrayList<background> backgroundsData = new ArrayList<background>();
    public ArrayList<player> playerData = new ArrayList<player>();

    public int SizeX = 8;
    public int SizeY = 8;

    // 方向
    public int f = KeyEvent.VK_UP;

    public GUI() {
        frm.setSize(600, 600);
        frm.setLayout(null);
        mainPan.setLayout(new GridBagLayout());
        mainPan.setBounds(-50, -50, 600, 600);
        ;
        for (int i = 0; i < SizeX ; i++) {
            for (int j = 0; j < SizeY ; j++) {
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = j;
                c.gridy = i;
                c.anchor = GridBagConstraints.CENTER;
                background bg = new background(j, i, ImageEnum.black.no, ImageEnum.black.URL);
                backgroundsData.add(bg);
                mainPan.add(bg.lab, c);
            }
        }

        // 創立頭部
        player p = new player(ImageEnum.head.no, ImageEnum.head.URL, 0, 7);
        // 創立身體
        player b1 = new player(ImageEnum.body.no, ImageEnum.body.URL, 0, 8);
        player b2 = new player(ImageEnum.body.no, ImageEnum.body.URL, 0, 9);
        player tail = new player(ImageEnum.body.no, ImageEnum.body.URL, 0, 10);
        playerData.add(p);
        playerData.add(b1);
        playerData.add(b2);
        playerData.add(tail);

        /* 按鍵事件 */
        frm.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent c) {
                f = c.getKeyCode();
            }
        });

        frm.add(mainPan);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
        game();
    }

    private void game() {
        player head = playerData.get(0);
        while (true) {
            System.out.println(playerData.size());
            for (int i = playerData.size() - 1; i >= 1; i--) {
                player p = playerData.get(i);
                player p2 = playerData.get(i - 1);

                p.x = p2.x;
                p.y = p2.y;

                background t = backgroundsData.stream()
                                .filter(c -> c.x == p.x && c.y == p.y)
                                .findFirst().orElse(null);

                if (t == null) {
                    continue;
                }

                t.setStatus(p.no, p.URL);

            }

            // 搜尋尾巴把尾巴變黑
            background t = backgroundsData.stream().filter(c -> c.x == playerData.get(playerData.size() - 1).x
                    && c.y == playerData.get(playerData.size() - 1).y).findFirst().orElse(null);
            // 還原(黑)
            if (t != null) {
                t.reBackground();
            }

            // 尋找場地目前有無金幣
            boolean isSetMonay = backgroundsData.stream().anyMatch(c -> c.no == money.no);
            
            // 無金幣設置
            if (!isSetMonay) {
                System.out.println("1111111111");
                while (true){
                    int x = (int)(Math.random() * SizeX);
                    int y = (int)(Math.random() * SizeY);
                    // 產生的亂數是否有跟玩家重疊到
                    boolean isSnake = backgroundsData.stream()
                                    .anyMatch(c -> (c.x == x && c.y == y) &&
                                        (c.no == ImageEnum.head.no || c.no == ImageEnum.body.no));
                        if (!isSnake) {
                            background Tmoney = backgroundsData.stream().filter(c -> c.x == x && c.y == y).findFirst().orElse(null);
                            Tmoney.setStatus(money.no, money.URL);
                            break;
                        }
                }

            }

            switch (f) {
                case KeyEvent.VK_UP:
                    System.out.println("上");
                    head.y--;
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("右");
                    head.x++;
                    break;
                case KeyEvent.VK_DOWN:
                    System.out.println("下");
                    head.y++;
                    break;
                case KeyEvent.VK_LEFT:
                    System.out.println("左");
                    head.x--;
                    break;
            }

            background Thead = backgroundsData.stream().filter(c -> c.x == head.x && c.y == head.y).findFirst()
                    .orElse(null);

            Thead.setStatus(head.no, head.URL);
            // ??????????
            if (head.x == money.x && head.x == money.y) {
                player tp = playerData.get(playerData.size() - 1);
                player b = new player(ImageEnum.body.no, ImageEnum.body.URL, tp.x, tp.y);

                System.out.println("吃");
            }

            try {
                Thread.sleep(500);
            } catch (Exception ex) {
                System.out.println(ex);
                break;
            }
        }
        System.out.println("跳出");
    }

}
