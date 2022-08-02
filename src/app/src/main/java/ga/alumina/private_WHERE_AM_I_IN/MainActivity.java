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
		
		try{
		FusedLocationProviderClient flpc;
		flpc = LocationServices.getFusedLocationProviderClient(getApplicationContext());
		
		flpc.getLastLocation()
		.addOnSuccessListener(new OnSuccessListener<Location>(){
			@Override
			public void onSuccess(Location lc){
				if(lc != null){
					String str = lc.getLatitude() + "," + lc.getLongitude();
					Toast.makeText(getApplicationContext(),"gps " + str,Toast.LENGTH_LONG).show();
					senddat sd = new senddat();
					try{
						String ret = sd.post("","",null,str);
						Toast.makeText(getApplicationContext(),"ret " + ret,Toast.LENGTH_LONG).show();
					}catch(IOException e){
						Toast.makeText(getApplicationContext(),"err " + e.getMessage(),Toast.LENGTH_LONG).show();
						copyToClipboard(getApplicationContext(),str);
					}
				}
			}
		})
		;
		
		
		}catch(Exception e){
			Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
		}
		
		
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
}
