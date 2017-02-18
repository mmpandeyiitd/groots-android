package groots.app.com.groots.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aakash on 3/2/17.
 */

public class MappingClass implements Parcelable {


    public int subscProdId , retailerId;
    public String status;



    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int flags) {

       parcel.writeInt(subscProdId);
        parcel.writeInt(retailerId);
      parcel.writeString(status);
    }
    public static final Creator<MappingClass> CREATOR = new Creator<MappingClass>() {
        @Override
        public MappingClass createFromParcel(Parcel in) {

            MappingClass cart = new MappingClass();

            cart.subscProdId = in.readInt();
            cart.retailerId=in.readInt();
            cart.status=in.readString();


            return cart;

        }


        @Override
        public MappingClass[] newArray(int size) {
            return new MappingClass[size];
        }
    };
}
