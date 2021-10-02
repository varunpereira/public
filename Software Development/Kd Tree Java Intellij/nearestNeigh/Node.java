package nearestNeigh;

/**
 * Represents a node in kd-tree
 */
public class Node
{
    // Data of the node
    private Point data;
    // Left subtree
    private Node left;
    // Right subtree
    private Node right;

    public Node( Point data )
    {
        this.data = data;
    }

    public Point getData()
    {
        return data;
    }

    public void setData( Point data )
    {
        this.data = data;
    }

    public Node getLeft()
    {
        return left;
    }

    public void setLeft( Node left )
    {
        this.left = left;
    }

    public Node getRight()
    {
        return right;
    }

    public void setRight( Node right )
    {
        this.right = right;
    }

    /**
     * Find the minimum out of this and that
     * according to the parameter index
     * @param that compare with
     * @param parameterIndex parameter index
     * @return Minimum node
     */
    public Node min( Node that, int parameterIndex )
    {
        // If that is not present
        if( that == null )
            return this;

        // Compare property according to the parameter index
        if( parameterIndex == 0 && this.getData().lat > that.getData().lat )
        {
            return that;
        }
        if( parameterIndex == 1 && this.getData().lon > that.getData().lon )
        {
            return that;
        }
        if( parameterIndex == 2 && this.getData().categoryIsSame( that.getData() ) )
        {
            return that;
        }
        return this;
    }
}
