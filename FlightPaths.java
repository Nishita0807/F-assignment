import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FlightPaths {

    static class Flight {
        List<int[]> path;

        Flight() {
            path = new ArrayList<>();
        }

        void addPoint(int x, int y) {
            path.add(new int[] { x, y });
        }

        void printPath(Set<String> excludedPoints) {
            for (int[] point : path) {
                String key = point[0] + "," + point[1];
                if (!excludedPoints.contains(key)) {
                    System.out.print("(" + point[0] + "," + point[1] + ") ");
                }
            }
            System.out.println();
        }

        boolean containsPoint(int x, int y) {
            for (int[] point : path) {
                if (point[0] == x && point[1] == y) {
                    return true;
                }
            }
            return false;
        }
    }

    static Set<String> findIntersectingPoints(List<Flight> flights) {
        Set<String> allPoints = new HashSet<>();
        Set<String> intersectingPoints = new HashSet<>();

        for (Flight flight : flights) {
            for (int[] point : flight.path) {
                String key = point[0] + "," + point[1];
                if (allPoints.contains(key)) {
                    intersectingPoints.add(key);
                } else {
                    allPoints.add(key);
                }
            }
        }

        return intersectingPoints;
    }

    public static void main(String[] args) {
        List<Flight> flights = new ArrayList<>();

        // Define the flight coordinates as provided
        int[][] flight1 = { { 1, 1 }, { 2, 2 }, { 3, 3 } };
        int[][] flight2 = { { 1, 1 }, { 2, 4 }, { 3, 2 } };
        int[][] flight3 = { { 1, 1 }, { 4, 2 }, { 3, 4 } };

        Flight f1 = new Flight();
        for (int[] coords : flight1) {
            f1.addPoint(coords[0], coords[1]);
        }
        flights.add(f1);

        Flight f2 = new Flight();
        for (int[] coords : flight2) {
            f2.addPoint(coords[0], coords[1]);
        }
        flights.add(f2);

        Flight f3 = new Flight();
        for (int[] coords : flight3) {
            f3.addPoint(coords[0], coords[1]);
        }
        flights.add(f3);

        Set<String> intersectingPoints = findIntersectingPoints(flights);

        System.out.println("Non-intersecting flight paths:");
        for (int i = 0; i < flights.size(); i++) {
            System.out.print("Flight " + (i + 1) + ": ");
            flights.get(i).printPath(intersectingPoints);
        }
    }
}
