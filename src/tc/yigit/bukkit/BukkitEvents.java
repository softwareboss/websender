package tc.yigit.bukkit;

import java.net.Socket;

import org.bukkit.Bukkit;

import tc.yigit.events.BukkitSocketOnCommandEvent;
import tc.yigit.events.BukkitSocketOnMessageEvent;
import tc.yigit.events.EventManager;
import tc.yigit.events.EventOutput;

public class BukkitEvents implements EventManager {

	@Override
	public EventOutput callOnCommandEvent(Socket socket, String command){	
		BukkitSocketOnCommandEvent event = new BukkitSocketOnCommandEvent(socket, command);
		Bukkit.getPluginManager().callEvent(event);
		
		return new EventOutput(event.getCommand(), event.isCancelled());
	}
	
	@Override
	public EventOutput callOnMessageEvent(Socket socket, String message){
		BukkitSocketOnMessageEvent event = new BukkitSocketOnMessageEvent(socket, message);
		Bukkit.getPluginManager().callEvent(event);
		
		return new EventOutput(event.getMessage(), event.isCancelled());
	}
	
}
