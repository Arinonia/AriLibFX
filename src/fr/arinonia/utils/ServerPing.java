package fr.arinonia.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerPing {

    private boolean online;
    private String host = "";
    private int port = 0;
    private Socket socket = new Socket();
    private String[] data = new String[999];

    /**
     * 
     * @return isOnline
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * 
     * @param online
     */
    private void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * 
     * @param host
     * @param port
     */
    public ServerPing(String h, int p) {
        host = h;
        port = p;
        try {
            setOnline(true);
            socket.connect(new InetSocketAddress(h, p));
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            out.write(0xFE);
            int b;
            StringBuffer str = new StringBuffer();
            while((b = in.read()) != -1) {
                if(b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                    str.append((char)b);
                }
            }
            data = str.toString().split("§");
            data[0] = data[0].substring(1, data[0].length());
        } catch (Exception e) {
            e.printStackTrace();
            setOnline(false);
        }
    }

    /**
     * 
     * @return motd
     */
    public String getMotd(){
        return data[0];
    }

    /**
     * 
     * @return player online
     */
    public int getOnline(){
        return Integer.parseInt(data[1]);
    }

    /**
     * 
     * @return max players
     */
    public int getMax(){
        return Integer.parseInt(data[2]);
    }

    /**
     *  refresh informations
     */
    public void update(){
        try {

            socket.close();
            socket = new Socket();
            socket.connect(new InetSocketAddress(host,port));
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            out.write(0xFE);
            setOnline(true);
            int b;
            StringBuffer str = new StringBuffer();
            while((b = in.read()) != -1){
                if(b != 0 && b > 16 && b != 255 && b != 23 && b != 24){
                    str.append((char)b);
                }
            }

            data = str.toString().split("§");
            data[0] = data[0].substring(1,data[0].length());
        } catch (Exception e) {
            e.printStackTrace();
            setOnline(false);
        }
    }

}
