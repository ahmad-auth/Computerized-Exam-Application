package cesbackend;

import java.io.*;
import java.util.logging.*;

/**
 *
 * @author ahmad
 */
public class Question implements FileRecord {
    
    public static int recordsCount = 0;
    
    private static final File PROGRAMMING = new File("records\\questions\\Programming.txt");
    private static final File MATHEMATICS = new File("records\\questions\\Mathematics.txt");
    private static final File ELECTRICAL = new File("records\\questions\\Electrical.txt");
    private static final File HUMANITIES = new File("records\\questions\\Humanities.txt");
    
    public static final int ID = 0;
    public static final int SUBJECT = 1;
    public static final int ANSWER = 7;
    
    private String id;
    private String subject;
    private String[] Question; // {Question, Option1, Option2, Option3, Option4, Answer}

    public Question() {
        this.id = null;
        this.subject = null;
        this.Question = null;
    }
    
    public Question(String id, String subject, String[] Question) {
        this.id = id;
        this.subject = subject;
        this.Question = Question;
    }

    public Question(String subject) {
        this.subject = subject;
    }
    
    public Question(Question question) {
        this.id = question.id;
        this.subject = question.subject;
        this.Question = question.Question;
    }
    
    public static Question getQuestion(String[] record) {
        
        String id = record[0];
        String subject = record[1];
        String[] question = new String[6];
        
        for(int i=0, j=2; i<question.length; i++, j++) {
            question[i] = record[j];
        }
        return new Question(id, subject, question);
    }
    
    @Override
    public boolean addRecord() {
        
        boolean addition = false;
        File file = this.determineSubject();
        
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
                return addition;
            }
        }
        
        BufferedWriter bufferedWriter = null;
        String line;
        
        try {
            bufferedWriter = new BufferedWriter( new FileWriter(file.getAbsoluteFile(), true) );
            line = this.toString();
            bufferedWriter.write(line + "\n");
            addition = true;
        } catch (Exception ex) {
            Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                if(bufferedWriter != null)
                    bufferedWriter.close();
            } catch (Exception ex) {
                Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return addition;
    }
    
    public static Question[] getAllQuestions(String subject) throws FileNotFoundException {

        Question q = new Question(subject);
        File file = q.determineSubject();
        
        BufferedReader bufferedReader = null;
        String line;
        String[] record;
        
        recordsCount = countRecords(file);
        Question[] questions = new Question[recordsCount];
        
        if(file.exists() && file!=null) {
            try {
                bufferedReader = new BufferedReader( new FileReader(file) );
                int i=0;
                while ( (line = bufferedReader.readLine()) != null ) {
                    record = line.split("#");
                    questions[i] = getQuestion(record);
                    i++;
                }
            } catch (Exception ex) {
                Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                    try {
                        if (bufferedReader != null)
                            bufferedReader.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        }
        else {
            throw new FileNotFoundException();
        }
        
        return questions;
    }
    
    public static String[][] geAlltRecords(String subject) throws FileNotFoundException {
        
        Question q = new Question(subject);
        File file = q.determineSubject();
        
        BufferedReader bufferedReader = null;
        String line;
        String[] record;
        
        recordsCount = countRecords(file);
        String[][] records = new String[recordsCount][];
        
        if(file.exists() && file!=null) {
            try {
                bufferedReader = new BufferedReader( new FileReader(file) );
                int i=0;
                while ( (line = bufferedReader.readLine()) != null ) {
                    record = line.split("#");
                    records[i] = record;
                    i++;
                }
            } catch (Exception ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                    try {
                        if (bufferedReader != null)
                            bufferedReader.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        }
        else {
            throw new FileNotFoundException();
        }
        
        return records;
    }
    
    public static Question searchQuestion(String subject, String property, int index) throws FileNotFoundException {
         
        Question q = new Question(subject);
        File file = q.determineSubject();
        
        Question question = null;
        BufferedReader bufferedReader = null;
        String line;
        String[] record;
        if(!file.exists()) {
            throw new FileNotFoundException();
        }
        else {
            try {
                bufferedReader = new BufferedReader( new FileReader(file) );
                while ( (line = bufferedReader.readLine()) != null ) {
                    record = line.split("#");
                    if(record[index].equalsIgnoreCase(property)) {
                            question = getQuestion(record);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return question;
    }
    
    public static boolean deleteQuestion(String id, String subject) throws FileNotFoundException {
        
        Question q = new Question(subject);
        File file = q.determineSubject();
        
        BufferedReader bufferedReader = null;
        String line;
        String[] record;
        boolean exist=false;
        
        recordsCount = countRecords(file);
        String[] records = new String[recordsCount];
        
        if(!file.exists()) {
            throw new FileNotFoundException();
        }
        else {
            try {
                bufferedReader = new BufferedReader( new FileReader(file) );
                int i=0;
                while ( (line = bufferedReader.readLine()) != null ) {
                    record = line.split("#");
                    if(!record[0].equalsIgnoreCase(id)) {
                        records[i] = line;
                        i++;
                    }
                    else
                        exist = true;
                }
            } catch (IOException ex) {
                Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (exist) {
            BufferedWriter bufferedWriter = null;
            try {
                bufferedWriter = new BufferedWriter( new FileWriter(file.getAbsoluteFile(), false) );
                for (int i=0; records[i] != null; i++) {
                    bufferedWriter.write( records[i] + "\n" );
                }
            } catch (IOException ex) {
                Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                    if (bufferedWriter != null)
                        bufferedWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
        else {
            return false;
        }
        
        return true;
    }
    
    public static int countRecords(File file) {
        BufferedReader bufferedReader = null;
        int count = 0;
        try {
            bufferedReader = new BufferedReader( new FileReader(file) );
            while (bufferedReader.readLine() != null) {                
                count++;
            }
        } catch (Exception ex) {
            Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
    
    public File determineSubject() {
        if(this.getSubject().equalsIgnoreCase("Programming")) {
            return PROGRAMMING;
        }
        else if(this.getSubject().equalsIgnoreCase("Math")) {
            return MATHEMATICS;
        }
        else if(this.getSubject().equalsIgnoreCase("Electrical")) {
            return ELECTRICAL;
        }
        else if(this.getSubject().equalsIgnoreCase("Humanities")) {
            return HUMANITIES;
        }
        else {
            return null;
        }
    }
    
    @Override
    public String toString() {
        return (this.id + "#" + this.subject + "#" + Question[0] + "#" + Question[1] + "#" + Question[2] + "#" + Question[3] 
                + "#" + Question[4] + "#" + Question[5]);
    }
    
    public boolean equals(Question question) {
        return (this.id.equals(question.id));
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public void setQuestion(String[] Question) {
        this.Question = Question;
    }

    public String getId() {
        return id;
    }

    public String[] getQuestion() {
        return Question;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
    
}
