package skyblock.main;


import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import nl.rutgerkok.worldgeneratorapi.WorldGeneratorApi;
import nl.rutgerkok.worldgeneratorapi.WorldRef;
import nl.rutgerkok.worldgeneratorapi.decoration.BaseDecorationType;
import skyblock.generator.VoidGenerator;
import skyblock.island.IslandCommand;
import skyblock.python.PythonModule;
import skyblock.recipe.Recipes;

public class Main extends JavaPlugin {
	
	private static Main instance;
	public PythonModule pythonModule;
	public SimpleCommandMap commandMap;
	
	@Override
	public void onEnable() {
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer(ChatColor.GREEN+"Server rebooted");
		}
		instance = this;
		ServerConfigs.loadConfigs();
		
		loadCommandMap();
		
		pythonModule = new PythonModule(this);
		
		commandMap.register(getName(), new IslandCommand(this));
		
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new Recipes(this), this);
		
		Recipes recipes = new Recipes(instance);
		recipes.load();

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
