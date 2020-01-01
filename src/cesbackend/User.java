package cesbackend;

/**
 *
 * @author ahmad
 */
public abstract class User implements FileRecord {
    
    private String id;
    private String password;
    private String name;
    private String cnic;

    public final static int USERID = 0;
    public final static int NAME = 2;
    public final static int CNIC = 3;
    //public static int PASSWORD = 1;
    
    public User() {
        this.id = null;
        this.password = null;
        this.name = null;
        this.cnic = null;
    }

    public User(String name, String cnic) {
        this.id = null;
        this.password = null;
        this.name = name;
        this.cnic = cnic;
    }

    public User(String id, String password, String name, String cnic) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.cnic = cnic;
    }
    
    public User(User user) {
        this.id = user.id;
        this.password = user.password;
        this.name = user.name;
        this.cnic = user.cnic;
    }
    
    public abstract boolean login();
    
    @Override
    public String toString() {
        return ( id + "#" + password + "#" + name + "#" + cnic);
    }
    
    public boolean equals(User u) {
        return (this.cnic.equals(u.cnic));
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
    
    public String getName() {
        return name;
    }

    public String getCnic() {
        return cnic;
    }
    
}
