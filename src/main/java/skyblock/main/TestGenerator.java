package skyblock.main;

import org.bukkit.Material;

import nl.rutgerkok.worldgeneratorapi.BaseTerrainGenerator;
import nl.rutgerkok.worldgeneratorapi.BiomeGenerator;

public class TestGenerator implements BaseTerrainGenerator {

	@Override
	public int getHeight(BiomeGenerator biomeGenerator, int x, int z, HeightType type) {
		return 63;
	}
	
	@Override
	public void setBlocksInChunk(GeneratingChunk chunk) {
		chunk.getBlocksForChunk().setRegion(0, 0, 0, CHUNK_SIZE, 63, CHUNK_SIZE, Material.STONE);
	}

}
