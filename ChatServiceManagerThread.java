import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ChatServiceManagerThread implements Runnable
{
  private final int max_users;
  private final List message_list;
  private final ArrayList <ChatConnectorThread> connections;
  private ServerSocket server_chat;



  /**
   * Istanzia un thread per la gestione della chat.
   * @param max_users Il numero massimo di utenti.
   * @param message_list La lista di messaggi dell'UI
   */

  public ChatServiceManagerThread(int max_users, List message_list)
  {
    this.connections = new ArrayList <> (max_users);
    this.max_users = max_users;
    this.message_list = message_list;
  }



  /**
   * Tenta la connessione ad un server socket per istanziare la chat.
   */

  @Override
  public void run()
  {
    try
    {
      server_chat = new ServerSocket(6789);

      try
      {
        for (int i = 0; i < max_users; i++)
        {
          Socket temp = server_chat.accept();
          connections.set(i, new ChatConnectorThread(this, temp));
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