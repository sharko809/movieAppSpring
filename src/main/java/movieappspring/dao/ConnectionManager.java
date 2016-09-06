package movieappspring.dao;

import movieappspring.PropertiesManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for managing connection to database
 */
public class ConnectionManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private PropertiesManager propertiesManager;

    @Autowired
    private ConnectionManager(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    /**
     * Performs connection to database with credentials specified in property file
     *
     * @return connection to URL specified in property file
     */
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("No mysql driver found.", e);
            throw new RuntimeException("no driver found");
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    propertiesManager.getProperty("dataSource"),
                    propertiesManager.getProperty("dbUser"),
                    propertiesManager.getProperty("dbPassword"));
        } catch (SQLException e) {
            LOGGER.fatal("Error during establishing connection to database. " + e, e);
            throw new RuntimeException("Error during establishing connection to database");
        }
        return connection;
    }
}
