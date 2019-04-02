//-----------------------------------------------------------------------------
//ListTest.java
//Name: Tung-Lin Lee
//ID: 1671967
//CruzId: tlee76
//HW: pa3
//This file will test the methods in List.java
//-----------------------------------------------------------------------------


public class ListTest {
	
	public static void main(String [] args) {
		List A = new List();
		List B = new List();
		
		System.out.println("Test#1: Inserting the element:");
		A.append(1);
		A.append(2);
		A.append(3);
		A.append(4);
		A.append(5);
		
		B.append(1);
		B.append(3);
		B.append(5);
		B.moveFront();
		B.moveNext();
		B.insertBefore(2);
		B.insertAfter(4);
		
		if(A.equals(B)) {
			System.out.println("- append, insertbefore, insertAfter, moveFront, equal work properly");
		}
		System.out.println("-----------------------------");
		
		System.out.println("Test#2: Funcionaility of cursor:");
		A.clear();
		B.clear();
		if(A.equals(B)) {
			System.out.println("- clear works properly");
		}
			
		A.append(1);
		A.append(2);
		A.append(3);
		A.append(4);
		A.append(5);
		
		B.prepend(5);
		B.prepend(4);
		B.prepend(3);
		B.prepend(2);
		B.prepend(1);
		
		if(A.equals(B)) {
			System.out.println("- prepend,equal work properly");
		}
		
		A.moveFront();
		B.moveBack();
		
		A.moveNext();
		A.moveNext();
		B.movePrev();
		B.movePrev();
		
		if(A.get() == B.get()) {
			System.out.println("- moveFront, moveBack, moveNext, movePrev, get, equal work properly");
		}
		
		System.out.println("-----------------------------");

		System.out.println("Test#3: deleting the elements");
		A.clear();
		B.clear();
		
		A.append(1);
		A.append(2);
		A.append(3);
		A.append(4);
		
		B.append(2);
		B.append(3);
		
		A.deleteFront();
		A.deleteBack();
		
		if(A.equals(B)) {
			System.out.println("- deleteFront, deleteBack work properly");
		}
		
		A.moveBack();
		System.out.println(((int)A.get()));
		while(A.index() != -1) {
			A.movePrev();
			if(((int)A.get()) == 2){
				System.out.println("- index work properly");
				break;
			}
		}
		
		A.delete();
		A.moveBack();
		if( ((int)A.get()) == 3){
			System.out.println("- delete work properly");
		}		
		System.out.println("-----------------------------");

	}
}
