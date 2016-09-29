package movieappspring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class for retrieving properties values from properties file
 */
public class PropertiesManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private final String FILE_NAME;

    public PropertiesManager(String fileName) {
        this.FILE_NAME = fileName;
    }

    /**
     * This method returns value of the property with specified property name.
     *
     * @param propName Name of the property which value you want to get
     * @return String value of specified property or null if property is not found
     */
    public String getProperty(String propName) {

        if (propName == null || propName.trim().equals("")) {
            LOGGER.fatal("No such property name: " + propName);
            throw new RuntimeException("No such property name found: " + propName);
        }

        Properties properties = new Properties();
        InputStream inputStream = PropertiesManager.class.getClassLoader().getResourceAsStream(FILE_NAME);
        if (inputStream == null) {
            LOGGER.fatal("Unable to find file: " + FILE_NAME);
            throw new RuntimeException("Unable to find file: " + FILE_NAME);
        }
        String prop = "";
        try {
            properties.load(inputStream);
            prop = properties.getProperty(propName);
        } catch (IOException e) {
            LOGGER.fatal("Seems that properties file is missing. That's totally not my fault, sorry. ", e);
            throw new RuntimeException("Seems that properties file is missing. That's totally not my fault, sorry. ", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error("Error while closing input stream. ", e);
            }
        }
        return prop;
    }

}
