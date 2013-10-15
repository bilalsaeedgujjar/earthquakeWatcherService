

/**
 * The class is the singleton design pattern of empty bin tree node using the
 * eager initialization strategy.
 *
 * Note: If your program always will need an instance of a class or the cost of
 * creating an instance is not too large then the programmer should use this
 * eager initialization.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 12, 2013
 * @param <E>
 */
public class BinTreeEmptyNode<E> extends BinTreeNode<E> {
    @SuppressWarnings("rawtypes")
    private static final BinTreeEmptyNode emptyBinTreeNode = new BinTreeEmptyNode();

    /**
     * Private constructor prevents instantiation from other classes.
     */
    private BinTreeEmptyNode() {
	// initialize nothing
    }

    /**
     * @return The single allowed instance of a EmptyBinTreeNode
     */
    @SuppressWarnings("rawtypes")
    public static BinTreeEmptyNode getInstance() {
	return emptyBinTreeNode;
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n===Bin Tree Empty Node===");
	stringBuilder.append("\nthis node is a flyweight");
	stringBuilder.append("\n=========================");
	String binTreeInformation = stringBuilder.toString();
	return binTreeInformation;
    }
}
