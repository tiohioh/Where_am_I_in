package ga.alumina.private_WHERE_AM_I_IN;

import android.app.*;
import android.os.*;
import android.widget.*;
import com.google.android.gms.location.*;
import com.google.android.gms.tasks.*;
import android.location.*;
import android.content.*;
import java.io.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		GPS();
	}
	
	//for debug
	//https://qiita.com/CUTBOSS/items/97669c712449510fe7f0
	public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboardManager =
			(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (null == clipboardManager) {
            return;
        }
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", text));
    }
	
	public void GPS(){
		FusedLocationProviderClient flpc;
		flpc = LocationServices.getFusedLocationProviderClient(getApplicationContext());

		flpc.getLastLocation()
			.addOnSuccessListener(new OnSuccessListener<Location>(){
				@Override
				public void onSuccess(Location lc){
					if(lc != null){
						String str = lc.getLatitude() + "," + lc.getLongitude();
						AsyncPost asp = new AsyncPost(str,getApplicationContext());
						asp.execute(1);
					}
				}
			});
	}
}