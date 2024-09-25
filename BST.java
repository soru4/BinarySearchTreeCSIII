import java.util.List;
import java.util.ArrayList;
public class BST<E extends Comparable>
{
    private BNode<E> root;

    public BST() 
    {
        root = null;
    }

    //add data to this BST
    public void add(E data)
    {
        addHelper(root, new BNode<E>(data));
    }

    //Recursive helper method for add
    private void addHelper(BNode<E> node, BNode<E> addMe)
    {
        if(root == null){
            root = addMe;

        }
        else{
            if(addMe.getData().compareTo(node.getData()) < 0){
                if(node.getLeft()!=null){
                    addHelper(node.getLeft(),addMe);
                }
                else{
                    node.setLeft(addMe);
                }
            }else if(addMe.getData().compareTo(node.getData()) >=0){
                if(node.getRight() != null){
                    addHelper(node.getRight(),addMe);
                }else{
                    node.setRight(addMe);
                }

            }

        }
    }

    public void addAll(List<E> data)
    {
        for(E x : data){
            add(x);
        } 
    }

    //reutrn the size of this tree (how many nodes are in it?)
    public int size()
    {
        return sizeHelper(root);
    }

    //recursive helper method for size
    public int sizeHelper(BNode<E> node)
    {
        if(node.getLeft() == null && node.getRight() == null)
            return 1;
        else{
            return 1 + sizeHelper(node.getLeft()) + sizeHelper(node.getRight());
        }

    }
    //Return a string with the data of this BST in pre order
    public String preorder()
    {
        String result = preorderHelper(root, "");
        return "["+result.substring(2, result.length())+"]";
    }

    //Recursive helper method for preorder
    private String preorderHelper(BNode<E> node, String ret)
    {
        String s = ret;
        if(node == null)
            return ret; 
        s += ", "+node.getData();
        s = preorderHelper(node.getLeft(), s);
        s = preorderHelper(node.getRight(), s);
        return s;
    }

    //Return a string with the data of this BST in order
    public String inorder()
    {
        String result = inorderHelper(root, "");
        return "["+result.substring(2, result.length())+"]";
    }

    //Recursive helper method for inorder
    private String inorderHelper(BNode<E> node, String ret)
    {
        if(node == null)
            return ret;

        ret = inorderHelper(node.getLeft(), ret);
        ret += ", "+node.getData();
        ret = inorderHelper(node.getRight(), ret);
        return ret;

    }

    //Return a string with the data of this BST in post order
    public String postorder()
    {
        String result = postorderHelper(root, "");
        return "["+result.substring(2, result.length())+"]";
    }

    //Recursive helper method for postorder
    private String postorderHelper(BNode<E> node, String ret)
    {
        if(node == null)
            return ret;

        ret = postorderHelper(node.getLeft(), ret);

        ret = postorderHelper(node.getRight(), ret);
        ret += ", "+node.getData();
        return ret;
    }
    //Check if this BST contains the specified data
    public boolean contains(E data)
    {
        return containsHelper(root, data);
    }

    //Recursive helper method for contains
    private boolean containsHelper(BNode<E> node, E data)
    {
        boolean y = false;

        if(node != null){

            if(node.getData().equals(data)){
                return true;
            }

            y = containsHelper(node.getLeft(), data);
            if(y == true)
                return true;
            y = containsHelper(node.getRight(),data);
            if(y == true)
                return true;

        }
        return y;
    }

    public List<BNode<E>> get(E data){
        return getHelper(root,  data);
    }

