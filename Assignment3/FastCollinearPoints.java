import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    
    private final ArrayList<LineSegment> mySegments;
    
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        
        mySegments = new ArrayList<LineSegment>();
        
        // Check for null points
        Point[] points2 = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            points2[i] = points[i];
        }
        
        Arrays.sort(points2);
        
        // Check for duplicate points
        for (int i = 0; i < points.length - 1; i++) {
            if (points2[i].compareTo(points2[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        
        for (int i = 0; i < points.length; i++) {
            Point p = points2[i];
            
            Point[] pointsBySlope = points2.clone();
            Arrays.sort(pointsBySlope, p.slopeOrder());

            // the first point is p itself
            int x = 1;
            while (x < points.length) {
                LinkedList<Point> otherPoints = new LinkedList<>();
                double tmpSlope = p.slopeTo(pointsBySlope[x]);
                do {
                    otherPoints.add(pointsBySlope[x++]);
                } while (x < points.length && p.slopeTo(pointsBySlope[x]) == tmpSlope);

                if (otherPoints.size() >= 3 && p.compareTo(otherPoints.peek()) < 0) {
                    Point min = p;
                    Point max = otherPoints.removeLast();
                    mySegments.add(new LineSegment(min, max));
                }
            }
        }
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return mySegments.size();
    }
    
    // the line segments
    public LineSegment[] segments() {
        return mySegments.toArray(new LineSegment[0]);
    }
    
}