import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;


public class MazeDriver {
    public static void main(String[] args) throws Exception {

        URL url = new URL("https://raw.githubusercontent.com/mikepound/mazesolving/master/examples/braid200.png");
        Maze maze = new Maze(url);
        bfs(maze);



    }

   /* private static void execute(File file) throws Exception {
        Maze maze = new Maze(url);
        dfs(maze);
        bfs(maze);
    }*/

    private static void bfs(Maze maze) throws IOException {
        long start = System.nanoTime();
        BFSMazeSolver bfs = new BFSMazeSolver();
        List<Coordinate> path = bfs.solve(maze);
        //maze.printPath(path);
        long end = System.nanoTime();
        maze.printImage(maze, path);
        System.out.println("Elapsed Time(not including rendering image): " + ((end-start)/1000000) + " miliseconds" );
        maze.reset();
    }

    private static void dfs(Maze maze) {
        DFSMazeSolver dfs = new DFSMazeSolver();
        List<Coordinate> path = dfs.solve(maze);
        maze.printPath(path);

        maze.reset();
    }
}