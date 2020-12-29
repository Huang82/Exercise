package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class server {
    
    public static int count = 0;    // 上線人數
    public static ArrayList<serverThread> serverThreadsData = new ArrayList<serverThread>();   // 連線的人的資料
    public static ArrayList<serverThread> removeData = new ArrayList<serverThread>();
    public static ArrayList<clientData> clientDatas = new ArrayList<clientData>();
    public static ArrayList<Thread> arrThread = new ArrayList<Thread>(); 
    public static ArrayList<String> mess = new ArrayList<String>();                     // 訊息資料
    public static boolean sendAll = false;                                              // 如果有人發送訊息就true


    public server() {
        System.out.println("Server啟動");

        // 新增Client執行序
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    ServerSocket server = new ServerSocket(9999);
                    Socket c;
        
                    // 客戶端連線就新開一個Thread
                    while(true){
                        // 等待新的Clinet
                        System.out.println("等待新的Client進來");
                        c = server.accept();
                        serverThread st = new serverThread(c, server);
                        Thread th = new Thread(st);
                        serverThreadsData.add(st);
                        arrThread.add(th);
                        th.start();
                        count++;
                        System.out.println("有新的Client進來了，目前有 " + count + " 個人");
                    }
        
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("創立Client錯誤");
                }
            }
        }).start();

        // 傳送人數和名字給所有的Client
        new Thread(new Runnable(){

            @Override
            public void run() {
                while(true) {
                    try {
                        String str = "people/" + Integer.toString(count) + "/";
                        for (serverThread st : serverThreadsData) {
                            str += st.getClientName() + ",";
                        }
                        Thread.sleep(1000);
                        allSendMess(str);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("");
                    } 
                   
                }
            }
        }).start();

        // 檢查Client是否有斷線，斷線就替除掉上線
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1000);
                        Object[] st = serverThreadsData.stream().filter(e -> (e.c.isClosed() == true)).toArray();
                        if (st.length != 0) {
                            int c = 0;
                            for (Object s : st) {
                                // 取得資料的index(2比陣列是同步增加的所以是一樣的)
                                int index = serverThreadsData.indexOf((serverThread)s);
                                // 刪除資料 + 停止Thread
                                serverThreadsData.remove(index);
                                arrThread.get(index).interrupt();
                                arrThread.remove(index);
                                c++;
                            }
                            count -= c;
                            System.out.println("刪除成功");
                        }
                    } catch (Exception ex) {
                        System.out.println("刪除資料出問題了");
                    }
                }
            }
        }).start();

    }
    
    // 傳送給所有人訊息
    public static void allSendMess(String str) {
        
        str = str + "\r\n";
        for (serverThread st : serverThreadsData) {
            try {
                st.bw.write(str);
                st.bw.flush();
            } catch (Exception ex) {
                System.out.println(st.getClientName() + "以斷線，所以無法傳給他");
            }
        }
    }

    // 確認名字是否有重複
    public static boolean checkName(String name) {
        boolean s = clientDatas.stream().anyMatch(e -> e.name.equals(name));
        return s;
    }

    // 檢查帳號是否有重複
    public static boolean checkAccount(String account) {
        boolean s = clientDatas.stream().anyMatch(e -> e.account.equals(account));
        return s;
    }

    // 登入檢查
    public static clientData checkSignIn(String account, String password) {
        clientData s = (clientData)clientDatas.stream()
                        .filter(e -> (e.account.equals(account) && e.password.equals(password)))
                        .findFirst().orElse(null);
        return s;
    }

}
