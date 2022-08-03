package ga.alumina.private_WHERE_AM_I_IN;
import java.net.*;
import java.io.*;
import java.util.*;
import org.apache.http.util.*;
import android.os.*;
import android.content.*;
import android.widget.*;

//ref https://qiita.com/riversun/items/5f78d47a3d95f857d34f

class AsyncPost extends AsyncTask<Integer, Integer, Integer> {

	private Context ctx;
	private String resp;
	private String requ;

	public AsyncPost(String str,Context p1) {
		super();
		ctx = p1;
		requ = str;
		resp = new String();
	}
	@Override
	protected Integer doInBackground(Integer... value) {
        try {
			Map<String, String> headers = new HashMap<String, String>(); // HTTPヘッダ(指定したければ)
			//headers.put("X-Example-Header", "Example-Value");
			resp = post(headers,requ);
        } catch (Exception e) {
			resp = "ERROR, " + e.getMessage();
        }
		return value[0] + 2;
	}

	/**
	 * バックグランド処理が完了し、UIスレッドに反映する
	 */
	@Override
	protected void onPostExecute(Integer result) {
		Toast.makeText(ctx,"SUCCESS, " + resp,Toast.LENGTH_LONG).show();
	}

	public static String post(Map<String, String> headers, String jsonString) throws IOException {
		String endpoint = "http://alumina.starfree.jp/WAII/";
		String encoding = "UTF-8";
		
		final StringBuffer sb = new StringBuffer("");
		HttpURLConnection httpConn = null;
		BufferedReader br = null;
		InputStream is = null;
		InputStreamReader isr = null;
		
		try{
			URL url = new URL(endpoint);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setConnectTimeout(10000);//ms
			httpConn.setReadTimeout(20000);//ms
			httpConn.setRequestMethod("POST");
			httpConn.setUseCaches(false);
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			
			if(headers != null){
				for(String key : headers.keySet()){
					httpConn.setRequestProperty(key, headers.get(key));
				}
			}
			
			OutputStream os = httpConn.getOutputStream();
			final boolean autoFlash = true;
			PrintStream ps = new PrintStream(os, autoFlash, encoding);
			ps.print(jsonString);
			ps.close();
			
			final int responseCode = httpConn.getResponseCode();
			
			if(responseCode == HttpURLConnection.HTTP_OK){
				is = httpConn.getInputStream();
				isr = new InputStreamReader(is, encoding);
				br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
			}else{
				//If responseCode is not HTTP_OK
			}
			
		}catch (IOException e){
			throw e;
		}finally{
			if(br != null){
				try{
					br.close();
				}catch (IOException e){}
			}
			if(isr != null){
				try {
					isr.close();
				}catch (IOException e){}
			}
			if(is != null){
				try{
					is.close();
				}catch(IOException e){}
			}
            if(httpConn != null){
				httpConn.disconnect();
			}
		}
	return sb.toString();
	}
}