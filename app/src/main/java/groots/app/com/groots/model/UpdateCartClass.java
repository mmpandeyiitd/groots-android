package groots.app.com.groots.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aakash on 18/11/16.
 */

public class UpdateCartClass implements Parcelable {


    public String product_name,product_image, product_description,packUnit,packSize;

    public  int subscribe_prod_id, base_product_id, store_id;

    public Double unit_price, total_unit_price ,product_qty;

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



    public static final Creator<UpdateCartClass> CREATOR = new Creator<UpdateCartClass>() {
        @Override
        public UpdateCartClass createFromParcel(Parcel in) {

            UpdateCartClass cart = new UpdateCartClass();

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
        public UpdateCartClass[] newArray(int size) {
            return new UpdateCartClass[size];
        }
    };



}
