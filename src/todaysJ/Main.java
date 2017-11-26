package todaysJ;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class Main {

	public static void main(String[] args) throws Exception {
		//java.util.HashMap<String, String> h = new java.util.HashMap<String, String>();
		//h.put("mame", "dank");
		//Http.makeConn("http://techtideapps.com", "GET", h, "");
		commandLoop();
	} 
	public static void commandLoop() throws Exception {
		String roomName;
		String token;
		String name;
		String command;
		String roomNumber;
		BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Please enter room to join>");
		roomName = buffer.readLine();
		System.out.print("Please enter nickname to use>");
		name = buffer.readLine();
		String[] troom = {"",""};
		try {
			troom = API.getTokenAndRoom(roomName);
		}
		catch (Exception e) {
			System.out.println("Error: room doesn't exist.");
		}
		token = troom[0];
		roomNumber = troom[1];
		while (true) {
			boolean breakflag = false;
			System.out.print("TodaysJ>");
			command = buffer.readLine();
			String[] tokens = {""};
			try {
				tokens = command.split(" ");
			}
			catch (Exception e) {
				tokens[0] = command;
			}
			try{
				switch(tokens[0]) {
				case "post":
					StringBuilder sb = new StringBuilder();
					for (int i=1;i<tokens.length;i++) {
						sb.append(tokens[i] + " ");
					}
					API.postMessage(roomNumber, sb.toString().trim(), name, token);
					break;
				case "get":
					System.out.print(getFormattedMessages(roomNumber));
					break;
				case "create":
					StringBuilder sba = new StringBuilder();
					for (int i=1;i<tokens.length;i++) {
						sba.append(tokens[i] + " ");
					}
					API.createRoom(sba.toString(), "1h", token);
					break;
				case "help":
					System.out.println("help: Show this help");
					System.out.println("post [message]: Post message to chat");
					System.out.println("get: Pull messages from chat");
					System.out.println("quit: Exit this application");
					break;
				case "quit":
					breakflag = true;
					break;
				default:
					System.out.println("Command not recognized. Type \"help\" for help.");
					break;
				}
			}
			catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
			if(breakflag) {
				break;
			}
		}
	}
	public static String getFormattedMessages(String roomNumber) throws Exception {
		StringBuilder sb = new StringBuilder();
		todaysJ.dataStructs.Response r = API.getMessages(roomNumber, "0", "40");
		int size = r.getMessages().size();
		for(int i=0;i<size;i++) {
			sb.append(r.getMessages().get(i).created);
			sb.append(":<");
			sb.append(r.getMessages().get(i).sender_name);
			sb.append(">:");
			sb.append(r.getMessages().get(i).message);
			sb.append("\n");
		}
		return sb.toString();
	}
}