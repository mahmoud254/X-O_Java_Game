package x_o_game;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Bassant Sayed
 */
public class Client implements Runnable {

    Socket mySocket;
    Thread th;
    ObjectInputStream in;
    ObjectOutputStream out;
    Message m;
    int id;

    public Client() {
        try {
            mySocket = new Socket("127.0.0.1", 5005);
            try {
                out = new ObjectOutputStream(mySocket.getOutputStream());
                in = new ObjectInputStream(mySocket.getInputStream());
            } catch (Exception ex) {
                System.out.println("Error in input stream or print stream");
            }
        } catch (Exception ex) {
            System.out.println("Error in making socket");

        }
        System.out.println("outside");

        th = new Thread(this);
        th.start();
        System.out.println("th started");

    }

    @Override
    public void run() {
        while (true) {
            try {
                
                System.out.println("mahmoud");
            } catch (Exception ex) {
                th.stop();
            }
        }

    }

}
