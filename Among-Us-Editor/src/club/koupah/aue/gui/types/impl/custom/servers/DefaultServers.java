package club.koupah.aue.gui.types.impl.custom.servers;

public enum DefaultServers {
	
	SkeldDotNetUS("Skeld.net US Server", "44.238.242.123"),
	SkeldDotNetEU("Skeld.net EU Server", "92.237.178.55");
	
	DefaultServers(String name, String ip) {
		this(name, ip, (short)22023);
	}
	
	DefaultServers(String name, String ip, short port) {
		new AUServer(name, ip, port);
	}
	
}
