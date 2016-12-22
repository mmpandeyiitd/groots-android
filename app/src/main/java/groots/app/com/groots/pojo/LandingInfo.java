package groots.app.com.groots.pojo;

/**
 * Created by Administrator on 04-04-2016.
 */
public class LandingInfo {

    private String itemName,itemdesc,itemprice,itemcount,itemsource,itemdia,itemcolor,itemgrade,itemquantity;
    private int imageitem;

    public LandingInfo(String itemName,String itemdesc,String itemprice,String itemcount,int imageitem)
    {
        this.itemName=itemName;
        this.itemdesc=itemdesc;
        this.itemprice=itemprice;
        this.itemcount=itemcount;
        this.imageitem=imageitem;
    }

    public LandingInfo(String itemName,String itemdesc,String itemprice,String itemcount,int imageitem,String itemsource,String itemdia,
                       String itemcolor,String itemgrade,String itemquantity)
    {
        this.itemName=itemName;
        this.itemdesc=itemdesc;
        this.itemprice=itemprice;
        this.itemcount=itemcount;
        this.imageitem=imageitem;
        this.itemsource=itemsource;
        this.itemdia=itemdia;
        this.itemcolor=itemcolor;
        this.itemgrade=itemgrade;
        this.itemquantity=itemquantity;
    }

    public LandingInfo(String itemName, String itemprice, int imageitem) {
        this.itemName=itemName;
        this.itemprice=itemprice;
        this.imageitem=imageitem;

    }

    public String getItemquantity()
    {
        return itemquantity;
    }
    public String getItemgrade()
    {
        return itemgrade;
    }
    public String getItemcolor()
    {
        return itemcolor;
    }
    public String getItemdia()
    {
        return itemdia;
    }

    public String getItemsource()
    {
        return itemsource;
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
