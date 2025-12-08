public class Main{

    boolean canFlowOff(int[][] map, int row, int col){
        // Base case: if on the edge, water flows off
        if (row == 0 || row == map.length - 1 || col == 0 || col == map[0].length - 1) {
            return true;
        }

        // Check all 4 adjacent cells (up, down, left, right)
        // Up
        if (map[row - 1][col] < map[row][col]) {
            if (canFlowOff(map, row - 1, col)) {
                return true;
            }
        }

        // Down
        if (map[row + 1][col] < map[row][col]) {
            if (canFlowOff(map, row + 1, col)) {
                return true;
            }
        }

        // Left
        if (map[row][col - 1] < map[row][col]) {
            if (canFlowOff(map, row, col - 1)) {
                return true;
            }
        }

        // Right
        if (map[row][col + 1] < map[row][col]) {
            if (canFlowOff(map, row, col + 1)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[]args){
        int[][] map = {
			{100, 99, 200, 200, 200, 200, 200, 200, 200, 200}, 
			{200, 98, 200, 200, 200, 200, 200, 200, 200, 200},
			{200, 97, 96, 200, 200, 200, 200, 200, 200, 200},
			{200, 200, 95, 200, 200, 200, 85, 84, 83, 200},
			{200, 200, 94, 93, 92, 200, 86, 200, 82, 200},
			{200, 150, 200, 200, 91, 200, 87, 200, 81, 200},
			{200, 200, 200, 200, 90, 89, 88, 200, 80, 200},
			{200, 150, 100, 200, 200, 200, 200, 200, 79, 200},
			{200, 200, 200, 200, 200, 200, 200, 200, 78, 77},
			{200, 98, 200, 200, 200, 200, 200, 200, 200, 76}		
	    };
    }
    


}