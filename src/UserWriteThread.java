import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class UserWriteThread  extends Thread {

    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;

    public UserWriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter your name: ");
        String userName = scanner.nextLine();
        client.setUserName(userName);
        writer.println(userName);

        String text = "";

        do {
            System.out.print("[" + userName + "] : ");
            text = scanner.nextLine();
            writer.println(text);

        } while (!text.equals("goodbye"));

        try {
            socket.close();
        } catch (IOException ex) {

            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}
