package nearestNeigh;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Implementation of kd tree data structure
 * Parameter index = 0 -> Latitude
 * Parameter index = 1 -> Longitude
 * Parameter index = 2 -> Category
 */
public class KdTree
{
    // Root of the tree
    private Node root;

    /**
     * Adds the given point to the tree
     * @param point point to be added
     * @return if successfully added
     */
    public boolean add( Point point )
    {
        // Empty tree
        // Add first node
        if( root == null )
        {
            root = new Node( point );
            return true;
        }
        // If point already exists,
        // can't be added
        if( contains( point ) )
            return false;

        // Traverse and add the point to correct position of the tree
        add( root, point, 0 );
        return true;
    }

    /**
     * Recursive Helper method to traverse the tree and add point
     * @param root current root
     * @param point point to be added
     * @param depth current depth
     * @return root of the subtree
     */
    private Node add( Node root, Point point, int depth )
    {
        // If root is null, create a new node and return it
        if( root == null )
        {
            return new Node( point );
        }

        // Find parameter index using depth
        int parameterIndex = depth % 3;

        // Compare property according to the parameter index(dimension) of the depth of the tree
        // Dimension split is decided here
        if( parameterIndex == 0 )
        {
            if( root.getData().lat < point.lat )
            {
                root.setLeft( add( root.getLeft(), point, depth + 1 ) );
            }
            else
            {
                root.setRight( add( root.getRight(), point, depth + 1 ) );
            }
        }
        else if( parameterIndex == 1 )
        {
            if( root.getData().lon < point.lon )
            {
                root.setLeft( add( root.getLeft(), point, depth + 1 ) );
            }
            else
            {
                root.setRight( add( root.getRight(), point, depth + 1 ) );
            }
        }
        else if( parameterIndex == 2 )
        {
            if( !root.getData().categoryIsSame( point ) )
            {
                root.setLeft( add( root.getLeft(), point, depth + 1 ) );
            }
            else
            {
                root.setRight( add( root.getRight(), point, depth + 1 ) );
            }
        }
        return root;
    }

    /**
     * Delete the given point from the tree
     * @param point point to delete
     * @return if deleted successfully
     */
    public boolean delete( Point point )
    {
        // Not possible to delete if it doesn't exist
        if( !contains( point ) )
            return false;
        // Recursively traverse the tree and delete the node
        delete( root, point, 0 );
        return true;
    }

    /**
     * Helper method to get node with minimum
     * value for the given parameter index
     * @param root root of the tree
     * @param parameterIndex parameter index
     * @return minimum node of given dimension in subtree
     */
    private Node getMin( Node root, int parameterIndex )
    {
        // Recursively find the minimum node
        return getMin( root, parameterIndex, 0 );
    }

    /**
     * Recursive method to find  node with minimum
     * value for the given parameter index
     * @param root current root
     * @param parameterIndex parameter index
     * @param depth current depth
     * @return minimum node of given dimension in subtree
     */
    private Node getMin( Node root, int parameterIndex, int depth )
    {
        // Leaf node reached in previous call, return null
        if( root == null )
            return null;

        // Find Parameter index by depth
        int actualParameterIndex = depth % 3;
        // If current level of tree matches the parameter index
        if( actualParameterIndex == parameterIndex )
        {
            // Leaf reached
            if( root.getLeft() == null )
                return root;
            // Recursive call to go deeper
            return getMin( root.getLeft(), parameterIndex, depth + 1 );
        }

        // Go deeper in both subtrees until matching levels are met
        Node leftMin = getMin( root.getLeft(), parameterIndex, depth + 1 );
        Node rightMin = getMin( root.getRight(), parameterIndex, depth + 1 );
        // Find minimum of the three nodes
        return root.min( leftMin, parameterIndex ).min( rightMin, parameterIndex );
    }

    /**
     * Recursive method to delete a given point
     * @param root root of the subtree
     * @param point point to delete
     * @param depth current depth
     * @return
     */
    private Node delete( Node root, Point point, int depth )
    {
        // Leaf reached in previous call, return null
        if( root == null )
            return null;

        int parameterIndex = depth % 3;

        // If the point is found
        if( root.getData().equals( point ) )
        {
            // If it has a right subtree
            if( root.getRight() != null )
            {
                // Find the node with minimum value for parameterIndex
                // and replace root's data with it
                Node min = getMin( root.getRight(), parameterIndex );
                root.setData( min.getData() );
                // Recursive call to rearrange the tree
                root.setRight( delete( root.getRight(), min.getData(), depth + 1 ) );
            }
            else if( root.getLeft() != null )
            {
                Node min = getMin( root.getLeft(), parameterIndex );
                root.setData( min.getData() );
                root.setLeft( delete( root.getLeft(), min.getData(), depth + 1 ) );
            }
            else
            {
                // No rearranging needed since the deleted point is a leaf
                return null;
            }
            return root;
        }

        // Point not found yet. Traverse tree and delete the point by considering the dimension splitting
        // Compare property according to the parameter index(dimension) of the depth of the tree
        if( parameterIndex == 0 )
        {
            if( root.getData().lat < point.lat )
            {
                root.setLeft( delete( root.getLeft(), point, depth + 1 ) );
            }
            else
            {
                root.setRight( delete( root.getRight(), point, depth + 1 ) );
            }
        }
        else if( parameterIndex == 1 )
        {
            if( root.getData().lon < point.lon )
            {
                root.setLeft( delete( root.getLeft(), point, depth + 1 ) );
            }
            else
            {
                root.setRight( delete( root.getRight(), point, depth + 1 ) );
            }
        }
        else if( parameterIndex == 2 )
        {
            if( !root.getData().categoryIsSame( point ) )
            {
                root.setLeft( delete( root.getLeft(), point, depth + 1 ) );
            }
            else
            {
                root.setRight( delete( root.getRight(), point, depth + 1 ) );
            }
        }
        return root;
    }

