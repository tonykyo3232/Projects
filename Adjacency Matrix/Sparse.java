//-----------------------------------------------------------------------------
//Sparse.java
//Name: Tung-Lin Lee
//This file will test the Matrix ADT operation in Matix.java
//-----------------------------------------------------------------------------



import java.util.Scanner;
import java.io.*;

@SuppressWarnings("overrides")
public class Sparse {
	public static void main(String[] args) {
		
		// check the commend line of the args
		if (args.length < 2) {
			System.err.println("cannot access input and output file.");
			System.exit(1);
		}
		
		String line, info;
		String [] data;
		int mSize, indexA, indexB;
		int row,col;
		double value;
		try {
			// reading files and store into a string array
			File inFile = new File(args[0]);
			Scanner sc = new Scanner(inFile);
			info = sc.nextLine();           // read the first line of the text file
			sc.nextLine();                  // skip a line
			data = info.split(" ");         // getting each element from the line
			
			// obtaining the value of the first line in the input file
			mSize = Integer.parseInt(data[0]);         // get the size of the matrix
			indexA = Integer.parseInt(data[1]);        // get the non-zero term in Matrix A
			indexB = Integer.parseInt(data[2]);        // get the non-zero term in Matrix B
			
			// create the space in Matrix A, Matrix B
			Matrix A = new Matrix(mSize);
			Matrix B = new Matrix(mSize);
			
			// read the file and store value in Matrix A
			for(int i = 0; i < indexA; i++) {
				line = sc.nextLine();
				data = line.split(" ");
				row = Integer.parseInt(data[0]);
				col = Integer.parseInt(data[1]);
				value = Double.parseDouble(data[2]);
				A.changeEntry(row, col, value);
			}
			
			sc.nextLine(); // skip a line
			// read the file and store value in Matrix B
			for(int j = 0; j < indexB; j++) {
				line = sc.nextLine();
				data = line.split(" ");
				row = Integer.parseInt(data[0]);
				col = Integer.parseInt(data[1]);
				value = Double.parseDouble(data[2]);
				B.changeEntry(row, col, value);				
			}
			
			// writing the elements in List to output file
			File file = new File(args[1]);
			PrintWriter outFile = new PrintWriter(file);	
			
			// write the info of Matrix A to output file
			outFile.println("A has " + indexA + " non-zero entries:");
			outFile.println(A);
			outFile.println();
			
			outFile.println("B has " + indexB + " non-zero entries:");
			outFile.println(B.toString());
			outFile.println();
			
			outFile.println("(1.5) *A =");
			outFile.println(A.scalarMult(1.5));
			outFile.println();

			outFile.println("A+B =");
			outFile.println(A.add(B));
			outFile.println();
			
			outFile.println("A+A = ");
			outFile.println(A.add(A));
			outFile.println();
			
			outFile.println("B-A =");
			outFile.println(B.sub(A));
			outFile.println();
			
			outFile.println("A-A =");
			outFile.println(A.sub(A));
			outFile.println();

			outFile.println("Transpose (A) =");
			outFile.println(A.transpose());
			outFile.println();
			
			outFile.println("A*B =");
			outFile.println(A.mult(B));
			outFile.println();
			
			outFile.println("B*B");
			outFile.println(B.mult(B));
			outFile.println();
			
			outFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file name you enter does not find in the folder!");
			e.printStackTrace();
		}
	}
}
