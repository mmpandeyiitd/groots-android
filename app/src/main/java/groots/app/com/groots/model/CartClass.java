package groots.app.com.groots.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by can on 9/2/16.
 */
public class CartClass implements Parcelable {


    public String product_name,product_image, product_description,packUnit,packSize;

    public  int subscribe_prod_id, base_product_id, store_id;

    public Double  total_unit_price  ;
    public Double product_qty , unit_price;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeInt(subscribe_prod_id);
        parcel.writeInt(base_product_id);
        parcel.writeInt(store_id);
        parcel.writeString(product_name);
        parcel.writeString(product_description);
        parcel.writeString(product_image);
        parcel.writeDouble(product_qty);
        parcel.writeDouble(unit_price);
        parcel.writeDouble(total_unit_price);
        parcel.writeString(packUnit);
        parcel.writeString(packSize);
    }



    public static final Creator<CartClass> CREATOR = new Creator<CartClass>() {
        @Override
        public CartClass createFromParcel(Parcel in) {

            CartClass cart = new CartClass();

            cart.subscribe_prod_id = in.readInt();
            cart.base_product_id=in.readInt();
            cart.store_id=in.readInt();
            cart.product_name=in.readString();
            cart.product_description=in.readString();
            cart.product_image=in.readString();
            cart.product_qty=in.readDouble();
            cart.unit_price=in.readDouble();
            cart.total_unit_price=in.readDouble();
            cart.packUnit=in.readString();
            cart.packSize=in.readString();

            return cart;

        }


        @Override
        public CartClass[] newArray(int size) {
            return new CartClass[size];
        }
    };
}
