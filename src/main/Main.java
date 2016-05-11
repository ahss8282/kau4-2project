package main;

import bluetooth.RfCommServer;
import bluetooth.Session;
import filter.Kalman;

public class Main {
	public static void main(String args[]) throws Exception {
		RfCommServer server = new RfCommServer();
		while (true) {  
            Session session = server.accept();  
            new Thread(session).start();  
        }
	}
}
