package tc.yigit.bungeecord;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import tc.yigit.shared.SocketConfig;
import tc.yigit.shared.UtilSocket;

public class BungeeCommand extends Command {
	
    public BungeeCommand(){
        super("websender", null, new String[0]);
    }
    
    @SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args){
        if(!(sender instanceof ProxiedPlayer)){
            BungeeMain.getPlugin().configLoad();
            sender.sendMessage(String.valueOf(SocketConfig.prefix) + SocketConfig.pluginReloaded);
            return;
        }
        if(!sender.hasPermission(SocketConfig.commandPermission)){
            UtilSocket.sendPlayerMsg(sender.getName(), SocketConfig.nothavePerm);
            return;
        }
        
        BungeeMain.getPlugin().configLoad();
        UtilSocket.sendPlayerMsg(sender.getName(), SocketConfig.pluginReloaded);
    }
    
}
