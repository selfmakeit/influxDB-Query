import influxdb.*;

import java.util.List;

public class Main {
    final static DataSourceConfig dataSourceConfig = new DataSourceConfig();
    public static void main(String[] args) {

        String ip =dataSourceConfig.getPropertyByName("ip");
        String port =dataSourceConfig.getPropertyByName("port");
        String dbName =dataSourceConfig.getPropertyByName("dbname");

        String sql1 = "SELECT \"usage_idle\" as idle ,(\"usage_system\" +\"usage_user\") as \"used\" ,\"host\" FROM \"cpu\" WHERE time > now() - 10s  group by \"host\"";
        String sql2 = ";SELECT \"usage_idle\" as idle ,(\"usage_system\" +\"usage_user\") as \"used\" ,\"host\" FROM \"cpu\" WHERE time > now() - 10s group by \"host\"";
        InfluxDB influxdb = new InfluxDB();
        influxdb.setServerIp(ip);
        influxdb.setServerPort(Integer.valueOf(port));
        influxdb.setDbName(dbName);
        if (influxdb.Connect()) {
            QueryResult qr = influxdb.Query(sql1+sql2);
            if(null==qr){
                return;
            }
            List<Statement> statements = qr.getStatements();
            for(Statement s:statements){
                System.out.println("Statement_id:"+s.getStatement_id());
                List<Series> series = s.getSeries();
                int i=0;
                for(Series ser:series){
                    System.out.println("      series"+(++i)+":");
                    System.out.println("           name:"+ser.getName());
                    System.out.println("           tags:"+ser.getTags().toString());
                    System.out.println("           datas:"+ser.getDatas().toString());
                    // 可循环取出datas里的单条数据。。。。
                }
            }
            influxdb.DisConnect();
        }

    }
}
