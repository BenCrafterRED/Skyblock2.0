package skyblock.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ServerConfigs {
	
	public static Properties serverProperties = new Properties();
	public static FileConfiguration bukkitConfig = new YamlConfiguration();
	
	public static void loadConfigs() {
		loadServerProperties();
		loadBukkitYml();
	}
	
	public static void loadServerProperties() {
		Main.get().getLogger().info("Loading server.properties");
		try {
			InputStream in = new FileInputStream("server.properties");
			serverProperties.load(in);
			in.close();
		} catch (Exception e) {
			Main.get().getLogger().log(Level.WARNING, "Could not load server.properties", e);
			e.printStackTrace();
		}
	}
	
	public static void saveServerProperties() {
		Main.get().getLogger().info("Saving server.properties");
		try {
			FileOutputStream out = new FileOutputStream("server.properties");
			serverProperties.store(out, null);
			out.close();
		} catch (Exception e) {
			Main.get().getLogger().log(Level.WARNING, "Could not save server.properties", e);
			e.printStackTrace();
		}
	}
	
	public static void loadBukkitYml() {
		Main.get().getLogger().info("Loading bukkit.yml");
		try {
			bukkitConfig.load("bukkit.yml");
		} catch (Exception e) {
			Main.get().getLogger().log(Level.WARNING, "Could not load bukkit.yml", e);
			e.printStackTrace();
		}
	}
	
	public static void saveBukkitYml() {
		Main.get().getLogger().info("Saving bukkit.yml");
		try {
			bukkitConfig.save("bukkit.yml");
		} catch (Exception e) {
			Main.get().getLogger().log(Level.WARNING, "Could not save bukkit.yml", e);
		}
	}
}
