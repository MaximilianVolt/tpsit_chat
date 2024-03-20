import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class ChatClientThread implements  Runnable
{
  private List message_list;
  private Socket client;
  private BufferedReader input = null;
  private PrintWriter output = null;

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

  public void chat_send_message(String message)
  {
    output.println(message);
  }
}
