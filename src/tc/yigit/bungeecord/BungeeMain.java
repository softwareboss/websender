package tc.yigit.bungeecord;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import tc.yigit.shared.SocketConfig;
import tc.yigit.shared.SocketServer;
import tc.yigit.shared.UtilSocket;

public class BungeeMain extends Plugin {
	
    private static BungeeMain instance;
    
    public void onEnable() {
        BungeeMain.instance = this;
        SocketConfig.status = "BungeeCord";
        
        this.configLoad();
        
        BungeeCord.getInstance().getPluginManager().registerCommand(this, new BungeeCommand());
        
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
        
        UtilSocket.createLog("Disabled!");
    }
    
    public void configLoad(){
        Configuration config = null;
        if(!this.getDataFolder().exists()){
            this.getDataFolder().mkdir();
        }
        
        final File file = new File(this.getDataFolder(), "config.yml");
        if(!file.exists()){
            try{
                file.createNewFile();
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
                config.set("socketPort", SocketConfig.port);
                config.set("socketPassword", SocketConfig.password);
                config.set("senderPrefix", SocketConfig.prefix);
                config.set("wrongPassword", SocketConfig.wrongPassword);
                config.set("wrongData", SocketConfig.wrongData);
                config.set("succesfullyLogin", SocketConfig.succesfullyLogin);
                config.set("consoleInfo", SocketConfig.consoleInfo);
                config.set("commandPermission", SocketConfig.commandPermission);
                config.set("nothavePerm", SocketConfig.nothavePerm);
                config.set("pluginReloaded", SocketConfig.pluginReloaded);
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(this.getDataFolder(), "config.yml"));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        
        try{
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
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
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static BungeeMain getPlugin(){
        return BungeeMain.instance;
    }
    
    public static boolean sendCommand(String command){
        return ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), command);
    }
    
}
