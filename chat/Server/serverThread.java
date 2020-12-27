package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

public class serverThread extends Thread implements Runnable {

    private ServerSocket ss;
    private String name = Integer.toString((int)(Math.random() * 100));
    public Socket c;
    private InputStreamReader sr;
    private BufferedReader br;
    public OutputStreamWriter os;
    public BufferedWriter bw;

    
    serverThread(Socket c, ServerSocket ss) {
        this.c = c;
        this.ss = ss;
        try {
            // Client連線近來
            sr = new InputStreamReader(c.getInputStream());
            br = new BufferedReader(sr);

            os = new OutputStreamWriter(c.getOutputStream());
            bw = new BufferedWriter(os);
            // client進入歡迎訊息
            bw.write("歡迎來到我的聊天室\r\n");
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // 隨時接收client的訊息
    @Override
    public void run() {
        try {
            String str;
            while (true) {
                // 讀取Client傳過來的資料
                str = br.readLine();
                // 接收到傳送給全部的人
                server.allSendMess(str);
                System.out.println("有人發訊息了");
                System.out.println(str);
                // 接收傳到各個人上
                // test
            }

        } catch (Exception ex) {
            try {
                c.close();
                System.out.println(getClientName() + "以斷線");        
            } catch (Exception e) {
                System.out.println("斷線失敗");
            }
        }

    }

    public String getClientName() {
        return this.name;
    }

}
