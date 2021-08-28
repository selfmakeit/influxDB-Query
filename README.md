# influxDB-Query
query from InfluxDB in java with only fastJson dependency needed
## 1. Configure datasource in datasource.properties.
![image](https://user-images.githubusercontent.com/11673734/131203975-c9048bb0-5c8e-4d05-844a-b48f97ff73e3.png)
## 2.Get message from property file and create connection.
    final static DataSourceConfig dataSourceConfig = new DataSourceConfig();
    String ip =dataSourceConfig.getPropertyByName("ip");
    String port =dataSourceConfig.getPropertyByName("port");
    String dbName =dataSourceConfig.getPropertyByName("dbname");
## 3. Do query in your method like this:
        InfluxDB influxdb = new InfluxDB();
        influxdb.setServerIp(ip);
        influxdb.setServerPort(Integer.valueOf(port));
        influxdb.setDbName(dbName);
        if (influxdb.Connect()) {
            String sql1 = "SELECT \"usage_idle\" as idle ,(\"usage_system\" +\"usage_user\") as \"used\" ,\"host\" FROM \"cpu\" WHERE time > now() - 10s  group by \"host\"";
            String sql2 = ";SELECT \"usage_idle\" as idle ,(\"usage_system\" +\"usage_user\") as \"used\" ,\"host\" FROM \"cpu\" WHERE time > now() - 10s group by \"host\"";
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
## 4. Dont forget close connect with influxdb.DisConnect();
## 5. You can send multi sql in one query ,and separate sql with ";"
## 6. print query result should be like this：
![image](https://user-images.githubusercontent.com/11673734/131204214-de30961c-6675-4d20-9e8b-942caec45097.png)

