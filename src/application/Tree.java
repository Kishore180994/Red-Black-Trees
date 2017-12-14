package application;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Tree {
	private Node root; // the root node of the tree
	private int select_node_value; //inode1icate which node is selected
	
	
	/*******************************	Implementation Here:  *****************************************/
    Queue<Integer> queue = new LinkedList<Integer>();
    int black=0;
    int red=1;
	/*
	 * The function below is to insert a new node to the tree. You need to modify it by imposing "Red-Black" constraints
	 * If a node is successfully inserted, it returns "true"
	 * If the node to be inserted has the value already exist in the tree, it should not be inserted anode1 return "false". So you need to modify the code to forbid 
	 * nodes with repeating values to be inserted. 
	 */
    Node granode1parent(Node node){
	        if((node != null) && (node.getParent() != null)){
	            return node.getParent().getParent();
	        }else{
	            return null;
	        }
	    }
	    
	Node uncle(Node node){
	        Node g = granode1parent(node);
	        if(g == null){
	            return null;
	        }
	        if(node.getParent() == g.getLeft()){
	            return g.getRight();
	        }else{
	            return g.getLeft();
	        }
	    }
	    
    public boolean insertNode(Node node) {
        System.out.println("Entry Node -> "+node.getValue());
 
            if (root == null) {
                root = node; 
                root.setColor(black);
                queue.add(node.getValue());
                root.setDepth(0);
            } else {
                if(queue.contains(node.getValue())){
                 // No Operation
                }else {
                    try{
                Node current_node = root;
                while (current_node != null) {
                    int value = current_node.getValue();
                    if (node.getValue() <= value) // go to the left sub-tree
                    {
                        if (current_node.getLeft() != null) // if the left node is
                                                            // not empty
                        {
                            current_node = current_node.getLeft();
                        } else // put node as the left child of current_node
                        { 
                            node.setColor(red);
                            current_node.setLeft(node);
                            node.setParent(current_node);
                            node.setLeft_child_of_parent(true);
                            node.setDepth(current_node.getDepth() + 1);
                            setNew_node(node);
                            setNewNodeProperties(node);
                            queue.add(node.getValue());
                            current_node = null;    
                            Insert_case1(node);
                        }
                        
                    } else // go to the right
                    {
                        if (current_node.getRight() != null) // if the right node is
                                                                // not empty
                        {
                            current_node = current_node.getRight();
                        } else // put node as the right child of current_node
                        {
                            current_node.setRight(node);
                            node.setParent(current_node);
                            node.setColor(red);
                            node.setDepth(current_node.getDepth() + 1);
                            node.setLeft_child_of_parent(false);
                            setNew_node(node);
                            setNewNodeProperties(node);
                            queue.add(node.getValue());
                            current_node = null;
                            Insert_case1(node);
                        }

                    }
                    
                    bfsTreeDraw(this);
                    setBasicTreeProperties(this);
                    showTree(true);
                    }
                }catch(Exception e){
                    
                }
                
            } 
            }     
        System.out.println(queue);
        return true;
    }
    
    
    
    
    
    
    
    
    
    
    
	private void Insert_case1(Node node) {
       
        if(node.getParent() == null){
            node.setColor(black);
        }else{
            Insert_case2(node);
        }
        
    }


    private void Insert_case2(Node node) {
        if(node.getParent().getColor() == black)
            return;
        else
            Insert_case3(node);
    }


    private void Insert_case3(Node node) {
      //Set Granode1Parent anode1 Uncle
       Node u = uncle(node);
       Node g;
        if((u != null) && (u.getColor() == red)){
            node.getParent().setColor(black);
            u.setColor(black);
            g = granode1parent(node);
            g.setColor(red);
            System.out.println("Color : "+node.getColor());
            System.out.println("Color : "+g.getColor());
            System.out.println("Color: "+u.getColor());
            Insert_case1(g);
        }else{
            Insert_case4(node);
        }
    }


    private void Insert_case4(Node node) {
        Node g = granode1parent(node);
        Node u = uncle(node);
        if(u == null){
            if(g.getParent()!=null){
                node.getParent().setColor(black);
                g.setColor(red);
                if(node == node.getParent().getLeft()){
                    g.getParent().setLeft(node.getParent());
                    node.getParent().setParent(g.getParent());
                    node.getParent().setRight(g);
                    Point2D position = g.getPosition();
                    g.setParent(node.getParent());
                    g.setDepth(g.getParent().getDepth()+1);
                    g.setLeft(null);
                    g.setRight(null);
                    g.setLeft_child_of_parent(false);
                    node.getParent().setPosition(position);
                    node.getParent().setDepth(node.getParent().getParent().getDepth()+1);
                    node.setLeft_child_of_parent(true);
                    setNodePosition(g);
                }else{
                    if(node == node.getParent().getRight()){
                    Point2D position = g.getPosition();
                    g.getParent().setRight(node.getParent());
                    node.getParent().setParent(g.getParent());
                    node.getParent().setDepth(node.getParent().getParent().getDepth()+1);
                    node.getParent().setLeft(g);
                    g.setParent(node.getParent());
                    g.setDepth(g.getParent().getDepth()+1);
                    g.setRight(null);
                    g.setLeft(null);
                    g.setLeft_child_of_parent(true);
                    node.getParent().setPosition(position);
                    node.setLeft_child_of_parent(false);
                    setNodePosition(g);
                    }
                }
            }else{
                node.getParent().setColor(black);
                g.setColor(red);
               if(node == node.getParent().getLeft()){ 
                Point2D position = g.getPosition();
                g.setParent(node.getParent());
                g.setLeft(null);
                g.setLeft_child_of_parent(false);
                root = node.getParent();
                node.getParent().setParent(null);
                node.getParent().setRight(g);
                node.setDepth(node.getParent().getDepth()+1);
                g.setRight(null);
                setRoot(node.getParent());
                root = node.getParent();
                node.getParent().setRight(g);
                g.setLeft_child_of_parent(false);
                node.getParent().setDepth(0);
                g.setDepth(g.getParent().getDepth()+1);
                node.setLeft_child_of_parent(true);
                node.getParent().setPosition(position);
                setNodePosition(g);
                setBasicTreeProperties(this);
                setNodePosition(node);
               }else{
                   Point2D position = g.getPosition();
                   g.setParent(node.getParent());
                   g.setRight(null);
                   g.setLeft_child_of_parent(true);
                   setRoot(node.getParent());
                   root = node.getParent();
                   node.getParent().setParent(null);
                   node.getParent().setLeft(g);
                   node.setDepth(node.getParent().getDepth()+1);
                   g.setLeft(null);
                   root = node.getParent();
                   node.getParent().setLeft(g);
                   g.setLeft_child_of_parent(true);
                   node.getParent().setDepth(0);
                   g.setDepth(g.getParent().getDepth()+1);
                   node.setLeft_child_of_parent(false);
                   node.getParent().setPosition(position);
                   setNodePosition(g);
                   setNodePosition(node); 
                   setBasicTreeProperties(this);
               }
            }
        }
        else{    
        Insert_case5(node);
        }
      }


    private void Insert_case5(Node node) {
        Node g = granode1parent(node);
        if((node == node.getParent().getRight())&&(node.getParent() == g.getLeft())){
            System.out.println("1.Assignment Error!!");
        }else if((node == node.getParent().getLeft()) && (node.getParent() == g.getRight())){
            System.out.println("2.Assignment Error!!");
        }
        Insert_case6(node);
    }
    
   private void Insert_case6(Node node) {
        System.out.println("Here");
        Node g = granode1parent(node);
        node.getParent().setColor(black);
        g.setColor(red);
        if(node == node.getParent().getLeft()){
            //rotate_right(granode1parent)
            System.out.println();
            if(g.getParent() == null){
                Point2D position = new Point2D(320, 0);
                root = node.getParent();
                node.getParent().setParent(null);
                if(node.getParent().getRight()!=null){
                    g.setLeft(node.getParent().getRight());
                    node.getParent().getRight().setParent(g);
                    node.getParent().getRight().setLeft_child_of_parent(true);
                    node.getParent().getRight().setDepth(node.getParent().getRight().getParent().getDepth()+1);
                }else{
                    g.setLeft(null);
                }
                node.getParent().setRight(g);
                g.setParent(node.getParent());
                g.setDepth(g.getParent().getDepth()+1);
                g.setLeft_child_of_parent(false);
                node.setParent(root);
                node.setLeft_child_of_parent(true);
                node.getParent().setLeft(node);
                node.setDepth(node.getParent().getDepth()+1);
                node.getParent().setPosition(position);
                setNodePosition(g);
                setNewNodeProperties(g);
                changePropogation();
            }else{
                if(g.getParent() != null){
                    node.getParent().setColor(black);
                    g.setColor(red);
                    Point2D position = g.getPosition();
                    g.getParent().setLeft(node.getParent());
                    node.getParent().setParent(g.getParent());
                    node.getParent().setDepth(node.getParent().getParent().getDepth()+1);
                    if(node.getParent().getRight()!=null){
                        g.setLeft(node.getParent().getRight());
                        node.getParent().getRight().setLeft_child_of_parent(true);
                        node.getParent().getRight().setParent(g);
                        node.getParent().getRight().setDepth(node.getParent().getRight().getParent().getDepth()+1);
                    }else{
                        g.setLeft(null);
                    }
                    g.setRight(g.getRight());
                    node.getParent().setRight(g);
                    g.getRight().setDepth(g.getDepth()+1);
                    g.setParent(node.getParent());
                    g.setDepth(g.getParent().getDepth()+1);
                    g.getRight().setParent(g);
                    g.getRight().setLeft_child_of_parent(false);
                    g.getRight().setDepth(g.getLeft().getParent().getDepth()+1);
                    g.setLeft_child_of_parent(false);
                    node.setLeft_child_of_parent(true);
                    node.getParent().setPosition(position);
                    setNodePosition(g);
                    setNodePosition(g.getRight());
                }
            }
        }else{
            if(node == node.getParent().getRight()){
            if(g.getParent()==null){
                Point2D position = new Point2D(320, 0);
                root = node.getParent();
                node.getParent().setParent(null);
                if(node.getParent().getLeft()!=null){
                    g.setRight(node.getParent().getLeft());
                    node.getParent().getLeft().setParent(g);
                    node.getParent().getLeft().setLeft_child_of_parent(false);
                    node.getParent().getLeft().setDepth(node.getParent().getLeft().getParent().getDepth()+1);
                }else{
                    g.setRight(null);
                }
                node.getParent().setLeft(g);
                g.setParent(node.getParent());
                g.setDepth(g.getParent().getDepth()+1);
                g.setLeft_child_of_parent(true);
                node.setParent(root);
                node.setLeft_child_of_parent(false);
                node.getParent().setRight(node);
                node.setDepth(node.getParent().getDepth()+1);
                node.getParent().setPosition(position);
                setNodePosition(g);
                setNewNodeProperties(g);
                changePropogation();
            }else{
                if(g.getParent()!=null){
                    node.getParent().setColor(black);
                    g.setColor(red);
                    Point2D position = g.getPosition();
                    g.getParent().setRight(node.getParent());
                    node.getParent().setParent(g.getParent());
                    node.getParent().setDepth(node.getParent().getParent().getDepth()+1);
                    if(node.getParent().getLeft()!=null){
                        g.setRight(node.getParent().getLeft());
                        node.getParent().getLeft().setLeft_child_of_parent(false);
                        node.getParent().getLeft().setParent(g);
                        node.getParent().getLeft().setDepth(node.getParent().getLeft().getParent().getDepth()+1);
                    }else{
                        g.setRight(null);
                    }
                    g.setLeft(g.getLeft());
                    node.getParent().setLeft(g);
                    g.getLeft().setDepth(g.getDepth()+1);
                    g.setParent(node.getParent());
                    g.setDepth(g.getParent().getDepth()+1);
                    g.getLeft().setParent(g);
                    g.getLeft().setLeft_child_of_parent(false);
                    g.getLeft().setDepth(g.getRight().getParent().getDepth()+1);
                    g.setLeft_child_of_parent(true);
                    node.setLeft_child_of_parent(false);
                    node.getParent().setPosition(position);
                    setNodePosition(g);
                    setNodePosition(g.getLeft()); 
                }
            }
        }
        } 
    }


    /*
	 * The function below is to delete a selected node from the tree. You need to finish the implementation
	 * So far, I just return false as no code is done here. In fact, it should return "true" if a node is deleted successfully.
	 */
	public boolean deleteNode() {
	    try{
            System.out.println("Delete Node with value: " + select_node_value);
            Tree tree = root.getTree();
            //Node current_node = tree.
            Node node = tree.dragged_node;
            DeleteCase1(node);
            deletecase5(node);
            }catch(Exception e){
                System.out.println("Select The Node!!");
            }
            
	    queue.remove(select_node_value);
            System.out.println("Del "+queue);
            select_node_value = -1;
            return true;
	}
	
	Node Sibling(Node node){
	    if((node == null)||node.getParent()==null)
	        return null;
	    if(node == node.getParent().getLeft())
	        return node.getParent().getRight();
	    else
	        return node.getParent().getLeft();
	}
private void deletecase5(Node node) {
        Node s = Sibling(node);
        if(s.getColor()==black){
            if((node == node.getParent().getLeft())&&(s.getRight().getColor()==black)&&(s.getLeft().getColor()==red)){
                s.setColor(red);
                s.getLeft().setColor(black);
            }else if((node == node.getParent().getRight())&&(s.getLeft().getColor()==black)&&(s.getRight().getColor()==red)){
                s.setColor(red);
                s.getRight().setColor(black);
            }
        }
        
    }

private void DeleteCase1(Node node) {

    if(node.getParent() == null && node.getRight() == null && node.getLeft() != null){
        Point2D pos = node.getPosition();
        root = node.getLeft();
        node.getLeft().setPosition(pos);
        setNodePosition(node.getLeft());
        changePropogation();
    }
    
    if(node.getParent() == null && node.getLeft() == null && node.getRight() != null){
        Point2D pos = node.getPosition();
        root = node.getRight();
        node.getRight().setPosition(pos);
        setNodePosition(node.getRight());
        changePropogation();
    }
    DeleteCase2(node);
    }

private void DeleteCase2(Node node) {
    if(node.isLeft_child_of_parent() && node.getParent() != null){
        if(node.getLeft() == null && node.getRight() == null){
            node.getParent().setLeft(null);
        }else{
            if(node.getLeft() == null){
                node.getParent().setLeft(node.getRight());
                node.getRight().setParent(node.getParent());
                node.getRight().setLeft_child_of_parent(true);
            }else{
                if(node.getRight() == null){
                    node.getParent().setLeft(node.getLeft());
                    node.getLeft().setParent(node.getParent());  
                    node.getLeft().setLeft_child_of_parent(false);
                }
            }
        }
    }else{
        if( node.getLeft() == null && node.getRight() == null && node.getParent() != null){
            node.getParent().setRight(null);
        }else{
            if( node.getLeft() == null && node.getParent() != null){
                node.getParent().setRight(node.getRight());
                node.getRight().setParent(node.getParent());
            }else{
                if(node.getRight() == null && node.getParent() != null){
                    node.getParent().setRight(node.getLeft());
                    node.getLeft().setParent(node.getParent());
                    node.getLeft().setLeft_child_of_parent(false);
                }
            }
        }
    }
    DeleteCase3(node);
}

private void DeleteCase3(Node node) {
    if(node.getLeft() != null && node.getRight() != null && node.getParent() != null){
        Node node1 = node.getRight();
        if(node1.getLeft() != null){
            Node node11 = node1.getLeft();
            while(node11.getLeft() != null){
                node11 = node11.getLeft();
                }
            System.out.println(node11.getValue());
            if(node.isLeft_child_of_parent()){
            node11.setLeft(node.getLeft());
            node11.setRight(node.getRight());
            node.getParent().setLeft(node11);
            node11.setLeft_child_of_parent(true);
            node.getLeft().setParent(node11);
            node11.getParent().setLeft(node);
            node.setLeft_child_of_parent(false);
            node.setLeft(null);
            node.setRight(null);
            node11.getParent().setLeft(null);
            node11.setParent(node.getParent());
            node11.setPosition(node.getPosition());
        }else{
            node11.setLeft(node.getLeft());
            node11.setRight(node.getRight());
            node.getParent().setRight(node11);
            node11.setLeft_child_of_parent(false);
            node.getLeft().setParent(node11);
            node11.getParent().setLeft(node);
            node.setLeft_child_of_parent(true);
            node.setLeft(null);
            node.setRight(null);
            node11.getParent().setLeft(null);
            node11.setParent(node.getParent());
            node11.setPosition(node.getPosition());
        }
            
        }else{
            if(node.getParent() != null){
            if(node.isLeft_child_of_parent()){
            node1.setLeft(node.getLeft());
            node.getParent().setLeft(node1);
            node1.setLeft_child_of_parent(true);
            node.getRight().setParent(node1);
            node.setLeft(null);
            node.setRight(null);
            node1.setParent(node.getParent());
            node1.setPosition(node.getPosition());
            }else{
                node1.setLeft(node.getLeft());
                node.getParent().setRight(node1);
                node1.setLeft_child_of_parent(false);
                   node.getLeft().setParent(node1);
                node.setLeft(null);
                node.setRight(null);
                node1.setParent(node.getParent());
                node1.setPosition(node.getPosition());
            }
        }
        }
    }else{
        DeleteCase4(node);
    }
}

private void DeleteCase4(Node node) {
    if(node.getParent()==null){
        if(node.getLeft() != null && node.getRight() != null){
            Node node1 = node.getRight();
              if(node1.getLeft() != null){
                    Node node11 = node1.getLeft();
                    while(node11.getLeft() != null){
                        node11 = node11.getLeft();
                    }
                    if(node11.getRight()!=null){
                        node11.getParent().setLeft(node11.getRight());
                        node11.getRight().setLeft_child_of_parent(true);
                        node11.getRight().setParent(node11.getParent());
                        node1.getRight().setDepth(node1.getRight().getDepth()+1);
                        setNodePosition(node1.getRight());
                        node11.setRight(null);
                    }
                    else{
                        node11.getParent().setLeft(null);
                    }
                    node11.setLeft(node.getLeft());
                    node11.setRight(node.getRight());
                    node11.setParent(null);
                    node.getLeft().setParent(node11);
                    node.getRight().setParent(node11);
                    node.setLeft(null);
                    node.setRight(null);
                    root = node11;
                    node.setParent(null);
                    root.setParent(null);
                    node11.setPosition(node.getPosition());
                    node11.setDepth(node.getDepth());
              }else{
                  node1.setLeft(node.getLeft());
                  node1.setParent(null);
                  node.getLeft().setParent(node1);
                  node.getLeft().setLeft_child_of_parent(true);
                  node.setLeft(null);
                  node.setRight(null);
                  root = node1;
                  node.setParent(null);
                  root.setParent(null);
                  node1.setDepth(node.getDepth());
                  node1.setPosition(node.getPosition());
              }
        }else{
            
        if(node.getRight() == null && node.getLeft()!=null){
            root = node.getLeft();
            node.setParent(null);
            root.setParent(null);
            if(root.getLeft()!=null)
            root.getLeft().setLeft_child_of_parent(true);
        }else{
            
        if(node.getLeft() == null && node.getRight() != null){
            root = node.getRight();
            node.setParent(null);
            root.setParent(null);
            if(root.getRight()!=null)
                root.getRight().setLeft_child_of_parent(false);
                  }
               }
           }
    }
}

/*************************************************  End of Implementation   **************************************************************/


/***********************************************    Below are read-only! You don't need to make any changes ******************************/
/***********************************************	 The data members anode1 functions below are just for the GUI Part ******************************************/
	
	/* Below are the GUI part you don't need to use*/
	private int tree_max_height; // record the maximum height of the tree
	private int tree_max_width; // record the maximum width of the tree
	private Canvas canvas; // the canvas where a node is drawn
	private GraphicsContext gc; // the brush used to draw on canvas
	private int canvas_width = 640;
	private int canvas_height = 480;
	private Vector<Vector<Node>> layer_nodes = new Vector<Vector<Node>>(); //store each node into the corresponode1ing layer 
	private Node new_node; //the newly inserted node
	private double old_dragging_x;
	private double old_dragging_y;
	private boolean dragging = false;
	private Node dragged_node;
	private double delta_x;
	private double delta_y;
	private int radius = 30; //the size of the node

	
	

	
	public Tree() {

	}

	public Tree(Canvas c, GraphicsContext gc) {
		canvas = c;
		this.gc = gc;
	}
	
	
	
	/*
	 * The function below is for GUI use to make sure the tree fits in the
	 * canvas: (1) according to the current tree distribution, determine the
	 * tree height anode1 width; (2) Check whether there is any overlap between
	 * tree nodes
	 */
	private void organizeTree() {
		//Reset several variables
		layer_nodes.clear(); //clear the layer
		
		
		//Set positions on Canvas
		
		
		
	}

	// Draw the tree on canvas
	public void showTree(boolean insertion_occur) {
		
	
		if(insertion_occur)
		{
			//Set the basic properties for then new node
			setNewNodeProperties(new_node);
		}
		
		// Traverse the tree anode1 draw all the nodes onto canvas
		bfsTreeDraw(this);
		
		//Debug:
//		System.out.println("Total layers: " + layer_nodes.size());
//		for(int y = 0; y < layer_nodes.size(); y++)
//		{
//			System.out.println("Layer " + y + ": ");
//			for(int x = 0; x < layer_nodes.get(y).size(); x++)
//			{
//				System.out.print("[" + x + "] " + layer_nodes.get(y).get(x).getValue() + "("+ layer_nodes.get(y).get(x).getDepth() + ")" + "(" + layer_nodes.get(y).get(x).getLayer_idx() + "), ");
//			}
//			System.out.println("");
//		}
	}

	
	/*For mouse dragging use (update the sub-tree)*/
	private void updateTreePos(Node moving_node, double delta_x, double delta_y) {
		
		Queue<Node> queue = new LinkedList<Node>();
		Node current_node;
		
		queue.add(moving_node); // push the root node into queue
		while (!queue.isEmpty()) {
			current_node = queue.remove();
		
			// Check left child
			if (current_node.getLeft() != null) {
				Point2D pos = new Point2D(current_node.getLeft().getPosition().getX() + delta_x, current_node.getLeft().getPosition().getY() + delta_y);
				current_node.getLeft().setPosition(pos);
				queue.add(current_node.getLeft());
				
			}

			// Check right child
			if (current_node.getRight() != null) {
				Point2D pos = new Point2D(current_node.getRight().getPosition().getX() + delta_x, current_node.getRight().getPosition().getY() + delta_y);
				current_node.getRight().setPosition(pos);
				queue.add(current_node.getRight());
				
			}
		}

	}
	
	/* Apply Breath First Search Tree to renode1er all the node */
	private void bfsTreeDraw(Tree tree) {
		Queue<Node> queue; // store the retrieved nodes from edges
		Node current_node; // point to the current node processing on

		if (tree.getRoot() == null) {
			return;
		}

		queue = new LinkedList<Node>();

		queue.add(tree.getRoot()); // push the root node into queue
		while (!queue.isEmpty()) {
			current_node = queue.remove();
			current_node.showNode(select_node_value); // draw the node on the canvas
		
			// Check left child
			if (current_node.getLeft() != null) {
				
				//Draw the edge between current node the the left child
				double start_x = current_node.getPosition().getX() + radius / 2;
				double start_y = current_node.getPosition().getY() + radius / 2;
				double enode1_x = current_node.getLeft().getPosition().getX() + radius / 2;
				double enode1_y = current_node.getLeft().getPosition().getY() + radius / 2;
				
				//Draw the edge
				gc.setStroke(Color.BLACK);
				gc.strokeLine(start_x, start_y, enode1_x, enode1_y);
				
				//Show current node again to cover the edge
				current_node.showNode(select_node_value); 
				
				queue.add(current_node.getLeft());
			}

			// Check right child
			if (current_node.getRight() != null) {
				//Draw the edge between current node the the right child
				double start_x = current_node.getPosition().getX() + radius / 2;
				double start_y = current_node.getPosition().getY() + radius / 2;
				double enode1_x = current_node.getRight().getPosition().getX() + radius / 2;
				double enode1_y = current_node.getRight().getPosition().getY() + radius / 2;
				
				//Draw the edge
				gc.setStroke(Color.BLACK);
				gc.strokeLine(start_x, start_y, enode1_x, enode1_y);
				
				//Show current node again to cover the edge
				current_node.showNode(select_node_value); 
				
				queue.add(current_node.getRight());
			}
		}

	}

	/* Apply Breath First Search Tree to (1) finode1 the parent of the target_node anode1 setup connection between them (2) set the GUI properties for the node*/
	private void setNewNodeProperties(Node target_node) {
	 
		Queue<Node> queue; // store the retrieved nodes from edges
		Node current_node; // point to the current node processing on
		layer_nodes.clear(); //clear all the layer
		
		if (target_node == root) {
		
			target_node.setParent(null);
			Point2D pos = new Point2D(320, 0); // do the setting for the root
			target_node.setPosition(pos);
			
			
			
			Vector<Node> layer = new Vector<Node>();
			layer.add(target_node);
			layer_nodes.add(layer);
			target_node.setLayer_idx(0);
			
			return;
		}
		
	
		//Start the BFS search
		queue = new LinkedList<Node>();
		queue.add(getRoot()); // push the root node into queue	
	
		
		while (!queue.isEmpty()) {
			current_node = queue.remove();
			
			/* If current node is in a new layer, then create a new layer */
			if(layer_nodes.size() - 1 < current_node.getDepth())
			{
				Vector<Node> layer = new Vector<Node>();
				layer.add(current_node);
				layer_nodes.add(layer);
				current_node.setLayer_idx(0);
			}
			else
			{
				layer_nodes.get(current_node.getDepth()).add(current_node);
				current_node.setLayer_idx(layer_nodes.get(current_node.getDepth()).size() - 1);
			}
			
		
			
			/*If the current node does not have the target node as one of its children, then keep finode1ing it*/
			if (current_node.getLeft() != null)
				queue.add(current_node.getLeft());
			if (current_node.getRight() != null)
				queue.add(current_node.getRight());
			
		}
		

		/*Set the 2D position on Canvas*/
		setNodePosition(target_node);

	}
	
	/*Set the target_node position. This new node may affect the other node's current positions*/
	void setNodePosition(Node target_node)
	{
		double x_left_offset = -(double)radius * 6.0 / (double) target_node.getDepth() + 2.0; // the left child x relative position to the current node's x position (maximum offset)
		double x_right_offset = (double)radius * 6.0 / (double) target_node.getDepth() + 2.0; 
		int y_offset = radius * 2; // the left child x position relative (maximum offset)
		
		//Below are the two inode1icator whether need to adjust the tree size
		boolean layer_crowded = false;
		boolean tree_too_high = false;
		
	
		//Set position for the target_node
		try{
	        //Set position for the target_node
	        Point2D pos = new Point2D(0,0); //the position will be assigned to the target_node
	        if(target_node.isLeft_child_of_parent())
	        {
	            pos = new Point2D(target_node.getParent().getPosition().getX() + x_left_offset, target_node.getParent().getPosition().getY() + y_offset);
	        }
	        else
	        {
	            pos = new Point2D(target_node.getParent().getPosition().getX() + x_right_offset, target_node.getParent().getPosition().getY() + y_offset);
	        }
	        target_node.setPosition(pos);
	        
	        
	        //Check this new node has overlapping with its left or right neighbors
	        if(target_node.getLayer_idx() > 0) //check overlapping with left neighbor
	        {
	            int left_neighbor = target_node.getLayer_idx() - 1;
	            Point2D left_pos = layer_nodes.get(target_node.getDepth()).get(left_neighbor).getPosition();
	            if(pos.getX() - left_pos.getX() < radius)
	            {
	                layer_crowded = true;
	                System.out.println("Overlapping with left! " + layer_nodes.get(target_node.getDepth()).get(left_neighbor).getValue());
	            }
	        }
	        }catch(Exception e){}
//		if(target_node.getLayer_idx() < (layer_nodes.get(target_node.getDepth()).size() - 1)) //check overlapping with right neighbor
//		{
//			int right_neighbor = target_node.getLayer_idx() + 1;
//			Point2D right_pos = layer_nodes.get(target_node.getDepth()).get(right_neighbor).getPosition();
//			if(right_pos.getX() - pos.getX() < radius)
//			{
//				layer_crowded = true;
//				System.out.println("Overlapping with right! " + layer_nodes.get(target_node.getDepth()).get(right_neighbor).getValue());
//			}
//		}
//		
//		if(target_node.getPosition().getX() < 0 || target_node.getPosition().getX() > canvas_width || 
//				target_node.getPosition().getY() < 0 || target_node.getPosition().getY() > canvas_height)
//		{
//			layer_crowded = true;
//		}
		
		/* Adjust the tree layer */
//		if(layer_crowded)
//		{
//			radius = 20;
//			//resetTree();
//		}
		
	}
	
	
	/* Tracking the mouse event to see whether a node is being dragged. */
	public void finishNodeDragging(double x, double y)
	{
		dragging = false;
		delta_x = 0;
		delta_y = 0;
	}
	
	/* Tracking the mouse event to see whether a node is being dragged. */
	public void checkNodeDragging(double x, double y)
	{
		for(int j = 0; j < layer_nodes.size(); j++)
		{
			for(int i = 0; i < layer_nodes.get(j).size(); i++)
			{
				double node_x = layer_nodes.get(j).get(i).getPosition().getX();
				double node_y = layer_nodes.get(j).get(i).getPosition().getY();
				if(((node_x + radius/2 - x) * (node_x  + radius/2 - x) + (node_y + radius/2 - y) * (node_y  + radius/2- y)) < radius * radius / 4)
				{
					dragging = true;
					dragged_node = layer_nodes.get(j).get(i);
					old_dragging_x = x;
					old_dragging_y = y;
					select_node_value = dragged_node.getValue();
					bfsTreeDraw(this);
					break;
				}
			}
			if(dragging == true)
				break;
		}
		
		//update the selection
		if(dragging == false)
		{
			select_node_value = -1;
			bfsTreeDraw(this);
		}
		
	}
	
	/* If a node is being dragged, update its position anode1 its children's positions */
	public void doNodeDragging(double x, double y)
	{
		// update dragged node position
		if (dragging == true) {

			delta_x = x - old_dragging_x;
			delta_y = y - old_dragging_y;
			Point2D pos = new Point2D(dragged_node.getPosition().getX() + delta_x, dragged_node.getPosition().getY() + delta_y);
			dragged_node.setPosition(pos);
			old_dragging_x = x;
			old_dragging_y = y;
			updateTreePos(dragged_node, delta_x, delta_y);
			gc.clearRect(0, 0, canvas_width, canvas_height);
			bfsTreeDraw(this);
		}

	}
	
	/* Adjust the tree width according to the layer value*/
	//layer_idx stores the layer inode1ex that has issue with the new node
	void adjustTreeWidth(int layer_idx)
	{
		int x_left_offset = -radius * 3; // the left child x relative position to the current node's x position (maximum offset)
		int x_right_offset = radius * 3;
		int y_offset = radius * 2; // the left child x position relative (maximum offset)
		
		//Calculate the width of problem layer = # * (node size + node gap) 
		int target_layer_width = layer_nodes.get(layer_idx).size() * radius * 2 + (layer_nodes.get(layer_idx).size() - 1) * x_right_offset * 2;
		Point2D layer_center = new Point2D(canvas_width/2, new_node.getPosition().getY()); 
		
		
		//If the problem layer width is smaller than the canvas width, then no need to adjust the node size but change the current layer's position
		if(target_layer_width < canvas_width)
		{
			//If there are even number of nodes
			if(layer_nodes.get(layer_idx).size() % 2 == 0)  
			{
				int left_node_besides_center = layer_nodes.get(layer_idx).size()/2;
				int right_node_besides_center =  layer_nodes.get(layer_idx).size()/2 + 1;
				System.out.println("center: " + layer_center.getX());
				//Update the nodes positions on the left side
				Point2D pos = new Point2D(layer_center.getX() + x_left_offset,  layer_center.getY());
				System.out.println("left: " + pos.getX());
				layer_nodes.get(layer_idx).get(left_node_besides_center).setPosition(pos);
				for(int i = left_node_besides_center - 1; i >= 0; i--)
				{
					pos = new Point2D(layer_nodes.get(layer_idx).get(i + 1).getPosition().getX() + x_left_offset * 2, layer_center.getY());
					layer_nodes.get(layer_idx).get(i).setPosition(pos);
				}
				
				//Update the nodes positions on the left side
				pos = new Point2D(layer_center.getX() + x_right_offset,  layer_center.getY());
				System.out.println("Right: " + pos.getX());
				layer_nodes.get(layer_idx).get(left_node_besides_center).setPosition(pos);
				for(int i = right_node_besides_center + 1; i < layer_nodes.get(layer_idx).size(); i++)
				{
					pos = new Point2D(layer_nodes.get(layer_idx).get(i - 1).getPosition().getX() + x_right_offset * 2, layer_center.getY());
					layer_nodes.get(layer_idx).get(i).setPosition(pos);
				}
				
				
			}
			//If there are odd number of nodes
			else
			{
				int node_at_center = (layer_nodes.get(layer_idx).size() + 1) /2;
				
				
				Point2D pos = new Point2D(layer_center.getX(),  layer_center.getY());
				layer_nodes.get(layer_idx).get(node_at_center).setPosition(pos);
				
				//Update the nodes positions on the left side
				for(int i = node_at_center - 1; i >= 0; i--)
				{
					pos = new Point2D(layer_nodes.get(layer_idx).get(i + 1).getPosition().getX() + x_left_offset * 2, layer_center.getY());
					layer_nodes.get(layer_idx).get(i).setPosition(pos);
				}
				
				//Update the nodes positions on the left side
				for(int i = node_at_center + 1; i < layer_nodes.get(layer_idx).size(); i++)
				{
					pos = new Point2D(layer_nodes.get(layer_idx).get(i - 1).getPosition().getX() + x_right_offset * 2, layer_center.getY());
					layer_nodes.get(layer_idx).get(i).setPosition(pos);
				}
			}
		}
		
	}

	/* Apply Breath First Search Tree to set basic tree properties for each node: (left or right) parent, children, depth, push node to corresponode1ing layer */
	private void setBasicTreeProperties(Tree tree) {
		
		Queue<Node> queue; // store the retrieved nodes from edges
		Node current_node; // point to the current node processing on

		if (tree.getRoot() == null) {
			return;
		}

		tree.getRoot().setDepth(0); //set the root depth to 0
		tree.getRoot().setParent(null);
		
	
		//Start the BFS search
		queue = new LinkedList<Node>();
		queue.add(tree.getRoot()); // push the root node into queue
		while (!queue.isEmpty()) {
			current_node = queue.remove();
			
			/* If current node is in a new layer, then create a new layer */
			if(layer_nodes.size() - 1 < current_node.getDepth())
			{
				Vector<Node> layer = new Vector<Node>();
				layer.add(current_node);
				layer_nodes.add(layer);
			}
			else
			{
				layer_nodes.get(current_node.getDepth()).add(current_node);
			}
			

			// Check left child
			if (current_node.getLeft() != null) {
				current_node.getLeft().setParent(current_node); //let the left child point to the current node
				current_node.getLeft().setLeft_child_of_parent(true); //let the left child have left parent
				current_node.getLeft().setDepth(current_node.getDepth() + 1); //assign the depth to the left child
				
				queue.add(current_node.getLeft());
			}

			// Check right child
			if (current_node.getRight() != null) {
				current_node.getRight().setParent(current_node); //let the left child point to the current node
				current_node.getRight().setLeft_child_of_parent(false); //let the left child have left parent
				current_node.getRight().setDepth(current_node.getDepth() + 1); //assign the depth to the left child
				queue.add(current_node.getRight());
			}
		}

	}
	
	/* Because of the position change of one layer of nodes, this information is propagated to other layers */
	void changePropogation()
	{

		Node node = root; // node is just a temporary node holder

		Point2D pos = new Point2D(320, 0); // do the setting for the root
		root.setPosition(pos);
		
		
		int x_left_offset = -radius * 3; // the left child x relative 
													// position
													// to the current node's x position (maximum offset)
		int x_right_offset = radius * 3;
		int y_offset = radius * 2; // the left child x position relative (maximum offset)
		
		/* Preset all the nodes' positions; some of them maybe changed later*/
		for(int i = 1; i < layer_nodes.size(); i++)
		{
			for(int j = 0; j < layer_nodes.get(i).size(); j++)
			{
				if(layer_nodes.get(i).get(j).isLeft_child_of_parent())
					pos = new Point2D(layer_nodes.get(i).get(j).getParent().getPosition().getX() + x_left_offset, layer_nodes.get(i).get(j).getParent().getPosition().getY() + y_offset);
				else
					pos = new Point2D(layer_nodes.get(i).get(j).getParent().getPosition().getX() + x_right_offset, layer_nodes.get(i).get(j).getParent().getPosition().getY() + y_offset);
				
				layer_nodes.get(i).get(j).setPosition(pos);
			}
		}
		
		
		/*Traverse all the layers to see if any layer has node overlapping or out of the range issue */
		for(int y = layer_nodes.size() - 1; y >= 0; y--) //start from the bottom layer
		{

			boolean width_issue = false;  //an inode1icator that the tree suffers width problem 
			boolean height_issue = false; //an inode1icator the tree suffers height problem
			
			//Below is to process each layer
			for(int x = 0; x < layer_nodes.get(y).size(); x++)
			{
				//If the tree is too wide
				if(layer_nodes.get(y).get(x).getPosition().getX() < radius/2 || 
						layer_nodes.get(y).get(x).getPosition().getX() > (canvas_width - radius/2))
				{
					width_issue = true;
				}
				
				//If the tree is too high
				else if(layer_nodes.get(y).get(x).getPosition().getY() > (canvas_height - radius/2))
				{
					
				}
				
			}
		}
		
	}
	

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}


	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public Node getNew_node() {
		return new_node;
	}

	public void setNew_node(Node new_node) {
		this.new_node = new_node;
	}
	

	public int getSelect_node_value() {
		return select_node_value;
	}

	public void setSelect_node_value(int select_node_value) {
		this.select_node_value = select_node_value;
	}
}
