package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

public class serverThread extends Thread implements Runnable {

    private ServerSocket ss;
    private String name;
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
                this.state(str);
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
    
    // 取得客戶端名字
    public String getClientName() {
        return this.name;
    }
    
    // 判斷Client傳過來的是甚麼內容再下去做動作
    public void state(String str) {
        String[] arr = str.split("/");
        String s = arr[0];
            switch (s) {
                case "chat":
                    server.allSendMess(str);
                    break;
                // 註冊
                case "signUp":
                    this.signUp(arr);
                    break;
                case "signIn":
                    this.signIn(arr);
                    break;
                // 如果client傳送name表示他登入帳號成功
                case "name":
                    String name = arr[1];
                    this.name = name;
                    try {
                        bw.write("歡迎來到慶煌的聊天室\r\n");
                        bw.flush();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
        }
    }

    // client傳送註冊處理
    private void signUp(String[] arr) {
        this.name = arr[0];
        String name = arr[2];
        String account = arr[3];
        String password = arr[4];
        try {
            // 檢查姓名是否重複
            if (server.checkName(name)) {
                bw.write("noName\r\n");
                bw.flush();
                return;
            } 

            // 檢查帳號是否重複
            if (server.checkName(account)) {
                bw.write("noAccount\r\n");
                bw.flush();
                return;
            } 

            server.clientDatas.add(new clientData(name, account, password));
            
            bw.write("success\r\n");
            bw.flush();
            c.close();
            System.out.println("創建成功");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // 登入傳送處理
    private void signIn(String[] arr) {
        System.out.println("ssssssssssssssssssss");
        String account = arr[2];
        String password = arr[3];
        System.out.println(account+ " | " + password);
        try {
            // 檢查是否有人註冊此帳密
            clientData client = server.checkSignIn(account, password);
            // 檢查帳密是否有人註冊
            if (client == null) {
                System.out.println("沒有人註冊過!!");
                bw.write("noSignIn\r\n");
                bw.flush();
                return;
            }

            String name = client.name;
            // 傳送client資料
            String loginStr = String.format("signIn/%s/%s/%s\r\n", name, account, password);
            bw.write(loginStr);
            bw.flush();
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
