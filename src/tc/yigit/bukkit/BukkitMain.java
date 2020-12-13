package tc.yigit.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import tc.yigit.shared.SocketConfig;
import tc.yigit.shared.SocketServer;
import tc.yigit.shared.UtilSocket;

public class BukkitMain extends JavaPlugin implements Listener {
	
    private static BukkitMain instance;
    FileConfiguration config;
    
    public BukkitMain(){
        this.config = this.getConfig();
    }
    
    public void onEnable() {
        BukkitMain.instance = this;
        SocketConfig.status = "Bukkit";
        
        this.config.addDefault("socketPort", SocketConfig.port);
        this.config.addDefault("socketPassword", SocketConfig.password);
        this.config.addDefault("senderPrefix", SocketConfig.prefix);
        this.config.addDefault("wrongPassword", SocketConfig.wrongPassword);
        this.config.addDefault("wrongData", SocketConfig.wrongData);
        this.config.addDefault("succesfullyLogin", SocketConfig.succesfullyLogin);
        this.config.addDefault("consoleInfo", SocketConfig.consoleInfo);
        this.config.addDefault("commandPermission", SocketConfig.commandPermission);
        this.config.addDefault("nothavePerm", SocketConfig.nothavePerm);
        this.config.addDefault("pluginReloaded", SocketConfig.pluginReloaded);
        this.config.options().copyDefaults(true);
        this.saveConfig();        
        this.configLoad();
        
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("websender").setExecutor(new BukkitCommand());
        
        if(SocketConfig.password.equalsIgnoreCase("123qwe")){
            UtilSocket.createLog("PLUGIN DISABLED! PLEASE CHANGE PASSWORD FROM CONFIG!");
            return;
        }
        
        UtilSocket.createLog("Starting...");
        
        SocketServer socket = new SocketServer();
        socket.start();
        
        UtilSocket.createLog("Started!");
    }
    
    public void onDisable(){
        try{
        	if(SocketServer.listenSock != null && !SocketServer.listenSock.isClosed()){
                SocketServer.listenSock.close();        		
        	}
        	if(SocketServer.in != null){
        		SocketServer.in.close();
        	}
        	if(SocketServer.out != null){
        		SocketServer.out.close();
        	}
        	if(SocketServer.sock != null && !SocketServer.sock.isClosed()){
                SocketServer.sock.close();        		
        	}
        }catch(Exception ex){
        	ex.printStackTrace();
        }
    }
    
    public void configLoad() {
        this.reloadConfig();
        final FileConfiguration config = this.getConfig();
        
        SocketConfig.prefix = String.valueOf(String.valueOf(config.get("senderPrefix")).replaceAll("&", "§")) + " ";
        SocketConfig.port = config.getInt("socketPort");
        SocketConfig.password = config.getString("socketPassword");
        SocketConfig.wrongPassword = config.getString("wrongPassword").replaceAll("&", "§");
        SocketConfig.wrongData = config.getString("wrongData").replaceAll("&", "§");
        SocketConfig.succesfullyLogin = config.getString("succesfullyLogin").replaceAll("&", "§");
        SocketConfig.consoleInfo = config.getString("consoleInfo");
        SocketConfig.commandPermission = config.getString("commandPermission");
        SocketConfig.nothavePerm = config.getString("nothavePerm").replaceAll("&", "§");
        SocketConfig.pluginReloaded = config.getString("pluginReloaded").replaceAll("&", "§");
    }
    
    public static BukkitMain getPlugin(){
        return BukkitMain.instance;
    }
    
    public static boolean sendCommand(String command){
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command));
        return true;
    }
    
}
