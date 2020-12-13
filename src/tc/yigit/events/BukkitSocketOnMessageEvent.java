package tc.yigit.events;

import java.net.Socket;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BukkitSocketOnMessageEvent extends Event implements Cancellable {
	
	private Socket socket;
	private String message;
	
	private boolean cancelled;
	
	public BukkitSocketOnMessageEvent(Socket socket, String message){		
		super(true);
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