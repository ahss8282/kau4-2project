package bluetooth;

import java.io.IOException;
import java.util.Date;

import javax.bluetooth.LocalDevice;
import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import bluetooth.Session;
import filter.Kalman;
import filter.Matrices;

public class RfCommServer {
	//Standard SerialPortService ID  
    private static final String serverUUID = "0000110100001000800000805f9b34fb";  
    private StreamConnectionNotifier server = null;
    private Kalman filter;
    private Matrices mat;
    
    public RfCommServer() throws IOException {  
    	  
        server = (StreamConnectionNotifier) Connector.open(  
                "btspp://localhost:" + serverUUID,  
                Connector.READ_WRITE, true  
        );  
          
        ServiceRecord record = LocalDevice.getLocalDevice().getRecord(server);  
        LocalDevice.getLocalDevice().updateRecord(record);  
        filter = null;
        mat = new Matrices(1, 24, 0.0625);
    }
    
    public Session accept() throws IOException {  
        log("Accept");  
        StreamConnection channel = server.acceptAndOpen();  
        log("Connected");  
        return new Session(channel, filter, mat);  
    }  
    public void dispose() {  
        log("Dispose");  
        if (server  != null) try {server.close();} catch (Exception e) {/*ignore*/}  
    }  
    private static void log(String msg) {  
        System.out.println("["+(new Date()) + "] " + msg);  
    }
}
