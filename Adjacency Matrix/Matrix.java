//-----------------------------------------------------------------------------
//Matrix.java
//Name: Tung-Lin Lee
//ID: 1671967
//CruzId: tlee76
//HW: pa3
//This file will implement the Matrix ADT operation
//-----------------------------------------------------------------------------


@SuppressWarnings("overrides")
public class Matrix {
	
@SuppressWarnings("overrides")
private class Entry{
		int col;
		double data;
		
		// constructor
		Entry(){
			col = 0;
			data = 0.0;
		}
		@Override
		public boolean equals(Object x)
        {
			boolean status = false;
			Entry obj;
			if(x instanceof Entry) {
				obj = (Entry)x;
				status = (this.data == obj.data && this.col == obj.col);
			}
			return status;
        }
		
		@Override
		public String toString() {
			String retVal = "";
			retVal = "(" + col + ", " + data + ") ";
			retVal = retVal.trim(); // remove the white space in the end
			return retVal;
		}
	}

	//Matrix private attribute
	private List[] listAry;

	 /******************
	 Constructor
	 ******************/
	 // Makes a new n x n zero Matrix
	 // pre: n >=1
	public Matrix(int n){
		if( n < 1){
			System.err.println("Error in Matrix(): n < 1");
		    System.exit(0);
		}
		
		listAry = new List[n];
		
		// allocate the memory for each element of list array
		for(int i  = 0; i < n; i++) {
			listAry[i] = new List();
		}
	}
	
	/**********************
	 Access functions 
	**********************/
	
	// Returns n, the number of rows and columns of this Matrix
	public int getSize() {
		if(listAry == null){
            System.err.println("Error in getSize(): listAry == null");
            System.exit(0);
		 }
		 return listAry.length;
	}
	
	// Returns the number of non-zero entries in this Matrix
	public int getNNZ() {
		// check if the list array is null
		if(listAry == null){
            System.err.println("Error in getNNZ(): listAry == null");
            System.exit(0);
		 }
		 
		 int numOfNNZ = 0;
		 
		 // get the number of non-zero element in the Matrix
		 for(int i = 0; i < listAry.length; i++){
			 if(listAry[i].length() > 0){
				 numOfNNZ += listAry[i].length();
			 }
		 }
		 return numOfNNZ;
	}
	
	// overrides Object's equals() method
	@Override
	public boolean equals(Object x) {
		boolean status = false;
		
		// if two matrices have difference size, return false
		if(this.getSize() != ((Matrix)x).getSize()) {
			return false;
		}
		else {
			for(int i = 0; i < getSize(); i++) {
				// if the list of in the array correspond to another list array that are comparing
				// maintain true
				if(listAry[i].equals(((Matrix)x).listAry[i])){ 
					 status = true;
				}
				// if any one of the list that is not the same when comparing
				// set the status to false and stop comparing immediately
				else{
					status = false;
					break;
				}
			}
		}
		 return status;
	}
	
	// sets this Matrix to the zero state 
	public void makeZero() {
		if(listAry == null){
            System.err.println("Error in makeZero(): listAry == null");
            System.exit(0);
		 }
		 
		// make every element in the listAry into null in the list
		 for(int x = 0; x < listAry.length; x++){
			listAry[x].clear();
		 }
	}
	
	// returns a new Matrix having the same entries as this Matrix 
	public Matrix copy() {
		if(listAry == null){
            System.err.println("Error in copy(): listAry == null");
            System.exit(0);
		 }
		
		// allocate the memory for the copyMatrix so it can be able to store list object  
		Matrix copyMatrix = new Matrix(this.listAry.length);
		 
		 for(int i = 0; i < getSize() ; i++){ 
		     
			// allocate the memory for the list in copyMatrix so it can be able to store value 
			 copyMatrix.listAry[i] = new List();
			 
			 // move the cursor to the front
			 listAry[i].moveFront(); 
			 
			 while(listAry[i].index() != -1){
				 copyMatrix.listAry[i].append(listAry[i].get());
				 listAry[i].moveNext(); 
			 }
		 }
		 return copyMatrix;
	}
	
