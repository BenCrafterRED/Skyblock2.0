package skyblock.io;

import java.io.IOException;
import java.io.Writer;

import org.bukkit.command.CommandSender;

public class CommandSenderWriter extends Writer {
	
	private CommandSender sender;
	private StringBuilder buffer;
	private String prefix;
	
	public CommandSenderWriter(CommandSender commandSender) {
		this(commandSender, "");
	}
	
	public CommandSenderWriter(CommandSender commandSender, String messagePrefix) {
		super();
		sender = commandSender;
		prefix = messagePrefix;
		buffer = new StringBuilder(prefix);
	}
	
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		buffer.append(cbuf, off, len);
	}

	@Override
	public void flush() throws IOException {
		String message = buffer.toString().trim();
		if (message.isEmpty()) {
			sender.sendMessage(message);
		}
		buffer = new StringBuilder();
	}

	@Override
	public void close() throws IOException {
		flush();
	}
}
