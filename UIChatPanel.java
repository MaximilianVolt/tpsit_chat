import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class ChatServer extends JFrame
{
  private final int port;
  public final String name = "Server";
  public UIChatPanel panel;



  /**
   * @see UIChatPanel
   * Istanzia l'interfaccia grafica della finestra della chat del server.
   */

  public ChatServer(int port)
  {
    super("Chat server");

    this.port = port;
    setSize(new Dimension(500, 300));
    setLocationRelativeTo(null);
    setEnabled(true);
    setBackground(Color.BLUE);
    panel = new UIChatPanel(name, this.port);
    getContentPane().add(panel);
    setVisible(true);
  }
}



class ChatClient extends JFrame
{
  private final int port;
  private final String name;
  public UIChatPanel panel;



  /**
   * @see UIChatPanel
   * Istanzia l'interfaccia grafica della finestra della chat del client.
   */

  public ChatClient(String name, int port)
  {
    super("Chat client");

    this.name = name;
    this.port = port;
    setSize(new Dimension(500, 300));
    setLocationRelativeTo(null);
    setEnabled(true);
    setBackground(Color.BLUE);
    panel = new UIChatPanel(this.name, this.port);
    getContentPane().add(panel);
    setVisible(true);
  }
}




public class UIChatPanel extends JPanel implements ActionListener
{
  private final int port;
  private final String name;
  private ChatServiceManagerThread service_manager;
  private final List ui_list;
  private final JPanel new_msg;
  private final JLabel new_msg_highlight;
  private final JTextField new_msg_content;



  /**
   * Definisce i parametri di configurazione dell'interfaccia grafica.
   * @param name Il nome dell'host.
   * @param port La porta dell'host.
   */

  public UIChatPanel(String name, int port)
  {
    super();
    this.name = name;
    this.port = port;

    setBackground(Color.DARK_GRAY);
    JPanel panel_list = new JPanel(new BorderLayout(20, 5));
    panel_list.setBackground(Color.DARK_GRAY);

    ui_list = new List();
    ui_list.setBackground(Color.LIGHT_GRAY);
    ui_list.setSize(100, 50);
    ui_list.setVisible(true);

    JLabel chat_1_label = new JLabel(this.name, JLabel.CENTER);
    JLabel chat_2_label = new JLabel(name, JLabel.CENTER);
    chat_1_label.setForeground(Color.ORANGE);
    chat_2_label.setForeground(Color.LIGHT_GRAY);

    panel_list.add(chat_1_label, BorderLayout.WEST);
    panel_list.add(ui_list, BorderLayout.CENTER);
    panel_list.add(chat_2_label, BorderLayout.EAST);

    new_msg = new JPanel(new BorderLayout(20, 5));
    new_msg.setBackground(Color.DARK_GRAY);

    new_msg_highlight = new JLabel("New message: ", JLabel.CENTER);
    new_msg_highlight.setForeground(Color.WHITE);

    new_msg_content = new JTextField();

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
    service_manager = new ChatServiceManagerThread(10, ui_list, port);
    service_manager.run();
  }



  /**
   * Risponde all'esecuzione di un'azione di un componente.
   * @param e L'evento da controllare.
   */

  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (!new_msg_content.getText().isBlank())
    {
      service_manager.send_message(name + ": " + new_msg_content.getText());
      new_msg_content.setText("");
    }
  }
}