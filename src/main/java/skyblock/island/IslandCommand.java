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
		super("island", "Creates a new island", "/island <create, delete or home> <type> <coordinates>", Arrays.asList("is"));
		this.plugin = plugin;
		
		

	}
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(sender instanceof Player) {
			Player player = ((Player) sender);
			if(args[0].equals("home")){
				if(player.getPersistentDataContainer().has(new NamespacedKey(plugin,"islandHomeX"), PersistentDataType.INTEGER)) {
					Location location = player.getLocation();
					location.setX(player.getPersistentDataContainer().get(new NamespacedKey(plugin,"islandHomeX"), PersistentDataType.INTEGER));
					location.setY(player.getPersistentDataContainer().get(new NamespacedKey(plugin,"islandHomeY"), PersistentDataType.INTEGER));
					location.setZ(player.getPersistentDataContainer().get(new NamespacedKey(plugin,"islandHomeZ"), PersistentDataType.INTEGER));
					player.teleport(location.add(0.5, 2.5, 0.5));
					return false;
				}else {
					player.sendMessage(ChatColor.DARK_RED+"you have no homepoint!");
					return false;
				}
				
			}
			if(args[0].equals("delete")){
				player.getPersistentDataContainer().set(new NamespacedKey(plugin,"hasIsland"), PersistentDataType.INTEGER, 0);
				player.getPersistentDataContainer().remove(new NamespacedKey(plugin,"islandHomeX"));
				player.getPersistentDataContainer().remove(new NamespacedKey(plugin,"islandHomeY"));
				player.getPersistentDataContainer().remove(new NamespacedKey(plugin,"islandHomeZ"));
				return false;
			}
			if(player.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"hasIsland"), PersistentDataType.INTEGER, 0) <= 0) {
				player.getPersistentDataContainer().set(new NamespacedKey(plugin,"hasIsland"), PersistentDataType.INTEGER, 1);
				if(args.length == 5) {
					if(args[0].equals("create")) {
						IslandType type = IslandType.valueOf(args[1]);
						Location location = player.getLocation();
						location.setX(Integer.valueOf(args[2])+0.5);
						location.setY(Integer.valueOf(args[3])-1.5);
						location.setZ(Integer.valueOf(args[4])+0.5);
						player.getPersistentDataContainer().set(new NamespacedKey(plugin,"islandHomeX"), PersistentDataType.INTEGER, location.getBlockX());
						player.getPersistentDataContainer().set(new NamespacedKey(plugin,"islandHomeY"), PersistentDataType.INTEGER, location.getBlockY());
						player.getPersistentDataContainer().set(new NamespacedKey(plugin,"islandHomeZ"), PersistentDataType.INTEGER, location.getBlockZ());
						player.teleport(location.add(0, 1.5, 0));
						IslandCreator Ceator = new IslandCreator(plugin);
						player.setBedSpawnLocation(location, true);
						Ceator.createIsland(location, type);
						player.setGameMode(GameMode.SURVIVAL);
						player.setFoodLevel(20);
						
						ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
						BookMeta Meta = (BookMeta)item.getItemMeta();
						Meta.setAuthor("Server");
						Meta.setTitle("Welcome");
						Meta.addPage(ChatColor.GOLD+"Welcome to skyblock\n\n"+ChatColor.BLUE+"Have Fun!");
						item.setItemMeta(Meta);
						
						player.openBook(item);
					}else {
						player.sendMessage(ChatColor.RED+"The parameter " +args[0]+ " does not exist!");
					}					
				}else {
						player.sendMessage(ChatColor.DARK_RED+"Invalid syntax!");
				}
				
				
				
			}else {
				sender.sendMessage(ChatColor.RED+"You have ready an insland!");
			}
		}
		return false;
	}

}
