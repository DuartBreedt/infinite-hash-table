public class Node{ 

	String value;
	Node[] array;
	int accessCounter;

	public Node (String val){
		value = val;
		accessCounter = 0;
		array = null;
	}

	public boolean contains(String val) {
		if(array != null)
			for(int i = 0; i<array.length; i++)
				if(array[i] != null)
					if(array[i].value == val)
						return true;

		return false;
	}
}