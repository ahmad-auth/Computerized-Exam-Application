package cesbackend;

import java.io.*;
import java.util.Arrays;
import java.util.logging.*;

/**
 *
 * @author ahmad
 */
public class Student extends User {
    
    public static int recordsCount;
    
    private static final File STUDENT = new File("records\\users\\Student.txt");
    
    public static final int EDU_GROUP = 4;
    public static final int MARKS = 5;
    
    private String eduGroup;
    private int marks;
    
    public Student() {
        super();
        this.eduGroup = null;
        this.marks = 0;
    }

    public Student(String name, String cnic, String eduGroup) {
        super(name, cnic);
        this.eduGroup = eduGroup;
        this.marks = 0;
    }
    
    public Student(String id, String password) {
        super(id, password, null, null);
    }
    
    public Student(String id, String password, String name, String cnic , String eduGroup, int marks) {
        super(id, password, name, cnic);
        this.eduGroup = eduGroup;
        this.marks = marks;
    }
    
    public Student(Student student) {
        super(student.getId(), student.getPassword(), student.getName(), student.getName());
        this.eduGroup = student.eduGroup;
        this.marks = student.marks;
    }
    
    public static Student getStudent(String[] record) {
        String userid = record[0];
        String pass = record[1];
        String name = record[2];
        String cnic = record[3];
        String eduGroup = record[4];
        String marks = record[5];
        return new Student(userid, pass, name, cnic, eduGroup, Integer.parseInt(marks));
    }
    
    @Override
    public boolean login() {
        
        BufferedReader bufferedReader = null;
        boolean exist = false;
        
        if(STUDENT.exists()) {
            
            try {
                bufferedReader = new BufferedReader( new FileReader(STUDENT) );
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
    
    @Override
    public boolean addRecord() {
        
        if(!STUDENT.exists()) {
            try {
                STUDENT.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        
        BufferedWriter bufferedWriter = null;
        String line;
        
        try {
            bufferedWriter = new BufferedWriter( new FileWriter(STUDENT.getAbsoluteFile(), true) );
            line = this.toString();
            bufferedWriter.write(line + "\n");
        } catch (Exception ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                if(bufferedWriter != null)
                    bufferedWriter.close();
            } catch (Exception ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }
    
    public static Student[] getAllStudents() throws FileNotFoundException {

        BufferedReader bufferedReader = null;
        String line;
        String[] record;
        
        recordsCount = countRecords();
        Student[] examiners = new Student[recordsCount];
        
        if(STUDENT.exists()) {
            try {
                bufferedReader = new BufferedReader( new FileReader(STUDENT) );
                int i=0;
                while ( (line = bufferedReader.readLine()) != null ) {
                    record = line.split("#");
                    examiners[i] = getStudent(record);
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
        
        return examiners;
    }
    
    
    public static String[][] geAlltRecords() throws FileNotFoundException {
        
        BufferedReader bufferedReader = null;
        String line;
        String[] record;
        
        recordsCount = countRecords();
        String[][] records = new String[recordsCount][];
        
        if(STUDENT.exists()) {
            try {
                bufferedReader = new BufferedReader( new FileReader(STUDENT) );
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
    
    public static Student searchStudent(String property, int index) throws FileNotFoundException {
        Student student = null;
        BufferedReader bufferedReader = null;
        String line;
        String[] record;
        if(!STUDENT.exists()) {
            throw new FileNotFoundException();
        }
        else {
            try {
                bufferedReader = new BufferedReader( new FileReader(STUDENT) );
                while ( (line = bufferedReader.readLine()) != null ) {
                    record = line.split("#");
                    if(record[index].equalsIgnoreCase(property)) {
                        student = getStudent(record);
                    }
                }
            } catch (IOException ex) {
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
        
        return student;
    }
    
    public static boolean deleteStudent(String id) throws FileNotFoundException {
        BufferedReader bufferedReader = null;
        String line;
        String[] record;
        boolean exist=false;
        
        recordsCount = countRecords();
        String[] records = new String[recordsCount];
        
        if(!STUDENT.exists()) {
            throw new FileNotFoundException();
        }
        else {
            try {
                bufferedReader = new BufferedReader( new FileReader(STUDENT) );
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
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (exist) {
            BufferedWriter bufferedWriter = null;
            try {
                bufferedWriter = new BufferedWriter( new FileWriter(STUDENT.getAbsoluteFile(), false) );
                for (int i=0; records[i] != null; i++) {
                    bufferedWriter.write( records[i] + "\n" );
                }
            } catch (IOException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                    if (bufferedWriter != null)
                        bufferedWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
        else {
            return false;
        }
        
        return true;
    }
    
    public void updateMarks() {
        BufferedReader bufferedReader = null;
        boolean exist = false;
        
        recordsCount = countRecords();
        String[] records = new String[recordsCount];
        
        if(STUDENT.exists()) {
            try {
                bufferedReader = new BufferedReader( new FileReader(STUDENT) );
                
                String line;
                String[] record;
                int i=0;
                while ( (line = bufferedReader.readLine()) != null ) {
                    record = line.split("#");
                    if(record[0].equals(this.getId())) {
                        record[5] = String.valueOf(this.getMarks());
                        line = (record[0] + "#" + record[1] + "#" + record[2] + "#" + record[3] 
                                + "#" + record[4] + "#" + record[5]);
                        records[i] = line;
                        exist = true;
                        i++;
                    }
                    else {
                        records[i] = line;
                        i++;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
        //System.out.println(Arrays.toString(records));
        if (exist) {
            BufferedWriter bufferedWriter = null;
            try {
                bufferedWriter = new BufferedWriter( new FileWriter(STUDENT.getAbsoluteFile(), false) );
                for (int i=0; i<records.length && records[i] != null; i++) {
                    bufferedWriter.write( records[i] + "\n" );
                }
            } catch (IOException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                try {
                    if (bufferedReader != null)
                        bufferedReader.close();
                    if (bufferedWriter != null)
                        bufferedWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static int countRecords() {
        BufferedReader bufferedReader = null;
        int count = 0;
        try {
            bufferedReader = new BufferedReader( new FileReader(STUDENT) );
            while (bufferedReader.readLine() != null) {                
                count++;
            }
        } catch (Exception ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
    
    @Override
     public String toString() {
        return (super.toString() + "#" + this.eduGroup + "#" + this.marks);
    }
    
    public boolean equals(Student s) {
        return (this.getCnic().equals(s.getCnic()));
    }

    public void setEduGroup(String eduGroup) {
        this.eduGroup = eduGroup;
    }
    
    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getEduGroup() {
        return eduGroup;
    }
    
    public int getMarks() {
        return marks;
    }
    
    public static void main(String[] args) {
//        try {
//            //System.out.println("Output");
//            Student s = Student.searchStudent("1100", Student.USERID);
//            System.out.println(s.toString());
//            s.setMarks(50);
//            System.out.println(s.toString());
//            s.updateMarks();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