	// changes ith row, jth column of this Matrix to x
	// pre: 1<=i<=getSize(), 1<=j<=getSize()
	public void changeEntry(int i, int j, double x) {
		if(listAry == null){
            System.err.println("Error in changeEnty(): listAry == null");
            System.exit(0);
		 }
		 
		// if the change column and row are greater than the size of the Matrix or smaller than 1
		// print error
		if((i < 1 && i > getSize()) || (j < 1 && j > getSize())){
            System.err.println("Error in changeEntry(): invaild size");
            System.exit(0);
		 }
		 
		int entryColumn;
		int row  = i-1; // always remember in coding, the index of row equals to the row in array 
		boolean foundCol = false;
		// move the cursor to front
		listAry[row].moveFront();
				
		if(listAry[row].index() != -1) 
		{
			while(listAry[row].index() != -1) 
			{
				// if the column is found, stop the while loop
				if(((Entry)listAry[row].get()).col == j) {
					foundCol = true;
					break;
				}
				listAry[row].moveNext();
			}
		}
		
		// case 1: if both the insert value and Matrix's value are 0
		if(x == 0.0 &&  foundCol == false/*listAry[row].index() == -1*/){
			 // do nothing
		}
		// case 2: if the insert value is 0 but the Matrix's value is not 0
		else if(x == 0 && foundCol == true && ((Entry)listAry[row].get()).data != 0) {
			listAry[row].delete();
		}
		// case 3: if the insert value is not zero but the Matrix's value is 0, append a new element
		else if(x != 0 && foundCol == false) {
			
			// store the data into the Entry
			Entry newEntry = new Entry();
			newEntry.col = j;
			newEntry.data = x;
			
			// set up the cursor
			listAry[row].moveFront();
			while(listAry[row].index() != -1) {
				
				// find the correct index of Matrix to insert
				if(((Entry)listAry[row].get()).col > newEntry.col)
				{
					listAry[row].insertBefore(newEntry);
					break;
				}
				listAry[row].moveNext();
			}
			
			// if the cursor finish traversing the list array (means that haven't insert element yet)
			// then mean it should be inserted in the end of the list
			if(listAry[row].index() == -1) {
				listAry[row].append(newEntry);
			}
		}
		// case 4: if the insert value and the Matrix value are not zero 
		else if(x != 0 && listAry[row].index() != -1) {
			((Entry)listAry[row].get()).data = x;
		}
		else {
			 // do nothing
		}
	}
	
	// returns a new Matrix that is the scalar product of this Matrix with x
	public Matrix scalarMult(double x) {
		
		// copy the original matrix to the new Matrix
		Matrix retM = new Matrix(getSize());
		for(int j = 0; j < getSize(); j++) {
			listAry[j].moveFront();
			while(listAry[j].index() != -1) {
				Entry newEntry = new Entry();
				newEntry.col = ((Entry)listAry[j].get()).col;
				newEntry.data = ((Entry)listAry[j].get()).data;
				retM.listAry[j].append(newEntry);
				listAry[j].moveNext();
			}
		}
		
		 for(int i = 0; i < retM.getSize(); i++){
			 
			 // move the cursor to the front
			 retM.listAry[i].moveFront();
			 while(retM.listAry[i].index() != -1){
				 ((Entry)retM.listAry[i].get()).data  *= x; // multiplying the x 
				 retM.listAry[i].moveNext();
			 }
		 }
		 return retM;
	}
	
