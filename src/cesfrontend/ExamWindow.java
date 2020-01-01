package cesfrontend;

import cesbackend.Exam;
import cesbackend.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author ahmad
 */
public class ExamWindow extends JFrame {
    
    private static JLabel subjectLabel;
    private static JLabel questionsCountLabel;
    private static JLabel totalTimeLabel;
    private static JLabel statusLabel;
    
    private static JTextField subjectField;
    private static JTextField questionsCountField;
    private static JTextField totalTimeField;
    
    private static JLabel questionLabel;
    
    private static JLabel[] optionLabel = new JLabel[4];
    
    private static JCheckBox[] optionCheck = new JCheckBox[4];
    
    private static JButton nextButton;
    private static JButton lockButton;
    private static JButton submitExamButton;
    
    private static JFrame mainFrame;
    
    private Timer countdown;
    
    private static final int WIDTH = 600;
    private static final int HEIGHT = 425;
    
    private static int questionCount=0;
    private static int index=0;
    private static int mark=0;
    
    private String questionID;
    private String answerSelected;
    
    private boolean answerSubmitted;
    
    private String[] questionIDs;
    private String[] answersGiven;
    
    private static Exam exam;
    private static Student student;
    
    public ExamWindow(Student student, Exam exam) {
        
        super();
        // Setting properties for ExamWindow
        this.setTitle("Exam Page");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.student = student;
        this.exam = exam;
        
        String question = ""; // = "Question from File";
        String option = ""; // = "Option from File";
        String subject = exam.getSubject(); // = "Programming";
        String totalTime = "2:00"; // = "10 min";
        
        answerSubmitted = false;
        
        //exam = new Exam(subject);
        questionIDs = exam.getQuestionsIDs();
        answersGiven = new String[questionIDs.length];
        
        int totalQuestions = questionIDs.length;
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(CColors.CLOUDS);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setMaximumSize(new Dimension(300, 100));
        
        JPanel subjectPanel = new JPanel();
        subjectPanel.setOpaque(false);
        subjectPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        subjectLabel = new JLabel("Subject:");
        subjectLabel.setForeground(CColors.BLUE_GREY_800);
        subjectLabel.setFont(new Font("Arial", Font.BOLD, 16));
        subjectPanel.add(subjectLabel);
        subjectField = new JTextField(10);
        subjectField.setOpaque(false);
        subjectField.setForeground(CColors.BLUE_GREY_900);
        subjectField.setFont(new Font("Arial", Font.PLAIN, 18));
        subjectField.setText(subject);
        subjectField.setEditable(false);
        subjectPanel.add(subjectField);
        infoPanel.add(subjectPanel);
        
        JPanel questionsCountPanel = new JPanel();
        questionsCountPanel.setOpaque(false);
        questionsCountPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        questionsCountLabel = new JLabel("Questions:");
        questionsCountLabel.setForeground(CColors.BLUE_GREY_800);
        questionsCountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionsCountPanel.add(questionsCountLabel);
        questionsCountField = new JTextField(7);
        questionsCountField.setOpaque(false);
        questionsCountField.setForeground(CColors.BLUE_GREY_900);
        questionsCountField.setFont(new Font("Arial", Font.PLAIN, 18));
        questionsCountField.setText( String.valueOf(totalQuestions - questionCount) 
                + " out of " + String.valueOf(totalQuestions) );
        questionsCountField.setEditable(false);
        questionsCountPanel.add(questionsCountField);
        infoPanel.add(questionsCountPanel);
        
        JPanel totalTimePanel = new JPanel();
        totalTimePanel.setOpaque(false);
        totalTimePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        totalTimeLabel = new JLabel("Time:");
        totalTimeLabel.setForeground(CColors.BLUE_GREY_800);
        totalTimeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalTimePanel.add(totalTimeLabel);
        totalTimeField = new JTextField(5);
        totalTimeField.setFont(new Font("Arial", Font.PLAIN, 20));
        totalTimeField.setOpaque(false);
        totalTimeField.setForeground(CColors.BLUE_GREY_900);
        totalTimeField.setText(totalTime);
        totalTimeField.setEditable(false);
        totalTimePanel.add(totalTimeField);
        infoPanel.add(totalTimePanel);
        
        mainPanel.add(infoPanel);
        
        JPanel questionPanel = new JPanel();
        questionPanel.setOpaque(false);
        questionPanel.setLayout(new BorderLayout());
        
        questionLabel = new JLabel("Q. " + question);
        questionLabel.setForeground(CColors.BLUE_GREY_800);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionPanel.add(questionLabel, BorderLayout.NORTH);
        
        JPanel answerPanel = new JPanel();
        answerPanel.setOpaque(false);
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        
        JPanel[] choicePanel = new JPanel[4];
        for (int i=0; i<optionLabel.length; i++) {
            choicePanel[i] = new JPanel();
            choicePanel[i].setOpaque(false);
            choicePanel[i].setLayout(new FlowLayout());
            optionCheck[i] = new JCheckBox((i+1) + ".");
            optionCheck[i].setActionCommand("optionCheck"+(i+1));
            optionCheck[i].addActionListener(new optionCheckBoxListener());
            choicePanel[i].add(optionCheck[i]);
            optionLabel[i] = new JLabel(option);
            optionLabel[i].setForeground(CColors.BLUE_GREY_900);
            optionLabel[i].setFont(new Font("Arial", Font.PLAIN, 16));
            choicePanel[i].add(optionLabel[i]);
            answerPanel.add(choicePanel[i]);
        }
        questionPanel.add(answerPanel, BorderLayout.WEST);
        questionPanel.setMaximumSize(new Dimension(WIDTH, 300));
        mainPanel.add(questionPanel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
        mainPanel.add(buttonPanel);
        
        lockButton = new JButton("Lock");
        lockButton.setPreferredSize(new Dimension(80, 40));
        lockButton.setBackground(CColors.TEAL_400);
        lockButton.setForeground(CColors.CLOUDS);
        lockButton.setFont(new Font("Arial", Font.BOLD, 16));
        lockButton.addActionListener(new lockButtonListener());
        buttonPanel.add(lockButton);
        nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(80, 40));
        nextButton.setBackground(CColors.TEAL_400);
        nextButton.setForeground(CColors.CLOUDS);
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.addActionListener(new nextButtonListener());
        buttonPanel.add(nextButton);
        
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        submitPanel.setOpaque(false);
        mainPanel.add(submitPanel);
        submitExamButton = new JButton("Submit");
        submitExamButton.setPreferredSize(new Dimension(100, 40));
        submitExamButton.setBackground(CColors.TEAL_400);
        submitExamButton.setForeground(CColors.CLOUDS);
        submitExamButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitExamButton.addActionListener(new SubmitExamButtonListener());
        submitPanel.add(submitExamButton);
        
        JPanel statusPanel = new JPanel();
        statusPanel.setOpaque(false);
        mainPanel.add(statusPanel);
        statusLabel = new JLabel("..");
        statusLabel.setForeground(CColors.POMEGRANATE);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusPanel.add(statusLabel);
        
        nextQuestion();
        initializeCountdown();
        countdown.start();
        
        this.add(mainPanel);
        mainFrame = this;
    }
    
