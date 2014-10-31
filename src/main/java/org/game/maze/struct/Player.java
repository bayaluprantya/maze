package org.game.maze.struct;

import org.game.maze.struct.intf.NodeVisitor;

import java.util.List;
import static java.lang.System.*;

public class Player implements NodeVisitor<Room, String> {

    private List<String> items;
    public Player(List<String> items) {
        this.items = items;
    }

    public void visit(Room room, String direction) {
        out.printf("I go %s\nIn the %s\n", direction, room.getName());
        if (items.contains(room.getItem()))
            out.printf("I collect %s\n", room.getItem());
    }
}