	// returns a new Matrix that is the sum of this Matrix with M
	// pre: getSize()==M.getSize()
	public Matrix add(Matrix M) {
		// if the size of the matrices does not match, print error
		if(this.getSize() != M.getSize()){
            System.err.println("Error in add(): invaild size");
            System.exit(0);
		 }
		
		// create a n by n empty matrix to store the result
		Matrix retM = new Matrix(getSize());
		
		Entry newEntry;
		boolean done = false;
		
		// case 1: if both Matrix are empty
		if(getNNZ() == 0 && M.getNNZ() == 0) {
			// do nothing
		}
		// case 2: if the 1st matrix has all 0 terms but the 2nd Matrix don't
		else if(getNNZ() == 0 && M.getNNZ() != 0) {
			retM = M.copy();
		}
		// case 3: if the 2nd matrix has all 0 terms but the 1nd Matrix don't
		else if(getNNZ() != 0 && M.getNNZ() == 0) {
			retM = this.copy();
		}
		// if the two Matrix are same, time itself by 2
		else if(this.equals(M)) {
			Matrix retM2 = M.scalarMult(2.0);
			retM = retM2;
		}
		// case 4: if any position of the matrices have 0 term
		else{
			for(int i = 0; i < getSize(); i++) {
				
				// move the cursor to the front
				listAry[i].moveFront();
				M.listAry[i].moveFront();
				done = false;
				
				while( (listAry[i].index() != -1 || M.listAry[i].index() != -1) && done == false) {
										
					newEntry = new Entry();

					//if the row in both Matrices are 0 
					if(listAry[i].index() == -1 && M.listAry[i].index() == -1) {
						done = true;
					}
					// if the row of 1st matrix are not 0, but 2nd are 0 
					// store the 1st one
					else if (listAry[i].index() != -1 && M.listAry[i].index() == -1) {
						
						// store the value to the corresponding position
						newEntry.col = ((Entry)listAry[i].get()).col;
						newEntry.data = ((Entry)listAry[i].get()).data;						
						retM.listAry[i].append(newEntry);
						
						// update the cursor
						listAry[i].moveNext();
					}
					// if the row of 2st matrix are not 0, but 1nd are 0 
					// store the 2nd one
					else if (listAry[i].index() == -1 && M.listAry[i].index() != -1) {
						
						// store the value to the corresponding position
						newEntry.col = ((Entry)M.listAry[i].get()).col;
						newEntry.data = ((Entry)M.listAry[i].get()).data;						
						retM.listAry[i].append(newEntry);
						
						// update the cursor
						M.listAry[i].moveNext();
					}
					// if the 1st Matrix's column is less than the 2nd one, means that the 2nd Matrix's position is 0
					// store only 1st Matrix value
					else if(((Entry)listAry[i].get()).col < ((Entry)M.listAry[i].get()).col) {
						
						// store the value to the corresponding position
						newEntry.col = ((Entry)listAry[i].get()).col;
						newEntry.data = ((Entry)listAry[i].get()).data;						
						retM.listAry[i].append(newEntry);
						
						// update the cursor
						listAry[i].moveNext();
					}
					// if the 1st Matrix's column is greater than the 2nd one, means that the 1nd Matrix's position is 0
					// so store only 2nd Matrix value
					else if(((Entry)listAry[i].get()).col > ((Entry)M.listAry[i].get()).col) {
						
						// store the value to the corresponding position
						newEntry.col = ((Entry)M.listAry[i].get()).col;
						newEntry.data = ((Entry)M.listAry[i].get()).data;						
						retM.listAry[i].append(newEntry);
						
						// update the cursor
						M.listAry[i].moveNext();
					}
					// if the column of both matrix match
					else if(((Entry)listAry[i].get()).col == ((Entry)M.listAry[i].get()).col) {
						newEntry.col = ((Entry)listAry[i].get()).col;
						newEntry.data = ((Entry)listAry[i].get()).data + ((Entry)M.listAry[i].get()).data;						
						
						// if the sum is 0, then don't store into the Matrix
						if(newEntry.data != 0) {
							retM.listAry[i].append(newEntry);
						}						
						// update the cursor
						listAry[i].moveNext();
						M.listAry[i].moveNext();
					}
				}
			}
		}
		return retM;
	}
	
