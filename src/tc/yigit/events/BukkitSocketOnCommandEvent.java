package tc.yigit.events;

import java.net.Socket;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BukkitSocketOnCommandEvent extends Event implements Cancellable {
	
	private Socket socket;
	private String command;
	
	private boolean cancelled;
	
	public BukkitSocketOnCommandEvent(Socket socket, String command){		
		super(true);
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
	
	private static final HandlerList handlers = new HandlerList();
	
    @Override
    public HandlerList getHandlers(){
        return handlers;
    }
     
    public static HandlerList getHandlerList(){
        return handlers;
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