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
        this.mat = mat;
    }
    
    public void run() {
    	try {  
        	EventPerformed event = new EventPerformed();
        	
            byte[] buff = new byte[512];  
            int n = 0;  
            while ((n = btIn.read(buff)) > 0) {  
                String data = new String(buff,0,n);  
                String[] msgSplit = data.split(",");
                if(msgSplit.length == 1){
	        		if (msgSplit[0].equals("lclick")) {
	        			event.left_clicked();
	        		}
	        		else if (msgSplit[0].equals("lpress")) {
	        			event.pressed();
	        		}
	        		else if (msgSplit[0].equals("lrelease")) {
	        			event.released();
	        		}
	        		else if (msgSplit[0].equals("rclick")) {
	        			event.right_clicked();
	        		}
	        		else if (msgSplit[0].equals("double")) {
	        			event.left_doubleClicked();
	        		}
	        		else if (msgSplit[0].equals("upwheel")) {
	        			event.upWheeled();
	        		}
	        		else if (msgSplit[0].equals("downwheel")) {
	        			event.downWheeled();
	        		}
	        		else if (msgSplit[0].equals("mcrestart")) {
	        			event.startCraeteMacro();
	        		}
	        		if (msgSplit[0].equals("mcrestop")) {
	        			event.stopCraeteMacro();
	        		}
	        		else if (msgSplit[0].equals("mdelete")) {
	        			event.deleteMacro();
	        		}
	        		else if (msgSplit[0].equals("mrunstart")) {
	        			event.runMacro();
	        		}
	        		else if (msgSplit[0].equals("mrunstop")) {
	        			event.stopMacro();
	        		} else { log("unexpected event received"); }
                } else if(msgSplit.length==5){
	        		/*< JS events  */
	        		if (msgSplit[0].equals("calidata")){
	        			double[] sdNmean = new double[4];
	        			for(int i=0;i<4;i++) sdNmean[i] = Double.valueOf(msgSplit[i+1]);
	        			filter = new Kalman(mat, 5, sdNmean, 0.1);
	        		}
	        		/*  JS events >*/
                } else { // �븘�꽣濡� �꽆湲곌린 吏꾩꽠(data);
        			if(filter!=null){
        				filter.prediction(msgSplit[1], msgSplit[2]);
        				filter.correction();
        				int weight=100;
        				log((int)(filter.getDispX()*weight)+" "+(int)(filter.getDispY()*weight));
        				event.movePointer((int)(filter.getDispX()*weight), (int)(filter.getDispY()*weight));
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