	// returns a new Matrix that is the difference of this Matrix with M
	// pre: getSize()==M.getSize()
	public Matrix sub(Matrix M) {
		// if the size of the matrices does not match, print error
		if(this.getSize() != M.getSize()){
            System.err.println("Error in sub(): invaild size");
            System.exit(0);
		 }
				 
		// create a n by n empty matrix to store the result
		Matrix retM = new Matrix(getSize());
		
		Entry newEntry;
		boolean done = false;
		
		// case 1: if both Matrix are empty
		if(getNNZ() == 0 && M.getNNZ() == 0) {
			// do nothing
		}
		// case 2: if the 1st matrix has all 0 terms but the 2nd Matrix don't
		else if(getNNZ() == 0 && M.getNNZ() != 0) {
			retM = M.scalarMult(-1);
		}
		// case 3: if the 2nd matrix has all 0 terms but the 1nd Matrix don't
		else if(getNNZ() != 0 && M.getNNZ() == 0) {
			retM = this.copy();
		}
		// case 4: if any position of the matrices have 0 term
		else{
			for(int i = 0; i < getSize(); i++) {
				
				// move the cursor to the front
 				listAry[i].moveFront();
				M.listAry[i].moveFront();
				done = false;
				
				while(( listAry[i].index() != -1 || M.listAry[i].index() != -1) && done == false) {
										
					newEntry = new Entry();

					//if the row in both Matrices are 0 
					if(listAry[i].index() == -1 && M.listAry[i].index() == -1) {
						done = true;
					}
					// if the row of 1st matrix are not 0, but 2nd are 0 
					// store the 1st one
					else if (listAry[i].index() != -1 && M.listAry[i].index() == -1) {
						
						// store the value to the corresponding position
						newEntry.col = ((Entry)listAry[i].get()).col;
						newEntry.data = ((Entry)listAry[i].get()).data;						
						retM.listAry[i].append(newEntry);
						
						// update the cursor
						listAry[i].moveNext();
					}
					// if the row of 2nd matrix are not 0, but 1nd are 0 
					// store the 2nd one with NEGATIVE value
					else if (listAry[i].index() == -1 && M.listAry[i].index() != -1) {
						
						// store the value to the corresponding position
						newEntry.col = ((Entry)M.listAry[i].get()).col;
						newEntry.data = ((Entry)M.listAry[i].get()).data * -1;						
						retM.listAry[i].append(newEntry);
						
						// update the cursor
						M.listAry[i].moveNext();
					}
					// if the 1st Matrix's column is less than the 2nd one, means that the 2nd Matrix's position is 0
					// store only 1st Matrix value
					else if(((Entry)listAry[i].get()).col < ((Entry)M.listAry[i].get()).col) {
						
						// store the value to the corresponding position
						newEntry.col = ((Entry)listAry[i].get()).col;
						newEntry.data = ((Entry)listAry[i].get()).data;						
						retM.listAry[i].append(newEntry);
						
						// update the cursor
						listAry[i].moveNext();
					}
					// if the 1st Matrix's column is greater than the 2nd one, means that the 1nd Matrix's position is 0
					// so store only 2nd Matrix value with NEGATIVE value
					else if(((Entry)listAry[i].get()).col > ((Entry)M.listAry[i].get()).col) {
						
						// store the value to the corresponding position
						newEntry.col = ((Entry)M.listAry[i].get()).col;
						newEntry.data = ((Entry)M.listAry[i].get()).data * -1;
						retM.listAry[i].append(newEntry);
						
						// update the cursor
						M.listAry[i].moveNext();
					}
					// if the column of both matrix match
					else if(((Entry)listAry[i].get()).col == ((Entry)M.listAry[i].get()).col) {
						newEntry.col = ((Entry)listAry[i].get()).col;
						newEntry.data = ((Entry)listAry[i].get()).data - ((Entry)M.listAry[i].get()).data;
						
						// if after the subtraction is 0, then refuse to append it into the list 
						if(newEntry.data != 0) {
							retM.listAry[i].append(newEntry);
						}
						
						// update the cursor
						listAry[i].moveNext();
						M.listAry[i].moveNext();
					}
				}
			}
		}
		return retM;
	}
	
