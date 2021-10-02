package nearestNeigh;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Find Nearest k neighbours using kd tree data structure
 */
public class KDTreeNN implements NearestNeigh{

    // KdTree data structure used to store data
    private final KdTree kdTree;

    public KDTreeNN() {
        this.kdTree = new KdTree();
    }

    @Override
    public void buildIndex(List<Point> points) {
        if( points != null ) {
            // Loop through points and add to kdTree
            for( Point point : points ) {
                kdTree.add( point );
            }
        }
    }

    @Override
    public List<Point> search(Point searchTerm, int k) {

        // Min heap to keep track of neighbours
        // Comparator definition passed as an argument
        PriorityQueue<Point> priorityQueue = new PriorityQueue<>( ( o1, o2 ) ->
        {
            double d1 = o1.distTo( searchTerm );
            double d2 = o2.distTo( searchTerm );

            boolean c1 = o1.categoryIsSame( searchTerm );
            boolean c2 = o2.categoryIsSame( searchTerm );

            // If category is the same as search item
            // Distance will decide which is better
            if( c1 && c2 ) {
                return Double.compare( d1, d2 );
            }

            // Category matching point is given priority irrespective of the distance
            if( c1 ) {
                return -1;
            }
            if( c2 ) {
                return 1;
            }
            // If both points don't have the correct category,
            // Distance will decide the better one
            return Double.compare( d1, d2 );
        });

        // Find k nearest neighbours and populate priority queue
        kdTree.findKnn( searchTerm, k, priorityQueue );

        // Make a list and return
        List<Point> knnList = new ArrayList<>();
        for( int i = 0; i < k; i++ ) {
            knnList.add( priorityQueue.poll() );
        }
        return knnList;
    }

    @Override
    public boolean addPoint(Point point) {
        // Add point to kdTree data structure
        return kdTree.add( point );
    }

    @Override
    public boolean deletePoint(Point point) {
        // Remove point from kdTree data structure
        return kdTree.delete( point );
    }

    @Override
    public boolean isPointIn(Point point) {
        // check if point exist in kdTree data structure
        return kdTree.contains( point );
    }

}
