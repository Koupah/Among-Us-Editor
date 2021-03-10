package club.koupah.aue.gui.types.impl.custom.servers;

public enum DefaultServers {
	
	SkeldDotNetUS("Skeld.net US Server", "192.241.154.115");
	
	DefaultServers(String name, String ip) {
		this(name, ip, (short)22023);
	}
	
	DefaultServers(String name, String ip, short port) {
		new AUServer(name, ip, port);
	}
	
}