	// returns a new Matrix that is the transpose of this Matrix
	public Matrix transpose() {
		Matrix MT = new Matrix(getSize());
		
		int indexA, indexB = 0;
		//double val = 0.0;
		
		// case 1: if the Matrix is all 0
		if(getNNZ() == 0) {
			// do nothing
		}
		// case 2: if the Matrix is not all 0
		else {
			for(indexA = 0; indexA < getSize(); indexA++) {
				// move the cursor to the front
				listAry[indexA].moveFront();
				while(listAry[indexA].index() != -1) {
					
					// store into value into the transpose matrix
		    		Entry newEntry = new Entry();
					newEntry.col = indexA+1;
					newEntry.data = ((Entry)listAry[indexA].get()).data;
					indexB = ((Entry)listAry[indexA].get()).col-1;
					if(MT.listAry[indexB] == null) {
						MT.listAry[indexB] = new List();
					}
					MT.listAry[indexB].append(newEntry);
					
					// update the iterator
					listAry[indexA].moveNext();
				}
			}
		}
		return MT;
	}
	
	// returns a new Matrix that is the product of this Matrix with M
	// pre: getSize() == M.getSize()
	public Matrix mult(Matrix M) {
		if(this.getSize() != M.getSize()){
            System.err.println("Error in mult(): invalid size");
            System.exit(0);
		 }
		 
		 Matrix retM = new Matrix(M.getSize());
		 double product = 0.0;
		 
		 // make a transpose of the Matrix M
		 Matrix MT = M.transpose();
		 
		 // case 1: if the matrix is empty
		 if(getNNZ() == 0 || M.getNNZ()== 0) {
			 product = 0.0;
		 }
		 // case 2: any types of matrix except empty matrix
		 else{
			 
			 for(int indexA = 0; indexA < getSize(); indexA++) {
					
				for(int indexB = 0; indexB < getSize(); indexB++) {
					
					// move the cursor to the front
					this.listAry[indexA].moveFront();
					MT.listAry[indexB].moveFront();
					
					while(this.listAry[indexA].index() != -1 && MT.listAry[indexB].index() != -1) {
						
						if( ((Entry)this.listAry[indexA].get()).col < ((Entry)MT.listAry[indexB].get()).col )
						{
							listAry[indexA].moveNext();
						}
						else if( ((Entry)this.listAry[indexA].get()).col > ((Entry)MT.listAry[indexB].get()).col )
						{
							MT.listAry[indexB].moveNext();
						}
						else
						{
							product += ((Entry)listAry[indexA].get()).data * ((Entry)MT.listAry[indexB].get()).data;
							this.listAry[indexA].moveNext();
							MT.listAry[indexB].moveNext();
						}
					}
					
					if(this.listAry[indexA].length() != 0 && MT.listAry[indexB].length() != 0) {
						// store the data into the Matrix
						Entry newEntry = new Entry();
						newEntry.col = indexB + 1;
						newEntry.data = product;
						if(newEntry.data != 0.0) {
							retM.listAry[indexA].append(newEntry);
						}
						// initialize the product 
						product = 0.0;
					}
				} // end 1st for loop
						
			} // end 2nd for loop
				 
		 }
		 return retM;
	}
	
	/******************************
	Other functions 
	*******************************/
	public String toString() {
		String retVal = "";
		for(int i = 0; i < listAry.length; i++) {
			if((listAry[i].toString()).equals("") == false) {
				retVal += i+1 + ": " + listAry[i].toString() + "\r\n";
			}
		}
		return retVal;
	}	
}
