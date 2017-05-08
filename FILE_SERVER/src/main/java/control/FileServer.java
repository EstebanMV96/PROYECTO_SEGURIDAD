package control;

public class FileServer {
	
	
	private Server servidor;

	public FileServer() {
		
		servidor=new Server();
		servidor.start();
		
	}

	public static void main(String[] args) {
		
		
		new FileServer();

	}

}
