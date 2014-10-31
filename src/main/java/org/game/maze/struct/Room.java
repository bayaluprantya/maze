package org.game.maze.struct;

import org.game.maze.struct.intf.Node;
import java.util.Map;

public class Room implements Node<Player, String> {

    private String id;
    private String name;
    private String item;

    private Map<String, Room> adjacentRooms;

    private boolean visited;

    public Room(String id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Room> getAdjacentRooms() {
        return adjacentRooms;
    }

    public void setAdjacentRooms(Map<String, Room> adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void accept(Player visitor, String direction) {
        visitor.visit(this, direction);
        setVisited(true);

        for (Map.Entry<String, Room> entry : getAdjacentRooms().entrySet()) {
            Room room = entry.getValue();
            if (room != null && !room.isVisited())
                room.accept(visitor, entry.getKey());
        }
    }
}



