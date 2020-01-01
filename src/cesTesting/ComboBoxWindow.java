package cesTesting;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;


/**
 *
 * @author ahmad
 */
public class ComboBoxWindow {

   static String Item;
    public static void main(String[] args) {
        
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        JComboBox CB=new JComboBox();
        CB.addItem("Name");
        CB.addItem("CNIC");
        CB.addItem("Address");
        CB.setSize(10,10);
        
        CB.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
         Item=(String) CB.getSelectedItem();
            System.out.println(Item);
          }
        });
        
        frame.setSize(300,300);
        frame.add(CB);
        frame.setVisible(true);
    }
}
