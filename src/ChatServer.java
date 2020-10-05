import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ChatServer {

    private Set<UserThread> userThreads;
    private int port;

    public ChatServer(int port) {
        this.port = port;
        userThreads = new HashSet<>();
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {

                Socket s = serverSocket.accept();
                String userName = new Scanner(new InputStreamReader(s.getInputStream())).nextLine();
                UserThread userThread = new UserThread(this, s, userName);
                userThread.start();
                userThreads.add(userThread);
                String text = userThreads.stream().map(ut -> ut.getUserName()).reduce("", (x, y) -> x+=(" "+y))
                        .stripLeading();
                System.out.println("users connected : " + text);
            }

        } catch (IOException e) {
            System.out.println("Error starting server");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer(1234);
        chatServer.execute();

    }

    public void removeUsers(UserThread userQuit) {
        try {
            userQuit.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userQuit.interrupt();
        userThreads.remove(userQuit);
    }

    public void broadcastToAll(UserThread sender, String text) {
        for (UserThread userThread : userThreads) {
            if (userThread != sender) {
                userThread.sendToClient(sender.getUserName(),text);
            }
        }
    }

}
