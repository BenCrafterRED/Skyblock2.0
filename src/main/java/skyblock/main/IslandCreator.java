package skyblock.main;

import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class IslandCreator implements Listener {
	
	public void onWorld(WorldLoadEvent event) {
		event.getWorld().setSpawnLocation(0, 63, 0);
	}
}
