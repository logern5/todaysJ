package todaysJ;

import java.io.IOException;
import java.net.URLEncoder;
import com.google.gson.Gson;

import todaysJ.dataStructs.Response;

public class API {
	public static void postMessage(String roomNumber, String message, String userName, String csrfmiddlewaretoken) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("csrfmiddlewaretoken=");
		sb.append(csrfmiddlewaretoken);
		sb.append("&message=");
		sb.append(URLEncoder.encode(message,"UTF-8"));
		sb.append("&message=");
		sb.append(URLEncoder.encode(message,"UTF-8"));
		sb.append("&sender_name=");
		sb.append(URLEncoder.encode(userName,"UTF-8"));
		sb.append("&room=");
		sb.append(URLEncoder.encode(roomNumber,"UTF-8"));
		StringBuilder roomN = new StringBuilder();
		roomN.append("https://todaysmeet.com/api/room/");
		roomN.append(roomNumber);
		roomN.append("/post");
		Http.makeConn(roomN.toString(), "POST", null, sb.toString());
		//Http.makeConn("https://todaysmeet.com/api/room/3104471/post", "POST", null, sb.toString());
		//Http.makeConn("https://todaysmeet.com/api/room/3104401/post", "POST", null, "csrfmiddlewaretoken=70i6sCfqqTn0YoifT08GruPOJUK8VDrC&message=SentFromJava&sender_name=Logern&room=3104401");
	}
	public static Response getMessages(String roomNumber, String since, String max) throws Exception {
		StringBuilder url = new StringBuilder();
		url.append("https://todaysmeet.com/api/room/");
		url.append(roomNumber);
		//url.append("/messages?since=0&max=40");
		url.append("/messages?since=");
		url.append(since);
		url.append("&max=");
		url.append(max);
		String response = Http.makeConn(url.toString(), "GET", null, "");
		//System.out.println(response);
		Gson gson = new Gson();
		Response r = gson.fromJson(response, Response.class);
		return r;
	}
	public static String[] getTokenAndRoom(String roomName) throws Exception {
		StringBuilder url = new StringBuilder();
		url.append("https://todaysmeet.com/");
		url.append(roomName);
		//System.out.println("Room URL: " + url.toString());
		String response = Http.makeConn(url.toString(), "GET", null, "");
		int i = response.indexOf("csrfmiddlewaretoken'"); 
		String token = "";
		if(i != -1) {
			String[] split = response.split("csrfmiddlewaretoken");
			String[] split2 = split[1].split("value='");
			String[] split3 = split2[1].split("'");
			token = split3[0];
		}
		String roomNumber = "";
		if (response.indexOf("id_room") != -1) {
			String[] split = response.split("id_room");
			String[] split2 = split[1].split("value=\"");
			String[] split3 = split2[1].split("\"");
			roomNumber = split3[0];
		}
		String[] returnData = new String[]{"",""};
		returnData[0] = token;
		returnData[1] = roomNumber;
		//System.out.println(returnData[1]);
		return returnData;
	}
	public static void createRoom(String roomName, String expires, String csrfmiddlewaretoken) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("csrfmiddlewaretoken=");
		sb.append(csrfmiddlewaretoken);
		sb.append("&name=");
		sb.append(roomName);
		sb.append("&expires=1h");
		Http.makeConn("https://todaysmeet.com/api/room/create", "POST", null, sb.toString());
	}
}
