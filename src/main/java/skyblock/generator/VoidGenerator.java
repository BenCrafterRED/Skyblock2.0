package skyblock.generator;

import org.bukkit.Material;

import nl.rutgerkok.worldgeneratorapi.BaseTerrainGenerator;
import nl.rutgerkok.worldgeneratorapi.BiomeGenerator;

public class VoidGenerator implements BaseTerrainGenerator {
	
	@Override
	public int getHeight(BiomeGenerator biomeGenerator, int x, int z, HeightType type) {
		return 1;
	}
	
	@Override
	public void setBlocksInChunk(GeneratingChunk chunk) {
		chunk.getBlocksForChunk().setRegion(0, 0, 0, CHUNK_SIZE, 1, CHUNK_SIZE, Material.AIR);
	}
	
	
}
