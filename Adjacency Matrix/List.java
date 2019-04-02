//-----------------------------------------------------------------------------
//List.java
//Name: Tung-Lin Lee
//This file will implement the List ADT operation
//-----------------------------------------------------------------------------


@SuppressWarnings("overrides")
public class List {

// private attributes
private int length;
private int index; // for the index of cursor
private Node front;
private Node rear;
private Node cursor;

// Inner class. it will create the node to store and link each element in the
// List
@SuppressWarnings("overrides")
private class Node {
	Object item;
	Node next;
	Node prev;
	
	@Override
	public boolean equals(Object x){
		boolean status = true;
		Node N;
		if( x instanceof Node ) {
			N = (Node)x;
			status = this.item.equals(N.item);
		}
		return status;
    }
}

/******************
 * Constructor
 *******************/

/**
 * List() Creates a new empty list.
 */
public List() {
	this.length = 0;
	this.index = -1;
	this.front = null;
	this.rear = null;
	this.cursor = null;
}

/****************************************************************
 * Access functions
 *****************************************************************/

/**
 * int length() this method will return the number of the element in the List
 * Pre: None
 */
public int length() {
	return this.length;
}

/**
 * int index() If cursor is defined, returns the index of the cursor element,
 * otherwise returns -1 Pre: None
 */
public int index() {
	if (cursor != null) {
		Node walker = front;
		int count = 0;
		while (walker != cursor) {
			walker = walker.next;
			count++;
		}
		return count;
	} else {
		return -1;
	}
}

/**
 * int front() Returns front element. if the List is empty, it will return -1
 * Pre: length()>0
 */
public Object front() {
	if (length() > 0) {
		return front.item;
	} else {
		return -1;
	}
}

/**
 * int back() Returns back element. if the List is empty, it will return -1 Pre:
 * length()>0
 */
public Object back() {
	if (length() > 0) {
		return rear.item;
	} else {
		return -1;
	}
}

/**
 * int get() Returns cursor element. if the cursor is undefined, it will return
 * -1 Pre: length()>0, index()>=0
 */
public Object get() {
	if (length() > 0 && index() >= 0) {
		return cursor.item;
	} else {
		return -1;
	}
}

/**
 * boolean equals(List) Returns true if and only if this List and L are the same
 * integer sequence. The states of the cursors in the two Lists are not used in
 * determining equality.
 */
@Override
public boolean equals(Object x) {
	boolean status = true;

	// if the length of the both lists are not same, return false	
	if(this.length != ((List)x).length) {
		return false;
	}
	else {
		Node ptr1 = front;
		Node ptr2 = ((List)x).front;
		while(ptr1 != null) {
			if(ptr1.equals(ptr2)) {
				ptr1 = ptr1.next;
				ptr2 = ptr2.next;
			}
			else {
				return false;
			}
		}
		return status;
	}
}

/**************************************
 * Manipulation procedures
 **************************************/

/**
 * void clear() Resets this List to its original empty state.
 */
public void clear() {
	
	// delete entire list
	front = null;
	rear = null;
	
	// initialize the attribute
	index = -1;
	length = 0;
	cursor = null;
}

/**
 * void moveFront() If List is non-empty, places the cursor under the front
 * element, otherwise does nothing.
 */
public void moveFront() {
	if (length() != 0) {
		cursor = front;
		index = 0;
	}
}

/**
 * void moveBack() If List is non-empty, places the cursor under the back
 * element, otherwise does nothing.
 */
public void moveBack() {
	if (length() != 0) {
		cursor = rear;
		index = length() - 1;
	}
}

/**
 * void movePrev() If cursor is defined and not at front, moves cursor one step
 * toward front of this List, if cursor is defined and at front, cursor becomes
 * undefined, if cursor is undefined does nothing.
 */
public void movePrev() {
	if (index != -1 && cursor != front) {
		cursor = cursor.prev;
		index--;
	} else if (index != -1 && cursor == front) {
		cursor = null;
		index = -1;
	}
}
/**
 * void moveNext() If cursor is defined and not at back, moves cursor one step
 * toward back of this List, if cursor is defined and at back, cursor becomes
 * undefined, if cursor is undefined does nothing.
 */
public void moveNext() {
	if (index != -1 && cursor != rear) {
		cursor = cursor.next;
		index++;
	} else if (index != -1 && cursor == rear) {
		cursor = null;
		index = -1;
	}
}

/**
 * void prepend(int) Insert new element into this List. If List is non-empty,
 * insertion takes place before front element.
 */
public void prepend(Object data) {

	// insert the data to the new node
	Node newNode = new Node();
	newNode.item = data;

	// if the List is empty, make front and back point to this node
	if (length() == 0) {
		front = newNode;
		rear = front;
		newNode.next = null;
		newNode.prev = null;
	} else {
		newNode.next = front;
		front.prev = newNode;
		front = newNode;
	}
	length++;

	// if the new node is inserted before the front and replace the front position,
	// change the old front to the back
	if (length() == 2) {
		newNode.next = rear;
	}
}

/**
 * void append(int) Insert new element into this List. If List is non-empty,
 * insertion takes place after back element.
 */
public void append(Object data) {

	// insert the data into the new node
	Node newNode = new Node();
	newNode.item = data;

	// if the List is empty, make front and back point to this node
	if (length() == 0) {
		front = newNode;
		rear = front;
		newNode.next = null;
		newNode.prev = null;
	} else {
		newNode.prev = rear;
		rear.next = newNode;
		rear = newNode;
	}
	length++;
}

/**
 * void insertBefore(int) Insert new element before cursor. Pre: length()>0,
 * index()>=0
 */
public void insertBefore(Object data) {

	// insert the new element into the new node
	Node newNode = new Node();
	newNode.item = data;

	// case 1: when cursor's previous element is front
	if (cursor.prev == front) {
		newNode.next = cursor;
		cursor.prev = newNode;
		front.next = newNode;
		newNode.prev = front;
	}
	// case 2: when cursor is in the front
	else if (cursor == front) {
		front.prev = newNode;
		newNode.next = front;
		front = newNode;
	}
	// case 3: when cursor's previous element is normal node
	else {
		Node oriBeforeCur = cursor.prev;
		cursor.prev.next = newNode;
		newNode.next = cursor;
		cursor.prev = newNode;
		newNode.prev = oriBeforeCur;
	}

	// update the List attribute
	index++; // increment by 1 since new element insert before cursor
	length++;
}

/**
 * void insertAfter(int) Inserts new element after cursor. Pre: length()>0,
 * index()>=0
 */
public void insertAfter(Object data) {
	// insert the new element into the new node
	Node newNode = new Node();
	newNode.item = data;

	// if insert after the back, update the back
	if (cursor == rear) {
		rear.next = newNode;
		newNode.prev = rear;
		rear = newNode;
	} else {
		Node oriAftCur = cursor.next; // oriAftCur stand for Originally After Cursor
		cursor.next = newNode;
		newNode.prev = cursor;
		oriAftCur.prev = newNode;
		newNode.next = oriAftCur;
	}

	// update the List attribute
	length++;
}

/**
 * void deleteFront() Deletes the front element. Pre: length()>0
 */
public void deleteFront() {
	if (length() > 0) {
		if (length == 1) { // if there's only one node
			front = null;
			rear = null;
			length--;
		} else {
			if (cursor == front) { // if cursor is in the front position but it needs to delete
				cursor = null; // set the cursor to null
				index = -1;
			}
			Node newFront = front.next;
			front = null;
			front = newFront;
			front.prev = null;
			length--;
		}
	}
}

/**
 * void deleteBack() Deletes the back element. Pre: length()>0
 */
public void deleteBack() {
	if (length() > 0) {
		if (length == 1) { // if there's only one node
			front = null;
			rear = null;
			length--;
		} else {
			if (cursor == rear) { // if the cursor is in back position
				cursor = null; // set the cursor to undefined
				index = -1;
			}
			Node newBack = rear.prev;
			rear = null;
			rear = newBack;
			newBack.next = null;
			length--;
		}
	}
}

/**
 * void delete() Deletes cursor element, making cursor undefined. Pre:
 * length()>0, index()>=0
 */
public void delete() {

	if (length() >= 0 && index() >= 0) {
		// case 1: if the cursor is not in front and back
		if (cursor != front && cursor != rear) {
			Node pre = cursor.prev;
			Node aft = cursor.next;
			pre.next = aft;
			aft.prev = pre;
		}
		// case 2: if the cursor is in the front position
		else if (cursor == front) {
			front = cursor.next;
			cursor.prev = null;
		}
		// case 3: if the cursor is in the back position
		else if (cursor == rear) {
			if (length == 2) { // if there is only two node
				rear = front;
				front.next = null;
			} else {
				rear = cursor.prev;
				rear.next = null;
			}
		}

		// set the cursor to undefined and decrement the length by 1
		cursor = null;
		index = -1;
		length--;
	}
}

/***************************************
 * other methods
 ***************************************/

/**
 * String toString() Overrides Object's toString method. Returns a String
 * representation of this List consisting of a space separated sequence of
 * integers, with front on left.
 */
@Override
public String toString() {
	Node nodeWalker = front;
	String retVal = "";
	while (nodeWalker != null) {
		retVal = retVal.concat(" " + String.valueOf(nodeWalker.item));
		nodeWalker = nodeWalker.next;
	}
	retVal = retVal.trim(); // remove the white space in the end of the string
	return retVal;
} 


}
