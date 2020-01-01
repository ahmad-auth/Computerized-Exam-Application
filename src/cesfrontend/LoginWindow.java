package cesfrontend;

import cesbackend.Student;
import cesbackend.Examiner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.util.logging.*;

/**
 *
 * @author ahmad
 */

//LoginWindow 'is a JFrame'
public class LoginWindow extends JFrame {

    private static JLabel cesLabel;
    private static JLabel usernameLabel;
    private static JLabel passwordLabel;
    private static JLabel statusLabel;
    
    private static JTextField usernameField;
    private static JPasswordField passwordField;
    
    private static JCheckBox examinerCheck;
    
    private static JButton loginButton;
    
    private static JPanel mainPanel;
    
    private static JFrame mainFrame;
    
    private static final int WIDTH = 360;
    private static final int HEIGHT = 350;
    
    private static Student student;
    private static Examiner examiner;
    
    public LoginWindow() {
        super();
        
        // Setting properties for LoginWindow
        this.setTitle("Login Page");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        /* Creating mainPanel to add all components */
        mainPanel = new JPanel();
        mainPanel.setBackground(CColors.BLUE_GREY_900);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //Creating logoPanel and adding a cesLabel
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        cesLabel = new JLabel("C.E.S");
        cesLabel.setForeground(CColors.GREEN_SEA);
        cesLabel.setFont(new Font("Arial", Font.BOLD, 46));
        logoPanel.add(cesLabel);
        mainPanel.add(logoPanel);
        //logoPanel added to mainPanel

        //Creating statusPanel and adding statusLabel
        JPanel statusPanel = new JPanel();
        statusPanel.setOpaque(false);
        statusLabel = new JLabel("..");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setForeground(CColors.CLOUDS);
        statusPanel.add(statusLabel);
        mainPanel.add(statusPanel);
        //statusPanel added to mainPanel
        
        /* Creating userdataPanel and adding usernamePanel & passwordPanel */
        JPanel userdataPanel = new JPanel();
        userdataPanel.setOpaque(false);
        userdataPanel.setLayout(new BoxLayout(userdataPanel, BoxLayout.Y_AXIS));
        
        //Creating usernamePanel and adding usernameField
        JPanel usernamePanel = new JPanel();
        usernamePanel.setOpaque(false);
        usernamePanel.setLayout(new FlowLayout());
        usernameLabel = new JLabel("USER ID");
        usernameLabel.setForeground(CColors.TEAL_400);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField = new JTextField(17);
        usernameField.setActionCommand("usernameField");
        usernameField.setOpaque(false);
        usernameField.setForeground(CColors.CLOUDS);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 22));
        userdataPanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        userdataPanel.add(usernamePanel);
        
        //Creating passwordPanel and adding passwordField
        JPanel passwordPanel = new JPanel();
        passwordPanel.setOpaque(false);
        passwordPanel.setLayout(new FlowLayout());
        passwordLabel = new JLabel("PASSWORD");
        passwordLabel.setForeground(CColors.TEAL_400);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField = new JPasswordField(17);
        passwordField.setActionCommand("passwordField");
        passwordField.setOpaque(false);
        passwordField.setForeground(CColors.CLOUDS);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 22));
        userdataPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        userdataPanel.add(passwordPanel);
        
        mainPanel.add(userdataPanel);
        /* userdataPanel added to mainPanel */
        
        //Creating loginPanel and adding loginButton
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        loginPanel.setOpaque(false);
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(80, 40));
        loginButton.setActionCommand("Student");
        loginButton.setForeground(Color.darkGray);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 18));
        loginButton.setForeground(CColors.CLOUDS);
        loginButton.setBackground(CColors.CYAN_800);
        loginButton.addActionListener(new LoginButtonListener());
        loginPanel.add(loginButton);
        mainPanel.add(loginPanel);
        //loginPanel added to mainPanel
        
        JPanel examinerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 10));
        examinerPanel.setOpaque(false);
        mainPanel.add(examinerPanel);
        JLabel examinerLabel = new JLabel("EXAMINER:");
        examinerPanel.add(examinerLabel);
        examinerLabel.setForeground(CColors.TEAL_400);
        examinerLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        examinerCheck = new JCheckBox();
        examinerCheck.setToolTipText("Select the \'checkbox\' if you are an Examiner.");
        examinerCheck.setBackground(CColors.BLUE_GREY_900);
        examinerCheck.addActionListener(new ExaminerCheckListener());
        examinerPanel.add(examinerCheck);
        
        this.add(mainPanel);
        //this.addWindowListener(new LoginWindowListener());
        mainFrame = this;
        /* mainPanel added to LoginWindow */
    }
    
    //LoginButtonListener is an ActionListener
    private class LoginButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            String userid = userid();
            String pass = password();
            
            if ( (userid.trim().equals("")) || (pass.trim().equals("")) ) {
                statusLabel.setText("UserID / Password can not be empty");
            }
            else {
                
                if (e.getActionCommand().equals("Student")) {
                    student = new Student(userid(), password());
                
                    if(student.login()) {
                        statusLabel.setText("Login Successful.");
                        try {
                            student = Student.searchStudent(userid, Student.USERID);
                            mainFrame.dispose();
                            StudentWindow studentWindow = new StudentWindow(student);
                            studentWindow.setVisible(true);

                        } catch (IOException ex) {
                            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else {
                        statusLabel.setText("Incorrect UserID / Password");
                    }
                }
                else if (e.getActionCommand().equals("Examiner")) {
                    examiner = new Examiner(userid, pass);
                
                    if(examiner.login()) {
                        statusLabel.setText("Login Successful.");
                        
                        try {
                            examiner = Examiner.searchExaminer(userid, Examiner.USERID);
                            mainFrame.dispose();
                            ExaminerWindow examinerWindow = new ExaminerWindow(examiner);
                            examinerWindow.setVisible(true);

                        } catch (IOException ex) {
                            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else {
                        statusLabel.setText("Incorrect UserID / Password");
                    }
                }
            }
        }
        
    }
    
    private class ExaminerCheckListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (examinerCheck.isSelected()) {
                loginButton.setActionCommand("Examiner");
            }
            else if (!examinerCheck.isSelected()) {
                loginButton.setActionCommand("Student");
            }
        }
        
    }
    
    private class LoginWindowListener extends WindowAdapter {
        
        @Override
        public void windowClosing(WindowEvent e) {
            e.getWindow().setVisible(false);
        }
        
    }
    
    private String userid() {
        return usernameField.getText();
    }
    
    private String password() {
        return passwordField.getText();
    }
    
    public static void main(String[] args) {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setVisible(true);
    }
    
}
