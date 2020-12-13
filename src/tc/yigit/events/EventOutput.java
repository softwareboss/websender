package tc.yigit.events;

public class EventOutput {
	
	private String message;
	private boolean cancelled;
	
	public EventOutput(String message, boolean cancelled){
		this.message = message;
		this.cancelled = cancelled;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public boolean isCancelled(){
		return this.cancelled;
	}
		
}
