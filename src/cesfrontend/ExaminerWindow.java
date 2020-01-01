package cesfrontend;

import cesbackend.Examiner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author ahmad
 */
public class ExaminerWindow extends JFrame {

    private static JFrame mainFrame;
    
    private static JLabel examinerNameLabel;
    private static JLabel examinerCnicLabel;
    private static JLabel examinerSubjectLabel;
    
    private static JTextField examinerNameField;
    private static JTextField examinerCnicField;
    private static JTextField examinerSubjectField;
    
    private static JButton studentButton;
    private static JButton examinerButton;
    private static JButton questionButton;
    
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    
    private static Examiner examiner;
    
    public ExaminerWindow(Examiner examiner) {
        
        super();
        this.setTitle("Examiner Window");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.examiner = examiner;
        
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(true);
        mainPanel.setBackground(CColors.BLUE_GREY_900);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JPanel examinerInfoPanel = new JPanel();
        examinerInfoPanel.setOpaque(false);
        examinerInfoPanel.setLayout( new GridLayout(3, 1) );
        mainPanel.add(examinerInfoPanel, BorderLayout.NORTH);
        
        JPanel examinerNamePanel = new JPanel();
        examinerNamePanel.setOpaque(false);
        examinerNamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        examinerInfoPanel.add(examinerNamePanel);
        examinerNameLabel = new JLabel("Name:");
        examinerNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        examinerNameLabel.setForeground(CColors.TEAL_400);
        examinerNamePanel.add(examinerNameLabel);
        examinerNamePanel.add(Box.createHorizontalStrut(20));
        examinerNameField = new JTextField(15);
        examinerNameField.setOpaque(false);
        examinerNameField.setFont(new Font("Arial", Font.PLAIN, 18));
        examinerNameField.setForeground(CColors.CLOUDS);
        examinerNameField.setText(examiner.getName());
        examinerNameField.setEditable(false);
        examinerNamePanel.add(examinerNameField);
        
        JPanel examinerCnicPanel = new JPanel();
        examinerCnicPanel.setOpaque(false);
        examinerCnicPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        examinerInfoPanel.add(examinerCnicPanel);
        examinerCnicLabel = new JLabel("C.N.I.C:");
        examinerCnicLabel.setFont(new Font("Arial", Font.BOLD, 16));
        examinerCnicLabel.setForeground(CColors.TEAL_400);
        examinerCnicPanel.add(examinerCnicLabel);
        examinerCnicPanel.add(Box.createHorizontalStrut(20));
        examinerCnicField = new JTextField(15);
        examinerCnicField.setOpaque(false);
        examinerCnicField.setFont(new Font("Arial", Font.PLAIN, 18));
        examinerCnicField.setForeground(CColors.CLOUDS);
        examinerCnicField.setText(examiner.getCnic());
        examinerCnicField.setEditable(false);
        examinerCnicPanel.add(examinerCnicField);
        
        JPanel examinerSubjectPanel = new JPanel();
        examinerSubjectPanel.setOpaque(false);
        examinerSubjectPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        examinerInfoPanel.add(examinerSubjectPanel);
        examinerSubjectLabel = new JLabel("Subject:");
        examinerSubjectLabel.setFont(new Font("Arial", Font.BOLD, 16));
        examinerSubjectLabel.setForeground(CColors.TEAL_400);
        examinerSubjectPanel.add(examinerSubjectLabel);
        examinerSubjectPanel.add(Box.createHorizontalStrut(20));
        examinerSubjectField = new JTextField(15);
        examinerSubjectField.setOpaque(false);
        examinerSubjectField.setFont(new Font("Arial", Font.PLAIN, 18));
        examinerSubjectField.setForeground(CColors.CLOUDS);
        examinerSubjectField.setText(examiner.getSubject());
        examinerSubjectField.setEditable(false);
        examinerSubjectPanel.add(examinerSubjectField);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        studentButton = new JButton("Student");
        studentButton.setPreferredSize(new Dimension(100, 50));
        studentButton.setBackground(CColors.TEAL_400);
        studentButton.setForeground(CColors.CLOUDS);
        studentButton.setFont(new Font("Arial", Font.BOLD, 15));
        studentButton.addActionListener(new NextWindowButton());
        buttonPanel.add(studentButton);
        examinerButton = new JButton("Examiner");
        examinerButton.setPreferredSize(new Dimension(100, 50));
        examinerButton.setBackground(CColors.TEAL_400);
        examinerButton.setForeground(CColors.CLOUDS);
        examinerButton.setFont(new Font("Arial", Font.BOLD, 15));
        examinerButton.addActionListener(new NextWindowButton());
        buttonPanel.add(examinerButton);
        questionButton = new JButton("Question");
        questionButton.setPreferredSize(new Dimension(100, 50));
        questionButton.setBackground(CColors.TEAL_400);
        questionButton.setForeground(CColors.CLOUDS);
        questionButton.setFont(new Font("Arial", Font.BOLD, 15));
        questionButton.addActionListener(new NextWindowButton());
        buttonPanel.add(questionButton);
        
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        logoutPanel.setOpaque(false);
        mainPanel.add(logoutPanel);
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(CColors.TEAL_400);
        logoutButton.setForeground(CColors.CLOUDS);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.addActionListener(new LogoutButtonListener());
        logoutPanel.add(logoutButton);
        
        this.add(mainPanel);
        mainFrame = this;
        
    }
    
    private class NextWindowButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            
            if (command.equals("Student")) {
                new StudentRecordWindow(examiner).setVisible(true);
            }
            else if (command.equals("Examiner")) {
                new ExaminerRecordWindow(examiner).setVisible(true);
            }
            else if (command.equals("Question")) {
                new QuestionRecordWindow(examiner).setVisible(true);
            }
        }
        
        
    }
    
    private class LogoutButtonListener implements ActionListener {

        JFrame logoutConfirmation;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            logoutConfirmation = new JFrame("Logout");
            logoutConfirmation.setSize(350, 150);
            logoutConfirmation.setResizable(false);
            logoutConfirmation.setVisible(true);
            
            JPanel panel = new JPanel();
            panel.setBackground(CColors.BLUE_GREY_900);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            logoutConfirmation.add(panel);
            
            JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            labelPanel.setOpaque(false);
            panel.add(labelPanel);
            JLabel label = new JLabel("Are you sure you want to logout?");
            labelPanel.add(label);
            label.setForeground(CColors.CLOUDS);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setOpaque(false);
            panel.add(buttonPanel);
            JButton yesButton = new JButton("Yes");
            yesButton.setBackground(CColors.CYAN_800);
            yesButton.setForeground(CColors.CLOUDS);
            yesButton.setFont(new Font("Arial", Font.BOLD, 14));
            yesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    logoutConfirmation.dispose();
                    mainFrame.dispose();
                    new LoginWindow().setVisible(true);
                }
            });
            buttonPanel.add(yesButton);
            JButton noButton = new JButton("No");
            noButton.setBackground(CColors.CYAN_800);
            noButton.setForeground(CColors.CLOUDS);
            noButton.setFont(new Font("Arial", Font.BOLD, 14));
            noButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    logoutConfirmation.dispose();
                }
            });
            buttonPanel.add(noButton);
        }
    }
    
    public static void main(String[] args) {
        
        ExaminerWindow ew = new ExaminerWindow(new Examiner("Examiner", "10102", "Programming"));
        ew.setVisible(true);
    }
}
