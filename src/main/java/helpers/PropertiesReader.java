package helpers;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesReader {

    public PropertiesReader() {}

    public String getProperty(String property) {
        Properties props = new Properties();

        try {
            // Synchronize to ensure thread safety during properties loading
            synchronized (this) {
                if (getOperatingSystem().toLowerCase().contains("mac")) {
                    props.load(new FileInputStream(System.getProperty("user.dir") + "/config.properties"));
                } else if (getOperatingSystem().toLowerCase().contains("win")) {
                    props.load(new FileInputStream(System.getProperty("user.dir") + "\\config.properties"));
                }

                if (props.getProperty("ct.accessKey") == null || props.getProperty("ct.accessKey").isEmpty()) {
                    throw new Exception("SeeTest Cloud Access Key not found. Please look in config.properties.");
                }

                if (props.getProperty("ct.cloudUrl") == null || props.getProperty("ct.cloudUrl").isEmpty()) {
                    throw new Exception("SeeTest Cloud URL not found. Please look config.properties.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return props.getProperty(property);
    }

    public String getOperatingSystem() {
        String os = System.getProperty("os.name");
        return os;
    }

}
