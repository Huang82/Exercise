import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;

public class GUI {

    public JFrame frm = new JFrame("Snake!!");
    public JPanel mainPan = new JPanel();
    public ArrayList<background> backgroundsData = new ArrayList<background>();
    public ArrayList<player> playerData = new ArrayList<player>();

    // 速度
    public int speed;
    // 場地大小
    public int SizeX = 10;
    public int SizeY = 10;

    // 方向
    public int f;

    public GUI() {
        frm.setSize(515, 537);
        frm.setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().width - frm.getSize().width) / 2),
                         (int)((Toolkit.getDefaultToolkit().getScreenSize().height - frm.getSize().height) / 2));
        frm.setLayout(null);
        mainPan.setLayout(new GridBagLayout());
        mainPan.setBounds(-50, -50, 600, 600);
        // 設置背景(黑窗)
        for (int i = 0; i < SizeX; i++) {
            for (int j = 0; j < SizeY; j++) {
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = j;
                c.gridy = i;
                c.anchor = GridBagConstraints.CENTER;
                background bg = new background(j, i, ImageEnum.black.no, ImageEnum.black.URL);
                backgroundsData.add(bg);
                mainPan.add(bg.lab, c);
            }
        }

        // 玩家初始化
        this.initPlayer();

        // 載入money
        new money();

        /* 按鍵事件 */
        frm.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent c) {
                if (f == KeyEvent.VK_UP) {
                    if (c.getKeyCode() != KeyEvent.VK_DOWN) {
                        f = c.getKeyCode();
                    }
                } else if (f == KeyEvent.VK_RIGHT) {
                    if (c.getKeyCode() != KeyEvent.VK_LEFT) {
                        f = c.getKeyCode();
                    }
                } else if (f == KeyEvent.VK_DOWN) {
                    if (c.getKeyCode() != KeyEvent.VK_UP) {
                        f = c.getKeyCode();
                    }
                } else if (f == KeyEvent.VK_LEFT) {
                    if (c.getKeyCode() != KeyEvent.VK_RIGHT) {
                        f = c.getKeyCode();
                    }
                }
            }
        });

        frm.add(mainPan);
        frm.setResizable(false);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
        game();
    }

    // 重新布置背景(全黑)
    private void reBackground() {
        for (int i = 0 ; i < backgroundsData.size() ; i++) {
            background t = backgroundsData.get(i);
            t.reBackground();
        }
    }

    // 初始化玩家
    private void initPlayer() {
        // 預設速度
        speed = 250;
        // 方向預設往上
        f = KeyEvent.VK_UP;
        playerData.clear();
        // 創立頭部
        player p = new player(ImageEnum.head.no, ImageEnum.head.URL, 0, SizeY - 1);
        // 創立身體
        player b1 = new player(ImageEnum.body.no, ImageEnum.body.URL, 0, SizeY);
        player b2 = new player(ImageEnum.body.no, ImageEnum.body.URL, 0, SizeY + 1);
        player tail = new player(ImageEnum.body.no, ImageEnum.body.URL, 0, SizeY + 2);
        playerData.add(p);
        playerData.add(b1);
        playerData.add(b2);
        playerData.add(tail);
    }

    private void game() {
        player head = playerData.get(0);
        while (true) {

            // 身體跟著頭部一起更動(座標是取前一個的座標)
            for (int i = playerData.size() - 1; i >= 1; i--) {
                player p = playerData.get(i);
                player p2 = playerData.get(i - 1);

                p.x = p2.x;
                p.y = p2.y;

                background t = backgroundsData.stream().filter(c -> c.x == p.x && c.y == p.y).findFirst().orElse(null);

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

            // 頭吃到金幣
            if (head.x == money.x && head.y == money.y) {
                player tp = playerData.get(playerData.size() - 1);
                player b = new player(ImageEnum.body.no, ImageEnum.body.URL, tp.x, tp.y);

                playerData.add(b);

                money.x = -1;
                money.y = -1;
                System.out.println("吃");
                // 每吃一個金幣加快
                speed -= 5;
            }

            // 尋找場地目前有無金幣
            boolean isSetMonay = backgroundsData.stream().anyMatch(c -> c.no == money.no);

            
            
            // 無金幣設置
            if (!isSetMonay) {
                while (true) {
                    int x = (int) (Math.random() * SizeX);
                    int y = (int) (Math.random() * SizeY);
                    // 產生的亂數是否有跟玩家重疊到
                    boolean isSnake = backgroundsData.stream().anyMatch(
                        c -> (c.x == x && c.y == y) && (c.no == ImageEnum.head.no || c.no == ImageEnum.body.no));
                        if (!isSnake) {
                            System.out.println(x + " - " + y);
                            background Tmoney = backgroundsData.stream().filter(c -> c.x == x && c.y == y).findFirst()
                            .orElse(null);
                            money.x = x;
                            money.y = y;
                            Tmoney.setStatus(money.no, money.URL);
                            break;
                        }
                    }
                }
                
                // 依目前狀態而改變方向
                switch (f) {
                    case KeyEvent.VK_UP:
                    // System.out.println("上");
                    head.y--;
                    break;
                    case KeyEvent.VK_RIGHT:
                    // System.out.println("右");
                    head.x++;
                    break;
                    case KeyEvent.VK_DOWN:
                    // System.out.println("下");
                    head.y++;
                    break;
                    case KeyEvent.VK_LEFT:
                    // System.out.println("左");
                    head.x--;
                    break;
                }
                
                // 上方座標控制完抓取下一個位置
                background Thead = backgroundsData.stream().filter(c -> c.x == head.x && c.y == head.y).findFirst()
                .orElse(null);
                
                // 如果Thead取不到表示撞到牆了
                if (Thead == null) {
                    System.out.println("遊戲結束");
                    break;
                }

                Thead.setStatus(head.no, head.URL);
                
                System.out.println(head.x + " " + head.y);
                
                // 是否結束遊戲
                boolean isGameover = false;
                // 頭是否有撞到身體(結束遊戲)
                for (int i= 1 ; i < playerData.size() ; i++) {
                    player tBody = playerData.get(i);
                    if (head.x == tBody.x && head.y == tBody.y) {
                        isGameover = true;
                        break;
                    }
                }
    
                if (isGameover) {
                    System.out.println("結束遊戲");
                    break;
                }
                
                try {
                    Thread.sleep(speed);
                } catch (Exception ex) {
                    System.out.println(ex);
                    break;
                }
            }
        // 結束遊戲選項
        String str[] = {"重玩", "離開"};
        int sel = JOptionPane.showOptionDialog(null, String.format("獲得分數： %d", (playerData.size() - 4)), "GameOver!!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, str, null);
        if (sel == JOptionPane.CLOSED_OPTION || sel == JOptionPane.NO_OPTION) {
            System.exit(0);
        }else if(sel == JOptionPane.OK_OPTION) {
            this.reBackground();
            this.initPlayer();
            money.init();
            game();
        }
    }

}
