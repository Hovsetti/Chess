package Controller;

public class Main {

	public static void main(String[] args) {
		Controller.FileIo fileIo = new Controller.FileIo(args[0]);
		fileIo.readFile();
	}
}
