package com.clicktominimize;

import net.runelite.client.input.KeyListener;
import java.awt.event.KeyEvent;
import javax.inject.Inject;

public class ClickToMinimizeKeyListener implements KeyListener
{
    @Inject
    private ClickToMinimizePlugin plugin;

    @Inject
    private ClickToMinimizeConfig config;

    @Override
    public void keyTyped(KeyEvent e)
    {
        // No action needed for keyTyped event in this case
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (config.minimizeKeybind().matches(e))
        {
            plugin.minimizeWindow();
            e.consume();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // No action needed for keyReleased event in this case
    }
}
