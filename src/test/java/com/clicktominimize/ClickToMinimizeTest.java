package com.clicktominimize;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ClickToMinimizeTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ClickToMinimizePlugin.class);
		RuneLite.main(args);
	}
}