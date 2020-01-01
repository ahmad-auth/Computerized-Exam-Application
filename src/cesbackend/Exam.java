package cesbackend;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmad
 */
public class Exam extends Question {
    
    public static final int TOTAL_QUESTIONS = 10;
    public static final int TOTAL_TIME = 10;
    
    public Exam() {
        super();
    }
    
    public Exam(String subject) {
        super(subject);
    }
    
    public String[] getQuestionRecord(String id) {
        Question q = null;
        try {
            q = searchQuestion(this.getSubject(), id, 0);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return q.toString().split("#");
    }
    
    public String[] getQuestionsIDs() {
        
        File file = determineSubject();
        BufferedReader bufferedReader = null;
        
        String[] questionIDs = new String[countRecords(file)];
        
        if(file.exists()) {
            try {
                bufferedReader = new BufferedReader( new FileReader(file) );
                String line;
                String[] record;
                int i=0;
                while ( (line = bufferedReader.readLine()) != null ) {
                    record = line.split("#");
                    questionIDs[i] = record[0];
                    i++;
                }
            } catch (IOException ex) {
                Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return questionIDs;
    }
    
    public int examineAnswers(String[] questionIds, String[] answersGiven) {
        
        String id;
        String answer;
        
        int marks=0;
        
        String[] record;
        
        for(int i=0; (i<answersGiven.length) && (answersGiven[i] != null); i++) {
            id = questionIds[i];
            answer = answersGiven[i];
            
            record = getQuestionRecord(id);
            
            if(answer.equals(record[record.length-1])) {
                marks++;
            }
        }
        
        return marks;
    }
}
