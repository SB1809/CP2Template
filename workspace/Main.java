public class Main{

    boolean canFlowOff(int[][] map, int row, int col){
        // Base case: if on the edge, water flows off
        if (row == 0 || row == map.length - 1 || col == 0 || col == map[0].length - 1) {
            return true;
        }

        
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
            {100, 200, 200, 200, 200, 200, 200, 200, 200, 200}, 
            {200, 200, 200, 200, 200, 200, 200, 200, 200, 200},
            {200, 10, 200, 200, 200, 200, 200, 200, 200, 200},
            {200, 11, 10, 200, 200, 6, 85, 84, 83, 200},
            {200, 200, 14, 15, 59, 200, 86, 200, 82, 200},
            {200, 11, 12, 200, 200, 200, 87, 200, 81, 200},
            {200, 10, 200, 200, 90, 89, 88, 200, 200, 200},
            {200, 9, 8, 200, 200, 200, 200, 200, 200, 200},
            {200, 200, 7, 200, 200, 200, 200, 200, 200, 200},
            {200, 98, 6, 200, 200, 200, 200, 200, 200, 200}
        };

        System.out.println(new Main().canFlowOff(map,4,5)); // true
    }

    
}