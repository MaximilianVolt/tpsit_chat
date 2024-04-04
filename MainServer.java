public class MainServer
{  
  /** 
   * @param args
   */

  public static void main(String[] args)
  {
    ChatServer server = new ChatServer(60000);
    server.panel.connect();
  }
}
