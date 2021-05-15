package skyblock.main;

import java.util.logging.Level;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import nl.rutgerkok.worldgeneratorapi.WorldGeneratorApi;
import nl.rutgerkok.worldgeneratorapi.WorldRef;
import nl.rutgerkok.worldgeneratorapi.decoration.BaseDecorationType;
import skyblock.generator.VoidGenerator;
import skyblock.island.IslandCommand;
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
		
		commandMap.register(getName(), new IslandCommand());
	}
	
	@Override
	public void onDisable() {
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return WorldGeneratorApi
				.getInstance(this, 1, 1)
				.createCustomGenerator(WorldRef.ofName(worldName), generator -> {
					generator.setBaseTerrainGenerator(new VoidGenerator());
					generator.getWorldDecorator().withoutDefaultBaseDecorations(BaseDecorationType.BEDROCK);
					getLogger().info("Enabled void generator for world \"" + worldName + "\".");
				});
	}
	
	private void loadCommandMap() {
		try {
			commandMap = (SimpleCommandMap) getServer().getClass().getMethod("getCommandMap").invoke(getServer());
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "Could not fetch server's command map.", e);
		}
	}
	
	public static Main get() {
		return instance;
	}
}
