package client; 

import database.DatabaseConnection;
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.SQLException; 
import java.sql.ResultSet;  

public class AutoRegister { 

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MapleClient.class); 
    private static final int ACCOUNTS_PER_IP = 3; 
    public static boolean success; 

    public static boolean getAccountExists(String login) { 
        boolean accountExists = false; 
        Connection con = DatabaseConnection.getConnection(); 
        try { 
            PreparedStatement ps = con.prepareStatement("SELECT name FROM accounts WHERE name = ?"); 
            ps.setString(1, login); 
            ResultSet rs = ps.executeQuery(); 
            if (rs.first()) { 
                accountExists = true; 
            } 
        } catch (Exception ex) { 
        } 
        return accountExists; 
    } 

    public static void createAccount(String login, String pwd, String eip) { 
        String sockAddr = eip; 
        Connection con; 
        try { 
            con = DatabaseConnection.getConnection(); 
        } catch (Exception ex) { 
            log.error("ERROR", ex); 
            return; 
        }
   
        try {

            PreparedStatement ps = con.prepareStatement("INSERT INTO accounts (name, password, email, birthday, macs, SessionIP) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, login);
            ps.setString(2, LoginCrypto.hexSha1(pwd));
            ps.setString(3, "");
            ps.setString(4, "2000-01-01");
            ps.setString(5, "00-00-00-00-00-00");
            ps.setString(6, sockAddr.substring(1, sockAddr.lastIndexOf(':')));
            ps.executeUpdate();
            ps.close();
            success = true;
        } catch (SQLException ex) {
            log.error("Something bad with autoregister.\r\n" + ex);
        }
    } 
}