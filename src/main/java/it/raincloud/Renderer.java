package it.raincloud;

import com.indvd00m.ascii.render.Render;
import com.indvd00m.ascii.render.api.ICanvas;
import com.indvd00m.ascii.render.api.IContextBuilder;
import com.indvd00m.ascii.render.api.IRender;
import com.indvd00m.ascii.render.elements.Dot;
import com.indvd00m.ascii.render.elements.Rectangle;

class Renderer {

  // Prints the board
  String render(int width, int height, final boolean[][] cells) {
    IRender render = new Render();
    IContextBuilder builder = render.newBuilder();
    drawBoundaries(builder, width, height);
    drawCells(builder, cells, width, height);
    ICanvas canvas = render.render(builder.build());
    return canvas.getText();
  }

  // Draws the cells as a dot
  private void drawCells(IContextBuilder builder, boolean[][] cells, int width, int height) {
    for(int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (cells[i][j]) {
          Dot dot = new Dot(i+1, j+1);
          builder.element(dot);
        }
      }
    }
  }

  // Draws the rectangle around the cells
  private void drawBoundaries(IContextBuilder builder, int width, int height) {
      builder.width(width+2).height(height+2);
      builder.element(new Rectangle());
    }

}
