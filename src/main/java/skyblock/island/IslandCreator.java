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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class IslandCreator {
	
	private Plugin plugin;
	
	public IslandCreator(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public void createIsland(Location location, IslandType type, Player player) {
		FileConfiguration config = plugin.getConfig();
		if (type == IslandType.classic) {
			if(config.getBoolean("islandOptions.islandTypes.classic.allowed")) {
				createClassicIsland(location);
				}else{
					player.sendMessage("The IslandType "+type+" is not allowed!");
				}
		}else if (type == IslandType.round) {
			if(config.getBoolean("islandOptions.islandTypes.round.allowed")) {
				createRoundIsland(location);
				}else{
					player.sendMessage("The IslandType "+type+" is not allowed!");
				}
		}else if (type == IslandType.cube) {
			if(config.getBoolean("islandOptions.islandTypes.cube.allowed")) {
				createCubeIsland(location);
				}else{
					player.sendMessage("The IslandType "+type+" is not allowed!");
				}
		}else if (type == IslandType.botania) {
			if(config.getBoolean("islandOptions.islandTypes.classic.allowed")) {
				createBotaniaIsland(location);
				}else{
					player.sendMessage("The IslandType "+type+" is not allowed!");
				}
		}else {
			throw new IllegalArgumentException("Unknown IslandType: " + type);
		}
	}
	
	public void createBotaniaIsland(Location location) {
		FileConfiguration config = plugin.getConfig();
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
		if(config.getBoolean("islandOptions.islandTypes.botania.spawnWithWater")) {
			world.getBlockAt(location.add(1, -2, 0)).setType(Material.WATER);
		}
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
		
		Block startChest = world.getBlockAt(location.getBlockX() +0, location.getBlockY()+3, location.getBlockZ()+1);
		startChest.setType(Material.CHEST);
		BlockData chestBlockData = startChest.getBlockData();
		((Directional) chestBlockData).setFacing(BlockFace.WEST);
		startChest.setBlockData(chestBlockData);
		Chest chestData = (Chest) startChest.getState();
		chestData.setLootTable(plugin.getServer().getLootTable(new NamespacedKey(plugin, "chests/start_chest")));
	}
	
	public void createCubeIsland(Location location) {
		FileConfiguration config = plugin.getConfig();
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
		
		world.generateTree(location.add(-1, 1, -1), TreeType.TREE);
		
		if(config.getBoolean("islandOptions.islandTypes.classic.spawnWithWater")) {
			world.getBlockAt(location.add(-1, -2, 0)).setType(Material.WATER);
		}
	}
	
	public void createRoundIsland(Location location) {
		FileConfiguration config = plugin.getConfig();
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
        
        world.generateTree(location.add(-3, 1, -2), TreeType.TREE);
        if(config.getBoolean("islandOptions.islandTypes.classic.spawnWithWater")) {
			world.getBlockAt(location.add(-3, -2, 0)).setType(Material.WATER);
		}
        
	}
	
	public void createClassicIsland(Location location) {
		FileConfiguration config = plugin.getConfig();
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
		if(config.getBoolean("islandOptions.islandTypes.classic.spawnWithWater")) {
			world.getBlockAt(location.add(2, -2, -1)).setType(Material.WATER);
		}
		
	}
}
