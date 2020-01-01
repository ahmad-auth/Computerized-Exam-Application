package cesfrontend;

import cesbackend.Student;
import cesbackend.Examiner;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.util.Arrays;
import java.util.logging.*;

/**
 *
 * @author ahmad
 */
public class StudentRecordWindow extends JFrame {
    
    private static JFrame mainFrame;
    
    private static JButton backButton;
    private static JButton addButton;
    private static JButton searchButton;
    private static JButton deleteButton;
    private static JButton refreshButton;
    
    private static JTextField searchField;
    
    private static JLabel searchStatusLabel;
    
    private static JTable recordsTable;
    
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    
    private static final String[] columnHeadings = {"USER-ID", "PASSWORD", "NAME", "CNIC", "GROUP", "MARKS"};
    
    private static Examiner examiner;
    
    public StudentRecordWindow(Examiner examiner) {
        
        super();
        this.setTitle("Student Record Management");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.add(mainPanel);
        
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainPanel.add(backButtonPanel);
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });
        backButtonPanel.add(backButton);
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonsPanel.setSize(new Dimension(100, 200));
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(80, 40));
        addButton.addActionListener(new AddButtonListener());
        buttonsPanel.add(addButton);
        deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(80, 40));
        deleteButton.addActionListener(new DeleteButtonListener());
        buttonsPanel.add(deleteButton);
        
        JPanel refreshButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mainPanel.add(refreshButtonPanel);
        
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new RefreshButtonListener());
        refreshButtonPanel.add(refreshButton);
        
        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(550, 300));
        mainPanel.add(tablePanel);
        
        String[][] data = getData();
        DefaultTableModel dtm = new DefaultTableModel(data, columnHeadings);
        recordsTable = new JTable();
        recordsTable.setModel(dtm);
        recordsTable.setMaximumSize(new Dimension(500, 500));
        recordsTable.setEnabled(false);
        JScrollPane tableScrollPane = new JScrollPane(recordsTable);
        //tableScrollPane.add(tableScrollBar);
        tableScrollPane.setSize(500, 300);
        tableScrollPane.setVisible(true);
        tableScrollPane.setVerticalScrollBar(new  JScrollBar(JScrollBar.VERTICAL));
        //tableScrollPane.add(recordsTable);
        tablePanel.add(tableScrollPane);
        
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.add(searchPanel);
        searchPanel.add(new JLabel("Search Panel:"), BorderLayout.NORTH);
        
        JPanel searchFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(searchFieldPanel, BorderLayout.WEST);
        searchField = new JTextField(10);
        searchFieldPanel.add(searchField);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        searchFieldPanel.add(searchButton);
        searchStatusLabel = new JLabel("..");
        searchFieldPanel.add(searchStatusLabel);
        
