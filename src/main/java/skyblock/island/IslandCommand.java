package skyblock.island;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
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
					if(args[0].equals("create")) {
						IslandType type = IslandType.valueOf(args[1]);
						Location location = player.getLocation();
						location.setX(Integer.valueOf(args[2])+0.5);
						location.setY(Integer.valueOf(args[3])+0.5);
						location.setZ(Integer.valueOf(args[4])+0.5);
						IslandCreator Ceator = new IslandCreator(plugin);
						player.setBedSpawnLocation(location, true);
						Ceator.createIsland(location, type);
						player.teleport(location.add(1, 0, -4));
						player.setGameMode(GameMode.SURVIVAL);
						
						ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
						BookMeta Meta = (BookMeta)item.getItemMeta();
						Meta.addPage(ChatColor.GOLD+("Wilkommen"));
						item.setItemMeta(Meta);
						
						player.openBook(null);
					}else {
						player.sendMessage(ChatColor.RED+"The parameter " +args[0]+ " does not exist");					
					}
				}
				
				
				
			}else {
				sender.sendMessage(ChatColor.RED+"You have ready an insland!");
				player.getPersistentDataContainer().set(new NamespacedKey(plugin,"hasIsland"), PersistentDataType.INTEGER, 0);
			}
		}
		return false;
	}

}
