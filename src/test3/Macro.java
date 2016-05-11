package test3;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Macro implements Runnable{
	
	 int currentMacroP;  // 매크로에 세팅된 좌료를 저장하는 배열의 인덱스
	 Point[] macroPoint = new Point[50]; // 매크로 좌표 저장 배열
	 
	public Macro(int currentP,Point[] macroP){
		currentMacroP = currentP;
		macroPoint = macroP;
	}
	
	public void run(){
		try{
			Robot ro = new Robot();	
			while(!Thread.currentThread().isInterrupted()){
						for (int i = 0; i < currentMacroP; i++) {
								ro.mouseMove(macroPoint[i].x, macroPoint[i].y); // 이동
								ro.mousePress(InputEvent.BUTTON1_MASK);
								ro.mouseRelease(InputEvent.BUTTON1_MASK);
								Thread.sleep(300);
						}
			}
		}catch(Exception e){
		 e.printStackTrace();  
		}
	}

}
