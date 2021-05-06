package skyblock.main;

import org.apache.logging.log4j.LogManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onLoad() {
		FileConfiguration bukkitConfig = new YamlConfiguration();
		try {
			LogManager.getLogger().info("Loading bukkit.yml");
			bukkitConfig.load("bukkit.yml");
		} catch (Exception e) {
			LogManager.getLogger().error("Could not load bukkit.yml");
			e.printStackTrace();
		}
	}
	
	@Override
	public void onEnable() {
	}
	
	@Override
	public void onDisable() {
	}
	
	
}
