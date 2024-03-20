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

  public ChatServiceManagerThread(int max_users, List message_list)
  {
    this.connections = new ArrayList <> (max_users);
    this.max_users = max_users;
    this.message_list = message_list;
  }

  @Override
  public void run()
  {
    boolean active = true;

    try
    {
      server_chat = new ServerSocket(6789);
    }
    catch (IOException e)
    {
      JOptionPane.showMessageDialog(null, "Impossibile istanziare il server.");
      active = false;
    }

    if (active)
    {
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
  }

  public void send_message(String message)
  {
    message_list.add(message);
    int queue_size = connections.size();

    for (int i = 0; i < queue_size; i++)
    {
      connections.get(i).send_chat_message(message);
    }
  }
}