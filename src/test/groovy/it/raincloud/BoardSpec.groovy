package it.raincloud

import spock.lang.Specification

class BoardSpec extends Specification {

  def 'generate: all true'() {
    when: 'generating'
    def generate = Board.&generate
    boolean[][] cells = generate(10, 15, 1)

    then: 'there are no off-by-one-errors'
    cells.length == 10
    cells[0].length == 15
    cells.flatten().every { it }
  }

  // http://spockframework.org/spock/docs/1.1/data_driven_testing.html
  def 'sumCoords: count'(int x, int y, int count) {

    expect:
    NaiveBoard.sumCoords(x, y, 3, 3).length == count

    where:
    x | y | count
    0 | 0 | 3
    1 | 1 | 8
    2 | 2 | 3
    0 | 1 | 5
  }

  def 'sumCoords: values'() {
    when:
    def coords = NaiveBoard.sumCoords(0, 0, 3, 3)

    then:
    coords == [
      Coords.of(0, 1),
      Coords.of(1, 0),
      Coords.of(1, 1)
    ]
  }

  def 'sumAround'(int x, int y, int count) {
    given: 'a matrix of cells'
    boolean[][] matrix = [
      [true, false, true].toArray(),
      [true, false, true].toArray(),
      [true, false, true].toArray()
    ].toArray()

    expect:
    NaiveBoard.countNeighbors(x, y, 3, 3, matrix) == count

    where:
    x | y | count
    0 | 0 | 1
    1 | 1 | 6
    2 | 2 | 1
  }

}
