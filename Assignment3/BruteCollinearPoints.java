import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    
    private final ArrayList<LineSegment> mySegments;
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
       
        mySegments = new ArrayList<LineSegment>();
        
        Point[] points2 = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            points2[i] = points[i];
        }
        
        // sort point array
        Arrays.sort(points2);
        
        // Check for duplicate points
        for (int i = 0; i < points.length - 1; i++) {
            if (points2[i].compareTo(points2[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        
        for (int i = 0; i < points.length - 3; i++) {
            if (points2[i] == null) {
                throw new java.lang.NullPointerException();
            }
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        Point p = points2[i];
                        Point q = points2[j];
                        Point r = points2[k];
                        Point s = points2[m];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            Point[] tmpPoints = new Point[]{p, q, r, s};
                            Arrays.sort(tmpPoints);
                            LineSegment ls = new LineSegment(tmpPoints[0], tmpPoints[3]);
                            mySegments.add(ls);
                        }
                    }
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
