package tc.yigit.events;

import java.net.Socket;

import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class BungeeSocketOnCommandEvent extends Event implements Cancellable {
	
	private Socket socket;
	private String command;
	private boolean cancelled;
	
	public BungeeSocketOnCommandEvent(Socket socket, String command){
		this.socket = socket;
		this.command = command;
	}
	
	public Socket getSocket(){
		return this.socket;
	}
	
	public String getCommand(){
		return this.command;
	}
	
	public void setCommand(String command){
		this.command = command;
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
