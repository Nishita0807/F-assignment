import java.util.*;

public class FindMyHomeCastle {

    static class Cell {
        int x, y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Cell cell = (Cell) o;
            return x == cell.x && y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static class SpecializedCastle {
        Cell start;
        Set<Cell> soldiers;
        Set<String> paths = new HashSet<>();

        SpecializedCastle(Cell start, Set<Cell> soldiers) {
            this.start = start;
            this.soldiers = soldiers;
        }

        boolean isSafe(int row, int col, int n, int m) {
            return !(row < 0 || col < 0 || row >= n || col >= m);
        }

        void solve(int currRow, int currCol, int n, int m, int homeRow, int homeCol, String path, char currDir,
                Set<Cell> visited) {
            // base case
            if (currRow == homeRow && currCol == homeCol && visited.size() == soldiers.size()) {
                paths.add(path + "Arrive " + "(" + homeRow + "," + homeCol + ")\n");
                return;
            }

            int newRow = currRow, newCol = currCol;

            // Calculate the next cell based on current direction
            switch (currDir) {
                case 'D':
                    newRow = currRow + 1;
                    break;
                case 'R':
                    newCol = currCol + 1;
                    break;
                case 'U':
                    newRow = currRow - 1;
                    break;
                case 'L':
                    newCol = currCol - 1;
                    break;
            }

            if (isSafe(newRow, newCol, n, m)) {
                Cell nextCell = new Cell(newRow, newCol);

                // Move to next cell if it's safe
                solve(newRow, newCol, n, m, homeRow, homeCol, path + "->" + nextCell, currDir, visited);

                if (soldiers.contains(nextCell) && !visited.contains(nextCell)) {
                    // If it's a soldier, kill and turn left
                    visited.add(nextCell);

                    char nextDir = currDir;
                    switch (currDir) {
                        case 'D':
                            nextDir = 'L';
                            break;
                        case 'R':
                            nextDir = 'U';
                            break;
                        case 'U':
                            nextDir = 'L';
                            break;
                        case 'L':
                            nextDir = 'D';
                            break;
                    }

                    solve(newRow, newCol, n, m, homeRow, homeCol,
                            path + " Kill " + nextCell + ". Turn " + nextDir, nextDir, visited);
                    visited.remove(nextCell); // backtrack
                }
            }
        }

        int getPathCount() {
            solve(start.x, start.y, 8, 8, start.x, start.y, "", 'D', new HashSet<>());
            return paths.size();
        }

        void printPaths() {
            System.out.println("Thanks. There are " + paths.size() + " unique paths for your ‘special_castle’");
            int pathIndex = 1;
            for (String path : paths) {
                System.out.println("Path " + pathIndex + "\n=======");
                System.out.println("Start " + start);
                System.out.println(path);
                pathIndex++;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of soldiers: ");
        int numSoldiers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Set<Cell> soldiers = new HashSet<>();
        for (int i = 0; i < numSoldiers; i++) {
            System.out.print("Enter coordinates for soldier " + (i + 1) + ": ");
            String[] coords = scanner.nextLine().split(",");
            int x = Integer.parseInt(coords[0].trim());
            int y = Integer.parseInt(coords[1].trim());
            soldiers.add(new Cell(x, y));
        }

        System.out.print("Enter the coordinates for your “special” castle: ");
        String[] castleCoords = scanner.nextLine().split(",");
        int startX = Integer.parseInt(castleCoords[0].trim());
        int startY = Integer.parseInt(castleCoords[1].trim());
        Cell start = new Cell(startX, startY);

        SpecializedCastle castle = new SpecializedCastle(start, soldiers);
        int pathCount = castle.getPathCount();
        castle.printPaths();

        System.out.println("Total number of unique paths: " + pathCount);
        scanner.close();
    }
}
