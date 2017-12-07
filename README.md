# Backend
An interactive console implementation of the Conway's Game of Life.

## Requirements
* Java 1.8

## How to run

    $ ./gradlew test installDist
    $ build/install/backend/bin/backend -s 10x10 -d .5


Where -s is the size of the matrix and -d is the density of alive cells in the seed.
