package skyblock.island;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
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
				if(player.getPersistentDataContainer().has(new NamespacedKey(plugin,"islandHome"), PersistentDataType.INTEGER_ARRAY)) {
					Location location = player.getLocation();
					int[] homepos = player.getPersistentDataContainer().get(new NamespacedKey(plugin,"islandHome"), PersistentDataType.INTEGER_ARRAY);
					location.setX(homepos[0]);
					location.setY(homepos[1]);
					location.setZ(homepos[2]);
					player.teleport(location.add(0.5, 2.5, 0.5));
					return false;
				}else {
					player.sendMessage(ChatColor.DARK_RED+"you have no homepoint!");
					return false;
				}
				
			}
			if(args[0].equals("delete")){
				player.getPersistentDataContainer().remove(new NamespacedKey(plugin,"islandHome"));
				return false;
			}
			if(!player.getPersistentDataContainer().has(new NamespacedKey(plugin,"islandHome"), PersistentDataType.INTEGER_ARRAY)) {
				if(args.length == 5) {
					if(args[0].equals("create")) {
						IslandType type = IslandType.valueOf(args[1]);
						Location location = player.getLocation();
						location.setX(Integer.valueOf(args[2])+0.5);
						location.setY(Integer.valueOf(args[3])-1.5);
						location.setZ(Integer.valueOf(args[4])+0.5);
						int[] home = {location.getBlockX(), location.getBlockY(), location.getBlockZ()};
						player.getPersistentDataContainer().set(new NamespacedKey(plugin,"islandHome"), PersistentDataType.INTEGER_ARRAY, home);
						player.teleport(location.add(0, 1.5, 0));
						IslandCreator Ceator = new IslandCreator(plugin);
						player.setBedSpawnLocation(location, true);
						Ceator.createIsland(location, type, player);
						player.setGameMode(GameMode.SURVIVAL);
						player.setFoodLevel(20);
						
						FileConfiguration config = plugin.getConfig();
						if(config.getBoolean("islandOptions.welcomeBook")) {
							ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
							BookMeta Meta = (BookMeta)item.getItemMeta();
							Meta.setAuthor("Server");
							Meta.setTitle("Welcome");
							Meta.addPage(ChatColor.GOLD+"Welcome to skyblock\n\n"+ChatColor.BLUE+"Have Fun!");
							item.setItemMeta(Meta);
							player.openBook(item);
						}	
					}else {
						player.sendMessage(ChatColor.RED+"The parameter " +args[0]+ " does not exist!");
					}					
				}else {
						player.sendMessage(ChatColor.DARK_RED+"Invalid syntax!");
				}
				
				
				
			}else {
				sender.sendMessage(ChatColor.RED+"You have already an insland!");
			}
		}
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player =(Player) sender;
			List<String> completion = new ArrayList<>();
			String[] parameter = {"create", "delete", "home"};
			if(args.length == 1) {
				for(int i = 0; i < parameter.length; i++) {
					if(parameter[i].startsWith(args[0])) {
						completion.add(parameter[i]);
					}
				}
				completion.sort(null);
				return completion;
				
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("create")) {
					for (IslandType type : IslandType.values()) {
					    if(type.toString().startsWith(args[1])) {
					    	completion.add(type.toString());
					    }
					}
				}
				completion.sort(null);
				return completion;
			}
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length == 3) {
					completion.add(Integer.valueOf(player.getLocation().getBlockX()).toString());
					return completion;
				}
				if(args.length == 4) {
					completion.add(Integer.valueOf(player.getLocation().getBlockY()).toString());
					return completion;
				}
				if(args.length == 5) {
					completion.add(Integer.valueOf(player.getLocation().getBlockZ()).toString());
					return completion;
				}
			}
		}
		return null;
	}

}
