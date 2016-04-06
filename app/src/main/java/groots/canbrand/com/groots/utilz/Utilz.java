package groots.canbrand.com.groots.utilz;

import android.content.Context;
import android.net.ConnectivityManager;

public class Utilz {

	/*CHECKING THE INTERNET CONNECTIVITY.......................
	 * */
	
	public boolean isInternetConnected(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}
	
	/*
	 * CHECK FOR EMAIL VALIDATION.....................
	 * */
	
	public boolean isValidEmail1(CharSequence target) {

		boolean flag=android.util.Patterns.EMAIL_ADDRESS.matcher(target)
				.matches();
		/*if (target == null) {
			return false;
		} else {*/
			return flag;
		//}
	}
	
	/*
	 * PASSWORD DECRYPT BY USING MD5..................
	 * */
	
	public  String MD5(String md5) {

		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}

		return md5;
	}
}
