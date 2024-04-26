package com.clicktominimize;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
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
		return "Pick-Fruit: Sq'irk tree, Mine: Crashed Star";
	}
}
