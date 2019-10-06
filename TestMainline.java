package nQueens;

public class TestMainline {

	public static void main(String[] args) {
		
		System.out.println("Solution using Recursion");
		GameBoard Test5By5 = new GameBoard(5);
		//Test5By5.place(0, 1);
		Test5By5.findOneSolution(Test5By5, 0);
		System.out.println(Test5By5);
	}

}
