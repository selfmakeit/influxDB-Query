package influxdb;

import java.util.List;
/**
 *
 * @Author :Spike
 * @Date   :2021-08-28
 *
 **/
public class Statement{
    private int statement_id;
    private List<Series> series;

    public int getStatement_id() {
        return statement_id;
    }

    public void setStatement_id(int statement_id) {
        this.statement_id = statement_id;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }


}