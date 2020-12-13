package tc.yigit.events;

import java.net.Socket;

import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class BungeeSocketOnMessageEvent extends Event implements Cancellable {
	
	private Socket socket;
	private String message;
	private boolean cancelled;
	
	public BungeeSocketOnMessageEvent(Socket socket, String message){
		this.socket = socket;
		this.message = message;
	}
	
	public Socket getSocket(){
		return this.socket;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	@Override
	public boolean isCancelled(){
		return this.cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;		
	}
	
}
