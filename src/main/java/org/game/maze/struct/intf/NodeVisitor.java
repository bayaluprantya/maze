package org.game.maze.struct.intf;

public interface NodeVisitor<A,B> {
    public void visit(A node, B direction);
}