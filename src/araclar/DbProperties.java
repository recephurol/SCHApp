package araclar;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DbProperties extends Properties {

    public static String get(String prop) throws IOException {
        File configFile = new File("db.properties");

        FileReader reader = new FileReader(configFile);

        DbProperties props = new DbProperties();
        props.load(reader);
        return props.getProperty(prop);
    }

}
