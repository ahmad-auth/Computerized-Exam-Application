package cesfrontend;

import cesbackend.Question;
import cesbackend.Examiner;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author ahmad
 */
public class QuestionRecordWindow extends JFrame {
    
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
    public static final int HEIGHT = 450;
    
    private static final String[] columnHeadings = {"ID", "SUBJECT", "QUESTION", "OPTION-A", "OPTION-B", "OPTION-C", "OPTION-D", "ANSWER"};
    
    private static String subject;
    
    private static Examiner examiner;
    
    public QuestionRecordWindow(Examiner examiner) {
        
        super();
        this.setTitle("Question Record Management");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.subject = examiner.getSubject();
        
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
            return Question.geAlltRecords(subject);
        } catch (IOException ex) {
            Logger.getLogger(QuestionRecordWindow.class.getName()).log(Level.SEVERE, null, ex);
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
                    Question q = Question.searchQuestion(subject, property, Question.ID);
                    if (q==null) {
                        searchStatusLabel.setText("Record does not exist.");
                    }
                    else {
                        searchStatusLabel.setText("");
                        JFrame searchRecordFrame = new JFrame("Add Question");
                        //searchRecordFrame.setLayout(new BorderLayout(10, 10));
                        searchRecordFrame.setSize(300, 250);
                        searchRecordFrame.setResizable(false);
                        //addRecordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        searchRecordFrame.setVisible(true);
                        
                        String[] question = q.getQuestion();
                        
                        JPanel infoPanel = new JPanel(new GridLayout(8, 2, 10, 10));
                        searchRecordFrame.add(infoPanel);
                        JTextField propertyField;
                        infoPanel.add(new JLabel("ID:"));
                        infoPanel.add(propertyField = new JTextField(q.getId(), 15));
                        propertyField.setEditable(false);
                        infoPanel.add(new JLabel("SUBJECT:"));
                        infoPanel.add(propertyField = new JTextField(q.getSubject(), 15));
                        propertyField.setEditable(false);
                        infoPanel.add(new JLabel("QUESTION:"));
                        infoPanel.add(propertyField = new JTextField(question[0], 15));
                        propertyField.setEditable(false);
                        infoPanel.add(new JLabel("OPTION-A:"));
                        infoPanel.add(propertyField = new JTextField(question[1], 15));
                        propertyField.setEditable(false);
                        infoPanel.add(new JLabel("OPTION-B:"));
                        infoPanel.add(propertyField = new JTextField(question[2], 15));
                        propertyField.setEditable(false);
                        infoPanel.add(new JLabel("OPTION-C:"));
                        infoPanel.add(propertyField = new JTextField(question[3], 15));
                        propertyField.setEditable(false);
                        infoPanel.add(new JLabel("OPTION-D:"));
                        infoPanel.add(propertyField = new JTextField(question[4], 15));
                        propertyField.setEditable(false);
                        infoPanel.add(new JLabel("ANSWER:"));
                        infoPanel.add(propertyField = new JTextField(question[5], 15));
                        propertyField.setEditable(false);
                    }
                    
                } catch (IOException ex) {
                    //Logger.getLogger(QuestionRecordWindow.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
        }
        
    }
    
    private class AddButtonListener implements ActionListener {

        JTextField questionIDField;
        JTextField questionField;
        JTextField choiceAField;
        JTextField choiceBField;
        JTextField choiceCField;
        JTextField choiceDField;
        JTextField answerField;
        
        JLabel statusLabel;
        
        JComboBox subjectBox;
        
        String sub;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame addRecordFrame = new JFrame("Add Question");
            addRecordFrame.setLayout(new BorderLayout(10, 10));
            addRecordFrame.setSize(300, 400);
            addRecordFrame.setResizable(false);
            //addRecordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            addRecordFrame.setVisible(true);
            
            JPanel infoPanel = new JPanel(new GridLayout(8, 2, 10, 10));
            addRecordFrame.add(infoPanel, BorderLayout.NORTH);
            infoPanel.add(new JLabel("ID:"));
            infoPanel.add(questionIDField = new JTextField(15));
            infoPanel.add(new JLabel("SUBJECT:"));
            subjectBox = new JComboBox();
            subjectBox.addItem("Select an option");
            subjectBox.addItem("Programming");
            subjectBox.addItem("Electrical");
            subjectBox.addItem("Mathematics");
            subjectBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sub = (String) subjectBox.getSelectedItem();
                }
            });
            infoPanel.add(subjectBox);
            infoPanel.add(new JLabel("QUESTION:"));
            infoPanel.add(questionField = new JTextField(15));
            infoPanel.add(new JLabel("OPTION-A:"));
            infoPanel.add(choiceAField = new JTextField(15));
            infoPanel.add(new JLabel("OPTION-B:"));
            infoPanel.add(choiceBField = new JTextField(15));
            infoPanel.add(new JLabel("OPTION-C:"));
            infoPanel.add(choiceCField = new JTextField(15));
            infoPanel.add(new JLabel("OPTION-D:"));
            infoPanel.add(choiceDField = new JTextField(15));
            infoPanel.add(new JLabel("ANSWER:"));
            infoPanel.add(answerField = new JTextField(15));
            
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
                        String id = questionIDField.getText();
                        String s = sub;
                        String question = questionField.getText();
                        String choiceA = choiceAField.getText();
                        String choiceB = choiceBField.getText();
                        String choiceC = choiceCField.getText();
                        String choiceD = choiceCField.getText();
                        String answer = answerField.getText();
                        String que[] = {question, choiceA, choiceB, choiceC, choiceD, answer};
                        Question q = new Question(id, s, que);
                        //s.addUserRecord();
                        boolean add = q.addRecord();
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
            return ((questionIDField.getText().equals("")) ||
                    (questionField.getText().equals("")) ||
                    (choiceAField.getText().equals("")) ||
                    (choiceBField.getText().equals("")) ||
                    (choiceCField.getText().equals("")) ||
                    (choiceDField.getText().equals("")) ||
                    (answerField.getText().equals("")) ||
                    (subject.equalsIgnoreCase("Select an option")) );
        }
        
    }
    
    private class DeleteButtonListener implements ActionListener {

        private JButton deleteButton;
        private JLabel deleteStatusLabel;
        private JTextField deleteField;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame deleteUserFrame = new JFrame("Delete Question");
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
                            boolean deleted = Question.deleteQuestion(id, subject);
                            if (deleted)
                                deleteStatusLabel.setText("Succesfully deleted.");
                            else
                                deleteStatusLabel.setText("Deletion unsuccessful.");
                        } catch (IOException ex) {
                            //Logger.getLogger(QuestionRecordWindow.class.getName()).log(Level.SEVERE, null, ex);
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
        QuestionRecordWindow srw = new QuestionRecordWindow(new Examiner("name", "30303", "Programming"));
        srw.setVisible(true);
    }
    
}
