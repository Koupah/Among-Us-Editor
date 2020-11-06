package club.koupah.aue.gui.types.impl.custom.servers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import club.koupah.aue.utility.PopUp;

public class AUServer {
	
	public static ArrayList<AUServer> servers = new ArrayList<AUServer>();
	
	String serverName;
	String serverIP;
	byte[] serverAddress;
	short serverPort;
	
	AUServer(String name, String ip) {
		this(name, ip, (short)22023);
	}
	
	AUServer(String name, String ip, short port) {
		this.serverName = name;
		this.serverIP = ip;
		try {
			this.serverAddress =  InetAddress.getByName(ip).getAddress();
		} catch (UnknownHostException e) {
			new PopUp("Fatal error with Custom Servers!");
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
		this.serverIP = serverIP2;
		try {
			this.serverAddress =  InetAddress.getByName(serverIP2).getAddress();
		} catch (UnknownHostException e) {
			new PopUp("Fatal error with Server Selector!");
		}
		System.out.println(this.serverAddress[0]);
		this.serverPort = port;
	}
	
	
}
