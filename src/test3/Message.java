package test3;

public class Message {

	String msg1 = "lclick"; // 왼쪽 클릭
	String msg2 = "lpress";// 왼쪽 프레스
	String msg3 = "lrelease";// 왼쪽 릴리즈
	String msg4 = "rclick";// 오른쪽 클릭
	String msg5 = "double";// 더블 클릭
	String msg6 = "upwheel";// 위로 휠 한칸
	String msg7 = "downwheel";// 아래로 휠 한칸
	String msg8 = "mcrestart";// 매크로 생성 시작
	String msg9 = "mcrestop";// 매크로 생성 중지
	String msg10 = "mdelete";// 매크로 삭제
	String msg11 = "mrunstart";// 매크로 run start
	String msg12 = "mrunstop";// 매크로 run 중단

	public void handle(String data) {
		EventPerformed event = new EventPerformed();

		if (data.equals(msg1)) {
			event.left_clicked();
		}
		if (data.equals(msg2)) {
			event.pressed();
		}
		if (data.equals(msg3)) {
			event.released();
		}
		if (data.equals(msg4)) {
			event.right_clicked();
		}
		if (data.equals(msg5)) {
			event.left_doubleClicked();
		}
		if (data.equals(msg6)) {
			event.upWheeled();
		}
		if (data.equals(msg7)) {
			event.downWheeled();
		}
		if (data.equals(msg8)) {
			event.startCraeteMacro();
		}
		if (data.equals(msg9)) {
			event.stopCraeteMacro();
		}
		if (data.equals(msg10)) {
			event.deleteMacro();
		}
		if (data.equals(msg11)) {
			event.runMacro();
		}
		if (data.equals(msg12)) {
			event.stopMacro();
		} else {
			// 필터로 넘기기
		}

	}

}
