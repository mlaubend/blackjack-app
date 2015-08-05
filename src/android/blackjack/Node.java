package android.blackjack;
import android.widget.ImageView;


public class Node {
	public ImageView[] iArray;
	public Node next = null;
	public Node previous = null;
	
	public Node(){
		iArray = new ImageView[13];
	}
	
	public Node(ImageView[] iArray){
		this.iArray = iArray;
	}
	
	public void add(Node node){
		if (next == null)
			next = node;
		else {
			node.next = next;
			next = node;
		}
	}
}
