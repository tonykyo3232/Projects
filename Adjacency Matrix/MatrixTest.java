//-----------------------------------------------------------------------------
//MatrixTest.java
//Name: Tung-Lin Lee
//This file will test the method in Matrix.java
//-----------------------------------------------------------------------------


public class MatrixTest {
	public static void main(String []args) {
		Matrix A = new Matrix(3);
		Matrix B = new Matrix(3);
		Matrix C = new Matrix(3);
		
		int row = 1; 
		int col = 1;
		
		System.out.println("Test#1: changeEntry functionality");
		// Test#1: insert different value and check it changeEntry and equal method work properly
		for(int i = 0; i < 3; i++) {
			A.changeEntry(row, col, 1);
			row++;
			col++;
		}
		
		row = 1;
		col = 1;
		for(int i = 0; i < 3;i++){
			B.changeEntry(row, col, 2);
			row++;
			col++;
		}
		
		if(!A.equals(B)) {
			System.out.println("A is different than B");
		}
		
		System.out.println("-------------------------------");

		// Test#2: resetting Matrix A and see if makeZero and equal method work properly
		System.out.println("Test#2: makeZero");
		A.makeZero();
		
		if(A.equals(C)) {
			System.out.println("A is equal to C");
		}
		System.out.println("-------------------------------");
		
		//Test#3: test if add, sub and scalarMult method work properly
		System.out.println("Test#3: add scalarmult");
		A = new Matrix(3);
		B = new Matrix(3);
		C = new Matrix(3);
		
		row = 1;
		col = 1;
		for(int i = 0; i < 3; i++) {
			A.changeEntry(row, col, 1);
			row++;
			col++;
		}
		
		C = A.add(A);
		B = A.scalarMult(2);
		
		if(B.equals(C)) {
			System.out.println("B is equal to C");
		}
		System.out.println("-------------------------------");
		
		// Test#4: test if transpose, mult and toString method work properly
		System.out.println("Test#4: transpose");
		A = new Matrix(3);
		B = new Matrix(3);
		A.changeEntry(1, 2, 3);
		A.changeEntry(1, 1, 5);
		A.changeEntry(1, 1, 3);
		A.changeEntry(1, 3, 1);
		A.changeEntry(1, 3, 0);
		System.out.print("Matrix A contaons: " + A.mult(A));
		
		A.makeZero();
		A.changeEntry(1, 1, 1);
		A.changeEntry(2, 2, 1);
		A.changeEntry(3, 3, 1);

		B.changeEntry(1, 1, 1);
		B.changeEntry(2, 2, 1);
		B.changeEntry(3, 3, 1);
		
		if(A.equals(B)) {
			System.out.println("A is equal to B");
		}
		System.out.println("-------------------------------");
	}
}
