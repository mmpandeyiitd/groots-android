package groots.app.com.groots.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 12/4/16.
 */
public class Product {



    @SerializedName("subscribed_product_id")
    @Expose
    public Integer subscribedProductId;
    @SerializedName("length_unit")
    @Expose
    public String length_unit;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("base_product_id")
    @Expose
    public Integer baseProductId;
    @SerializedName("store_id")
    @Expose
    public Integer storeId;
   /* @SerializedName("store_price")
    @Expose
    public double storePrice;*/

    @SerializedName("out_of_stock")
    @Expose
    public boolean outOfStock;

    @SerializedName("store_offer_price")
    @Expose
    public float storeOfferPrice;
    @SerializedName("subscribed_shipping_charges")
    @Expose
    public Integer subscribedShippingCharges;
    @SerializedName("is_cod")
    @Expose
    public Integer isCod;
    @SerializedName("weight")
    @Expose
    public Integer weight;
    @SerializedName("length")
   /* @Expose
    public String length_unit;
    @SerializedName("length_unit")*/
    @Expose
    public Float length;
    @SerializedName("width")
    @Expose
    public Integer width;
    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("diameter")
    @Expose
    public String diameter;
    @SerializedName("grade")
    @Expose
    public String grade;
    @SerializedName("pack_size")
    @Expose
    public Integer packSize;
    @SerializedName("pack_unit")
    @Expose
    public String packUnit;
    @SerializedName("pack_size_in_gm")
    @Expose
    public String packSizeInGm;
    @SerializedName("min_order_qunatity")
    @Expose
    public Integer minOrderQunatity;
    @SerializedName("tags")
    @Expose
    public String tags;
    @SerializedName("specific_key")
    @Expose
    public String specificKey;
    @SerializedName("sku")
    @Expose
    public String sku;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("modified_at")
    @Expose
    public String modifiedAt;
    @SerializedName("quantity")
    @Expose
    public Integer quantity;
    @SerializedName("store_create_date")
    @Expose
    public String storeCreateDate;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("color")
    @Expose
    public String color;
    @SerializedName("size")
    @Expose
    public String size;
    @SerializedName("is_configurable")
    @Expose
    public Boolean isConfigurable;
    @SerializedName("configurable_with")
    @Expose
    public String configurableWith;
    @SerializedName("store_name")
    @Expose
    public String storeName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("store_details")
    @Expose
    public String storeDetails;
    @SerializedName("store_logo")
    @Expose
    public String storeLogo;
    @SerializedName("seller_name")
    @Expose
    public String sellerName;
    @SerializedName("business_address")
    @Expose
    public String businessAddress;
    @SerializedName("business_address_country")
    @Expose
    public String businessAddressCountry;
    @SerializedName("business_address_state")
    @Expose
    public String businessAddressState;
    @SerializedName("business_address_city")
    @Expose
    public String businessAddressCity;
    @SerializedName("business_address_pincode")
    @Expose
    public String businessAddressPincode;
    @SerializedName("mobile_numbers")
    @Expose
    public String mobileNumbers;
    @SerializedName("telephone_numbers")
    @Expose
    public String telephoneNumbers;
    @SerializedName("category_id")
    @Expose
    public List<Integer> categoryId = new ArrayList<Integer>();
    @SerializedName("category_name")
    @Expose
    public List<String> categoryName = new ArrayList<String>();
    @SerializedName("category_paths")
    @Expose
    public List<String> categoryPaths = new ArrayList<String>();
    @SerializedName("store_front_id")
    @Expose
    public List<Integer> storeFrontId = new ArrayList<Integer>();
    @SerializedName("media_url")
    @Expose
    public List<String> mediaUrl = new ArrayList<String>();
    @SerializedName("thumb_url")
    @Expose
    public List<String> thumbUrl = new ArrayList<String>();
    @SerializedName("default_thumb_url")
    @Expose
    public String defaultThumbUrl;
    @SerializedName("checkout_url")
    @Expose
    public String checkoutUrl;
    @SerializedName("_version_")
    @Expose
    public long _version_;

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    private int itemCount;

}
