package cesTesting;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

/**
 *
 * @author ahmad
 */
public class TableWindow extends JFrame {

    public TableWindow() {
        super();
        this.setTitle("Table Window");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Object[][] data = { {"Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false)},
        {"John", "Doe", "Rowing", new Integer(3), new Boolean(true)},
        {"Sue", "Black", "Knitting", new Integer(2), new Boolean(false)},
        {"Jane", "White", "Speed reading", new Integer(20), new Boolean(true)},
        {"Joe", "Brown", "Pool", new Integer(10), new Boolean(false)} };
        String[] columnNames = {"First Name", "Last Name", "Sport", "# of Years", "Vegetarian"};
        
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        this.add(panel);
        
        
        
//       View_Table=new JTable();
//       View_SP=new JScrollPane(View_Table);
//       View_DTM=new DefaultTableModel();
//       View_Table.setModel(View_DTM);
//       String columns[] =  {"Name","CNIC","Address","Cell Phone","Minimum Amount","Interest Rate","Account ID","Balance","Date of Opening"};
//       View_DTM.setColumnIdentifiers(columns);
        
        JTable table = new JTable(data, columnNames);
        //table.setSize(300, 300);
        panel.add(table);
    }
    
    public static void main(String[] args) {
        TableWindow tableWindow = new TableWindow();
        tableWindow.setVisible(true);
    }
}
