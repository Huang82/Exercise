package Client;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

// 註冊登入在連的
public class TSocket {
    
    public final String name = "_user";
    public Socket ket;
    public OutputStreamWriter osw;
    public BufferedWriter bw;
    public InputStreamReader isr;
    public BufferedReader br;

    public TSocket() {
        try {
            ket = new Socket(IpPort.add.ip, IpPort.add.port);
            osw = new OutputStreamWriter(ket.getOutputStream());
            bw = new BufferedWriter(osw);
            isr = new InputStreamReader(ket.getInputStream());
            br = new BufferedReader(isr);
        
        } catch (Exception ex) {
            System.out.println("連線失敗");
        }
    }

    // 註冊專用傳輸資料
    public String signUpSocket(String name, String account, String password) {
        String str = String.format("%s/%s/%s/%s/%s\r\n", "signUp", this.name, name, account, password);
        try {
            bw.write(str);
            bw.flush();

            // 如果server傳送success代表註冊成功
            String s = br.readLine();
            ket.close();
            bw.close();
            osw.close();
            br.close();
            ket.close();
            return s;

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("傳送失敗");
        }
        // 不應該跑來這除非傳送失敗
        return "";
    }

    // 登入專用傳送資料
    public String signInSocket(String account, String password) {
        String str = String.format("%s/%s/%s/%s\r\n", "signIn", this.name, account, password);
        try {
            bw.write(str);
            bw.flush();

            // 如果server傳送success代表登入成功
            String s = br.readLine();
            ket.close();
            bw.close();
            osw.close();
            br.close();
            ket.close();
            return s;

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("傳送失敗");
        }

        // 不應該跑來這邊
        return "";
    }

}
