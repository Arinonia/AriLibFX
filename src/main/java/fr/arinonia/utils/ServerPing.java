package fr.arinonia.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerPing
{
    private boolean online;
    private String host = "";
    private int port = 0;
    private Socket socket = new Socket();
    private String[] data = new String[999];

    public boolean isOnline() {
        return online;
    }

    private void setOnline(boolean online) {
        this.online = online;
    }
  
    public ServerPing(String h, int p)
    {
        this.host = h;
        this.port = p;

        try {
            this.setOnline(true);
            this.socket.connect(new InetSocketAddress(this.host, this.port));

            final OutputStream out = this.socket.getOutputStream();
            final InputStream in = this.socket.getInputStream();

            out.write(0xFE);

            int b;
            final StringBuffer str = new StringBuffer();

            while((b = in.read()) != -1)
            {
                if(b > 16 && b != 255 && b != 23 && b != 24)
                {
                    str.append((char)b);
                }
            }
            this.data = str.toString().split("§");
            this.data[0] = this.data[0].substring(1, this.data[0].length());
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setOnline(false);
        }
    }
    
    public String getMotd(){
        return this.data[0];
    }

    public int getOnline(){
        return Integer.parseInt(this.data[1]);
    }

    public int getMax(){
        return Integer.parseInt(this.data[2]);
    }

    public void update()
    {
        try {

            this.socket.close();
            this.socket = new Socket();
            this.socket.connect(new InetSocketAddress(this.host, this.port));

            final OutputStream out = this.socket.getOutputStream();
            final InputStream in = this.socket.getInputStream();

            out.write(0xFE);
            this.setOnline(true);

            int b;
            final StringBuffer str = new StringBuffer();

            while((b = in.read()) != -1)
            {
                if(b > 16 && b != 255 && b != 23 && b != 24)
                {
                    str.append((char)b);
                }
            }

            this.data = str.toString().split("§");
            this.data[0] = this.data[0].substring(1, this.data[0].length());
        } catch (Exception e)
        {
            e.printStackTrace();
            this.setOnline(false);
        }
    }
}
