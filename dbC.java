import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class dbC
{
    String db;
    String port = "5432"; // default PG port.
    String un;
    String pw;
    String hn = "localhost";
    private boolean connected = false;
    public boolean isConnected() {return connected;}

    public String getHostname()
    {
        return hn;
    }

    public void setHostname(String hn)
    {
        this.hn = hn;
        url = "jdbc:postgresql://" + hn + ":" + port + "/";
    }


    String url = "jdbc:postgresql://" + hn + ":" + port + "/";
    private boolean debug;

    public boolean isDebug()
    {
        return debug;
    }

    public boolean getEnv()
    {
        Map<String, String> env = System.getenv();
        if (env.containsKey("PGDATABASE") &&
                env.containsKey("PGUSER") &&
                env.containsKey("PGPASSWORD"))
        {
            setPort("5432");
            db = env.get("PGDATABASE");
            un = env.get("PGUSER");
            pw = env.get("PGPASSWORD");
            if (env.containsKey("PGHOST"))
            {
                setHostname(env.get("PGHOST"));
            }
            else
            {
                setHostname("localhost");
            }
            System.out.println("Connecting through environment");
            connect();
            return true;
        }
        return false;
    }

    public void setDebug(boolean debug)
    {
        this.debug = debug;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(String port)
    {
        this.port = port;
        url = "jdbc:postgresql://" + hn + ":" + port + "/";
    }
    public dbC()
    {
        System.out.println("d");
    }


    public dbC(String hostname, String database, String username, String password,String port)
    {
        hn=hostname;
        setPort(port);
        db = database;
        un = username;
        pw = password;
        connected =  connect();
    }

    public dbC(String hostname, String database, String username, String password)
    {
        hn=hostname;
        setPort("5432");
        db = database;
        un = username;
        pw = password;
        connected =  connect();
    }

    public dbC(String database, String username, String password)
    {
        setPort("5432");
        db = database;
        un = username;
        pw = password;
        connected =  connect();
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }


    private Connection con;
    public Connection getConnection(){return con;}


    public int do_sql_nr(String s)
    {
        int i=0;
        if (debug) System.out.println("NR_Executing ->" + s);
        if (con==null)
        {
            System.out.println("No Connection...");
            return -1;
        }
        try
        {
            PreparedStatement st = con.prepareStatement(s);
            long st_time = System.currentTimeMillis();
            st.execute();
            long en_time = System.currentTimeMillis();
            i = (int) (en_time - st_time);
        }
        catch (Exception e)
        {
            System.out.println("EXCEPTION -> \"" + s + "\"");
            e.printStackTrace();System.exit(1);
        }
        return i;
    }

    public ResultSet do_sql(String s)
    {
        try
        {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if (debug) System.out.println("Executing ->" + s);
            ResultSet rs = st.executeQuery(s);
            if (rs != null)
            {
                return rs;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();System.out.println("Ouch..." + s);
            //System.exit(1);
        }
        return null;
    }

    public boolean connect()
    {
        Statement st;
        ResultSet rs = null;
        try
        {
            System.out.println("Connecting->" + url + db + " un=" + un + " pw=" + pw + " port=" + port);
            con = DriverManager.getConnection(url + db, un, pw);
            return true;
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return false;
    }

}