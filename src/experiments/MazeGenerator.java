package experiments;


import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


public class MazeGenerator {
    public static final int BORDER = 0;
    public static final int ATTENDANCE = 1;
    public static final int NON_ATTENDANCE = 2;
   

    private int[][] mazeData;
    private Random random = new Random();

    public MazeGenerator(int columns, int rows) {
        mazeData = new int[rows * 2 + 1][columns * 2 + 1];

        for (int i = 1; i < mazeData.length; i += 2)
            for (int j = 1; j < mazeData[i].length; j += 2)
                mazeData[i][j] = NON_ATTENDANCE;

    }

    public int[][] getMazeData() {
        return mazeData;
    }

    public void generate() {
        Point currentPosition = new Point(1, 1);
        LinkedList<Point> history = new LinkedList<>();
        while (true) {
            ArrayList<Point> neighbors = getNeighbors(currentPosition);
            mazeData[currentPosition.y][currentPosition.x] = ATTENDANCE;
            if (history.size() == 0 && neighbors.size() == 0)
                break;
            else if (neighbors.size() > 0) {
                history.add((Point) currentPosition.clone());
                Point randomNeighbor = neighbors.get(random.nextInt(neighbors.size()));
                mazeData[(randomNeighbor.y + currentPosition.y) / 2][(randomNeighbor.x + currentPosition.x) / 2] = ATTENDANCE;
                currentPosition = randomNeighbor;
            } else {
                currentPosition = history.pop();
            }
        }
    }

    private ArrayList<Point> getNeighbors(Point cell) {
        ArrayList<Point> neighbors = new ArrayList<>();
        if (safelyGet(cell.y + 2, cell.x) == NON_ATTENDANCE)
            neighbors.add(new Point(cell.x, cell.y + 2));
        if (safelyGet(cell.y - 2, cell.x) == NON_ATTENDANCE)
            neighbors.add(new Point(cell.x, cell.y - 2));
        if (safelyGet(cell.y, cell.x + 2) == NON_ATTENDANCE)
            neighbors.add(new Point(cell.x + 2, cell.y));
        if (safelyGet(cell.y, cell.x - 2) == NON_ATTENDANCE)
            neighbors.add(new Point(cell.x - 2, cell.y));
        return neighbors;
    }

    public void show() {
        for (int i = 0; i < mazeData.length; i++) {
            for (int j = 0; j < mazeData[i].length; j++) {
                System.out.print(mazeData[j][i] == 0 ? '#' : ' ');
            }
            System.out.println();
        }
    }

    private int safelyGet(int row, int column) {
        if (row < 0 || row >= mazeData.length || column < 0 || column >= mazeData[0].length)
            return ATTENDANCE;
        else
            return mazeData[row][column];
    }
}
