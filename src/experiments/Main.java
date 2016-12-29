package experiments;

public class Main {

    public static void main(String[] args) {
        MazeGenerator mazeGenerator = new MazeGenerator(30, 20);
        mazeGenerator.generate();
        mazeGenerator.show();
    }

}