    /**
     * Checks if the given point is included in the tree
     * @param point point to check
     * @return if point is included
     */
    public boolean contains( Point point )
    {
        // Call recursive method starting from root
        return contains( root, point, 0 );
    }

    /**
     * Recursive helper method to traverse the tree
     * and check if given point is included
     * @param root root of the subtree
     * @param point point to check
     * @param depth current depth
     * @return if contains
     */
    private boolean contains( Node root, Point point, int depth )
    {
        // Leaf node reached in previous call
        if( root == null )
            return false;

        // Point found
        if( point.equals( root.getData() ) )
        {
            return true;
        }

        int parameterIndex = depth % 3;

        // Point not yet found. Go deeper in the tree. Use dimension split to traverse correct subtrees
        // Compare property according to the parameter index(dimension) of the depth of the tree
        if( parameterIndex == 0 )
        {
            if( root.getData().lat < point.lat )
            {
                return contains( root.getLeft(), point, depth + 1 );
            }
            else
            {
                return contains( root.getRight(), point, depth + 1 );
            }
        }
        else if( parameterIndex == 1 )
        {
            if( root.getData().lon < point.lon )
            {
                return contains( root.getLeft(), point, depth + 1 );
            }
            else
            {
                return contains( root.getRight(), point, depth + 1 );
            }
        }
        else if( parameterIndex == 2 )
        {
            if( !root.getData().categoryIsSame( point ) )
            {
                return contains( root.getLeft(), point, depth + 1 );
            }
            else
            {
                return contains( root.getRight(), point, depth + 1 );
            }
        }
        return false;
    }

    /**
     * Find the k nearest neighbours and populate the given priorityQueue
     * @param searchTerm point to find neighbours
     * @param k no of neighbours to find
     * @param priorityQueue neighbour register
     */
    public void findKnn( Point searchTerm, int k, PriorityQueue<Point> priorityQueue )
    {
        // Call recursive method to find knn starting from root
        findKnn(root, searchTerm, k, 0, priorityQueue);
    }

    /**
     * Recursive helper method to find knn
     * Populate the priority queue with neighbours
     * @param root root of the subtree
     * @param searchTerm point to find neighbours from
     * @param k neighbour count
     * @param depth current depth
     * @param priorityQueue neighbour register
     */
    private void findKnn( Node root, Point searchTerm, int k, int depth, PriorityQueue<Point> priorityQueue )
    {
        // Leaf reached in previous call
        if( root == null )
            return;

        // Add current point to priority queue
        priorityQueue.add( root.getData() );
        // Remove furthest neighbour if neighbour count exceeds the limit
        removeWorst( priorityQueue, k );

        int parameterIndex = depth % 3;
        // If we decided to take left subtree to explore
        boolean wentLeft = false;
        // Compare property according to the parameter index(dimension) of the depth of the tree
        if( parameterIndex == 0 )
        {
            if( root.getData().lat < searchTerm.lat )
            {
                findKnn( root.getLeft(), searchTerm, k, depth + 1, priorityQueue );
                wentLeft = true;
            }
            else
            {
                findKnn( root.getRight(), searchTerm, k, depth + 1, priorityQueue );
            }
        }
        else if( parameterIndex == 1 )
        {
            if( root.getData().lon < searchTerm.lon )
            {
                findKnn( root.getLeft(), searchTerm, k, depth + 1, priorityQueue );
                wentLeft = true;
            }
            else
            {
                findKnn( root.getRight(), searchTerm, k, depth + 1, priorityQueue );
            }
        }
        else if( parameterIndex == 2 )
        {
            if( !root.getData().categoryIsSame( searchTerm ) )
            {
                findKnn( root.getLeft(), searchTerm, k, depth + 1, priorityQueue );
                wentLeft = true;
            }
            else
            {
                findKnn( root.getRight(), searchTerm, k, depth + 1, priorityQueue );
            }
        }

        // If we already went on left subtree, traverse on right to find neighbours
        if( wentLeft )
        {
            findKnn( root.getRight(), searchTerm, k, depth + 1, priorityQueue );
        }
        else
        {
            findKnn( root.getLeft(), searchTerm, k, depth + 1, priorityQueue );
        }
    }

    /**
     * Find and remove the furthest neighbour
     * @param priorityQueue neighbour registry
     * @param k number of neighbours needed
     */
    private void removeWorst( PriorityQueue<Point> priorityQueue, int k )
    {
        // No need to remove if neighbour count is not reached
        if( k > priorityQueue.size() )
            return;

        // Get the best k points to an arrayList
        List<Point> points = new ArrayList<>();
        for( int i = 0; i < k && !priorityQueue.isEmpty(); i++ )
        {
            points.add( priorityQueue.poll() );
        }
        // Clear the queue and add only best k points
        priorityQueue.clear();
        priorityQueue.addAll( points );
    }

}
