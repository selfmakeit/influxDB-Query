package influxdb;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @Author :Spike
 * @Date   :2021-08-28
 *
 **/
public class QueryResult {
    private List<Statement> statements;

    public List<Statement> getStatements() {
        return statements;
    }

    public QueryResult(String resultString){
//        System.out.println(resultString);
        statements =new ArrayList<>();
        JSONObject object = JSONObject.parseObject(resultString);
        JSONArray results = object.getJSONArray("results");
        int rSize=results.size();
        for(int i=0;i<rSize;i++){
            Statement statement = new Statement();
            JSONObject resultsJSONObject = results.getJSONObject(i);
            statement.setStatement_id((int)resultsJSONObject.get("statement_id"));
            if(!resultsJSONObject.containsKey("series")){
                return;
            }
            JSONArray serArr= resultsJSONObject.getJSONArray("series");
            int sSize=serArr.size();
            List<Series> seriesArr = new ArrayList<>();
            for(int s=0;s<sSize;s++){
                Series series =new Series();
                series.setName(serArr.getJSONObject(s).getString("name"));
                series.setTags(serArr.getJSONObject(s).getJSONObject("tags"));
                List<String> columnNames = JSONArray.parseArray(serArr.getJSONObject(s).getString("columns"),String.class);
                JSONArray columnValues = serArr.getJSONObject(s).getJSONArray("values");
                int kSize = columnNames.size();
                int vSize = columnValues.size();
                JSONArray datas = new JSONArray();
                for (int j=0;j<vSize;j++){
                    JSONObject data = new JSONObject();
                    for(int k=0;k<kSize;k++){
                        JSONArray vObject = columnValues.getJSONArray(j);
                        data.put(columnNames.get(k),dealValue(vObject.get(k)));
                    }
                    datas.add(data);
                }
                series.setDatas(datas);
                seriesArr.add(series);
            }
            statement.setSeries(seriesArr);
            this.statements.add(statement);
        }
    }
    /**
     * 判断是否为小数，是则保留小数点后两位
     * @param val
     * @return
     */
    private Object dealValue(Object val){
        try {
           double dNumber = Double.parseDouble(val.toString());
            BigDecimal bg = new BigDecimal(dNumber);
            double rNumber = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return rNumber;
        } catch(NumberFormatException e){
            return val;
        }
    }

}
