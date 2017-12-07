package it.raincloud;

import java.util.Random;

public abstract class Board {

  protected final int width;
  protected final int height;

  private final Renderer renderer;

  protected boolean[][] cells;
  private final int MIN_SIZE = 3;

  Board(Renderer renderer, int width, int height) {
    this.renderer = renderer;
    this.width = Math.max(MIN_SIZE, width);
    this.height = Math.max(MIN_SIZE, height);
  }

  String render() {
    assertValidCells();
    return renderer.render(width, height, cells);
  }

  // seeds the board with a number representing the chance of being populated for each cell
  void seed(float chancePopulated) {
    float chance = Math.max(0, Math.min(1, chancePopulated));
    cells = generate(width, height, chance);
  }

  // Computes the next state
  Board next() {
    assertValidCells();
    cells = next(cells);
    return this;
  }

  // the amount of alive cells
  int aliveCount() {
    assertValidCells();
    int aliveCount = 0;
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        if (cells[i][j]) aliveCount++;
      }
    }
    return aliveCount;
  }

  // region privates

  // Used to prevent calling next or render before seeding
  private void assertValidCells() {
    if (cells == null) throw new IllegalArgumentException("Unseeded board");
  }

  // pure generator for a board
  public static boolean[][] generate(int width, int height, float chancePopulated) {
    boolean[][] target = new boolean[width][height];
    Random random = new Random();
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        target[i][j] = random.nextFloat() <= chancePopulated;
      }
    }
    return target;
  }

  // left for concrete classes to implement...
  protected abstract boolean[][] next(boolean[][] current);

  // endregion
}
