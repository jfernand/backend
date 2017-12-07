package it.raincloud;

import lombok.Data;

// Coordinates in a board
@Data(staticConstructor = "of")
class Coords {

  private final int x;

  private final int y;

  // only coordinates within the bounds are valid
  boolean isValid(int width, int height) {
    return x >= 0 && x < width &&
      y >= 0 && y < height;
  }
}
