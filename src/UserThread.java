import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class UserThread extends Thread {

    private ChatServer chatServer;
    private Socket userSocket;
    private String userName;
    private PrintWriter printWriter;

    public UserThread(ChatServer chatServer, Socket socket, String userName) {
        this.chatServer = chatServer;
        this.userSocket = socket;
        this.userName = userName;


    }

    public Socket getSocket(){
        return this.userSocket;
    }

    public String getUserName() {
        return this.userName;
    }

    public void sendToClient(String senderName, String text) {
        try {
            if (printWriter == null) {
                printWriter = new PrintWriter(userSocket.getOutputStream(), true);
            }

            printWriter.println("["+senderName+"]" + " : "+text);

        } catch (IOException iox) {
            System.out.println("error sending to client : " + this.userName);
            System.out.println("text : " + text);
            System.out.println("from sender : " + senderName);
        }
    }

    @Override
    public void run() {
        String incomingMessage = "";
        try {
            while (!incomingMessage.equals("goodbye")) {
                incomingMessage = new BufferedReader(new InputStreamReader(userSocket.getInputStream())).readLine();
                System.out.println(userName+" says : "+incomingMessage);
                chatServer.broadcastToAll(this, incomingMessage);
            }
        } catch (IOException ioex) {
            System.out.println("error");
        } finally {
            chatServer.removeUsers(this);
        }



    }

}
