import static java.lang.Math.abs;
import java.nio.ByteBuffer;
public class FractalHash {

	private Node []headArray;
	private Integer numberOfNodes = 0;
	private final Integer ARRAY_SIZE = 1;
	private final Integer ACCESSES_UNTIL_OPTIMISE = 5;
	private final Integer ACCESSES_UNTIL_RESET = 20;

	public FractalHash (){ headArray = new Node[ARRAY_SIZE]; }
	public int numberOfNodes (){ return numberOfNodes; }
	private Node[] createArray () { return new Node[ARRAY_SIZE]; }
	private Node[] createArray (int size){ return new Node[size]; }

	public void insert (String value){
		dig(headArray, 0, value, hash(0, value));
	}

	private void dig (Node[] arr, int level, String value, int hash){
		int hashedValue = -1;
		if(hash == -1) { System.out.println("ERROR!: function dig()"); return;}
		if(arr[hash] != null) {
			if(arr[hash].array == null){
				arr[hash].array = createArray(1);
				hashedValue = 0;
			} else {
				hashedValue = hash(level+1, value) - hash(level+1, arr[hash].array[0].value);
				if(arr[hash].array.length <= hashedValue){ 
					arr[hash].array = growArray(arr[hash].array, hashedValue+1); 
					hashedValue = arr[hash].array.length-1;
				} else if(hashedValue < 0) { 
					arr[hash].array = growArrayLeft(arr[hash].array, (hashedValue*-1)+arr[hash].array.length);
					hashedValue = 0;
				}
			}
			dig(arr[hash].array, level+1, value, hashedValue);
		} else {
			Node newNode = new Node(value);
			arr[hash] = newNode;
			numberOfNodes++;
		}
	}

	private Node[] growArray (Node[] arr, int size){
		Node[] newArr = new Node[size];
		for (int i = 0; i < arr.length; i++)
			newArr[i] = arr[i];
		return newArr;
	}

	private Node[] growArrayLeft (Node[] arr, int size){
		Node[] newArr = new Node[size];
		int difference = size - arr.length;
		for (int i = 0; i < arr.length; i++)
			newArr[i+difference] = arr[i];
		return newArr;
	}

	private int hash (int level, String value){
		// int fixedLevel = (level+1) << 1;
		// int seed = fixedLevel+value.charAt(abs(value.length()-(level+1))%value.length());
		// byte[] seedBytes = ByteBuffer.allocate(4).putInt(seed).array();
		// long asciiSum = 0;
		// for(int i = 0; i<value.length(); i++)
		// 	asciiSum += value.charAt(i);
		// long asciiSumRand = (asciiSum << value.charAt(abs(value.length()-(level+1))%value.length())%7)/value.charAt(0);
		// long hash = 33*fixedLevel + ( (asciiSumRand*value.charAt(1))/fixedLevel )*seedBytes[3]*seedBytes[3]*fixedLevel;
		// hash = (abs(hash)/value.charAt(0));
		// return (int) hash  % ARRAY_SIZE;

		int h = 0;
        char val[] = value.toCharArray();
        for (int i = 0; i < value.length(); i++) {
            h = 31 * h * level + val[i] + (level<<2);
        }
        h ^= (level<<3) + h>>5;
	    return h % ARRAY_SIZE;
	}

	public void delete (int val){

	}

	public Node find (String value){
		if(value != "" && value != null && numberOfNodes != 0) {
			Node foundNode = find(headArray, 0, value);
			if(foundNode != null)
				if((foundNode.accessCounter%ACCESSES_UNTIL_RESET) >= (ACCESSES_UNTIL_RESET-1) && ACCESSES_UNTIL_RESET != 0)
					reset();

			return foundNode;
		} else { System.out.println("Invalid String"); }
		return null;
	}

	private Node find (Node[] arr, int level, String value){ 
		int hashedValue = hash(level, value);
		if(arr.length < ARRAY_SIZE) { hashedValue -= hash(level, arr[0].value); }
		if(hashedValue < arr.length) {			
			if(arr[hashedValue] != null) {
				if(arr[hashedValue].value == value) {
					arr[hashedValue].accessCounter++;
				 	return arr[hashedValue];
				} else if(arr[hashedValue].array != null) { 
					Node foundNode = find(arr[hashedValue].array, level+1, value); 
					if(foundNode != null) { 
						// Optimise this node and the foundNode if it is the direct child of this node by swapping them if the child is accessed more often
						if((foundNode.accessCounter%ACCESSES_UNTIL_OPTIMISE) >= (ACCESSES_UNTIL_OPTIMISE-1) && ACCESSES_UNTIL_OPTIMISE != 0 && arr[hashedValue].contains(foundNode.value) && foundNode.accessCounter > arr[hashedValue].accessCounter) {
							int tempCounter = arr[hashedValue].accessCounter;
							arr[hashedValue].accessCounter = foundNode.accessCounter;
							foundNode.accessCounter = tempCounter;
							String tempValue = arr[hashedValue].value;
							arr[hashedValue].value = foundNode.value;
							foundNode.value = tempValue;
						}
						return foundNode;
					}
				}
			}
		}
		return null;
	}

	public void reset (){ reset(headArray, null); }

	// Optimise and reset the accessCounters 
	private Node reset (Node[] arr, Node parentNode){
		for(int i = 0; i<arr.length; i++) {
			if(arr[i] != null) {
				if(arr[i].array != null) {
					Node newNode = reset(arr[i].array, arr[i]);	
					while(newNode != null) { 
						arr[i] = newNode; 
						newNode = reset(arr[i].array, arr[i]);
					}
				}
				if(parentNode != null){
					if(arr[i].accessCounter > parentNode.accessCounter){
						parentNode.accessCounter = arr[i].accessCounter;
						arr[i].accessCounter = 0;
						String tempValue = parentNode.value;
						parentNode.value = arr[i].value;
						arr[i].value = tempValue;
						return parentNode;
					}
					arr[i].accessCounter = 0;
				}
			}	
		}
		return null;
	}

	public void display (){ display(headArray, 0); }
	private void display (Node[] arr, int level){
		for(int i = 0; i < arr.length; i++) {
			String spacer = "";
			for (int e = 0; e<level; e++) { spacer += "                    "; }
			if(arr[i] != null && arr[i].array != null) {
				System.out.println(spacer+arr[i].value+" i: "+i);
			 } else if(arr[i] != null) { System.out.println(spacer+arr[i].value+" i: "+i); }
		}
	}
	public int getNumberOfSlots (){ return numberOfSlots(headArray, 0); }
	public void numberOfSlots (){
		int accumulator = numberOfSlots(headArray, 0);
		System.out.println("Number of array slots: "+accumulator);
		System.out.println("Number of nodes: "+numberOfNodes());
	}
	private int numberOfSlots (Node[] arr, int level){
		int acc = arr.length;
		for(int i = 0; i < arr.length; i++)
			if(arr[i] != null && arr[i].array != null) 
				acc += numberOfSlots(arr[i].array, level+1);
		return acc;
	}
}
