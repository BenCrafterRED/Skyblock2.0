package skyblock.island;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.plugin.Plugin;

public class IslandCreator {
	
	private Plugin plugin;
	
	public IslandCreator(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public void createIsland(Location location, IslandType type) {
		if (type == IslandType.classic) {
			createClassicIsland(location);
		} else {
			throw new IllegalArgumentException("Unknown IslandType: " + type);
		}
	}
	
	public void createClassicIsland(Location location) {
		World world = location.getWorld();
		
		for (int y = location.getBlockY(); y < location.getBlockY(); y++) {
			for (int x = location.getBlockX(); x < location.getBlockX(); x++) {
				for (int z = location.getBlockZ(); z < location.getBlockZ(); z++) {
					world.getBlockAt(x, y, z).setType(Material.DIRT);
				}
			}
		}
		
		for (int x = location.getBlockX() - 1; x < location.getBlockX() + 5; x++) {
			for (int z = location.getBlockZ() - 1; z < location.getBlockZ() + 5; z++) {
				world.getBlockAt(x, location.getBlockY(), z).setType(Material.GRASS);
			}
		}
		
		for (int y = location.getBlockY() - 2; y <= location.getBlockY(); y++) {
			for (int x = location.getBlockX() + 2; x < location.getBlockX() + 5; x++) {
				for (int z = location.getBlockZ() + 2; z < location.getBlockZ() + 5; z++) {
					world.getBlockAt(x, y, z).setType(Material.AIR);
				}
			}
		}
		
		Block startChest = world.getBlockAt(location.getBlockX() + 4, location.getBlockY() + 1, location.getBlockZ());
		startChest.setType(Material.CHEST);
		BlockData chestBlockData = startChest.getBlockData();
		((Directional) chestBlockData).setFacing(BlockFace.WEST);
		startChest.setBlockData(chestBlockData);
		Chest chestData = (Chest) startChest.getState();
		chestData.setLootTable(plugin.getServer().getLootTable(new NamespacedKey(plugin, "chests/start_chest")));
		
		world.generateTree(location.add(-1, +1, +4), TreeType.TREE);
		
	}
}
