import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class ChatServer extends JFrame
{
  public UIChatPanel panel;



  /**
   * @see UIChatPanel
   * Istanzia l'interfaccia grafica della finestra della chat del server.
   */

  public ChatServer()
  {
    super("Chat server");

    setSize(new Dimension(500, 300));
    setLocationRelativeTo(null);
    setEnabled(true);
    setBackground(Color.BLUE);
    panel = new UIChatPanel();
    getContentPane().add(panel);
    setVisible(true);
  }
}



class ChatClient extends JFrame
{
  public UIChatPanel panel;



  /**
   * @see UIChatPanel
   * Istanzia l'interfaccia grafica della finestra della chat del client.
   */

  public ChatClient()
  {
    super("Chat client");

    setSize(new Dimension(500, 300));
    setLocationRelativeTo(null);
    setEnabled(true);
    setBackground(Color.BLUE);
    panel = new UIChatPanel();
    getContentPane().add(panel);
    setVisible(true);
  }
}




public class UIChatPanel extends JFrame implements ActionListener
{
  private ChatServiceManagerThread service_manager;
  private final List ui_list;
  private final JPanel new_msg;
  private final JLabel new_msg_highlight;
  private final JTextField new_msg_content;



  /**
   * Definisce i parametri di configurazione dell'interfaccia grafica.
   */

  public UIChatPanel()
  {
    super();

    setBackground(Color.MAGENTA);
    JPanel panel_list = new JPanel(new BorderLayout(20, 5));
    panel_list.setBackground(Color.MAGENTA);

    ui_list = new List();
    ui_list.setBackground(Color.LIGHT_GRAY);
    ui_list.setSize(100, 50);
    ui_list.setVisible(true);

    JLabel chat_1 = new JLabel("Chat", JLabel.CENTER);
    JLabel chat_2 = new JLabel("Chat 2", JLabel.CENTER);
    chat_1.setForeground(Color.ORANGE);
    chat_2.setForeground(Color.YELLOW);

    panel_list.add(chat_1, BorderLayout.WEST);
    panel_list.add(ui_list, BorderLayout.CENTER);
    panel_list.add(chat_2, BorderLayout.EAST);

    new_msg = new JPanel(new BorderLayout(20, 5));
    new_msg.setBackground(Color.CYAN);

    new_msg_highlight = new JLabel("New message: ", JLabel.CENTER);
    new_msg_highlight.setForeground(Color.GREEN);

    new_msg_content = new JTextField("");

    JButton ui_button_send = new JButton("Send");
    ui_button_send.addActionListener(this);

    new_msg.add(new_msg_highlight, BorderLayout.WEST);
    new_msg.add(new_msg_content, BorderLayout.CENTER);
    new_msg.add(ui_button_send, BorderLayout.EAST);

    setLayout(new BorderLayout(0, 5));
    add(panel_list, BorderLayout.CENTER);
    add(new_msg, BorderLayout.SOUTH);
  }



  /**
   * Istanzia e avvia un thread per la gestione della chat.
   */

  public void connect()
  {
    service_manager = new ChatServiceManagerThread(10, ui_list);
    service_manager.run();
  }



  /**
   * Risponde all'esecuzione di un'azione di un componente.
   * @param e L'evento da controllare.
   */

  @Override
  public void actionPerformed(ActionEvent e)
  {
    String ui_button = e.getActionCommand();

    if (ui_button.equals("Send"))
    {
      service_manager.send_message(new_msg_content.getText());
      new_msg_content.setText("");
    }
  }
}