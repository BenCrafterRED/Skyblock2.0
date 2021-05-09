package skyblock.main;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Main instance;
	public static String logPrefix;
	
	@Override
	public void onLoad() {
		instance = this;
		logPrefix = "[" + getName() + "]";
		ServerConfigs.loadConfigs();
	}
	
	@Override
	public void onEnable() {
	}
	
	@Override
	public void onDisable() {
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new ChunkGenerator() {
			@Override
			public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
				return createChunkData(world);
			}
		};
	}
	
	public static Main get() {
		return instance;
	}
}
