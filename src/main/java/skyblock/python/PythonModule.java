package skyblock.python;

import org.bukkit.plugin.Plugin;

import skyblock.main.Main;

public class PythonModule {
	
	PythonCommand pythonCommand;
	Plugin plugin;
	
	public PythonModule(Plugin plugin) {
		this.pythonCommand = new PythonCommand(plugin);
		this.plugin = plugin;
		Main.get().commandMap.register(Main.get().getName(), pythonCommand);
	}
}
