import java.awt.*;
import java.util.*;
import java.util.List;

public class ConvexHull {
    public static List<Point> solve(List<Point> points) throws IllegalArgumentException {
        if (points.size() < 3)
            throw new IllegalArgumentException();

        ArrayList<Point> pointsArray = new ArrayList<>(points);
        Point p0 = Collections.min(pointsArray, new MinimumComparator());
        pointsArray.remove(p0);
        pointsArray.sort(new PolarComparator(p0));

        ArrayList<Point> stack = new ArrayList<>();
        stack.add(p0);
        stack.add(pointsArray.get(0));
        Point p;
        for (int i = 1; i < pointsArray.size(); i++) {
            p = pointsArray.get(i);
            while (stack.size() > 1 && det(stack.get(stack.size() - 2), stack.get(stack.size() - 1), p) <= 0) {
                stack.remove(stack.size() - 1);
            }
            stack.add(p);
        }
        if (stack.size() < 3)
            throw new IllegalArgumentException();
        stack.add(p0);
        return stack;
    }

    static int det(Point p, Point q, Point r) {
        return p.x * (q.y - r.y) + r.x * (p.y - q.y) + q.x * (r.y - p.y);
    }

    private static class MinimumComparator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            int c = o1.y - o2.y;
            if (c != 0)
                return c;
            return o1.x - o2.x;
        }
    }

    private static class PolarComparator implements Comparator<Point> {
        private final Point point;

        public PolarComparator(Point point) {
            this.point = point;
        }

        @Override
        public int compare(Point o1, Point o2) {
            int c = det(point, o2, o1);
            if (c != 0)
                return c;
            c = o1.y - o2.y;
            if (c != 0)
                return c;
            return o1.x - o2.x;
        }
    }
}
