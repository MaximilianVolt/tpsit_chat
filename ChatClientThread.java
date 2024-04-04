import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ChatClientThread implements Runnable
{
  private List message_list;
  private Socket client;
  private BufferedReader input = null;
  private PrintWriter output = null;



  /**
   * Istanzia un thread per la ricezione dei messaggi del client.
   * @param message_list L'elenco della lista dei messaggi.
   * @param server_ip L'indirizzo IP del server.
   * @param port La porta del socket client.
   */

  public ChatClientThread(List message_list, String server_ip, int port)
  {
    this.message_list = message_list;

    try
    {
      client = new Socket(server_ip, port);
      input = new BufferedReader(new InputStreamReader(client.getInputStream()));
      output = new PrintWriter(client.getOutputStream(), true);
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null, "Impossibile connettersi al server.");
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
          message_list.add(message);
        }
      } 
      catch (IOException e)
      {
        output.println("Errore  di collegamento al server.");
      }
    }
  }



  /**
   * Invia il messaggio del client in chat.
   * @param message Il messaggio da inviare.
   */

  public void chat_send_message(String message)
  {
    output.println(message);
  }
}
