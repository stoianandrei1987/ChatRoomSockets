import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class UserReadThread extends Thread {

    Socket server;
    ChatClient client;
    BufferedReader reader;

    public UserReadThread(Socket server, ChatClient client) {
        this.server = server;
        this.client = client;
        try {
            reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
        } catch (IOException e) {
            System.out.println("error getting input stream from server");
        }
    }

    @Override
    public void run() {

        while (true) {

            try {

                String response = reader.readLine();
                System.out.println("\n" + response);

                if (client.getUserName() != null) {
                    System.out.print("[" + client.getUserName() + "]: ");
                }
            } catch (SocketException ex) {
                System.out.println("socket closed!");
                break;

            } catch (Exception ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }

}

