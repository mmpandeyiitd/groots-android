
/**
 * Created by aakash on 22/11/16.
 */

package groots.app.com.groots.databases;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import groots.app.com.groots.R;
import groots.app.com.groots.interfaces.UpdateCart;
import groots.app.com.groots.model.UpdateCartClass;

/**
 * Created by Administrator
 */

public class dbHelp extends SQLiteOpenHelper {


    private String DB_FULL_PATH = "";
    public static final String DB_NAME = "grootsdb.sqlite";
    public static String DB_PATH;
    public static volatile SQLiteDatabase db;
    Context context;
    public int count = 0;


    public dbHelp(Context context) {
        super(context, DB_NAME, null, 2);
        this.context = context;

        DB_PATH = "/data/data/" + this.context.getPackageName() + "/databases/";
        DB_FULL_PATH = DB_PATH + DB_NAME;
        Log.v("DB PATH", DB_FULL_PATH);

    }


    public void deleteDb() {

        db = openDataBase();

        boolean boolDeleteStatus;
        try {
            File file = new File(DB_PATH + DB_NAME);
            boolDeleteStatus = file.delete();
            Log.d("Database :", "Old Data Base Deleted" + boolDeleteStatus);
        } catch (Exception e) {
            Log.e("Error:", e.getMessage());
        }

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        /*try{
            db.execSQL("CREATE TABLE UpdateCart (subscribe_prod_id INTEGER,base_product_id INTEGER,store_id INTEGER,product_name VARCHAR,product_description VARCHAR,product_image VARCHAR,product_qty INTEGER,unit_price FLOAT,total_unit_price FLOAT,pack_unit VARCHAR, pack_size VARCHAR);");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }*/



    }
    @Override
    public void onOpen(SQLiteDatabase db) {

        /*try{
            db.execSQL("CREATE TABLE if not exists UpdateCart (subscribe_prod_id INTEGER,base_product_id INTEGER,store_id INTEGER,product_name VARCHAR,product_description VARCHAR,product_image VARCHAR,product_qty INTEGER,unit_price FLOAT,total_unit_price FLOAT,pack_unit VARCHAR, pack_size VARCHAR);");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
*/


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDb(boolean versionChange) {
        boolean chkverres;
        chkverres = checkversion();

        if (!checkDb() || chkverres) {
            // Not Exist. So we have to copy the database
            copyDataBase();

        }

        db = openDataBase();

        try{
            db.execSQL("CREATE TABLE if not exists UpdateCart (subscribe_prod_id INTEGER,base_product_id INTEGER,store_id INTEGER,product_name VARCHAR,product_description VARCHAR,product_image VARCHAR,product_qty INTEGER,unit_price FLOAT,total_unit_price FLOAT,pack_unit VARCHAR, pack_size VARCHAR);");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public boolean checkversion() {
        String MISC_PREFS = "MiscPrefsFile";
        String Versionname, currentVersion;
        // Checking for database existence.
        PackageManager manager = this.context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(
                    this.context.getPackageName(), 0);
            Versionname = info.versionName;
            SharedPreferences miscPrefs = context.getSharedPreferences(
                    MISC_PREFS, Context.MODE_PRIVATE);
            currentVersion = miscPrefs.getString("Current Version", null);
            Log.v("getting version name", "getAppVersionToPrefs: got "
                    + currentVersion);
            miscPrefs.edit().putString("Current Version", Versionname).commit();
            Log.v("settinf version name",
                    "setAppVersionToPrefs: set app version to prefs"
                            + Versionname);
            if (Versionname.equals(currentVersion)) {
                return false;
            } else {
                return true;
            }

        } catch (NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return false;

        }

    }

    public boolean checkDb() {
        db = null;

        try {
            db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
                    SQLiteDatabase.OPEN_READWRITE
                            | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (db != null) {
            db.close();
        }

        return (db == null) ? false : true;
    }

    public static SQLiteDatabase openDataBase() throws SQLException {
        try {
            if (db == null) {

                db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
                        SQLiteDatabase.OPEN_READWRITE
                                | SQLiteDatabase.CREATE_IF_NECESSARY
                                | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            } else if (!db.isOpen()) {
                db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
                        SQLiteDatabase.OPEN_READWRITE
                                | SQLiteDatabase.CREATE_IF_NECESSARY
                                | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return db;
    }


    public void copyDataBase() {
        InputStream in;
        OutputStream os;
        byte arrByte[] = new byte[1024];

        try {
            in = context.getAssets().open(DB_NAME);

            // Making directory
            File dbFolder = new File(DB_PATH);
            if (!dbFolder.exists())
                dbFolder.mkdir();

            os = new FileOutputStream(DB_PATH + DB_NAME);
            int length;

            while ((length = in.read(arrByte)) > 0) {
                os.write(arrByte, 0, length);
            }

            // Closing the streams
            os.flush();
            in.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void copyDBToPhoneSD1() {
        InputStream in;
        OutputStream os;
        byte arrByte[] = new byte[1024];

        try {
            in = new FileInputStream(DB_PATH + DB_NAME);

            os = new FileOutputStream(Environment.getExternalStorageDirectory()
                    + "/" + DB_NAME);
            int length;

            while ((length = in.read(arrByte)) > 0) {
                os.write(arrByte, 0, length);
            }

            // Closing the streams
            os.flush();
            in.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*.............................Mail List.............................................*/

    public void insertMailData(String mail)
    {
        try
        {
            db=openDataBase();
            ContentValues contentValues=new ContentValues();
            contentValues.put("mailid",mail);
            Cursor cursor=null;
            String query = "select * from Maillist";
            cursor = db.rawQuery(query, null);
            int count = cursor.getCount();
            /*if (count > 0) {
                db.execSQL("UPDATE Maillist SET mailid= "+mail);
            }*/ /*else if (count == 0)*/
            db.insert("Maillist", null, contentValues);


            copyDBToPhoneSD1();
            if (db != null)
                db.close();
        }catch (Exception e)
        {
            Log.e("Exception ",e.toString());
        }
    }

   /* ................................ReadData for AutoSuggestion..................................*/

    public ArrayList<String> getMaillist()
    {
        db = openDataBase();

        ArrayList<String> arrayList = new ArrayList<>();

        String countQuery = "SELECT * FROM Maillist";

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(countQuery, null);
            count = cursor.getCount();

            if (cursor.moveToNext()) {
                do {

                    arrayList.add(cursor.getString(cursor.getColumnIndexOrThrow("mailid")));

                } while (cursor.moveToNext());
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null)
                db.close();

            if (cursor != null)
                cursor.close();
        }
        return arrayList;
    }
 /*   .................................Total Count of Autosuggestion..............................*/

    public int getTotalmail() {


        db = openDataBase();
        String countQuery = "SELECT  * FROM Maillist";
        int cnt = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(countQuery, null);
            cnt = cursor.getCount();
            cursor.close();
        } catch (Exception ex) {

        } finally {
            if (db != null)
                db.close();

            if (cursor != null)
                cursor.close();
        }
        return cnt;
    }
    //...............................Product Table.............................

	/* insert cart data
     * in product table
	 * */

    public void insertUpdateCartData(int subscribe_prod_id, int base_product_id, int store_id, String product_name,
                                     String product_description, String product_image, Double product_qty, Double unit_price, String pack_size, String packUnit) {

        try {

            db = openDataBase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("subscribe_prod_id", subscribe_prod_id);
            contentValues.put("base_product_id", base_product_id);
            contentValues.put("store_id", store_id);
            contentValues.put("product_name", product_name);
            contentValues.put("product_description", product_description);
            contentValues.put("product_image", product_image);
            contentValues.put("product_qty", product_qty);
            contentValues.put("unit_price", unit_price);
            contentValues.put("pack_size",pack_size);
            contentValues.put("pack_unit",packUnit);

            Double total_unit_price = product_qty * unit_price;
            total_unit_price = (Double) (Math.round(total_unit_price*100)/100.0d);
            // Log.e("Value at db",String.valueOf(product_qty)+unit_price+"tprice"+total_unit_price);
            contentValues.put("total_unit_price", total_unit_price);



          //String hashm =  (String)hash.get("what");

            String que = "select * from UpdateCart where  subscribe_prod_id = " + subscribe_prod_id;

            Cursor cursor = null;
            cursor = db.rawQuery(que, null);
            int count = cursor.getCount();



            /*if (hashm == "insert") {

                // String query = "select * from UpdateCart where  subscribe_prod_id = " + subscribe_prod_id;
                String Query = "insert into UpdateCart (subscribe_prod_id,base_product_id ,store_id ,product_name ,product_description ,product_image ,product_qty ,unit_price ,total_unit_price ,pack_unit, pack_size) values" +
                        "(" + "'" + subscribe_prod_id + "'" + "," + "'" + base_product_id + "'" + "," + "'" + store_id + "'" + "," + "'" + product_name.toString() + "'" + "," + "'" + product_description.toString() + "'" + "," + "'" + product_image.toString() + "'" + "," + "'" + product_qty + "'" + "," + "'" + unit_price + "'" + "," + "'" + total_unit_price + "'" + "," + "'" + packUnit.toString() + "'" + "," + "'" + pack_size.toString() + "'" + ")";
                System.out.println(Query);
                db.execSQL(Query);

            }*/


                if (count == 0){



                    String Query = "insert into UpdateCart (subscribe_prod_id,base_product_id ,store_id ,product_name ,product_description ,product_image ,product_qty ,unit_price ,total_unit_price ,pack_unit, pack_size) values" +
                            "(" + "'" + subscribe_prod_id + "'" + "," + "'" + base_product_id + "'" + "," + "'" + store_id + "'" + "," + "'" + product_name.toString() + "'" + "," + "'" + product_description.toString() + "'" + "," + "'" + product_image.toString() + "'" + "," + "'" + product_qty + "'" + "," + "'" + unit_price + "'" + "," + "'" + total_unit_price + "'" + "," + "'" + packUnit.toString() + "'" + "," + "'" + pack_size.toString() + "'" + ")";
                    System.out.println(Query);
                    db.execSQL(Query);






                }
                else if (count == 1) {

                    if (product_qty == 0.0){
                        String Query = "delete from UpdateCart where subscribe_prod_id =" + subscribe_prod_id ;
                        db.execSQL(Query);
                    }
                    else if (product_qty > 0.0) {

                        String updatequery = "update UpdateCart set product_qty" + "=" + "'" + product_qty + "'," + "total_unit_price" + "=" + "'" + total_unit_price + "'," + "unit_price" + "=" + "'" + unit_price + "pack_unit" + "=" + "'" + packUnit + "pack_size" + "=" + "'" + pack_size.toString() + "where subscribe_product_id" + "=" + "'" + subscribe_prod_id + "'";
                        System.out.println(updatequery);
                        db.execSQL(updatequery);
                    }
                }




           /* Cursor cursor = null;
            cursor = db.rawQuery(Query, null);
            int count = cursor.getCount();*/


            copyDBToPhoneSD1();
            if (db != null)
                db.close();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }


    /* delete the
	 * particular record
	* */

    public void deleteRecords(int subscribe_prod_id, int base_product_id) {

        try {
            db = openDataBase();
            db.execSQL("DELETE FROM UpdateCart WHERE subscribe_prod_id = " + subscribe_prod_id + " AND base_product_id = " + base_product_id);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null)
                db.close();


        }
    }

    public static void deleterec() {
        try {
            db = openDataBase();
            db.execSQL("DELETE FROM UPDATECART");

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    /*update the product
	 Qty*/
    public void updateProductQty(Double product_qty, Double unit_price, int subscribe_prod_id) {

        try {
            db = openDataBase();

            Double total_unit_price =(Double) unit_price * product_qty;
            total_unit_price = (Double) (Math.round(total_unit_price*100)/100.0d);
            //   Log.e("Total Price",String.valueOf(total_unit_price));
            db.execSQL("UPDATE UpdateCart SET product_qty= " + product_qty + ", total_unit_price= " + total_unit_price + " WHERE subscribe_prod_id = " + subscribe_prod_id);
            copyDBToPhoneSD1();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }





    public ArrayList<UpdateCartClass> getupdateProductQty() {


        db = openDataBase();

        ArrayList<UpdateCartClass> arrayList = new ArrayList<>();
        UpdateCartClass updateCartClass;
        String countQuery = "SELECT * FROM UpdateCart";
        int cnt = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(countQuery, null);
            cnt = cursor.getCount();

            if (cursor.moveToNext()) {
                do {
                    updateCartClass = new UpdateCartClass();
                    updateCartClass.subscribe_prod_id = cursor.getInt(cursor.getColumnIndexOrThrow("subscribe_prod_id"));
                    updateCartClass.product_qty = cursor.getDouble(cursor.getColumnIndexOrThrow("product_qty"));

                    arrayList.add(updateCartClass);

                } while (cursor.moveToNext());
            }
          //  copyDBToPhoneSD1();


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null)
                db.close();

            if (cursor != null)
                cursor.close();
        }
        return arrayList;
    }


     /*get product count in cart data
    * */




       /*get all row count in cart data
    * */

    public int getTotalRow() {


        db = openDataBase();
        String countQuery = "SELECT  * FROM UpdateCart";
        int cnt = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(countQuery, null);
            cnt = cursor.getCount();
            cursor.close();
        } catch (Exception ex) {

        } finally {
            if (db != null)
                db.close();

            if (cursor != null)
                cursor.close();
        }
        return cnt;
    }


    public float fetchTotalUpdateCartAmount() {

        String query = "SELECT sum(total_unit_price) from UpdateCart";

        float i = 0;
        db = openDataBase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, null);
            while ((cursor.moveToNext())) {
                i = cursor.getFloat((int) i);

            }
        } catch (Exception exception) {

        }
        return i;
    }










    public ArrayList<UpdateCartClass> updateorder() {

        ArrayList<UpdateCartClass> arrayList = new ArrayList<>();
        UpdateCartClass updateCartClass;
        String countQuery = "SELECT * FROM UpdateCart";
        int cnt = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(countQuery, null);
            cnt = cursor.getCount();

            if (cursor.moveToNext()) {
                do {
                    updateCartClass = new UpdateCartClass();
                    updateCartClass.subscribe_prod_id = cursor.getInt(cursor.getColumnIndexOrThrow("subscribe_prod_id"));
                    updateCartClass.base_product_id = cursor.getInt(cursor.getColumnIndexOrThrow("base_product_id"));
                    updateCartClass.store_id = cursor.getInt(cursor.getColumnIndexOrThrow("store_id"));
                    updateCartClass.product_name = cursor.getString(cursor.getColumnIndexOrThrow("product_name"));
                    updateCartClass.product_description = cursor.getString(cursor.getColumnIndexOrThrow("product_description"));
                    updateCartClass.product_image = cursor.getString(cursor.getColumnIndexOrThrow("product_image"));
                    updateCartClass.product_qty = cursor.getDouble(cursor.getColumnIndexOrThrow("product_qty"));
                    updateCartClass.unit_price = cursor.getDouble(cursor.getColumnIndexOrThrow("unit_price"));
                    updateCartClass.total_unit_price = cursor.getDouble(cursor.getColumnIndexOrThrow("total_unit_price"));
                    updateCartClass.packUnit=cursor.getString(cursor.getColumnIndex("pack_unit"));
                    updateCartClass.packSize=cursor.getString(cursor.getColumnIndex("pack_size"));

                    arrayList.add(updateCartClass);
                }
                while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null)
                db.close();

            if (cursor != null)
                cursor.close();
        }


        return arrayList;

    }




/*

	*/
/*update the product
	 Qty*//*

	public void updateProductQty(int PRODUCTQTY, int PRODUCTID , int PRODUCTPRICE, int CATEGORYID ){

		int productTotalPrice=0;
		try { db=openDataBase();

			productTotalPrice=PRODUCTQTY* PRODUCTPRICE;
			db.execSQL("UPDATE product_list SET PRODUCTQTY= "+PRODUCTQTY +", PRODTOTALPRICE= "+productTotalPrice+" WHERE PRODUCTID = "+PRODUCTID +" AND CATEGORYID = "+CATEGORYID);

		} catch (Exception e)
		{
			e.printStackTrace();

		}

	}

    */


/*get all row count in cart data
    * *//*


	public  int getTotalRow(int CATEGORYID) {


		db = openDataBase();
		String countQuery = "SELECT  * FROM product_list where CATEGORYID= "+CATEGORYID;
		int cnt=0;
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(countQuery, null);
			cnt = cursor.getCount();
			cursor.close();
		} catch (Exception ex) {

		} finally {
			if (db != null)
				db.close();

			if (cursor != null)
				cursor.close();
		}
		return cnt;
	}

	 */
/*get all row count in cart data
    * *//*


	public  ArrayList<CartClass> getProductQty(int CATEGORYID, int SUBCATEGORYID) {


		db = openDataBase();

		ArrayList<CartClass> arrayList=new ArrayList<>();
		CartClass cartClass;
		String countQuery = "SELECT * FROM product_list WHERE CATEGORYID = " + CATEGORYID+" AND SUBCATEGID= "+SUBCATEGORYID;
		int cnt=0;
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(countQuery, null);
			cnt = cursor.getCount();

			if (cursor.moveToNext()) {
				do {
					cartClass = new CartClass();
					cartClass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
					cartClass.PRODUCTID= cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTID"));

					arrayList.add(cartClass);

				}while (cursor.moveToNext());
			}


		} catch (Exception ex) {
            ex.printStackTrace();
		} finally {
			if (db != null)
				db.close();

			if (cursor != null)
				cursor.close();
		}
		return arrayList;
	}


    public  ArrayList<CartClass> getAllProductToShare(int CATEGORYID) {

        db = openDataBase();

        ArrayList<CartClass> arrayList=new ArrayList<>();
        CartClass cartClass;
        String countQuery = "SELECT * FROM product_list WHERE CATEGORYID = " + CATEGORYID;
        int cnt=0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(countQuery, null);
            cnt = cursor.getCount();

            if (cursor.moveToNext()) {
                do {
                    cartClass = new CartClass();
                    cartClass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
                    cartClass.PRODUCTID= cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTID"));
                    cartClass.PRODUCTNAME=cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTNAME"));
                    arrayList.add(cartClass);

                }while (cursor.moveToNext());
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null)
                db.close();

            if (cursor != null)
                cursor.close();
        }
        return arrayList;
    }



	*/
/* delete the
	 * particular record
	* *//*


	public void deleteRecords(int productId, int CategoryId) {
		//db.delete("carttable","productid =?",new String[]{Integer.toString(productid)});
		try {
			db = openDataBase();
			db.execSQL("DELETE FROM product_list WHERE PRODUCTID = " + productId + " AND CATEGORYID = " + CategoryId);
		//	db.delete("product_list", "PRODUCTID =?", new String[]{Integer.toString(productId)});

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (db != null)
				db.close();


		}
	}


    */
/*fetch data for cart
    * *//*


    public static HashMap<String, ArrayList<CartClass>> getAllData(int CATEGORYID) {

        HashMap<String, ArrayList<CartClass>> getallrecord = new HashMap<String, ArrayList<CartClass>>();
        ArrayList<CartClass> allrecords = new ArrayList<CartClass>();
        int serviceone = 0;

        String SUBCATEGNAME = "";

        String query = "select * from product_list where CATEGORYID= "+CATEGORYID;

        db = openDataBase();
        Cursor cursor = null;
        try {

            cursor = db.rawQuery(query, null);
            int count = cursor.getCount();
            CartClass cartclass = null;
            if (cursor.moveToFirst()) {


                do {
                    cartclass = new CartClass();

                    if (SUBCATEGNAME.equals("")) {

                        SUBCATEGNAME = cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME"));
                        cartclass.SUBCATEGNAME = cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME"));

                        cartclass.SUBCATEGID = cursor.getInt(cursor.getColumnIndexOrThrow("SUBCATEGID"));
                        cartclass.CATEGORYID = cursor.getInt(cursor.getColumnIndexOrThrow("CATEGORYID"));
                        cartclass.CATEGORYNAME = cursor.getString(cursor.getColumnIndexOrThrow("CATEGORYNAME"));
                        cartclass.CATEGORYIMAGE=cursor.getString(cursor.getColumnIndexOrThrow("CATEGORYIMAGE"));

                        cartclass.PRODUCTID = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTID"));
                        cartclass.PRODUCTNAME = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTNAME"));
                        cartclass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
						cartclass.PRODUCTIMAGE = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTIMAGE"));
                        cartclass.COMMENT=cursor.getString(cursor.getColumnIndexOrThrow("COMMENT"));
                        //Double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                        //cartclass.price = price.intValue();
                        cartclass.PRODUCTPRICE = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTPRICE"));
                        //Double range = cursor.getDouble(cursor.getColumnIndexOrThrow("range"));
                        //cartclass.range =  range.intValue();
                        allrecords.add(cartclass);

                    } else if (SUBCATEGNAME.equals(cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME")))) {

                        cartclass.SUBCATEGNAME = cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME"));

                        cartclass.SUBCATEGID = cursor.getInt(cursor.getColumnIndexOrThrow("SUBCATEGID"));
                        cartclass.CATEGORYID = cursor.getInt(cursor.getColumnIndexOrThrow("CATEGORYID"));
                        cartclass.CATEGORYNAME = cursor.getString(cursor.getColumnIndexOrThrow("CATEGORYNAME"));
                        cartclass.CATEGORYIMAGE=cursor.getString(cursor.getColumnIndexOrThrow("CATEGORYIMAGE"));

                        cartclass.PRODUCTID = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTID"));
                        cartclass.PRODUCTNAME = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTNAME"));
                        cartclass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
                        cartclass.COMMENT=cursor.getString(cursor.getColumnIndexOrThrow("COMMENT"));
                        cartclass.PRODUCTIMAGE = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTIMAGE"));
                        //Double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                        //cartclass.price = price.intValue();
                        cartclass.PRODUCTPRICE = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTPRICE"));
                        allrecords.add(cartclass);

                    } else {
                        serviceone = 1;

                        getallrecord.put(SUBCATEGNAME, allrecords);
                        allrecords = new ArrayList<>();
                        SUBCATEGNAME = cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME"));

                        cartclass.SUBCATEGNAME = cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME"));

                        cartclass.SUBCATEGID = cursor.getInt(cursor.getColumnIndexOrThrow("SUBCATEGID"));
                        cartclass.CATEGORYID = cursor.getInt(cursor.getColumnIndexOrThrow("CATEGORYID"));
                        cartclass.CATEGORYNAME = cursor.getString(cursor.getColumnIndexOrThrow("CATEGORYNAME"));
                        cartclass.CATEGORYIMAGE=cursor.getString(cursor.getColumnIndexOrThrow("CATEGORYIMAGE"));

                        cartclass.PRODUCTID = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTID"));
                        cartclass.PRODUCTNAME = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTNAME"));
                        cartclass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
                        cartclass.COMMENT=cursor.getString(cursor.getColumnIndexOrThrow("COMMENT"));
                        //cartclass.discount = cursor.getString(cursor.getColumnIndexOrThrow("discount"));
                        cartclass.PRODUCTIMAGE = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTIMAGE"));
                        //Double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                        //cartclass.price = price.intValue();
                        cartclass.PRODUCTPRICE = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTPRICE"));
                        allrecords.add(cartclass);

                    }
                }
                while (cursor.moveToNext());

                getallrecord.put(SUBCATEGNAME, allrecords);

            }

        } catch (Exception ex) {

        } finally {
            if (db != null)
                db.close();

            if (cursor != null)
                cursor.close();
        }


        return getallrecord;
    }



	public int fetchTotalCartAmount(int CATEGORYID){

		String query="SELECT sum(PRODTOTALPRICE) from product_list where CATEGORYID= "+CATEGORYID;

		int i=0;
		db=openDataBase();

		Cursor cursor=null;

		try{
			cursor=db.rawQuery(query, null);
			while ((cursor.moveToNext())) {
				i= cursor.getInt(i);

			}
		}catch(Exception exception){

		}
		return i;
	}




	public  ArrayList<CartClass> order(int CategoryId) {

		ArrayList<CartClass> allrecords = new ArrayList<CartClass>();
		int serviceone = 0;

		String categoryName = "";

		//String query = "select * from product_list ORDER BY SUBCATEGNAME";
        String query = "select * from product_list WHERE CATEGORYID= "+CategoryId;

		db = openDataBase();


		Cursor cursor = null;


		try {

			cursor = db.rawQuery(query, null);

			if (cursor.moveToFirst()) {


				do {


					if (cursor.getInt(cursor.getColumnIndexOrThrow("CATEGORYID")) > 0) {
                        categoryName = cursor.getString(cursor.getColumnIndexOrThrow("CATEGORYNAME"));

                        CartClass cartclass = new CartClass();


						cartclass.CATEGORYID = cursor.getInt(cursor.getColumnIndexOrThrow("CATEGORYID"));
						cartclass.SUBCATEGID= cursor.getInt(cursor.getColumnIndexOrThrow("SUBCATEGID"));
						cartclass.PRODUCTID = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTID"));
						cartclass.PRODUCTNAME = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTNAME"));
						cartclass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
                        cartclass.PRODUCTPRICE = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTPRICE"));
                        cartclass.PRODUCTIMAGE =cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTIMAGE"));
                        cartclass.COMMENT=cursor.getString(cursor.getColumnIndexOrThrow("COMMENT"));


                        allrecords.add(cartclass);
					}


				}
				while (cursor.moveToNext());

			}

		} catch (Exception ex) {

		} finally {
			if (db != null)
				db.close();

			if (cursor != null)
				cursor.close();
		}


		return allrecords;

	}



    public ArrayList<CartClass> fetchMainCateg() {

        ArrayList<CartClass> allrecords = new ArrayList<CartClass>();

        String query = "SELECT * FROM product_list GROUP BY CATEGORYID";

        db = openDataBase();

        Cursor cursor = null;

        try {

            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                        CartClass cartclass = new CartClass();

                        cartclass.CATEGORYID = cursor.getInt(cursor.getColumnIndexOrThrow("CATEGORYID"));
                        cartclass.CATEGORYNAME=cursor.getString(cursor.getColumnIndexOrThrow("CATEGORYNAME"));
                        cartclass.CATEGORYIMAGE=cursor.getString(cursor.getColumnIndexOrThrow("CATEGORYIMAGE"));

                        allrecords.add(cartclass);
                }
                while (cursor.moveToNext());
            }

        } catch (Exception ex) {

        } finally {
            if (db != null)
                db.close();

            if (cursor != null)
                cursor.close();
        }


        return allrecords;

    }


	*/
/*. Delete Cart Data after place the order
    .*//*


	public  void deleteCategoryRecord(int CATEGORYID) {
		try {
			db = openDataBase();


			String deletequery = "DELETE FROM product_list WHERE CATEGORYID= "+CATEGORYID;

			db.execSQL(deletequery);

			if (db != null)
				db.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}



    */
/*............................ Working ON Custom_product table.........................................*//*


    */
/* insert cart data
	 * in product table
	 * *//*



    public  void insertCustomProduct(String PRODUCTNAME,
                                int PRODUCTPRICE, String SUBCATEGNAME, int CATEGID,
                                String CUSID, String PRODUCTIMAGE, int PRODUCTQTY){

        try {

            db = openDataBase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("PRODUCTNAME", PRODUCTNAME);
            contentValues.put("PRODUCTPRICE", PRODUCTPRICE);
            contentValues.put("SUBCATEGNAME", SUBCATEGNAME);
            contentValues.put("CATEGID", CATEGID);
            contentValues.put("CUSID", CUSID);
            contentValues.put("PRODUCTIMAGE", PRODUCTIMAGE);
            contentValues.put("PRODUCTQTY", PRODUCTQTY);

            db.insert("custom_product", null, contentValues);

            copyDBToPhoneSD1();
            if (db != null)
                db.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


    public int fetchLastRowId(){

        String query="SELECT  MAX(Id) from custom_product";

        int i=0;
        db=openDataBase();

        Cursor cursor=null;

        try{
            cursor=db.rawQuery(query, null);
            while ((cursor.moveToNext())) {
                i= cursor.getInt(i);

            }
        }catch(Exception exception){

        }
        return i;
    }


    */
/*update the custom Id
	 *//*

    public void updateCustomId(int id , int customId ){

        int productTotalPrice=0;
        try { db=openDataBase();


            db.execSQL("UPDATE custom_product SET CUSID = "+customId + " WHERE Id = "+id);

        } catch (Exception e)
        {
            e.printStackTrace();

        }

    }


    public  ArrayList<CartClass> fetchCustomProd(int id){

        ArrayList<CartClass> arryCustProd = new ArrayList<CartClass>();
        int serviceone = 0;

        String categoryName = "";

        //String query = "select * from product_list ORDER BY SUBCATEGNAME";
        String query = "select * from custom_product WHERE Id= "+id;

        db = openDataBase();


        Cursor cursor = null;


        try {

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {


                do {


                   //  categoryName = cursor.getString(cursor.getColumnIndexOrThrow("CATEGORYNAME"));

                        CartClass cartclass = new CartClass();


                        cartclass.CATEGORYID = cursor.getInt(cursor.getColumnIndexOrThrow("CATEGID"));
                        cartclass.SUBCATEGNAME= cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME"));
                        cartclass.PRODUCTID = cursor.getInt(cursor.getColumnIndexOrThrow("CUSID"));
                        cartclass.PRODUCTNAME = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTNAME"));
                        cartclass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
                        cartclass.PRODUCTPRICE = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTPRICE"));
                        cartclass.PRODUCTIMAGE =cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTIMAGE"));


                        arryCustProd.add(cartclass);



                }
                while (cursor.moveToNext());

            }

        } catch (Exception ex) {
			ex.printStackTrace();
        } finally {
            if (db != null)
                db.close();

            if (cursor != null)
                cursor.close();
        }

        return arryCustProd;
    }



		*/
/*..............................Store Product Cart Table........................*//*




	*/
/* insert cart data
	 * in product table
	 * *//*


	public  void insertStoreCartData(int STOREID,  String STORENAME, int SUBCATEGID,String SUBCATEGNAME, int PRODUCTID, String PRODUCTNAME,
								String PRODUCTIMAGE, int PRODUCTPRICE, int PRODUCTQTY,
								 String COMMENT){

		try {

			db = openDataBase();

			ContentValues contentValues = new ContentValues();

			contentValues.put("STOREID",STOREID);
			contentValues.put("STORENAME", STORENAME);
			contentValues.put("SUBCATEGID", SUBCATEGID);
			contentValues.put("SUBCATEGNAME",SUBCATEGNAME);
			contentValues.put("PRODUCTID", PRODUCTID);
			contentValues.put("PRODUCTNAME", PRODUCTNAME);
			contentValues.put("PRODUCTIMAGE", PRODUCTIMAGE);
			contentValues.put("PRODUCTPRICE", PRODUCTPRICE);
			contentValues.put("PRODUCTQTY", PRODUCTQTY);
			int totalPrice=PRODUCTQTY*PRODUCTPRICE;
			contentValues.put("PRODTOTALPRICE", totalPrice);
			contentValues.put("COMMENT", COMMENT);

			String query = "select * from store_product_list where  PRODUCTID = " + PRODUCTID;

			Cursor cursor = null;

			cursor = db.rawQuery(query, null);
			int count = cursor.getCount();
			if (count > 0) {

				db.execSQL("UPDATE store_product_list SET PRODUCTQTY= "+PRODUCTQTY +" WHERE PRODUCTID = "+PRODUCTID+" AND STOREID = "+STOREID);


			} else if (count == 0)
				db.insert("store_product_list", null, contentValues);

			copyDBToPhoneSD1();
			if (db != null)
				db.close();

		}
		catch (Exception e){
			e.printStackTrace();
		}


	}



    */
/*fetch data for store cart
    * *//*


	public static HashMap<String, ArrayList<CartClass>> getAllDataOfStoreCart(int STOREID) {

		HashMap<String, ArrayList<CartClass>> getallrecord = new HashMap<String, ArrayList<CartClass>>();
		ArrayList<CartClass> allrecords = new ArrayList<CartClass>();
		int serviceone = 0;

		String SUBCATEGNAME = "";

		String query = "select * from store_product_list where STOREID= "+STOREID;

		db = openDataBase();


		Cursor cursor = null;


		try {

			cursor = db.rawQuery(query, null);
			int count = cursor.getCount();
			CartClass cartclass = null;
			if (cursor.moveToFirst()) {


				do {
					cartclass = new CartClass();

					if (SUBCATEGNAME.equals("")) {

						SUBCATEGNAME = cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME"));
						cartclass.SUBCATEGNAME = cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME"));

						cartclass.SUBCATEGID = cursor.getInt(cursor.getColumnIndexOrThrow("SUBCATEGID"));
						cartclass.CATEGORYID = cursor.getInt(cursor.getColumnIndexOrThrow("STOREID"));
						cartclass.CATEGORYNAME = cursor.getString(cursor.getColumnIndexOrThrow("STORENAME"));

						cartclass.PRODUCTID = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTID"));
						cartclass.PRODUCTNAME = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTNAME"));
						cartclass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
						cartclass.PRODUCTIMAGE = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTIMAGE"));
						cartclass.COMMENT=cursor.getString(cursor.getColumnIndexOrThrow("COMMENT"));
						//Double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
						//cartclass.price = price.intValue();
						cartclass.PRODUCTPRICE = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTPRICE"));
						//Double range = cursor.getDouble(cursor.getColumnIndexOrThrow("range"));
						//cartclass.range =  range.intValue();
						allrecords.add(cartclass);

					} else if (SUBCATEGNAME.equals(cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME")))) {

						cartclass.SUBCATEGNAME = cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME"));
						cartclass.SUBCATEGID = cursor.getInt(cursor.getColumnIndexOrThrow("SUBCATEGID"));
						cartclass.CATEGORYID = cursor.getInt(cursor.getColumnIndexOrThrow("STOREID"));
						cartclass.CATEGORYNAME = cursor.getString(cursor.getColumnIndexOrThrow("STORENAME"));

						cartclass.PRODUCTID = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTID"));
						cartclass.PRODUCTNAME = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTNAME"));
						cartclass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
						cartclass.COMMENT=cursor.getString(cursor.getColumnIndexOrThrow("COMMENT"));
						cartclass.PRODUCTIMAGE = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTIMAGE"));
						//Double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
						//cartclass.price = price.intValue();
						cartclass.PRODUCTPRICE = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTPRICE"));
						allrecords.add(cartclass);

					} else {
						serviceone = 1;

						getallrecord.put(SUBCATEGNAME, allrecords);
						allrecords = new ArrayList<>();
						SUBCATEGNAME = cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME"));

						cartclass.SUBCATEGNAME = cursor.getString(cursor.getColumnIndexOrThrow("SUBCATEGNAME"));
						cartclass.SUBCATEGID = cursor.getInt(cursor.getColumnIndexOrThrow("SUBCATEGID"));
						cartclass.CATEGORYID = cursor.getInt(cursor.getColumnIndexOrThrow("STOREID"));
						cartclass.CATEGORYNAME = cursor.getString(cursor.getColumnIndexOrThrow("STORENAME"));

						cartclass.PRODUCTID = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTID"));
						cartclass.PRODUCTNAME = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTNAME"));
						cartclass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
						cartclass.COMMENT=cursor.getString(cursor.getColumnIndexOrThrow("COMMENT"));
						//cartclass.discount = cursor.getString(cursor.getColumnIndexOrThrow("discount"));
						cartclass.PRODUCTIMAGE = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTIMAGE"));
						//Double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
						//cartclass.price = price.intValue();
						cartclass.PRODUCTPRICE = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTPRICE"));
						allrecords.add(cartclass);

					}
				}
				while (cursor.moveToNext());

				getallrecord.put(SUBCATEGNAME, allrecords);

			}

		} catch (Exception ex) {

		} finally {
			if (db != null)
				db.close();

			if (cursor != null)
				cursor.close();
		}


		return getallrecord;
	}

	*/
/* delete the
	 * particular record
	 * *//*


	public void deleteStoreProductListRecords(int productId, int storeId) {


		try {
			db = openDataBase();
			db.execSQL("DELETE FROM store_product_list WHERE PRODUCTID = " + productId + " AND STOREID = " + storeId);
			//	db.delete("product_list", "PRODUCTID =?", new String[]{Integer.toString(productId)});

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (db != null)
				db.close();


		}
	}


	 */
/*get all row count in cart data
    * *//*


	public  ArrayList<CartClass> getStoreProductQty(int STOREID, int SUBCATEGORYID) {


		db = openDataBase();

		ArrayList<CartClass> arrayList=new ArrayList<>();
		CartClass cartClass;
		String countQuery = "SELECT * FROM store_product_list WHERE STOREID = " + STOREID+" AND SUBCATEGID= "+SUBCATEGORYID;
		int cnt=0;
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(countQuery, null);
			cnt = cursor.getCount();

			if (cursor.moveToNext()) {
				do {
					cartClass = new CartClass();
					cartClass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
					cartClass.PRODUCTID= cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTID"));

					arrayList.add(cartClass);

				}while (cursor.moveToNext());
			}


		} catch (Exception ex) {

		} finally {
			if (db != null)
				db.close();

			if (cursor != null)
				cursor.close();
		}
		return arrayList;
	}


     */
/*get all row count in cart data
    * *//*


    public  ArrayList<CartClass> getStoreAllProductShare(int STOREID) {


        db = openDataBase();

        ArrayList<CartClass> arrayList=new ArrayList<>();
        CartClass cartClass;
        String countQuery = "SELECT * FROM store_product_list WHERE STOREID = " + STOREID;
        int cnt=0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(countQuery, null);
            cnt = cursor.getCount();

            if (cursor.moveToNext()) {
                do {
                    cartClass = new CartClass();
                    cartClass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
                    cartClass.PRODUCTID= cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTID"));
                    cartClass.PRODUCTNAME=cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTNAME"));
                    arrayList.add(cartClass);

                }while (cursor.moveToNext());
            }


        } catch (Exception ex) {

        } finally {
            if (db != null)
                db.close();

            if (cursor != null)
                cursor.close();
        }
        return arrayList;
    }


	*/
/*get all row count in cart data
    * *//*


	public  int getTotalItemOfStore(int StoreId) {

		db = openDataBase();
		String countQuery = "SELECT  * FROM store_product_list where STOREID= "+StoreId;
		int cnt=0;
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(countQuery, null);
			cnt = cursor.getCount();
			cursor.close();
		} catch (Exception ex) {

		} finally {
			if (db != null)
				db.close();

			if (cursor != null)
				cursor.close();
		}
		return cnt;
	}


	*/
/*update the product
	 Qty*//*

	public void updateStoreProductQty(int PRODUCTQTY, int PRODUCTID , int PRODUCTPRICE, int storeId ){

		int productTotalPrice=0;
		try { db=openDataBase();

			productTotalPrice=PRODUCTQTY* PRODUCTPRICE;
			db.execSQL("UPDATE store_product_list SET PRODUCTQTY= "+PRODUCTQTY +", PRODTOTALPRICE= "+productTotalPrice+" WHERE PRODUCTID = "+PRODUCTID +" AND STOREID = "+storeId);

		} catch (Exception e)
		{
			e.printStackTrace();

		}

	}

	*/
/*update the product comment
         *//*

	public void updateStoreProductComment(int PRODUCTID , String Comment, int storeId ){

		int productTotalPrice=0;
		try { db=openDataBase();


			db.execSQL("UPDATE store_product_list SET COMMENT= ' "+Comment + " ' WHERE PRODUCTID = "+PRODUCTID +" AND STOREID = "+storeId);


        } catch (Exception e)
		{
			e.printStackTrace();

		}

	}


	public int fetchTotalStoreCartAmount(int STOREID){

		String query="SELECT sum(PRODTOTALPRICE) from store_product_list where STOREID= "+STOREID;

		int i=0;
		db=openDataBase();

		Cursor cursor=null;

		try{
			cursor=db.rawQuery(query, null);
			while ((cursor.moveToNext())) {
				i= cursor.getInt(i);

			}
		}catch(Exception exception){

		}
		return i;
	}



	public  ArrayList<CartClass> orderByStore(int STOREID) {

		ArrayList<CartClass> allrecords = new ArrayList<CartClass>();
		int serviceone = 0;

		String categoryName = "";

		//String query = "select * from product_list ORDER BY SUBCATEGNAME";
		String query = "select * from store_product_list WHERE STOREID= "+STOREID;

		db = openDataBase();


		Cursor cursor = null;


		try {

			cursor = db.rawQuery(query, null);

			if (cursor.moveToFirst()) {


				do {


					if (cursor.getInt(cursor.getColumnIndexOrThrow("STOREID")) > 0) {

						CartClass cartclass = new CartClass();

						cartclass.CATEGORYID = cursor.getInt(cursor.getColumnIndexOrThrow("STOREID"));
						cartclass.SUBCATEGID= cursor.getInt(cursor.getColumnIndexOrThrow("SUBCATEGID"));
						cartclass.PRODUCTID = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTID"));
						cartclass.PRODUCTNAME = cursor.getString(cursor.getColumnIndexOrThrow("PRODUCTNAME"));
						cartclass.PRODUCTQTY = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTQTY"));
						cartclass.PRODUCTPRICE = cursor.getInt(cursor.getColumnIndexOrThrow("PRODUCTPRICE"));
						cartclass.COMMENT=cursor.getString(cursor.getColumnIndexOrThrow("COMMENT"));


						allrecords.add(cartclass);
					}


				}
				while (cursor.moveToNext());

			}

		} catch (Exception ex) {

		} finally {
			if (db != null)
				db.close();

			if (cursor != null)
				cursor.close();
		}


		return allrecords;

	}



    */
/*. Delete Cart Data after place the order
    .*//*


    public void deleteStoreRecord(int storeId) {
        try {
            db = openDataBase();


            String deletequery = "DELETE FROM store_product_list WHERE STOREID= "+storeId;

            db.execSQL(deletequery);

            if (db != null)
                db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
*/


}
