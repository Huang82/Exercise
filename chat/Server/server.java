package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class server {
    
    public static int count = 0;    // 上線人數
    public static ArrayList<serverThread> serverThreadsData = new ArrayList<serverThread>();   // 連線的人的資料
    public static ArrayList<serverThread> removeData = new ArrayList<serverThread>();
    public static ArrayList<String> mess = new ArrayList<String>();                     // 訊息資料
    public static boolean sendAll = false;                                              // 如果有人發送訊息就true


    public server() {
        System.out.println("Server啟動");
        try {
            ServerSocket server = new ServerSocket(9999);
            Socket c;

            // 只要有人發布訊息就發給全部上線的人
            new Thread(new Runnable(){

                @Override
                public void run() {
                    while(true){
                        try {
                            // 聊天室必須要有一個人和有人發送訊息
                            if(serverThreadsData.size() != 0 && sendAll == true){
                                for(serverThread a : serverThreadsData) {
                                    // 如果資料裡有人斷線(未連線)就剔除掉
                                    if (a.c.isClosed()) {
                                        System.out.println("有人要被刪除了");
                                        removeData.add(a);
                                        continue;
                                    }
                                    sendMess(a);
                                }
                                sendAll = false;
                                mess.clear();
                            }
                            // 如果有人下線就必須刪除上限中裡的資料
                            if (removeData.size() != 0) {
                                for (serverThread a : removeData) {
                                    serverThreadsData.remove(a);
                                }
                                removeData.clear();
                            }

                            Thread.sleep(500);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            System.out.println("發布訊息出問題了");
                            break;
                        }
                    }
                }
                
            }).start();
            
           




            while (true) {
                System.out.println("等待新的Client端連線進來");
                System.out.println(String.format("目前總共有%d個人在聊天室",count));
                c = server.accept();
                
                // 只要有一個新的Client就多開一個執行續(負責處理接收Client的訊息)
                serverThread st = new serverThread(c, server);
                serverThreadsData.add(st);
                Thread t = new Thread(st);
                t.start();

                count++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // 把訊息陣列裡的都拿出來發給client
    public static void sendMess(serverThread st) {
        try{
            for (String str : mess){
                str = str + "\r\n";
                st.bw.write(str);
                st.bw.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("發送出錯了");
        }
    }

    public static void removePeople() {
        for(serverThread a : serverThreadsData) {
            // 如果資料裡有人斷線(未連線)就剔除掉
            if (a.c.isClosed()) {
                System.out.println("有人要被刪除了");
                removeData.add(a);
                continue;
            }
            sendMess(a);
        }
        sendAll = false;
        mess.clear();

        // 如果有人下線就必須刪除上限中裡的資料
        if (removeData.size() != 0) {
            for (serverThread a : removeData) {
                serverThreadsData.remove(a);
            }
            removeData.clear();
        }
        
    }
}
