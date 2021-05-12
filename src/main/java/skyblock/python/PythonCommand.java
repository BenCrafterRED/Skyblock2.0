package skyblock.python;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyObject;
import org.python.core.PyStringMap;
import org.python.util.InteractiveConsole;

import skyblock.io.CommandSenderReader;
import skyblock.io.CommandSenderWriter;

public class PythonCommand extends Command {

	private Map<CommandSender, InteractiveConsole> interpreters;
	private Plugin plugin;
	
	protected PythonCommand(Plugin plugin) {
		super("python", "Runs Python scripts and commands using Jython 2.7.2", "<command> book", Collections.emptyList());
		this.plugin = plugin;
		this.interpreters = new HashMap<>();
		setPermission(PermissionDefault.OP.name());
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("book")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "This command can only be executen by a player.");
					return false;
				}
				Player player = (Player) sender;
				ItemStack book = player.getInventory().getItemInMainHand();
				if (book.getType() != Material.WRITABLE_BOOK && book.getType() != Material.WRITTEN_BOOK) {
					book = player.getInventory().getItemInOffHand();
					if (book.getType() != Material.WRITABLE_BOOK && book.getType() != Material.WRITTEN_BOOK) {
						player.sendMessage("You must be holding a writable or written book.");
						return false;
					}
				}
				BookMeta bookMeta = (BookMeta) book.getItemMeta();
				String script = String.join("\n", bookMeta.getPages());
				new Thread(() -> {
					InteractiveConsole interpreter = getInterpreter(player);
					PyCode compiled = interpreter.compile(script, "book");
					try {
						interpreter.exec(compiled);
					} catch (Exception e) {
						plugin.getLogger().log(Level.WARNING, "Exception occured while trying to execute python code.", e);
						//PyException exc = Py.JavaError(e);
						//player.sendMessage(Py.formatException(exc.type, exc.value));
					}
				}).start();
				return true;
			}
		}
		return false;
	}
	
	protected InteractiveConsole getInterpreter(CommandSender sender) {
		if (!interpreters.containsKey(sender)) {
			Map<Object, PyObject> locals = new HashMap<>();
			locals.put("executor", Py.java2py(sender));
			InteractiveConsole interpreter = new InteractiveConsole(new PyStringMap(locals));
			interpreter.setOut(new CommandSenderWriter(sender, ChatColor.GRAY.toString()));
			interpreter.setErr(new CommandSenderWriter(sender, ChatColor.RED.toString()));
			interpreter.setIn(new CommandSenderReader(sender, plugin, true));
			interpreters.put(sender, interpreter);
		}
		return interpreters.get(sender);
	}
}
