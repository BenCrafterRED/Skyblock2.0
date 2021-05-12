package skyblock.io;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class CommandSenderReader extends PipedReader implements Listener {
	
	private PipedWriter writer;
	private CommandSender sender;
	private boolean registered;
	private Plugin plugin;
	
	public CommandSenderReader(CommandSender commandSender, Plugin plugin) {
		this(commandSender, plugin, false);
	}
	
	public CommandSenderReader(CommandSender commandSender, Plugin plugin, boolean register) {
		super();
		try {
			writer = new PipedWriter(this);
		} catch (IOException e) {
			plugin.getLogger().log(Level.WARNING, "Could not connect to CommandSenderReader.", e);
		}
		commandSender = sender;
		this.plugin = plugin;
		if (register) {
			register();
		}
	}
	
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (event.getPlayer().equals(sender)) {
			broadcast(event.getMessage());
		}
	}
	
	public void broadcast(String message) {
		try {
			writer.write(message);
		} catch (IOException e) {
			plugin.getLogger().log(Level.WARNING, "Error occured while broadcasting message using CommandSenderReader.", e);
		}
	}
	
	public void register() {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		registered = true;
	}
	
	public void unregister() {
		HandlerList.unregisterAll(this);
		registered = false;
	}
	
	@Override
	public void close() {
		unregister();
		try {
			writer.close();
			super.close();
		} catch (IOException e) {
			plugin.getLogger().log(Level.WARNING, "Could not close CommandSenderReader properly.", e);
		}
	}
}
