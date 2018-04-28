package com.nearby.algo;


public class MergeSort {
	
	public static void sort(String[] inputArray){
		sort(inputArray, 0, inputArray.length-1);
	}
	
	public static void sort(String[] inputArray,int start, int end){
		if(end <= start){
			return; // we are done traversing the array
		}
		
		int mid = (start + end)/2;
		sort(inputArray, start, mid); //start from index 0 to mid. Left side
		sort(inputArray, mid+1, end); // start from mid+1 to end. Right side
		merge(inputArray, start, mid, end);
	}
	
	private static void merge(String[] inputArray, int start, int mid, int end){
		String tempArray[] = new String[end - start + 1];
		int leftSlot = start;
		int rightSlot = mid+1;
		int k=0;
		
 		while(leftSlot <= mid && rightSlot <= end){
			if(Integer.parseInt(inputArray[leftSlot]) < Integer.parseInt(inputArray[rightSlot])){
				tempArray[k] = inputArray[leftSlot];
				leftSlot = leftSlot+1;
			}else{
				tempArray[k] = inputArray[rightSlot];
				rightSlot = rightSlot+1;
			}
			k++;
		}

		if(leftSlot <= mid){
			while(leftSlot<=mid){
				tempArray[k] = inputArray[leftSlot];
				leftSlot = leftSlot+1;
				k = k+1;
			}

		}else if(rightSlot <= end){
			while(rightSlot<=end){
				tempArray[k] = inputArray[rightSlot];
				rightSlot = rightSlot+1;
				k = k+1;
			}
		}

		for(int i=0; i<tempArray.length; i++){
			inputArray[start+i] = tempArray[i];
		}
	}
}
