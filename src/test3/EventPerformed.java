package test3;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;


public class EventPerformed {

	 int macroSetOn; // 1이면 매크로 세팅모드
	 int currentMacroP;  // 매크로에 세팅된 좌료를 저장하는 배열의 인덱스
	 Point[] macroPoint = new Point[50]; // 매크로 좌표 저장 배열
	 Thread thread;
	
	public EventPerformed() {
		macroSetOn = 0; // 1이면 매크로 세팅모드
		currentMacroP = 0;
		for (int i = 0; i < 50; i++) {
			macroPoint[i] = new Point();
		}
	}

	
	// 마우스 커서를 지정 포인트로 이동시킴
	public void movePointer(int moveX, int moveY) {
		Point po = new Point();
		PointerInfo p = MouseInfo.getPointerInfo();

		try {
			Robot ro = new Robot();
			po = p.getLocation(); // 현재 위치를 추출
			ro.mouseMove((int) po.getX() + moveX, (int) po.getY() + moveY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// InputEvent.BUTTON1_MASK : 좌측 버튼
	// InputEvent.BUTTON1_MASK : 우측 버튼
	// 클릭
	public void left_clicked() {
		try {
			if (macroSetOn == 1) {
				PointerInfo p = MouseInfo.getPointerInfo();
				Point po = p.getLocation(); // 현재 위치를 추출
				macroPoint[currentMacroP].x = (int) po.getX();
				macroPoint[currentMacroP].y = (int) po.getY();
				currentMacroP++;
			}
			Robot ro = new Robot();
			ro.mousePress(InputEvent.BUTTON1_MASK);
			ro.mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 왼쪽버튼 프레스
	public void pressed() {
		try {
			Robot ro = new Robot();
			ro.mousePress(InputEvent.BUTTON1_MASK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 왼쪽버튼 릴리즈
	public void released() {
		try {
			Robot ro = new Robot();
			ro.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 오른쪽 버튼 클릭
	public void right_clicked() {
		try {
			Robot ro = new Robot();
			ro.mousePress(InputEvent.BUTTON3_MASK);
			ro.mouseRelease(InputEvent.BUTTON3_MASK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void left_doubleClicked() {
		try {
			Robot ro = new Robot();
			ro.mousePress(InputEvent.BUTTON1_MASK);
			ro.mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(50);
			ro.mousePress(InputEvent.BUTTON1_MASK);
			ro.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 음수는 휠을 윗방향으로 돌리는, 양수는 아래방향으로 돌리는
	// 숫자는 휠의 한칸을 움직이는 단위
	// 윗방향으로 휠 한칸
	public void upWheeled() {
		try {
			Robot ro = new Robot();
			ro.mouseWheel(-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 아랫방향으로 휠 한칸
	public void downWheeled() {
		try {
			Robot ro = new Robot();
			ro.mouseWheel(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startCraeteMacro() {
		macroSetOn = 1;
	}

	public void stopCraeteMacro() {
		macroSetOn = 0;
	}

	public void deleteMacro() {
		macroSetOn = 0;
		currentMacroP = 0;
	}

	public void runMacro() {
		Macro macro = new Macro(currentMacroP,macroPoint);
		thread = new Thread(macro);
		thread.start();
	}

	public void stopMacro() {
		thread.interrupt();
	}


}