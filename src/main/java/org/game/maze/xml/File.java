package org.game.maze.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.game.maze.struct.Room;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class File {

    @XStreamAlias("room")
    class XMLRoom {
        @XStreamAsAttribute
        String id;
        @XStreamAsAttribute
        String name;
        @XStreamAsAttribute
        String east;
        @XStreamAsAttribute
        String west;
        @XStreamAsAttribute
        String north;
        @XStreamAsAttribute
        String south;
        @XStreamAsAttribute
        XMLRoomContent object;
    }

    @XStreamAlias("object")
    class XMLRoomContent {
        @XStreamAsAttribute
        String name;
    }

    public static Map<String, Room> xForm(List<XMLRoom> mapXML) {
        final Map<String, Room> rooms = new HashMap<>(mapXML.size());

        for (XMLRoom xmlRoom : mapXML) {
            Room room = new Room(xmlRoom.id, xmlRoom.name);
            rooms.put(xmlRoom.id, room);
        }

        for (final XMLRoom xmlRoom : mapXML) {
            Room room = rooms.get(xmlRoom.id);
            Map<String, Room> adjRooms = new HashMap<String, Room>(4) {{
                put("East", rooms.get(xmlRoom.east));
                put("West", rooms.get(xmlRoom.west));
                put("North", rooms.get(xmlRoom.north));
                put("South", rooms.get(xmlRoom.south));
            }};
            room.setAdjacentRooms(adjRooms);
            if (xmlRoom.object != null) {
                room.setItem(xmlRoom.object.name);
            }
        }

        return rooms;
    }

    public static Map<String, Room> read(String file) throws URISyntaxException, MalformedURLException {

        XStream xstream = new XStream(new StaxDriver());
        xstream.processAnnotations(XMLRoom.class);
        xstream.processAnnotations(XMLRoomContent.class);
        xstream.alias("map", List.class);

        List<XMLRoom> mapXML = (List<XMLRoom>) xstream.fromXML(File.class.getResource(file));

        return xForm(mapXML);
    }

    public static List<String> load(String file) throws URISyntaxException, IOException {
        URI uri = File.class.getResource(file).toURI();
        Path path;
        if (uri.toString().contains("!")) {
            String[] array = uri.toString().split("!");
            FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), new HashMap<String, String>());
            path = fs.getPath(array[1]);
        } else {
            path = Paths.get(uri);
        }

        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }
}