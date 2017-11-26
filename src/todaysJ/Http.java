package todaysJ;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Http {
	public static String makeConn(String url, String requestMethod, HashMap <String, String> extraHeaders, String data) throws IOException {
		URL addr = new URL(url);
		HttpURLConnection con = (HttpURLConnection) addr.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod(requestMethod);
		con.setRequestProperty("Host", "todaysmeet.com");
		con.setRequestProperty("Accept","*/*");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36"); //fake user agent because reasons
		if (extraHeaders != null) {
			Set hSet = extraHeaders.entrySet();
			Iterator hIterator = hSet.iterator();
			while (hIterator.hasNext()) {
				Map.Entry mEntry = (Map.Entry)hIterator.next();
				System.out.println(mEntry.getKey().toString());
				System.out.println(mEntry.getValue().toString());
				con.setRequestProperty(mEntry.getKey().toString(), mEntry.getValue().toString());
			}
		}
		if (requestMethod == "POST") {
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		}
		if (data != null) {
			con.setRequestProperty("Content-Length", Integer.toString(data.length()));
		}
		else {
			con.setRequestProperty("Content-Length","0");
		}
		if (requestMethod == "POST") {
			try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
				wr.writeUTF( data );
		}
		}
		con.getResponseCode();
		BufferedReader reader = null;
		if ("gzip".equals(con.getContentEncoding())) {
			reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(con.getInputStream())));
		}
		else {
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		}
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = reader.readLine())!= null) {
			content.append(inputLine);
		}
		return content.toString();
	}
}
//con.setRequestProperty("X-Requested-With", "XMLHttpRequest"); //This isnt needed
//con.setRequestProperty("Accept-Encoding", "gzip, deflate, br"); //This isnt needed
//con.setRequestProperty("Accept-Language", "en-US,en;q=0.9"); //This isnt needed
//con.setRequestProperty("Cookie", "csrftoken=" + csrftoken); //This isnt needed