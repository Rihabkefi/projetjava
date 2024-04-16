package Data_source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Config {
    private String url = "jdbc:mysql://localhost:3306/gym_box";
    private String login = "rihab";
    private String pwd = "rihab";
    private static Config data;
    private Connection con;

    private Config() {
        try {
            this.con = DriverManager.getConnection(this.url, this.login, this.pwd);
            System.out.println("connexion établie");
        } catch (SQLException var2) {
            System.out.println(var2);
        }

    }

    public Connection getCon() {
        return this.con;
    }

    public static Config getInstance() {
        if (data == null) {
            data = new Config();
        }

        return data;
    }
}
