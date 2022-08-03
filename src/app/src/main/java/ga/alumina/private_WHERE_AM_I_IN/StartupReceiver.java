package ga.alumina.private_WHERE_AM_I_IN;
import android.content.*;
import android.widget.*;

public class StartupReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context p1, Intent p2)
	{
		// TODO: Implement this method
		Toast.makeText(p1,"on Start",Toast.LENGTH_LONG).show();
		
		MainActivity ma = new MainActivity();
		ma.GPS();
	}

	
}