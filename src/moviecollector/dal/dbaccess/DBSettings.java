/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviecollector.dal.dbaccess;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

/**
 *
 * @author fauxtistic
 */
public class DBSettings {
    
    private SQLServerDataSource dataSource;

    /**
     * Sets the user credentials, ip, and port configuration
     * Also calls for DBSettings.txt for the passwords and usernames,
     * Which is protected by .gitignore
     * @throws IOException
     */
    public DBSettings() throws IOException {
        Properties props = new Properties();
        props.load(new FileReader("DBSettings.txt"));
        dataSource = new SQLServerDataSource();
        dataSource.setDatabaseName(props.getProperty("database"));
        dataSource.setUser(props.getProperty("user"));
        dataSource.setPassword(props.getProperty("password"));
        dataSource.setServerName(props.getProperty("server"));
    }

    /**
     * establishes a connection to the database
     *
     * @return a Connection to the database
     * @throws SQLServerException
     */
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
    
}
