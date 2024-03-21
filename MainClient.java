public class MainClient
{
  public static void main(String[] args)
  {
    String client_name = System.getProperty("user.name");
    ChatClient client = new ChatClient(client_name);
    client.panel.connect();
  }
}
