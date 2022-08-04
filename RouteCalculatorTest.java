import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class RouteCalculatorTest extends TestCase {
    private StationIndex stationIndex = new StationIndex();
    private RouteCalculator routeCalculator = new RouteCalculator(stationIndex);
    private List<Station> listConnectionOne = new ArrayList<>();
    private List<Station> listConnectionTwo = new ArrayList<>();

    private Line lineFirst;
    private Line lineSecond;
    private Line lineThree;

    private Station stationVladimir;
    private Station stationDevyatkino;
    private Station stationAcademic;
    private Station stationGorkovskaya;
    private Station stationParnassus;
    private Station stationPrimorskaya;

    @Override
    public void setUp() throws Exception {
        lineFirst = new Line(1, "First");
        lineSecond = new Line(2, "Second");
        lineThree = new Line(3, "Three");

        stationVladimir = new Station("Vladimir", lineFirst);
        stationDevyatkino = new Station("Devyatkino", lineFirst);
        stationAcademic = new Station("Academic", lineFirst);

        stationGorkovskaya = new Station("Gorkovskaya", lineSecond);
        stationParnassus = new Station("Parnassus", lineSecond);

        stationPrimorskaya = new Station("Primorskaya", lineThree);

        stationIndex.addLine(lineFirst);
        stationIndex.addLine(lineSecond);
        stationIndex.addLine(lineThree);

        lineFirst.addStation(stationVladimir);
        lineFirst.addStation(stationDevyatkino);
        lineFirst.addStation(stationAcademic);
        lineSecond.addStation(stationGorkovskaya);
        lineSecond.addStation(stationParnassus);
        lineThree.addStation(stationPrimorskaya);

        stationIndex.addStation(stationVladimir);
        stationIndex.addStation(stationDevyatkino);
        stationIndex.addStation(stationAcademic);
        stationIndex.addStation(stationGorkovskaya);
        stationIndex.addStation(stationParnassus);
        stationIndex.addStation(stationPrimorskaya);

        listConnectionOne.add(stationAcademic);
        listConnectionOne.add(stationGorkovskaya);
        listConnectionOne.add(stationParnassus);
        stationIndex.addConnection(listConnectionOne);

        listConnectionTwo.add(stationParnassus);
        listConnectionTwo.add(stationPrimorskaya);
        stationIndex.addConnection(listConnectionTwo);

    }

    @Test
    public void testCalculateDuration() {
        List<Station> stations = new ArrayList<>();
        stations.add(stationDevyatkino);
        stations.add(stationParnassus);
        double actual = RouteCalculator.calculateDuration(stations);
        double expected = 3.5;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRouteOnTheLine() {
        List<Station> actual = routeCalculator.getShortestRoute(stationIndex.getStation("Devyatkino"),
                stationIndex.getStation("Academic"));
        List<Station> expected = new ArrayList<>();
        expected.add(stationDevyatkino);
        expected.add(stationAcademic);
        assertEquals(expected, actual);

    }

    @Test
    public void testGetRouteWithOneConnection() {
        List<Station> actual = routeCalculator.getShortestRoute(stationIndex.getStation("Devyatkino"),
                stationIndex.getStation("Parnassus"));
        List<Station> expected = new ArrayList<>();
        expected.add(stationDevyatkino);
        expected.add(stationAcademic);
        expected.add(stationParnassus);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetRouteWithTwoConnections() {
        List<Station> actual = routeCalculator.getShortestRoute(stationIndex.getStation("Vladimir"),
                stationIndex.getStation("Primorskaya"));
        List<Station> expected = new ArrayList<>();
        expected.add(stationVladimir);
        expected.add(stationDevyatkino);
        expected.add(stationAcademic);
        expected.add(stationGorkovskaya);
        expected.add(stationParnassus);
        expected.add(stationPrimorskaya);
        assertEquals(expected, actual);
    }
}