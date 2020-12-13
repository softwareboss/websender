package tc.yigit.shared;

public class SocketConfig {
	
    public static int    port;
    public static String password;
    public static String prefix;
    public static String wrongPassword;
    public static String wrongData;
    public static String succesfullyLogin;
    public static String consoleInfo;
    public static String commandPermission;
    public static String nothavePerm;
    public static String pluginReloaded;
    public static String status;
    
    static {
        SocketConfig.port               = 9876;
        SocketConfig.password           = "123qwe";
        SocketConfig.prefix             = "[WebSender]";
        SocketConfig.wrongPassword      = "Incorrect password, please enter it carefully.";
        SocketConfig.wrongData          = "Please check PHP variables.";
        SocketConfig.succesfullyLogin   = "Login is successful.";
        SocketConfig.consoleInfo        = "true";
        SocketConfig.commandPermission  = "websender.admin";
        SocketConfig.nothavePerm        = "You not have permission!";
        SocketConfig.pluginReloaded     = "Websender reloaded!";
    }
    
    public static boolean isBukkit(){
    	return status != null && status.equals("Bukkit");
    }
    
    public static boolean isBungee(){
    	return status != null && status.equals("BungeeCord");
    }
    
}
