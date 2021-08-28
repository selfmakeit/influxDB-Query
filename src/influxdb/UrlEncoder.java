package influxdb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
/**
 *
 * @Author :Spike
 * @Date   :2021-08-28
 *
 **/
public class UrlEncoder {
    private static Map<String, String> replaceMap;

    private static void CheckReplaceMap()
    {
        if (replaceMap == null) {
            replaceMap = new HashMap<String, String>();
            replaceMap.put("\"", "%22");
            replaceMap.put(">", "%3E");
            replaceMap.put("<", "%3C");
            replaceMap.put(" ", "%20");
            replaceMap.put("+", "%2b");
            replaceMap.put(";", "%3B");
        }
    }

    public static String EncodeString(String url)
    {
        CheckReplaceMap();
        Iterator<Entry<String, String>> iter = replaceMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = (Entry<String, String>) iter.next();
            url = url.replace(entry.getKey(), entry.getValue());
        }
        return url;
    }
}