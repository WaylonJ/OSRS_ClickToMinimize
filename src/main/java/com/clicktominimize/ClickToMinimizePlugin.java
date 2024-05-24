package com.clicktominimize;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.swing.JFrame;
import java.awt.Frame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

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

	@Override
	protected void startUp() throws Exception
	{
		log.info("Click To Minimize started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Click To Minimize stopped!");
	}

	public Map<String, List<String>> parseActions(String actionConfig) {
		Map<String, List<String>> actionsMap = new HashMap<>();
		String[] actions = actionConfig.split(",");
		for (String action : actions) {
			String[] parts = action.trim().split(":");
			if (parts.length == 2) {
				String key = parts[0].trim();
				String value = parts[1].trim();
				actionsMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
			}
		}
		return actionsMap;
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		Map<String, List<String>> actionsMap = parseActions(config.actions());
		String action = event.getMenuOption();
		String target = Jsoup.parse(event.getMenuTarget()).text();

		for (Map.Entry<String, List<String>> entry : actionsMap.entrySet()) {
			String configAction = entry.getKey();
			List<String> configTargets = entry.getValue();

			if (action.equals(configAction)) {
				for (String configTarget : configTargets) {
					if (target.contains(configTarget)) {
						minimizeWindow();
						return;
					}
				}
			}
		}
	}


	private void minimizeWindow() {
		JFrame frame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(client.getCanvas());
		if (frame != null) {
			frame.setState(Frame.ICONIFIED);
		} else {
			log.warn("No frame found to minimize!");
		}
	}

	@Provides
	ClickToMinimizeConfig provideConfig(ConfigManager configManager)
{
	return configManager.getConfig(ClickToMinimizeConfig.class);
}
}
