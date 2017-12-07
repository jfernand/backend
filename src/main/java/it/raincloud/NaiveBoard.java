package it.raincloud;

import java.util.Arrays;

// Implements next in an "expensive" way, meaning that it won't memoize previous results to
// compute the amount of neighbors.
public class NaiveBoard extends Board {

  NaiveBoard(Renderer renderer, int width, int height) {
    super(renderer, width, height);
  }

  @Override
  protected boolean[][] next(boolean[][] current) {
    boolean[][] next = new boolean[width][height];
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        boolean currentlyAlive = cells[i][j];
        int neighborCount = countNeighbors(i, j, width, height, cells);
        next[i][j] = nextCell(currentlyAlive, neighborCount);
      }
    }
    return next;
  }

  // Based on the current state of a cell, and the neighbor count computes the next state
  private boolean nextCell(boolean currentlyAlive, int neighborCount) {
    if (currentlyAlive) {
      return neighborCount == 2 || neighborCount == 3;
    }
    return neighborCount == 3;
  }

  // For a given cell computes the count of neighbors
  public static int countNeighbors(int x, int y, int width, int height, boolean[][] cellMatrix) {
    Coords[] coords = sumCoords(x, y, width, height);
    return (int) Arrays // Cast is alright, we don't expect more than 8
      .stream(coords)
      .filter(v -> cellMatrix[v.getX()][v.getY()]) // looks for cells with the value "true"
      .count(); // and counts them
  }

  // Returns a list of coordinates for the effective neighbors
  // filters out out-of-bounds coordinates for the corners
  public static Coords[] sumCoords(int x, int y, int width, int height) {
    Coords[] coords = new Coords[8];
    int cur = 0;
    for(int i = -1; i <= 1 ; i++) {
      for(int j = -1; j <= 1 ; j++) {
        if (i == 0 && j == 0) continue; // exclude the cell itself
        coords[cur] = Coords.of(x + i, y + j);
        cur++;
      }
    }
    return Arrays
      .stream(coords).filter(c -> c.isValid(width, height)) // filters out invalid coordinates
      .toArray(Coords[]::new); // puts it into a new array
  }
}
