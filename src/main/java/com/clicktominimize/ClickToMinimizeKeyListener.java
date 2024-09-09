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

    private boolean preventMinimize = false; // Add this field

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

        if (config.holdToPreventMinimizeKeybind().matches(e))
        {
            preventMinimize = true; // Set to true when the key is pressed
            e.consume();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (config.holdToPreventMinimizeKeybind().matches(e))
        {
            preventMinimize = false; // Reset when the key is released
            e.consume();
        }
    }

    public boolean isPreventMinimizeHeld()
    {
        return preventMinimize;
    }
}