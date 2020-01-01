package cesfrontend;

import cesbackend.Student;
import cesbackend.Exam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author ahmad
 */
public class StudentWindow extends JFrame {

    private static JLabel studentNameLabel;
    private static JLabel studentCnicLabel;
    private static JLabel studentEduGroupLabel;
    
    private static JTextField studentNameField;
    private static JTextField studentCnicField;
    private static JTextField studentEduGroupField;
    
    private static JButton takeExamButton;
    private static JButton checkResultButton;
    
    private static JFrame mainFrame;
    
    private static final int WIDTH = 360;
    private static final int HEIGHT = 250;
    
    private static Student student;
    private static Exam exam;
    
    public StudentWindow(Student student) {
        
        super();
        // Setting properties for ExamWindow
        this.setTitle("Sutdent Panel");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.student = student;
        
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(true);
        mainPanel.setBackground(CColors.BLUE_GREY_900);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JPanel studentInfoPanel = new JPanel();
        studentInfoPanel.setOpaque(false);
        studentInfoPanel.setLayout( new GridLayout(3, 1) );
        mainPanel.add(studentInfoPanel, BorderLayout.NORTH);
        JPanel studentNamePanel = new JPanel();
        studentNamePanel.setOpaque(false);
        studentNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        studentInfoPanel.add(studentNamePanel);
        studentNameLabel = new JLabel("Name:");
        studentNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        studentNameLabel.setForeground(CColors.TEAL_400);
        studentNamePanel.add(studentNameLabel);
        studentNamePanel.add(Box.createHorizontalStrut(20));
        studentNameField = new JTextField(15);
        studentNameField.setOpaque(false);
        studentNameField.setFont(new Font("Arial", Font.PLAIN, 18));
        studentNameField.setForeground(CColors.CLOUDS);
        studentNameField.setText(student.getName());
        studentNameField.setEditable(false);
        studentNamePanel.add(studentNameField);
        
        JPanel studentCnicPanel = new JPanel();
        studentCnicPanel.setOpaque(false);
        studentCnicPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        studentInfoPanel.add(studentCnicPanel);
        studentCnicLabel = new JLabel("C.N.I.C:");
        studentCnicLabel.setFont(new Font("Arial", Font.BOLD, 16));
        studentCnicLabel.setForeground(CColors.TEAL_400);
        studentCnicPanel.add(studentCnicLabel);
        studentCnicPanel.add(Box.createHorizontalStrut(20));
        studentCnicField = new JTextField(15);
        studentCnicField.setOpaque(false);
        studentCnicField.setFont(new Font("Arial", Font.PLAIN, 18));
        studentCnicField.setForeground(CColors.CLOUDS);
        studentCnicField.setText(student.getCnic());
        studentCnicField.setEditable(false);
        studentCnicPanel.add(studentCnicField);
        
        JPanel studentEduGroupPanel = new JPanel();
        studentEduGroupPanel.setOpaque(false);
        studentEduGroupPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        studentInfoPanel.add(studentEduGroupPanel);
        studentEduGroupLabel = new JLabel("Edu-Group:");
        studentEduGroupLabel.setFont(new Font("Arial", Font.BOLD, 16));
        studentEduGroupLabel.setForeground(CColors.TEAL_400);
        studentEduGroupPanel.add(studentEduGroupLabel);
        studentEduGroupPanel.add(Box.createHorizontalStrut(20));
        studentEduGroupField = new JTextField(15);
        studentEduGroupField.setOpaque(false);
        studentEduGroupField.setFont(new Font("Arial", Font.PLAIN, 18));
        studentEduGroupField.setForeground(CColors.CLOUDS);
        studentEduGroupField.setText(student.getEduGroup());
        studentEduGroupField.setEditable(false);
        studentEduGroupPanel.add(studentEduGroupField);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        takeExamButton = new JButton("Take Exam");
        takeExamButton.setFont(new Font("Arial", Font.PLAIN, 18));
        takeExamButton.setForeground(CColors.CLOUDS);
        takeExamButton.setBackground(CColors.CYAN_800);
        takeExamButton.addActionListener(new ExamButtonListener());
        buttonPanel.add(takeExamButton);
        checkResultButton = new JButton("Check Result");
        checkResultButton.setForeground(CColors.CLOUDS);
        checkResultButton.setBackground(CColors.CYAN_800);
        checkResultButton.addActionListener(new ExamButtonListener());
        checkResultButton.setFont(new Font("Arial", Font.PLAIN, 18));
        //checkResultButton.setEnabled(false);
        buttonPanel.add(checkResultButton);
        
        
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
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
    
    private class ExamButtonListener implements ActionListener {

        JFrame marksFrame;
                
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Take Exam")) {
                String subject = determineSubject(student.getEduGroup());
                mainFrame.dispose();
                exam = new Exam(subject);
                ExamWindow examWindow = new ExamWindow(student, exam);
                examWindow.setVisible(true);
            }
            else if (e.getActionCommand().equals("Check Result")) {
                marksFrame = new JFrame("Marks Window");
                marksFrame.setSize(300, 150);
                marksFrame.setVisible(true);
                
                JPanel mainPanel = new JPanel();
                mainPanel.setBackground(CColors.BLUE_GREY_900);
                mainPanel.setLayout(new GridLayout(2, 1));
                
                JPanel marksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
                marksPanel.setOpaque(false);
                JLabel marksLabel = new JLabel("Marks:");
                marksLabel.setForeground(CColors.TEAL_400);
                marksLabel.setFont(new Font("Arial", Font.BOLD, 20));
                marksPanel.add(marksLabel);
                JTextField marksField = new JTextField(""+student.getMarks(), 5);
                marksField.setOpaque(false);
                marksField.setForeground(CColors.CLOUDS);
                marksField.setFont(new Font("Arial", Font.PLAIN, 22));
                marksField.setEditable(false);
                marksPanel.add(marksField);
                mainPanel.add(marksPanel);
                
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.setOpaque(false);
                JButton backButton = new JButton("Back");
                backButton.setForeground(CColors.CLOUDS);
                backButton.setFont(new Font("Arial", Font.BOLD, 16));
                backButton.setBackground(CColors.CYAN_800);
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        marksFrame.dispose();
                    }
                });
                buttonPanel.add(backButton);
                mainPanel.add(buttonPanel);
                
                marksFrame.add(mainPanel);
            }
        }
        
        public String determineSubject(String eduGroup) {
            String subject;
            switch (eduGroup) {
                case "Computer Science": {
                    subject = "Programming";
                    break;
                }
                case "Electrical": {
                    subject = "Electrical";
                    break;
                }
                case "Mathematics": {
                    subject = "Mathematics";
                    break;
                }
                default : {
                    subject = null;
                }
            }
            return subject;
        }
    }
    
    private class LogoutButtonListener implements ActionListener {

        JFrame logoutConfirmation;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            logoutConfirmation = new JFrame("Submit Exam");
            logoutConfirmation.setSize(350, 150);
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
        
        Student s = new Student("name", "10101", "CS");
        StudentWindow window = new StudentWindow(s);
        window.setVisible(true);
    }
    
}
