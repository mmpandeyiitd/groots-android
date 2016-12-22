package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 12/4/16.
 */
public class ProductListParamterData {

    @SerializedName("facet")
    @Expose
    public String facet;
    @SerializedName("sort")
    @Expose
    public String sort;
    @SerializedName("fl")
    @Expose
    public String fl;
    @SerializedName("indent")
    @Expose
    public String indent;
    @SerializedName("start")
    @Expose
    public String start;
    @SerializedName("stats.field")
    @Expose
    public String statsField;
    @SerializedName("stats")
    @Expose
    public String stats;
    @SerializedName("q")
    @Expose
    public String q;
    @SerializedName("facet.field")
    @Expose
    public List<String> facetField = new ArrayList<String>();
    @SerializedName("wt")
    @Expose
    public String wt;
    @SerializedName("fq")
    @Expose
    public String fq;
    @SerializedName("rows")
    @Expose
    public String rows;
}
