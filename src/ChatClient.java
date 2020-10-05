import java.io.IOException;
import java.net.Socket;

public class ChatClient {

    private String hostName;
    private int port;
    private String userName;

    public ChatClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void execute(){
        try {
            Socket server = new Socket(hostName, port);
            System.out.println("Connected to the chat server!");
            new UserReadThread(server, this).start();
            new UserWriteThread(server, this).start();


        } catch (IOException e) {
            System.out.println("error connecting, please insert proper host-name and port");
        }
    }

    public static void main(String[] args) {

        ChatClient chatClient = new ChatClient("127.0.0.1", 1234);
        chatClient.execute();
    }



}
