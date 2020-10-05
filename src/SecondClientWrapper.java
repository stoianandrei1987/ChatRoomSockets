public class SecondClientWrapper {

    public static void main(String[] args) {
        new ChatClient("127.0.0.1", 1234).execute();
    }
}
