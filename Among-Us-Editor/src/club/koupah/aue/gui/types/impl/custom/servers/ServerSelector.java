package club.koupah.aue.gui.types.impl.custom.servers;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.UIManager.LookAndFeelInfo;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.GUIPanel;
import club.koupah.aue.gui.types.Setting;

public class ServerSelector extends Setting {

	LookAndFeelInfo[] allLookAndFeels;

	boolean customServer;

	public ServerSelector(JLabel label, final JComboBox<String> component) {
		super(label, component);

		component.addItem("Keep Current");

		DefaultServers.values();

		Editor.getInstance().regionInfoManager.getServers();

		for (AUServer server : AUServer.servers) {
			component.addItem(server.serverName);
		}

		component.addItem("Custom Server");
	}

	@Override
	public void addToPane(GUIPanel contentPane) {
		super.addToPane(contentPane);
	}

	@Override
	public String getComponentValue(boolean fromLabel) {
		customServer = ((JComboBox<String>) component).getSelectedItem().toString().equalsIgnoreCase("Custom Server");
		return (String) ((JComboBox<String>) component).getSelectedItem();
	}

	boolean removedKC = false;

	@Override
	public void updateComponent() {

		JComboBox<String> component = (JComboBox<String>) this.component;

		AUServer server = AUServer.getByName(component.getSelectedItem().toString());
		if (server == null) {
			if (component.getSelectedItem().toString().equalsIgnoreCase("Custom Server")) {
				Object[] customServerInfo = Editor.getInstance().configManager.getCustomServerInfo();
				if (customServerInfo[2] != null) {
					Editor.getInstance().regionInfoManager.setServer((String) customServerInfo[0],
							(String) customServerInfo[1], (short) customServerInfo[2]);
				}
			} else if (component.getSelectedItem().toString().equalsIgnoreCase("None")) {
				Editor.getInstance().regionInfoManager.clearServer();
			} else {
				// Do nothing
			}
		} else if (server != null) {
			Editor.getInstance().regionInfoManager.setServer(server.serverName, server.serverAddress, server.serverIP,
					server.serverPort);
		}

		if (!removedKC && !component.getSelectedItem().toString().equals("Keep Current")) {
			removedKC = true;
			component.removeItemAt(0);
			component.insertItemAt("None", 0);
		}

	}

	public void updateItems() {
		JComboBox<String> component = (JComboBox<String>) this.component;

		component.removeAllItems();

		component.addItem("Keep Current");

		DefaultServers.values();

		for (AUServer server : AUServer.servers) {
			component.addItem(server.serverName);
		}

		component.addItem("Custom Server");
	}

	public boolean customServer() {
		return this.customServer;
	}
}
