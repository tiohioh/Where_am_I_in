package ga.alumina.private_WHERE_AM_I_IN;
import java.net.*;
import java.io.*;
import java.util.*;
import org.apache.http.util.*;

public class senddat
{
	public static String post(String endpoint, String encoding, Map<String, String> headers, String jsonString) throws IOException {
		//ref https://qiita.com/riversun/items/5f78d47a3d95f857d34f

		endpoint = "http://alumina.starfree.jp/WAII/";
		encoding = "utf-8";
		headers = null;

        final int TIMEOUT_MILLIS = 0;// タイムアウトミリ秒：0は無限

        final StringBuffer sb = new StringBuffer("");

        HttpURLConnection httpConn = null;
        BufferedReader br = null;
        InputStream is = null;
        InputStreamReader isr = null;

        try {
            URL url = new URL(endpoint);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setConnectTimeout(TIMEOUT_MILLIS);// 接続にかかる時間
            httpConn.setReadTimeout(TIMEOUT_MILLIS);// データの読み込みにかかる時間
            httpConn.setRequestMethod("POST");// HTTPメソッド
            httpConn.setUseCaches(false);// キャッシュ利用
            httpConn.setDoOutput(true);// リクエストのボディの送信を許可(GETのときはfalse,POSTのときはtrueにする)
            httpConn.setDoInput(true);// レスポンスのボディの受信を許可

            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpConn.setRequestProperty(key, headers.get(key));// HTTPヘッダをセット
                }
            }

            OutputStream os = httpConn.getOutputStream();
            final boolean autoFlash = true;
            PrintStream ps = new PrintStream(os, autoFlash, encoding);
            ps.print(jsonString);
            ps.close();

            final int responseCode = httpConn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                is = httpConn.getInputStream();
                isr = new InputStreamReader(is, encoding);
                br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } else {
                // If responseCode is not HTTP_OK
            }

        } catch (IOException e) {
            throw e;
        } finally {
            // fortify safeかつJava1.6 compliantなclose処理
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return sb.toString();
    }
}
