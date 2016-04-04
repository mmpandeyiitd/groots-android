package groots.canbrand.com.groots.Model;

/**
 * Created by Administrator on 04-04-2016.
 */
public class LandingInfo {

    private String itemName,itemdesc,itemprice,itemcount;
    private int imageitem;

    public LandingInfo(String itemName,String itemdesc,String itemprice,String itemcount,int imageitem)
    {
        this.itemName=itemName;
        this.itemdesc=itemdesc;
        this.itemprice=itemprice;
        this.itemcount=itemcount;
        this.imageitem=imageitem;
    }


    public String getItemName()
    {
        return itemName;
    }

    public String getItemDesc()
    {
     return itemdesc;
    }

    public String getItemprice()
    {
        return itemprice;
    }

    public String getItemcount()
    {
        return itemcount;
    }
    public int getImageitem()
    {
        return imageitem;
    }
}
