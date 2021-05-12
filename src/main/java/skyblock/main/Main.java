package skyblock.main;

import java.util.logging.Level;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import skyblock.python.PythonModule;

public class Main extends JavaPlugin {
	
	private static Main instance;
	public PythonModule pythonModule;
	public SimpleCommandMap commandMap;
	
	@Override
	public void onEnable() {
		instance = this;
		ServerConfigs.loadConfigs();
		
		loadCommandMap();
		
		pythonModule = new PythonModule(this);
	}
	
	@Override
	public void onDisable() {
	}
	
	/*@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new ChunkGenerator() {
			@Override
			public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
				return createChunkData(world);
			}
		};
	}*/
	
	private void loadCommandMap() {
		try {
			commandMap = (SimpleCommandMap) getServer().getClass().getMethod("getCommandMap").invoke(getServer());
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "Could not get server's command map.", e);
		}
	}
	
	public static Main get() {
		return instance;
	}
}
