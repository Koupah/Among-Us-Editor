package club.koupah.aue.gui.types.impl.custom.servers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import club.koupah.aue.gui.types.SettingType;
import club.koupah.aue.utility.PopUp;

public class AUServer {

	public static ArrayList<AUServer> servers = new ArrayList<AUServer>();

	String serverName;
	String serverIP;
	byte[] serverAddress;
	short serverPort;

	AUServer(String name, String ip) {
		this(name, ip, (short) 22023);
	}

	AUServer(String name, String ip, short port) {
		this.serverName = name;
		this.serverIP = ip;
		try {
			this.serverIP = InetAddress.getByName(ip).getHostAddress();
			this.serverAddress = InetAddress.getByName(ip).getAddress();
		} catch (UnknownHostException e) {
			new PopUp("Fatal error with Custom Servers!\nYou'll be unable to use them!", false);
			SettingType.SERVERS.setVisible(false);
			return;
		}

		this.serverPort = port;
		AUServer.servers.add(this);
	}

	public static AUServer getByName(String string) {
		for (AUServer server : servers) {
			if (server.serverName.equalsIgnoreCase(string))
				return server;
		}

		return null;
	}

	public static AUServer getOrMake(String name, String ip, short port) {
		for (AUServer server : AUServer.servers) {
			if (server.serverName.equalsIgnoreCase(name)) {
				return server;
			}
		}

		return new AUServer(name, ip, port);
	}

	public void update(String serverName2, String serverIP2, short port) {

		this.serverName = serverName2;
		try {
			this.serverIP = InetAddress.getByName(serverIP2).getHostAddress();
			this.serverAddress = InetAddress.getByName(serverIP2).getAddress();
		} catch (UnknownHostException e) {
			new PopUp("Fatal error with Custom Servers!\nYou'll be unable to use them!", false);
			SettingType.SERVERS.setVisible(false);
			return;
		}

		this.serverPort = port;
	}

}
