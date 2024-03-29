import java.util.Scanner;

public class MainClient
{
  private static final Scanner keyboard = new Scanner(System.in);

  public static void main(String[] args)
  {
    System.out.println("Inserisci il nome del nuovo client:");

    try (keyboard)
    {
      String client_name = keyboard.next();
      ChatClient client = new ChatClient(client_name, 60000);
      client.panel.connect();
    }
    catch (Exception e)
    {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
