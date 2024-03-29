public class MainServer
{
  public static void main(String[] args)
  {
    ChatServer server = new ChatServer(60000);
    server.panel.connect();
  }
}
