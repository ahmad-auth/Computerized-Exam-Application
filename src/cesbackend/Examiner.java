package cesbackend;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author ahmad
 */
public class Examiner extends User {
    
    public static int recordsCount = 0;
    
    private static final File EXAMINER = new File("records\\users\\Examiner.txt");
    
    public static final int SUBJECT = 4;
    
    private String subject;

    public Examiner() {
        super();
        this.subject = null;
    }
    
    public Examiner(String name, String cnic, String subject) {
        super(name, cnic);
        this.subject = subject;
    }
    
    public Examiner(String id, String password) {
        super(id, password, null, null);
    }
    
    public Examiner(String id, String password, String name, String cnic, String subject) {
        super(id, password, name, cnic);
        this.subject = subject;
    }

    public Examiner(Examiner examiner) {
        super(examiner.getId(), examiner.getPassword(), examiner.getName(), examiner.getCnic());
        this.subject = examiner.subject;
    }
    
    public static Examiner getExaminer(String[] record) {
        String userid = record[0];
        String pass = record[1];
        String name = record[2];
        String cnic = record[3];
        String subject = record[4];
        return new Examiner(cnic, pass, name, cnic, subject);
    }
    
    @Override
    public boolean login() {
        
        BufferedReader bufferedReader = null;
        boolean exist = false;
        
        if(EXAMINER.exists()) {
            
            try {
                bufferedReader = new BufferedReader( new FileReader(EXAMINER) );
                String line;
                String[] record = null;
                while( (line = bufferedReader.readLine()) != null ) {
                    record = line.split("#");
                    if(record[0].equals(this.getId()) && record[1].equals(this.getPassword())) {
                        exist = true;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }
        
        return exist;
    }
    
    public boolean addRecord() {
        
        boolean addition = false;
        
        if(!EXAMINER.exists()) {
            try {
                EXAMINER.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
                return addition;
            }
        }
        
        BufferedWriter bufferedWriter = null;
        String line;
        
        try {
            bufferedWriter = new BufferedWriter( new FileWriter(EXAMINER.getAbsoluteFile(), true) );
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
    
    public static Examiner[] getAllExaminers() throws FileNotFoundException {

        BufferedReader bufferedReader = null;
        String line;
        String[] record;
        
        recordsCount = countRecords();
        Examiner[] examiners = new Examiner[recordsCount];
        
        if(EXAMINER.exists()) {
            try {
                bufferedReader = new BufferedReader( new FileReader(EXAMINER) );
                int i=0;
                while ( (line = bufferedReader.readLine()) != null ) {
                    record = line.split("#");
                    examiners[i] = getExaminer(record);
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
        
        return examiners;
    }
    
    public static String[][] geAlltRecords() throws FileNotFoundException {
        
        BufferedReader bufferedReader = null;
        String line;
        String[] record;
        
        recordsCount = countRecords();
        String[][] records = new String[recordsCount][];
        
        if(EXAMINER.exists()) {
            try {
                bufferedReader = new BufferedReader( new FileReader(EXAMINER) );
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
    
    public static Examiner searchExaminer(String property, int index) throws FileNotFoundException {
        Examiner examiner = null;
        BufferedReader bufferedReader = null;
        String line;
        String[] record;
        if(!EXAMINER.exists()) {
            throw new FileNotFoundException();
        }
        else {
            try {
                bufferedReader = new BufferedReader( new FileReader(EXAMINER) );
                while ( (line = bufferedReader.readLine()) != null ) {
                    record = line.split("#");
                    if(record[index].equalsIgnoreCase(property)) {
                        examiner = getExaminer(record);
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
        
        return examiner;
    }
    
    public static boolean deleteExaminer(String id) throws FileNotFoundException {
        BufferedReader bufferedReader = null;
        String line;
        String[] record;
        boolean exist=false;
        
        recordsCount = countRecords();
        String[] records = new String[recordsCount];
        
        if(!EXAMINER.exists()) {
            throw new FileNotFoundException();
        }
        else {
            try {
                bufferedReader = new BufferedReader( new FileReader(EXAMINER) );
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
                bufferedWriter = new BufferedWriter( new FileWriter(EXAMINER.getAbsoluteFile(), false) );
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
    
    public static int countRecords() {
        
        BufferedReader bufferedReader = null;
        int count = 0;
        
        try {
            bufferedReader = new BufferedReader( new FileReader(EXAMINER) );
            while (bufferedReader.readLine() != null) {                
                count++;
            }
        } catch (Exception ex) {
            Logger.getLogger(Examiner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
    
    @Override
    public String toString() {
        return (super.toString() + "#" + this.subject);
    }
    
    public boolean equals(Examiner e) {
        return (this.getCnic().equals(e.getCnic()));
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
    
}
