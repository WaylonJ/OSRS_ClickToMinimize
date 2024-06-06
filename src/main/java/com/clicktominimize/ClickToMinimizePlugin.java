package com.clicktominimize;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.swing.JFrame;
import java.awt.Frame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.runelite.client.util.Text.removeTags;

@Slf4j
@PluginDescriptor(
		name = "Click To Minimize"
)
public class ClickToMinimizePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClickToMinimizeConfig config;

	@Inject
	private KeyManager keyManager;

	@Inject
	private ClickToMinimizeKeyListener keyListener;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Click To Minimize started!");
		keyManager.registerKeyListener(keyListener);
	}

	@Override
	protected void shutDown() throws Exception
	{
		keyManager.unregisterKeyListener(keyListener);
		log.info("Click To Minimize stopped!");
	}

	@Provides
	ClickToMinimizeConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ClickToMinimizeConfig.class);
	}

	public Map<String, List<String>> parseActions(String actionConfig, boolean ignoreCase) {
		Map<String, List<String>> actionsMap = new HashMap<>();
		String[] actions = actionConfig.split(",");
		for (String action : actions) {
			String[] parts = action.trim().split(":");
			if (parts.length == 2) {
				String key = ignoreCase ? parts[0].trim().toLowerCase() : parts[0].trim();
				String value = ignoreCase ? parts[1].trim().toLowerCase() : parts[1].trim();
				actionsMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
			}
		}
		return actionsMap;
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		boolean ignoreCase = config.ignoreCase();
		boolean checkNoTargets = config.checkNoTargets();
		Map<String, List<String>> actionsMap = parseActions(config.actions(), ignoreCase);
		String action = ignoreCase ? event.getMenuOption().toLowerCase() : event.getMenuOption();
		String target = ignoreCase ? removeTags(event.getMenuTarget()).toLowerCase() : removeTags(event.getMenuTarget());

		for (Map.Entry<String, List<String>> entry : actionsMap.entrySet()) {
			String configAction = entry.getKey();
			List<String> configTargets = entry.getValue();

			if (action.equals(configAction)) {
				for (String configTarget : configTargets) {
					if (target.contains(configTarget) || (checkNoTargets && target.isEmpty())) {
						minimizeWindow();
						return;
					}
				}
			}
		}
	}

	public boolean isInventoryFull()
	{
		Item[] items = client.getItemContainer(InventoryID.INVENTORY).getItems();

		for (Item item : items) {
			if (item == null || item.getId() == -1) {
				return false;
			}
		}
		return true;
	}

	public void minimizeWindow() {
		if (config.ignoreFullInventory() && isInventoryFull()) {
			log.info("Inventory is full. Skipping window minimize.");
			return;
		}

		JFrame frame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(client.getCanvas());
		if (frame != null) {
			frame.setState(Frame.ICONIFIED);
		} else {
			log.warn("No frame found to minimize!");
		}
	}
}
