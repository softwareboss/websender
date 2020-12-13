package tc.yigit.bungeecord;

import java.net.Socket;

import net.md_5.bungee.api.ProxyServer;
import tc.yigit.events.BungeeSocketOnCommandEvent;
import tc.yigit.events.BungeeSocketOnMessageEvent;
import tc.yigit.events.EventManager;
import tc.yigit.events.EventOutput;

public class BungeeEvents implements EventManager {

	@Override
	public EventOutput callOnCommandEvent(Socket socket, String command){	
		BungeeSocketOnCommandEvent event = new BungeeSocketOnCommandEvent(socket, command);
		ProxyServer.getInstance().getPluginManager().callEvent(event);
		
		return new EventOutput(event.getCommand(), event.isCancelled());
	}
	
	@Override
	public EventOutput callOnMessageEvent(Socket socket, String message){
		BungeeSocketOnMessageEvent event = new BungeeSocketOnMessageEvent(socket, message);
		ProxyServer.getInstance().getPluginManager().callEvent(event);
		
		return new EventOutput(event.getMessage(), event.isCancelled());
	}
	
}
