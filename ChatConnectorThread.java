import java.io.*;
import java.net.*;

public class ChatConnectorThread implements Runnable
{
  private ChatServiceManagerThread service_manager;
  private BufferedReader input = null;
  private PrintWriter output = null;
  private Socket client;
  private int port;



  /**
   * Configura gli stream per la comunicazione.
   * @param service_manager L'istanza del gestore della chat.
   * @param client Il socket del client con cui comunicare.
   * @param port La porta dell'host.
   */

  public ChatConnectorThread(ChatServiceManagerThread service_manager, Socket client, int port)
  {
    this.service_manager = service_manager;
    this.client = client;
    this.port = port;

    try
    {
      input = new BufferedReader(new InputStreamReader(client.getInputStream()));
      output = new PrintWriter(client.getOutputStream(), true);  
    }
    catch (IOException e)
    {
      output.println("Errore nella lettura: " + e.getMessage());
    }
  }



  /**
   * Effettua il caricamento dei messaggi della chat.
   */

  @Override
  public void run()
  {
    while (true)
    {
      try
      {
        String message;

        while ((message = input.readLine()) != null)
        {
          service_manager.send_message(message);
        }
      }
      catch (IOException e)
      {
        output.println("Errore nell'invio del messaggio in multicast: " + e.getMessage());
      }
    }
  }



  /**
   * Invia un messaggio nella chat.
   * @param message Il messaggio da inviare.
   */

  public void send_chat_message(String message)
  {
    output.println(message);
  }
}