package bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.microedition.io.StreamConnection;

import event.EventPerformed;
import filter.Kalman;
import filter.Matrices;

public class Session implements Runnable{
	private StreamConnection channel = null;  
    private InputStream btIn = null;  
    private OutputStream btOut = null;
    private Kalman filter;
    private Matrices mat;
    
    public Session(StreamConnection channel, Kalman filter, Matrices mat) throws IOException {  
        this.channel = channel;  
        this.btIn = channel.openInputStream();  
        this.btOut = channel.openOutputStream();  
        this.filter = filter;
    }
    
    public void run() {
        try {  
        	EventPerformed event = new EventPerformed();
        	
            byte[] buff = new byte[512];  
            int n = 0;  
            while ((n = btIn.read(buff)) > 0) {  
                String data = new String(buff);  
                
        		if (data.equals("lclick")) {
        			event.left_clicked();
        		}
        		else if (data.equals("lpress")) {
        			event.pressed();
        		}
        		else if (data.equals("lrelease")) {
        			event.released();
        		}
        		else if (data.equals("rclick")) {
        			event.right_clicked();
        		}
        		else if (data.equals("double")) {
        			event.left_doubleClicked();
        		}
        		else if (data.equals("upwheel")) {
        			event.upWheeled();
        		}
        		else if (data.equals("downwheel")) {
        			event.downWheeled();
        		}
        		else if (data.equals("mcrestart")) {
        			event.startCraeteMacro();
        		}
        		if (data.equals("mcrestop")) {
        			event.stopCraeteMacro();
        		}
        		else if (data.equals("mdelete")) {
        			event.deleteMacro();
        		}
        		else if (data.equals("mrunstart")) {
        			event.runMacro();
        		}
        		else if (data.equals("mrunstop")) {
        			event.stopMacro();
        		} 
        		/*< JS events  */
        		else if (data.equals("calidata")){
        			String[] calData = data.split(",");
        			double[] sdNmean = new double[4];
        			for(int i=0;i<4;i++) sdNmean[i] = Double.valueOf(calData[i]);
        			filter = new Kalman(mat, 5, sdNmean, 0.1);
        		}
        		/*  JS events >*/
        		else { // 필터로 넘기기 진섭(data);
        			if(filter==null){
        				String[] accData = data.split(",");
        				filter.prediction(accData[0], accData[1]);
        				filter.correction();
        				event.movePointer((int)filter.getDispX(), (int)filter.getDispY());
        			} else{ log("we have no calibrated data yet"); }
        		}
                
                log("Receive:"+data);  
                btOut.write(data.toUpperCase().getBytes());  
                btOut.flush();  
            }  
        } catch (Throwable t) {  
            t.printStackTrace();  
        } finally {  
            close();
        }  
    }
    
    public void close() {  
        log("Session Close");  
        if (btIn    != null) try {btIn.close();} catch (Exception e) {/*ignore*/}  
        if (btOut   != null) try {btOut.close();} catch (Exception e) {/*ignore*/}  
        if (channel != null) try {channel.close();} catch (Exception e) {/*ignore*/}  
    }
    
    private static void log(String msg) {  
        System.out.println("["+(new Date()) + "] " + msg);  
    }
}
