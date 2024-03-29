import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ChatServiceManagerThread implements Runnable
{
  private final int port;
  private final int max_users;
  private final List message_list;
  private final ArrayList <ChatConnectorThread> connections;
  private ServerSocket server_chat;
  



  /**
   * Istanzia un thread per la gestione della chat.
   * @param max_users Il numero massimo di utenti.
   * @param message_list La lista di messaggi dell'UI
   * @param port La porta dell'host.
   */

  public ChatServiceManagerThread(int max_users, List message_list, int port)
  {
    this.connections = new ArrayList <> (max_users);
    this.message_list = message_list;
    this.max_users = max_users;
    this.port = port;
  }



  /**
   * Tenta la connessione ad un server socket per istanziare la chat.
   */

  @Override
  public void run()
  {
    try
    {
      server_chat = new ServerSocket(port);

      try
      {
        for (int i = 0; i < max_users; i++)
        {
          Socket temp = server_chat.accept();
          connections.set(i, new ChatConnectorThread(this, temp, port));
        }

        server_chat.close();
      }
      catch (IOException e)
      {
        JOptionPane.showMessageDialog(null, "Impossibile istanziare la chat.");
      }
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null, "Impossibile istanziare il server.");
    }
  }



  /**
   * Invia un messaggio.
   * @param message Il messaggio da inviare.
   */

  public void send_message(String message)
  {
    int queue_size = connections.size();
    message_list.select(message_list.getItemCount());
    message_list.add(message);

    for (int i = 0; i < queue_size; i++)
    {
      connections.get(i).send_chat_message(message);
    }
  }
}