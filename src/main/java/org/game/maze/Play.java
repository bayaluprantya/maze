package org.game.maze;

import org.game.maze.struct.Player;
import org.game.maze.struct.Room;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.game.maze.xml.File.*;

public class Play {

    Map<String, Room> rooms;
    List<String> scenarios;

    public Play() throws IOException, URISyntaxException {
        rooms = read("/map.xml");
        scenarios = load("/scenario.txt");
    }

    public void start() {
        rooms.get(scenarios.remove(0)).accept(new Player(scenarios), "from here");
    }

    public static void main(String[] args) throws Exception {
        new Play().start();
    }
}