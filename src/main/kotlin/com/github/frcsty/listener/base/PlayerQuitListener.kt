package com.github.frcsty.listener.base

import com.github.frcsty.listener.event.FrozenQuitEvent
import com.github.frcsty.load.Loader
import com.github.frcsty.message.MessageFormatter
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener(private val loader:Loader) : Listener {

    companion object {
        private const val ACTION = "quit"
    }

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent) {

        // If the quit message is already empty, assume that this event isn't supposed to run
        if (event.quitMessage.equals("")) return

        event.quitMessage = ""

        val manager = loader.formatManager
        val actionHandler = loader.actionHandler
        val player = event.player

        val actions = MessageFormatter.executeFormat(player, manager, actionHandler, ACTION)
        Bukkit.getServer().pluginManager.callEvent(FrozenQuitEvent(player, actions))
    }
}