    private List<BNode<E>> getHelper(BNode<E> node, E data)
    {
        BNode<E> child = null;

        List<BNode<E>> c = new ArrayList<>();

        if(node != null){

            if(node.getData().equals(data)){
                return c;
            }

            c = getHelper(node.getLeft(), data);
            c.add(node);
            c.add(node.getLeft());
            if(c.get(1) != null && c.get(1).getData().equals(data)){

                return c;
            }
            c = getHelper(node.getRight(),data);
            c.add(node);
            c.add(node.getRight());
            if(c.get(1) != null  && c.get(1).getData().equals(data))
                return c;

        }
        return c;
    }
    //Remove the first occurance of data from this BST tree.
    //If data is successfully removed return true, otherwise return false.
    public boolean remove(E data)
    {
        if(!contains(data))
            return false;
        if(data.equals(root.getData())){
            BNode<E> r = root; 
            root = combine(r.getLeft(), r.getRight());
            return true;
        }
        if(get(data).size() <= 0){
            return true;
        }
        
        if(get(data).get(1).getNumChildren() <=0){
            if((get(data).get(0) != null && get(data).get(0).getLeft()!=null) && get(data).get(0).getLeft().getData().equals(data)){
                get(data).get(0).setLeft(null);
                return true;
            }
            else if((get(data).get(0) != null && get(data).get(0).getLeft()!=null) && get(data).get(0).getRight().getData().equals(data)){
                get(data).get(0).setRight(null);
                return true;
            }
        }else if(get(data).get(1).getNumChildren() ==1 ){
            if((get(data).get(0) != null && get(data).get(0).getLeft()!=null) && get(data).get(0).getLeft().getData().equals(data)){
                get(data).get(0).setLeft(get(data).get(1).getLeft() != null ? get(data).get(1).getLeft()  : null);
                return true;
            }
            else if((get(data).get(0) != null && get(data).get(0).getLeft()!=null) && get(data).get(0).getRight().getData().equals(data)){
                get(data).get(0).setRight(get(data).get(1).getRight() != null ? get(data).get(1).getRight()  : null);
                return true;
            }
        }
        else if(get(data).get(1).getNumChildren() ==2 ){
            if((get(data).get(0) != null && get(data).get(0).getLeft()!=null) && get(data).get(0).getLeft().getData().equals(data)){
                get(data).get(0).setLeft(combine(get(data).get(1).getLeft(), get(data).get(1).getRight()));
                return true;
            }
            if((get(data).get(0) != null && get(data).get(0).getRight()!=null) && get(data).get(0).getRight().getData().equals(data)){
                get(data).get(0).setRight(combine(get(data).get(1).getLeft(), get(data).get(1).getRight()));
                return true;
            }
        }
        return false;
    }

    //Recursive helper method for remove. Removes the smallest descendant from the specified node.
    public BNode<E> removeSmallestChild(BNode<E> node)
    {
        if((!node.hasChildren() || !node.hasLeftChild()))
            return node;
        if(node.hasLeftChild())
        {
            BNode<E> x  = removeSmallestChild(node.getLeft());
            if(x.getData() == node.getLeft().getData())
                node.setLeft(x.getRight());
            return x;
        }
        return node;
    }

    //Helper method for remove. Reforms the left and right subtrees into a single
    //BST and returns its root node.
    public BNode<E> combine(BNode<E> left, BNode<E> right)
    {

        BNode<E> x = removeSmallestChild(right);
        if(x.getData() == right.getData())
            right = right.getRight();
        x.setLeft(left);
        x.setRight(right);
        return x;
    }

    ///////////////////
    //  Helper Class //
    ///////////////////
    class BNode<E extends Comparable>
    {
        private E data;
        private BNode<E> left, right;

        public BNode(E data)
        {this.data = data;}

        public E getData()
        {return data;}

        public BNode<E> getLeft()
        {return left;}

        public BNode<E> getRight()
        {return right;}

        public void setLeft(BNode<E> left)
        {this.left = left;}

        public void setRight(BNode<E> right)
        {this.right = right;}

        public boolean hasLeftChild()
        {return null != left;}

        public boolean hasRightChild()
        {return null != right;}

        public boolean hasChildren()
        {return getNumChildren() > 0;}

        public int getNumChildren()
        {
            int ret = 0;
            if(hasLeftChild())  ret++;
            if(hasRightChild()) ret++;
            return ret;
        }

        public String toString(){
            return (String)getData();
        }
    }
}