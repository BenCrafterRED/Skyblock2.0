package skyblock.island;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class IslandCreator {
	
	public static void createIsland(Location location, IslandType type) {
		if (type == IslandType.CLASSIC) {
			createClassicIsland(location);
		} else {
			throw new IllegalArgumentException("Unknown IslandType: " + type);
		}
	}
	
	public static void createClassicIsland(Location location) {
		World world = location.getWorld();
		
		for (int ry = -2; ry < 0; ry++) {
			for (int rx = -1; rx < 5; rx++) {
				for (int rz = -1; rz < 5; rz++) {
					world.getBlockAt(rx, ry, rz).setType(Material.DIRT);
				}
			}
		}
		
		for (int rx = -1; rx < 5; rx++) {
			for (int rz = -1; rz < 5; rz++) {
				world.getBlockAt(rx, 0, rz).setType(Material.GRASS);
			}
		}
		
		for (int ry = -2; ry < 1; ry++) {
			for (int rx = 2; rx < 5; rx++) {
				for (int rz = 2; rz < 5; rz++) {
					world.getBlockAt(rx, ry, rz).setType(Material.DIRT);
				}
			}
		}
	}
}
