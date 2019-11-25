package Server;


public class ServerUDP
{
	private String host;
	private int port;
	
	public ServerUDP(String host, int port)
	{		
		this.host = host;
		this.port = port;
	}
	
	
	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}


}