    private class optionCheckBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            if(actionCommand.equals("optionCheck1")) {
                answerSelected = "a";
                optionCheck[1].setSelected(false);
                optionCheck[2].setSelected(false);
                optionCheck[3].setSelected(false);
            }
            else if(actionCommand.equals("optionCheck2")) {
                answerSelected = "b";
                optionCheck[0].setSelected(false);
                optionCheck[2].setSelected(false);
                optionCheck[3].setSelected(false);
            }
            else if(actionCommand.equals("optionCheck3")) {
                answerSelected = "c";
                optionCheck[0].setSelected(false);
                optionCheck[1].setSelected(false);
                optionCheck[3].setSelected(false);
            }
            else if(actionCommand.equals("optionCheck4")) {
                answerSelected = "d";
                optionCheck[0].setSelected(false);
                optionCheck[1].setSelected(false);
                optionCheck[2].setSelected(false);
            }
            else {
                //DO NOTHING
            }
                
        }
        
    }
    
    private class lockButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(isCheckBoxSelected()) {
                answerSubmitted = true;
                lockAllCheckBoxes();
                answersGiven[questionCount] = answerSelected;
            }
            else {
                statusLabel.setText("Select an answer.");
            }
        }
        
    }
    
    private class nextButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (answerSubmitted) {
                questionCount++;
                questionsCountField.setText( String.valueOf(10 - questionCount) + " out of " + 10 );
                nextQuestion();
                setAllSelected(false);
                unlockAllCheckBoxes();
            }
            else {
                statusLabel.setText("Submit an answer");
            }
        }
        
    }
    
    private class SubmitExamButtonListener implements ActionListener {

        JFrame submitConfirmation;
        
        @Override
        public void actionPerformed(ActionEvent e) {
            submitConfirmation = new JFrame("Submit Exam");
            submitConfirmation.setSize(350, 150);
            submitConfirmation.setVisible(true);
            
            JPanel panel = new JPanel();
            panel.setBackground(CColors.BLUE_GREY_900);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            submitConfirmation.add(panel);
            
            JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            labelPanel.setOpaque(false);
            panel.add(labelPanel);
            JLabel label = new JLabel("Are you sure you want to submit the exam?");
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
                    submitConfirmation.dispose();
                    submitExam();
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
                    submitConfirmation.dispose();
                }
            });
            buttonPanel.add(noButton);
        }
    }
    
    public void submitExam() {
//        JFrame marksFrame = new JFrame("Marks Panel");
//        marksFrame.setSize(100, 50);
//        marksFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        marksFrame.setResizable(false);
//        marksFrame.setVisible(true);
        
        int marks = exam.examineAnswers(questionIDs, answersGiven);
        student.setMarks(marks);
        student.updateMarks();
        mainFrame.dispose();
        StudentWindow studentWindow = new StudentWindow(student);
        studentWindow.setVisible(true);
        
//        marksFrame.add( new JLabel("Marks = " + student.getMarks()) );
    }
    
    public void initializeCountdown() {
        countdown  = new Timer(1000, new ActionListener() {
            private int count = 120;
            private int minute = 0;
            private int  second = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count>=0) {
                    if (count%60==0) {
                        minute = (count-60)/60;
                        totalTimeField.setText( String.valueOf(minute) + ":" + "00"/*String.valueOf(second)*/ );
                        second = 59;
                    }
                    else {
                        totalTimeField.setText( String.valueOf(minute) + ":" + String.valueOf(second) );
                        second--;
                    }
                    count--;
                }
                else {
                    ((Timer)e.getSource()).stop();
                    statusLabel.setText("Time Finished!");
                    lockAllCheckBoxes();
                    questionCount=10; //Workaroung for finish
                    submitExam();
                }
            }
        });
    }
    
    public void nextQuestion() {
        if(questionCount<10) {
            String[] Q;
            questionID = questionIDs[questionCount];
            Q = exam.getQuestionRecord( questionID );
            setQuestion(Q);
            answerSubmitted = false;
        }
        else {
            countdown.stop();
            lockAllCheckBoxes();
            submitExam();
        }
    }
    
    public void setQuestion(String[] Question) {
        questionLabel.setText( "Q" + String.valueOf(questionCount+1) + ". " + Question[2] );
        optionLabel[0].setText(Question[3]);
        optionLabel[1].setText(Question[4]);
        optionLabel[2].setText(Question[5]);
        optionLabel[3].setText(Question[6]);
    }
    
    public boolean isCheckBoxSelected() {
        return (optionCheck[0].isSelected() || optionCheck[1].isSelected() ||
                optionCheck[2].isSelected() || optionCheck[3].isSelected());
    }
    
    public void setAllSelected(boolean state) {
        optionCheck[0].setSelected(state);
        optionCheck[1].setSelected(state);
        optionCheck[2].setSelected(state);
        optionCheck[3].setSelected(state);
    }
    
    public void lockAllCheckBoxes() {
        optionCheck[0].setEnabled(false);
        optionCheck[1].setEnabled(false);
        optionCheck[2].setEnabled(false);
        optionCheck[3].setEnabled(false);
    }
    
    public void unlockAllCheckBoxes() {
        optionCheck[0].setEnabled(true);
        optionCheck[1].setEnabled(true);
        optionCheck[2].setEnabled(true);
        optionCheck[3].setEnabled(true);
    }
    
    public static void main(String[] args) {
        Student s = new Student();
        Exam e = new Exam();
        ExamWindow examWindow = new ExamWindow(s, e);
        examWindow.setVisible(true);
    }
}
