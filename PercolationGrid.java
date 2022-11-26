import java.util.Random;
import java.util.Scanner;

public class PercolationGrid {


    public PercolationGrid(int size) {
        isPercolated = false;
        spotsOpen = 0;
        this.size = size;
        spots = new int[size][size];
        gridWidth = size;
        TOP = gridWidth*gridWidth;
        BOTTOM = TOP + 1;
        for(int i = 0; i < gridWidth; i++) {
            for(int j = 0; j < gridWidth; j++) {
                spots[i][j] = BLOCKED;
            }
        }
        groupedSpots = new PathCompressingUnionFind((size * size) + 2);
    }

    public static void main(String[] args) {
        PercolationGrid grid = new PercolationGrid(20);
            grid.attemptPercolate();
            grid.averageSum += grid.spotsOpen;
        System.out.println("Average number of open spots: " + grid.averageSum/10);
        System.out.println("Percolation threshold: " + ((float)(grid.averageSum/10))/400);
    }

    public void attemptPercolate() {
        Random randGen = new Random();
        while(!isPercolated) {
                int randx = randGen.nextInt(gridWidth);
                int randy = randGen.nextInt(gridWidth);
                while (isOpen(randx, randy)) {
                    randx = randGen.nextInt(gridWidth);
                    randy = randGen.nextInt(gridWidth);
                }
                openSpot(randx, randy);
                isPercolated = checkPercolation();
                if(isPercolated) {break;}

            System.out.println("Grid after a set of 50 opened");
            printGrid();
            System.out.println();
        }
        System.out.println("Finished percolated grid at " + spotsOpen);
        System.out.println();
        printGrid();
    }

    public void reset() {
        isPercolated = false;
        spotsOpen = 0;
        spots = new int[size][size];
        gridWidth = size;
        TOP = gridWidth*gridWidth;
        BOTTOM = TOP + 1;
        for(int i = 0; i < gridWidth; i++) {
            for(int j = 0; j < gridWidth; j++) {
                spots[i][j] = BLOCKED;
            }
        }
        groupedSpots = new PathCompressingUnionFind((size * size) + 2);
    }


    public boolean isOpen(int x, int y) {
        if(this.spots[x][y] >= OPEN) {
            return true;
        } else {return false;}
    }

    public boolean checkPercolation() {
        if(groupedSpots.connected(TOP, BOTTOM)) {return true;}
        return false;
    }

    public int getID(int cellX, int cellY) {
        return gridWidth * cellX + cellY;
    }

    public void openSpot(int x, int y) {
        spots[x][y] = 1;
        spotsOpen++;
        unionNeighbors(x, y);
    }

    public void unionNeighbors(int cellX, int cellY) {
        int thisID = getID(cellX, cellY);
        if(cellX == 0) {
            groupedSpots.union(TOP, thisID);}
        else {
            if(spots[cellX-1][cellY] >= OPEN) {groupedSpots.union(thisID, getID(cellX-1, cellY));}
        }
        if(cellX == gridWidth-1) {groupedSpots.union(BOTTOM, thisID);}
        else {
            if(spots[cellX+1][cellY] >= OPEN) {groupedSpots.union(thisID, getID(cellX+1, cellY));}
        }

        if(cellY > 0) {
            if(spots[cellX][cellY-1] >= OPEN) {groupedSpots.union(thisID, getID(cellX, cellY-1));}
        }
        if(cellY < gridWidth-1) {
            if(spots[cellX][cellY+1] >= OPEN) {groupedSpots.union(thisID, getID(cellX, cellY+1));}
        }
    }

    public void setSpotsBlocked() {
        for(int i = 0; i < spots.length; i++) {
            for(int j = 0; j < spots.length; j++) {
                spots[i][j] = 0;
            }
        }
    }

    public void printGrid() {
        for (int i = 0; i < spots.length; i++) {
            for(int j = 0; j < spots.length; j++) {
                if(groupedSpots.connected(400, getID(i, j))) {spots[i][j] = 2;}
                System.out.print(colorBlocks[spots[i][j]] + " ");
            }
            System.out.println();
        }

    }


    private PathCompressingUnionFind groupedSpots;
    private int[][] spots;
    private int gridWidth;
    private int spotsOpen;
    private int size;
    private int averageSum;
    private boolean isPercolated;
    private final String BLUE = "\033[0;94m\u2588\u2588\033[0;94m";
    //private final String BLUE = "\u001B[34m\u2588\u2588\u001B[0m";
    private final String WHITE = "\033[0;97m\u2588\u2588\033[0;97m";
    private final String RED = "\033[0;90m\u2588\u2588\033[0;90m";
    //private final String RED = "\u001B[31m\u2588\u2588\u001B[0m";
    public String[] colorBlocks = {RED, WHITE, BLUE};
    private int FULL = 2;
    private  int OPEN = 1;
    private int BLOCKED = 0;
    private int TOP;
    private int BOTTOM;
}
