package yehric2018.keyp;

public class Launcher {
	public static void main(String[] args) {
		Program program = new Program(510, 400);
		program.start();
		while (true) {
			program.update();
		}
	}
}
