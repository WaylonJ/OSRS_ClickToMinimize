package com.clicktominimize;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;

@ConfigGroup("ClickToMinimize")
public interface ClickToMinimizeConfig extends Config
{
	@ConfigItem(
		keyName = "Actions",
		name = "List of Actions to Minimize",
		description = "Enter actions in the format 'Action: Target, Action: Target. Note, capitalization DOES matter" +
			", E.g. 'Pick-Fruit: Sq'irk tree, Mine: Crashed Star'"
	)
	default String actions()
	{
		return "Pick-Fruit: Sq'irk tree, \n" +
				"Mine: Crashed Star, \n" +
				"Bait: Rod Fishing spot,\n" +
				"Use: Hammer -> Infernal eel,\n" +
				"Make: yew longbow,";
	}

	@ConfigItem(
		keyName = "ignoreCase",
		name = "Ignore Case",
		description = "Ignore text capitalization when matching actions and targets",
		position = 2
	)
	default boolean ignoreCase()
	{
		return true;
	}

	@ConfigItem(
			keyName = "ignoreFullInventory",
			name = "Don't Minimize on Full Inventory",
			description = "If enabled, will not minimize window whenever the inventory is completely full.",
			position = 3
	)
	default boolean ignoreFullInventory()
	{
		return true;
	}

	@ConfigItem(
			keyName = "checkNoTargets",
			name = "Check for No Targets",
			description = "Mainly used for menu options, e.g. at the grand exchange. Using 'Confirm: x' will make it so that when you press the confirm after inputting a trade, it will minimize.",
			position = 4
	)
	default boolean checkNoTargets()
	{
		return true;
	}

	@ConfigItem(
		keyName = "minimizeKeybind",
		name = "Minimize Keybind",
		description = "Keybind to minimize the screen",
		position = 5
	)
	default Keybind minimizeKeybind()
	{
		return Keybind.NOT_SET;
	}
}