//        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        searchPanel.add(checkBoxPanel, BorderLayout.SOUTH);
        
        
        mainFrame = this;
    }
    
    public String[][] getData() {
        try {
            return Student.geAlltRecords();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentRecordWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private class RefreshButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[][] Data = getData();
            DefaultTableModel dtm  = new DefaultTableModel(Data, columnHeadings);
            recordsTable = new JTable();
            recordsTable.setModel(dtm);
        }
        
    }
    
    private class SearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String property = searchField.getText();
            if(property.equals("")) {
                searchStatusLabel.setText("Field can not be empty");
            }
            else {
                //JFrame searchResultFrame = new JFrame("Search Result Frame");
                searchStatusLabel.setText("");
                try {
                    Student s = Student.searchStudent(property, Student.USERID);
                    if (s==null) {
                        searchStatusLabel.setText("Record does not exist.");
                    }
                    else {
                        searchStatusLabel.setText("");
                        JFrame searchRecordFrame = new JFrame("Add Student");
                        //searchRecordFrame.setLayout(new BorderLayout(10, 10));
                        searchRecordFrame.setSize(300, 250);
                        searchRecordFrame.setResizable(false);
                        //addRecordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        searchRecordFrame.setVisible(true);

                        JPanel infoPanel = new JPanel(new GridLayout(7, 2, 10, 10));
                        searchRecordFrame.add(infoPanel);
                        JTextField propertyField = new JTextField();
                        infoPanel.add(new JLabel("USERID:"));
                        infoPanel.add(propertyField = new JTextField(s.getId(), 15));
                        propertyField.setEditable(false);
                        infoPanel.add(new JLabel("PASSWORD:"));
                        infoPanel.add(propertyField = new JTextField(s.getPassword(), 15));
                        propertyField.setEditable(false);
                        infoPanel.add(new JLabel("NAME:"));
                        infoPanel.add(propertyField = new JTextField(s.getName(), 15));
                        propertyField.setEditable(false);
                        infoPanel.add(new JLabel("CNIC:"));
                        infoPanel.add(propertyField = new JTextField(s.getCnic(), 15));
                        propertyField.setEditable(false);
                        infoPanel.add(new JLabel("GROUP:"));
                        infoPanel.add(propertyField = new JTextField(s.getEduGroup(), 15));
                        propertyField.setEditable(false);
                        infoPanel.add(new JLabel("MARKS:"));
                        infoPanel.add(propertyField = new JTextField(s.getMarks() + "", 15));
                        propertyField.setEditable(false);
                    }
                    
                } catch (IOException ex) {
                    //Logger.getLogger(StudentRecordWindow.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
        }
        
    }
    
    private class AddButtonListener implements ActionListener {

        JTextField useridField;
        JTextField passwordField;
        JTextField nameField;
        JTextField cnicField;
        //JTextField groupField;
        JTextField marksField;
        
        JLabel statusLabel;
        
        JComboBox groupBox;
        
        String groupName;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame addRecordFrame = new JFrame("Add Student");
            addRecordFrame.setLayout(new BorderLayout(10, 10));
            addRecordFrame.setSize(300, 300);
            addRecordFrame.setResizable(false);
            //addRecordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            addRecordFrame.setVisible(true);
            
            JPanel infoPanel = new JPanel(new GridLayout(7, 2, 10, 10));
            addRecordFrame.add(infoPanel, BorderLayout.NORTH);
            infoPanel.add(new JLabel("USERID:"));
            infoPanel.add(useridField = new JTextField(15));
            infoPanel.add(new JLabel("PASSWORD:"));
            infoPanel.add(passwordField = new JTextField(15));
            infoPanel.add(new JLabel("NAME:"));
            infoPanel.add(nameField = new JTextField(15));
            infoPanel.add(new JLabel("CNIC:"));
            infoPanel.add(cnicField = new JTextField(15));
            infoPanel.add(new JLabel("GROUP:"));
            groupBox = new JComboBox();
            groupBox.addItem("Select an option");
            groupBox.addItem("Computer Science");
            groupBox.addItem("Electrical");
            groupBox.addItem("Mathematics");
            groupBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    groupName = (String) groupBox.getSelectedItem();
                }
            });
            infoPanel.add(groupBox);            
            
            infoPanel.add(new JLabel("MARKS:"));
            infoPanel.add(marksField = new JTextField("0", 15));
            marksField.setBackground(Color.white);
            marksField.setEditable(false);
            
            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
            addRecordFrame.add(bottomPanel, BorderLayout.SOUTH);
            
            JPanel statusPanel = new JPanel();
            statusLabel = new JLabel("..");
            bottomPanel.add(statusLabel);
            
            JPanel buttonPanel = new JPanel();
            bottomPanel.add(buttonPanel);
            JButton addButton = new JButton("Add");
            addButton.setPreferredSize(new Dimension(80, 40));
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isFieldEmpty()) {
                        statusLabel.setText("Field can not be empty.");
                    }
                    else if (!isFieldEmpty()) {
                        String userid = useridField.getText();
                        String password = passwordField.getText();
                        String name = nameField.getText();
                        String cnic = cnicField.getText();
                        String group = groupName;
                        int marks = Integer.parseInt( marksField.getText() );
                        Student s = new Student(userid, password, name, cnic, group, marks);
                        //s.addUserRecord();
                        boolean add = s.addRecord();
                        if (add) {
                            statusLabel.setText("Record added successfully.");
                        }
                        else if(!add) {
                            statusLabel.setText("Record addition failed.");
                        }
                    }
                }
            });
            buttonPanel.add(addButton);
        }
        
        public boolean isFieldEmpty() {
            return ((useridField.getText().equals("")) ||
                    (passwordField.getText().equals("")) ||
                    (nameField.getText().equals("")) ||
                    (cnicField.getText().equals("")) ||
                    (groupName.equalsIgnoreCase("Select an option")) );
        }
        
    }
    
    private class DeleteButtonListener implements ActionListener {

        private JButton deleteButton;
        private JLabel deleteStatusLabel;
        private JTextField deleteField;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame deleteUserFrame = new JFrame("Delete Student");
            deleteUserFrame.setSize(500, 100);
            deleteUserFrame.setResizable(false);
            //deleteUserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            deleteUserFrame.setVisible(true);
            
            JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            deleteUserFrame.add(deletePanel);
            deletePanel.add(new JLabel("USER-ID:"));
            deleteField = new JTextField(10);
            deletePanel.add(deleteField);
            deleteButton = new JButton("Delete");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String id = deleteField.getText();
                    if(id.equals("")) {
                        deleteStatusLabel.setText("Field can not be empty.");
                    }
                    else {
                        deleteStatusLabel.setText("");
                        try {
                            boolean deleted = Student.deleteStudent(id);
                            if (deleted)
                                deleteStatusLabel.setText("Succesfully deleted.");
                            else
                                deleteStatusLabel.setText("Deletion unsuccessful.");
                        } catch (IOException ex) {
                            //Logger.getLogger(StudentRecordWindow.class.getName()).log(Level.SEVERE, null, ex);
                            ex.printStackTrace();
                        }
                    }
                    
                }
            });
            deletePanel.add(deleteButton);
            deleteStatusLabel = new JLabel("..");
            deletePanel.add(deleteStatusLabel);
        }
        
    }
    
    public static void main(String[] args) {
        StudentRecordWindow srw = new StudentRecordWindow(new Examiner("name", "30303", "Programming"));
        srw.setVisible(true);
    }
    
}
