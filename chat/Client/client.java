package Client;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.*;

class client {
    
    public static playerData testPlayer = new playerData("Huang82", "21118950", "123");
    public static String mess = "";

    public static Socket sk;
    public static OutputStreamWriter bw;   // 輸出到伺服器
    public static BufferedWriter br;       // 輸出到伺服器
    public static InputStreamReader st;        // 從伺服器接收訊息
    public static BufferedReader read;         // 從伺服器接收訊息

    client() {
        System.out.println("Client端上線");
        // Client連上到Server
        connect();
    }

    // Connect
    public static void connect() {
        try{
            sk = new Socket("127.0.0.1", 9999);
            bw = new OutputStreamWriter(sk.getOutputStream());
            br = new BufferedWriter(bw);

            st = new InputStreamReader(sk.getInputStream());
            read = new BufferedReader(st);

            // server傳送給Client的執行序
            new Thread(new Runnable(){
                
                @Override
                public void run() {
                    String str;
                    while(true){
                        System.out.println("run");
                        try{
                            // client讀取server傳來的訊息
                            str = read.readLine();

                            // 狀態機
                            state(str);

                            System.out.println(str);
                            Thread.sleep(500);
                        } catch (Exception ex) {
                            System.out.println("123");
                            ex.printStackTrace();
                        }
                    }

                }
            }).start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // 傳送訊息給伺服器(必須按下傳送按鈕才能傳送)
    public static void send() {
        try {
            // 正在傳送
            System.out.println("傳送中");
            // 寫入flush裡
            api();
            br.write(mess);
            // 清除flush送出到Server
            br.flush();
            System.out.println("傳送完畢");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("伺服器未開或有問題");
        }
    }
    
    // 打包傳給伺服器的String
    private static void api() {
        mess = "chat/" + testPlayer.name + "/" + mess + "\r\n";
        System.out.println(mess);
    }


    private static void state(String str) {
        String t[] = str.split("/");
        String status = t[0];
        
        switch(status) {
            // 聊天
            case "chat":
                String name = t[1];
                String mess = t[2];
                GUI.addMess(String.format("%s ： %s", name, mess));
                break;
            case "people":
                String num = t[1];
                System.out.println(num);
                break;
            // 伺服器直接給予訊息
            default:
                GUI.addMess(status);
                break;

        }
    }

}
