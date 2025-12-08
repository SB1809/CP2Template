public static <T extends Comparable<T>>  
boolean binarySearch(T[] data, int min, int max, T target){
    min = 0;
    max = data.length - 1;
    int mid = min + (max - min) / 2;

    if (data[mid].equals(target)){
        return mid;
    } else if (data[mid].compareTo(target)) {
        min = mid + 1; 
    } else {
        max = mid - 1; 
    }
}


}