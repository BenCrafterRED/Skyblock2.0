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
		}else if (type == IslandType.round) {
			createRoundIsland(location);
		}else if (type == IslandType.cube) {
			createCubeIsland(location);
		}else if (type == IslandType.botania) {
			createBotaniaIsland(location);
		}else {
			throw new IllegalArgumentException("Unknown IslandType: " + type);
		}
	}
	
	public void createBotaniaIsland(Location location) {
		World world = location.getWorld();
		int yStart = location.getBlockY()-1;
		int xStart = location.getBlockX()+2;
		int zStart = location.getBlockZ()+2;
		for(int y = yStart-3; y <= yStart; y++){
			 for(int x = xStart-2; x <= xStart; x++){
				 for(int z = zStart-2; z <= zStart; z++){
					 location.setX(x);
                 	 location.setY(y);
                 	 location.setZ(z);
                 	 if(y < yStart) {
                		 world.getBlockAt(location).setType(Material.DIRT);
                	 }else {
                	 world.getBlockAt(location).setType(Material.GRASS_BLOCK);
                	 }
				 }
				 
			 }
		}world.getBlockAt(location.add(-1, 1, -1)).setType(Material.OAK_SAPLING);
		world.getBlockAt(location.add(1, -2, 0)).setType(Material.WATER);
		world.getBlockAt(location.add(0, -1, 1)).setType(Material.SPRUCE_WOOD);
		world.getBlockAt(location.add(1, -1, 0)).setType(Material.SPRUCE_WOOD);
		world.getBlockAt(location.add(0, 0, 1)).setType(Material.SPRUCE_WOOD);
		world.getBlockAt(location.add(-3, 0, -1)).setType(Material.SPRUCE_WOOD);
		world.getBlockAt(location.add(0, -1, 0)).setType(Material.SPRUCE_WOOD);
		world.getBlockAt(location.add(-1, 0, 0)).setType(Material.SPRUCE_WOOD);
		world.getBlockAt(location.add(0, -1, -1)).setType(Material.SPRUCE_WOOD);
		world.getBlockAt(location.add(2, 1, -3)).setType(Material.SPRUCE_WOOD);
		world.getBlockAt(location.add(0, 1, 0)).setType(Material.SPRUCE_WOOD);
		world.getBlockAt(location.add(0, 0, 1)).setType(Material.SPRUCE_WOOD);
		world.getBlockAt(location.add(0, 1, 0)).setType(Material.SPRUCE_WOOD);
	}
	
	public void createCubeIsland(Location location) {
		World world = location.getWorld();
		int yStart = location.getBlockY()-1;
		int xStart = location.getBlockX()+2;
		int zStart = location.getBlockZ()+2;
		for(int y = yStart-2; y <= yStart; y++){
			 for(int x = xStart-2; x <= xStart; x++){
				 for(int z = zStart-2; z <= zStart; z++){
					 location.setX(x);
                 	 location.setY(y);
                 	 location.setZ(z);
                 	 if(y < yStart) {
                		 world.getBlockAt(location).setType(Material.DIRT);
                	 }else {
                	 world.getBlockAt(location).setType(Material.GRASS_BLOCK);
                	 }
				 }
				 
			 }
		}
		Block startChest = world.getBlockAt(location.getBlockX() -1, location.getBlockY()+1, location.getBlockZ()-2);
		startChest.setType(Material.CHEST);
		BlockData chestBlockData = startChest.getBlockData();
		((Directional) chestBlockData).setFacing(BlockFace.WEST);
		startChest.setBlockData(chestBlockData);
		Chest chestData = (Chest) startChest.getState();
		chestData.setLootTable(plugin.getServer().getLootTable(new NamespacedKey(plugin, "chests/start_chest")));
		
		world.generateTree(location.add(-1, 0, -1), TreeType.TREE);
	}
	
	public void createRoundIsland(Location location) {
		int xStart = location.getBlockX()+2;
    	int yStart = location.getBlockY()-1;
    	int zStart = location.getBlockZ();
    	int r = 4;
    	World world = location.getWorld();
        for(int x = xStart - r; x <= xStart + r; x++){
            for(int y = yStart - r; y <= yStart; y++){
                for(int z = zStart - r; z <= zStart + r; z++){
                    if((x-xStart)*(x-xStart)+(y-yStart)*(y-yStart)+(z-zStart)*(z-zStart) < r*r){
                    	location.setX(x);
                    	location.setY(y);
                    	location.setZ(z);
                    	if(y < yStart) {
                    		world.getBlockAt(location).setType(Material.DIRT);
                    	}else {
                    		world.getBlockAt(location).setType(Material.GRASS_BLOCK);
                    	}
                    }
                }
            }
        }
        Block startChest = world.getBlockAt(location.getBlockX() -4, location.getBlockY()+1, location.getBlockZ()-2);
		startChest.setType(Material.CHEST);
		BlockData chestBlockData = startChest.getBlockData();
		((Directional) chestBlockData).setFacing(BlockFace.WEST);
		startChest.setBlockData(chestBlockData);
		Chest chestData = (Chest) startChest.getState();
		chestData.setLootTable(plugin.getServer().getLootTable(new NamespacedKey(plugin, "chests/start_chest")));
        
        world.generateTree(location.add(-3, 0, -2), TreeType.TREE);
        
	}
	
	public void createClassicIsland(Location location) {
		World world = location.getWorld();
		
		for (int y = location.getBlockY() - 3; y < location.getBlockY() - 1; y++) {
			for (int x = location.getBlockX()-1; x < location.getBlockX() + 5; x++) {
				for (int z = location.getBlockZ()-1; z < location.getBlockZ() + 5; z++) {
					world.getBlockAt(x, y, z).setType(Material.DIRT);
				}
			}
		}
		
		for (int x = location.getBlockX() - 1; x < location.getBlockX() + 5; x++) {
			for (int z = location.getBlockZ() - 1; z < location.getBlockZ() + 5; z++) {
				world.getBlockAt(x, location.getBlockY() - 1, z).setType(Material.GRASS_BLOCK);
			}
		}
		
		for (int y = location.getBlockY() - 3; y < location.getBlockY(); y++) {
			for (int x = location.getBlockX() + 2; x < location.getBlockX() + 5; x++) {
				for (int z = location.getBlockZ() + 2; z < location.getBlockZ() + 5; z++) {
					world.getBlockAt(x, y, z).setType(Material.AIR);
				}
			}
		}
		
		Block startChest = world.getBlockAt(location.getBlockX() + 4, location.getBlockY(), location.getBlockZ());
		startChest.setType(Material.CHEST);
		BlockData chestBlockData = startChest.getBlockData();
		((Directional) chestBlockData).setFacing(BlockFace.WEST);
		startChest.setBlockData(chestBlockData);
		Chest chestData = (Chest) startChest.getState();
		chestData.setLootTable(plugin.getServer().getLootTable(new NamespacedKey(plugin, "chests/start_chest")));
		
		world.generateTree(location.add(-1, 0, 4), TreeType.TREE);
		
	}
}
