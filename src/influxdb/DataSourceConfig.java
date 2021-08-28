package influxdb;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
/**
 *
 * @Author :Spike
 * @Date   :2021-08-28
 *
 **/
public class DataSourceConfig {
    public static Properties myProp = new Properties();

    public static InputStream myResource = DataSourceConfig.class.getResourceAsStream("/datasource.properties");

    static {
        try {
            myProp.load(new InputStreamReader(myResource, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getPropertyByName(String props) {

        return myProp.getProperty(props);

    }
}
