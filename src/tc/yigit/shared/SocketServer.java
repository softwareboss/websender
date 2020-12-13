package tc.yigit.shared;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;

import tc.yigit.events.EventOutput;

public class SocketServer extends Thread {
	
    private boolean 					connect_status;
    public static int					port = SocketConfig.port;
    public static ServerSocket			listenSock = null;
    public static DataInputStream 		in = null;
    public static DataOutputStream 		out = null;
    public static Socket 				sock = null;
    
    public SocketServer(){
        this.connect_status = false;
    }
    
    @Override
    public void run(){
        try{
            listenSock = new ServerSocket(port);
            
            while(true){
                sock = listenSock.accept();
                in = new DataInputStream(sock.getInputStream());
                out = new DataOutputStream(sock.getOutputStream());
                this.connect_status = true;
                
                try{
                    if(in.readByte() == 1){
                        int random_code = new SecureRandom().nextInt();
                        
                        out.writeInt(random_code);
                        boolean success = UtilSocket.readString(in, false).equals(UtilSocket.hash(random_code + SocketConfig.password));
                        
                        if(success){
                            out.writeInt(1);
                            UtilSocket.createLog(SocketConfig.succesfullyLogin);
                        }else{
                            out.writeInt(0);
                            UtilSocket.createLog(SocketConfig.wrongPassword);
                            this.connect_status = false;
                        }
                    }else{
                        UtilSocket.createLog(SocketConfig.wrongData);
                        this.connect_status = false;
                    }
                    
                    while(this.connect_status){
                        final byte packetNumber = in.readByte();
                        
                        if(packetNumber == 2){  // SEND_COMMAND
                            String command = UtilSocket.readString(in, true);
                            
                            EventOutput event = UtilSocket.getManager().callOnCommandEvent(sock, command);
                            if(event.isCancelled()){
                                out.writeInt(0);
                                out.flush();
                                
                                continue;
                            }
                            
                            UtilSocket.sendCommand(event.getMessage(), out);                            
                        }else if(packetNumber == 3){ // CLOSE_CHANNEL
                            this.connect_status = false;
                        }else if(packetNumber == 4){ // SEND_MESSAGE
                            String message = UtilSocket.readString(in, true);
                            EventOutput event = UtilSocket.getManager().callOnMessageEvent(sock, message);
                            
                            out.writeInt(event.isCancelled() ? 0 : 1);
                            out.flush();
                        }else{
                            UtilSocket.createLog("Packet not found!");
                        }
                    }
                    
                    out.flush();
                    out.close();
                    in.close();
                }catch(IOException ex){
                    UtilSocket.createLog(ex.getMessage());
                    this.connect_status = false;
                }
                
                sock.close();
            }
        }catch(IOException ex){
            UtilSocket.createLog(ex.getMessage());
        }
    }
    
}
