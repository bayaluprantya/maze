package org.game.maze.struct.intf;

public interface Node<A,B> {
    public void accept(A visitor, B direction);
}
