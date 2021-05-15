package skyblock.island;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;


public class IslandCommand extends Command{
	private Plugin plugin;

	public IslandCommand(Plugin plugin) {
		super("island", "##", "/island create", Arrays.asList("is"));
		this.plugin = plugin;
		
		

	}
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(sender instanceof Player) {
			Player player = ((Player) sender);
			if(player.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"hasIsland"), PersistentDataType.INTEGER, 0) <= 0) {
				player.getPersistentDataContainer().set(new NamespacedKey(plugin,"hasIsland"), PersistentDataType.INTEGER, 1);
				if(args.length == 5) {
					IslandType type = IslandType.valueOf(args[1]);
					Location location = player.getLocation();
					location.setX(Integer.valueOf(args[2]));
					location.setY(Integer.valueOf(args[3]));
					location.setZ(Integer.valueOf(args[4]));
					IslandCreator Ceator = new IslandCreator(plugin);
					Ceator.createIsland(location, type);
					player.teleport(location);
					player.setGameMode(GameMode.SURVIVAL);
					player.setBedSpawnLocation(location, true);
				}
				
				
				
			}else {
				sender.sendMessage(ChatColor.RED+"You have ready an insland!");
			}
		}
		return false;
	}

}
