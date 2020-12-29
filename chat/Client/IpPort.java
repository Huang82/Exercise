package Client;

public enum IpPort {
    
    add("127.0.0.1", 9999);

    public String ip;
    public int port;

    IpPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
}
