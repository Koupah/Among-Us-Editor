package club.koupah.aue.utility.regioninfo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.SettingType;
import club.koupah.aue.gui.types.impl.custom.servers.AUServer;
import club.koupah.aue.utility.PopUp;

public class RegionInfoManager {

	File regionInfo;

	boolean exists = true;
	
	boolean oldRegionInfo;
	
	public RegionInfoManager(File newer, File older) {
		this.regionInfo = newer;

		if (newer != null && newer.exists()) {
			oldRegionInfo = false;
			regionInfo = newer;
		} else if (older != null && older.exists()) {
			oldRegionInfo = true;
			regionInfo = older;
		} else {
			exists = false;
		}
		
		if (!this.exists) {
			new PopUp("Couldn't find your \"regionInfo\" file!\nCustom servers won't be available!", false);
			SettingType.SERVERS.setVisible(false);
		}
		
	}

	public void getServers() {

		Thread getWarnings = new Thread() {
			public void run() {

				try {
					URLConnection connection = new URL(
							"https://raw.githubusercontent.com/Koupah/Among-Us-Editor/master/servers").openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

					String line = "";

					while ((line = in.readLine()) != null) {
						line = line.replaceAll("\n", ""); // Just sanitization incase
						System.out.println(line);
						if (line.startsWith("//") || line.length() < 2)
							continue;

						if (line.startsWith("AddAUServer:")) { // Only add relevant warnings
							String[] info = line.split("AddAUServer:")[1].split(",");
							// Format should be
							// AUServer:Koupah's Server,192.168.1.1,22023
							String serverName = info[0];
							String serverIP = info[1];
							short port = Short.parseShort(info[2]);

							AUServer server = AUServer.getOrMake(serverName, serverIP, port);
							server.update(serverName, serverIP, port);
						} else if (line.startsWith("RemoveAUServer:")) {
							AUServer server = AUServer.getByName(line.split("RemoveAUServer:")[1]);
							if (server != null)
								AUServer.servers.remove(server);
						}
					}

					in.close();

					Editor.getInstance().serverSelector.updateItems();

				} catch (Exception e) {
					System.out.println("Failed to get servers: " + e.getMessage());
					e.printStackTrace();
					new PopUp(
							"Some servers in the server selector may be out of date!\nThis is due to Among Us Editor not being able to check the server list!",
							false);
				}
			}
		};

		getWarnings.start();
	}
	
	/*
	 * Good old temporary permanent fixes
	 */
	public void setServer(String name, String ip, short port) {
		if (oldRegionInfo) {
			setServerOLD(name, null, ip, port);
			return;
		}
		try {
			byte[] address = InetAddress.getByName(ip).getAddress();
			setServer(name, address, ip, port);
		} catch (UnknownHostException e) {
			new PopUp("IP Address error!\nCouldn't get the address for " + ip, false);
			e.printStackTrace();
			return;
		}

	}

	String jsonFormat = "{\"NHKLLGFLCLM\":0,\"PBKMLNEHKHL\":[{\"$type\":\"DnsRegionInfo, Assembly-CSharp\",\"Fqdn\":\"%IPADDRESS%\",\"DefaultIp\":\"%IPADDRESS%\",\"Name\":\"AUE-Custom-Server\",\"TranslateName\":292}]}";
	public void setServer(String name, byte[] serverAddress, String ip, short port) {

		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(regionInfo))) {
			dos.writeBytes(jsonFormat.replace("%IPADDRESS%", ip));
			dos.close();
		} catch (IOException e) {
			System.out.println(String.format("There was an error writing to the regionInfo file!", e.getMessage()));
			new PopUp("Error writing to regionInfo file!\n" + e.getMessage(), true);
		}
	}
	/*
	 * Credit to NaokiStark and their Crewmate-switcher
	 */

	public void setServerOLD(String name, byte[] serverAddress, String ip, short port) {

		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(regionInfo))) {
			// Server master
			dos.writeInt(0); // Padding
			dos.write(name.getBytes().length); // length name
			dos.writeBytes(name); // Name
			dos.write(ip.getBytes().length); // length ip
			dos.writeBytes(ip); // ip
			dos.write(1); // Server count

			// Server Info
			dos.writeInt((name + "-Master-1").getBytes().length); // Server name byte-length
			dos.writeBytes(name + "-Master-1"); // First Server
			dos.write(serverAddress); // Ip address

			/*
			 * Stupid fucking little endian vs big endian BS had me stuck for an hour
			 * because a "V" was being placed in the wrong spot
			 */
			ByteBuffer byteBuffer = ByteBuffer.allocate(2);
			byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
			byteBuffer.putShort(port);
			byte[] result = byteBuffer.array();
			dos.write(result);

			dos.writeInt(0);
			dos.close();
		} catch (IOException e) {
			System.out.println(String.format("There was an error writing to the regionInfo file!", e.getMessage()));
			new PopUp("Error writing to regionInfo file!\n" + e.getMessage(), true);
		}
	}

	public boolean exists() {
		return this.exists;
	}

	public void clearServer() {
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(regionInfo))) {
			// Server master
			dos.write("".getBytes());
			dos.close();
		} catch (IOException e) {
			System.out.println(String.format("There was an error writing to the regionInfo file!", e.getMessage()));
			new PopUp("Error writing to regionInfo file!\n" + e.getMessage(), true);
		}
	}

}
