import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


public class Maze {

    private static final int ROAD = 0;
    private static final int WALL = 1;
    private static final int START = 2;
    private static final int EXIT = 3;
    private static final int PATH = 4;


    private int[][] maze;
    private boolean[][] visited;
    private Coordinate start;
    private Coordinate end;

    //URL url = new URL("https://raw.githubusercontent.com/mikepound/mazesolving/master/examples/tiny.png");

    public Maze(URL url) throws IOException {
       // BufferedImage image = ImageIO.read(url);
        try{
            initializeMaze(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //downloads image, builds maze
    public void initializeMaze(URL url) throws IOException {
        BufferedImage image = ImageIO.read(url);
        maze = new int[image.getWidth()][image.getHeight()];
        visited = new boolean[image.getWidth()][image.getHeight()];


        for(int row = 0; row < image.getHeight(); row++){
            for(int col = 0; col < image.getWidth(); col++){

                //for some reason this has to be backwards
                int color = image.getRGB(col,row);
                int red = (color & 0x00ff0000) >> 16;
                int green = (color & 0x0000ff00) >> 8;
                int blue = (color & 0x0000ff);

                //assigns start coordinate
                if(red == 255 && green == 255 && blue == 255 && row == 0) {
                    maze[row][col] = START;
                    start = new Coordinate(row,col);
                }
                //assigns coordinate to a wall
                else if(red == 0 && green == 0 && blue == 0){
                    maze[row][col] = WALL;
                }

                else if(red == 255 && green == 255 && blue == 255 && row == image.getHeight()-1){
                    maze[row][col] = EXIT;
                    end = new Coordinate(row,col);
                }
                else
                    maze[row][col] = ROAD;
            }
        }

    }

    public int getHeight(){
        return maze.length;
    }
    public int getWidth(){
        return maze[0].length;
    }
    public Coordinate getEntry() {
        return start;
    }

    public Coordinate getExit() {
        return end;
    }

    public boolean isExit(int x, int y) {
        return x == end.getX() && y == end.getY();
    }

    public boolean isStart(int x, int y) {
        return x == start.getX() && y == start.getY();
    }

    public boolean isExplored(int row, int col) {
        return visited[row][col];
    }

    public boolean isWall(int row, int col) {
        return maze[row][col] == WALL;
    }

    public void setVisited(int row, int col, boolean value) {
        visited[row][col] = value;
    }

    public boolean isValidLocation(int row, int col) {
        if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
            return false;
        }
        return true;
    }

    public void printPath(List<Coordinate> path) {
        int[][] tempMaze = Arrays.stream(maze)
                .map(int[]::clone)
                .toArray(int[][]::new);
        for (Coordinate coordinate : path) {
            if (isStart(coordinate.getX(), coordinate.getY()) || isExit(coordinate.getX(), coordinate.getY())) {
                continue;
            }
            tempMaze[coordinate.getX()][coordinate.getY()] = PATH;
        }
        System.out.println(toString(tempMaze));
    }

    public String toString(int[][] maze) {
        StringBuilder result = new StringBuilder(getWidth() * (getHeight() + 1));
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (maze[row][col] == ROAD) {
                    result.append(' ');
                } else if (maze[row][col] == WALL) {
                    result.append('#');
                } else if (maze[row][col] == START) {
                    result.append('S');
                } else if (maze[row][col] == EXIT) {
                    result.append('E');
                } else {
                    result.append('.');
                }
            }
            result.append('\n');
        }
        return result.toString();
    }

    public void printImage(Maze maze, List<Coordinate> path) throws IOException {
        int width = getWidth();
        int height = getHeight();
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);



        for (int x = 0; x < width; x++){
            for(int y = 0; y< height; y++){
                if(maze.maze[y][x] == WALL){
                    output.setRGB(x, y, 0x000000);
                }
                else if(maze.maze[y][x]== START){
                    output.setRGB(x,y,0xff0000);
                }
                else if (maze.maze[y][x]== EXIT){
                    output.setRGB(x,y,0x0000ff);
                }

                else if(maze.maze[y][x]==ROAD){
                    output.setRGB(x,y,0xffffff);
                }

            }

        }

        for(Coordinate coordinate : path){
            output.setRGB(coordinate.getY(), coordinate.getX(), 0x00ff00);
        }


        File file = new File("solvedmaze.png");
        ImageIO.write(output, "png", file);

    }

    public void reset() {
        for (int i = 0; i < visited.length; i++)
            Arrays.fill(visited[i], false);
    }

    /*public static void main(String args[]) throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/mikepound/mazesolving/master/examples/tiny.png");
        Maze maze = new Maze(url);



    }
*/





}
