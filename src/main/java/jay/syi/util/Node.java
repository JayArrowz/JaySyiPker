package jay.syi.util;

public class Node {
	public Node next;

	public Node prev;

	public void unlink() {
		if (this.prev == null)
			return;
		this.prev.next = this.next;
		this.next.prev = this.prev;
		this.next = null;
		this.prev = null;
	}
}
