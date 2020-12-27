package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

public class serverThread extends Thread implements Runnable {

    private ServerSocket ss;
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

         // 定時傳送目前上線人數
         new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    while (true) {
                        // 發送目前人數
                        bw.write(String.format("people/%d\r\n", server.count));
                        bw.flush();
                        Thread.sleep(1000);
                    }
                } catch (Exception ex) {
                    closeClient();
                    server.removePeople();
                    stopServer();
                }

            }
            
        }).start();

    }
    
    @Override
    public void run() {
        try {
            String str;
            while (true) {
                str = br.readLine();
                server.mess.add(str);
                System.out.println(str);

                // 接收傳到各個人上
                // test
                server.sendAll = true;
            }

        } catch (Exception ex) {
            closeClient();
            server.removePeople();
            stopServer();         
        }

    }

    // 如果有人斷線就人數減
    private synchronized void closeClient() {
        try {
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("有人跳出或者出問題了");
        server.count--;
        System.out.printf("目前總共有%d個人在聊天室\r\n", server.count);
    }

    private void stopServer() {
        try{
            this.interrupt();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("停止出問題了");
        }
    }

}
