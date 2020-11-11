package jay.syi.util;

public final class Deque {
	private final Node head;
	private Node current;

	public Deque() {
		this.head = new Node();
		this.head.next = this.head;
		this.head.prev = this.head;
	}

	public void insertBack(Node node) {
		if (node.prev != null)
			node.unlink();
		node.prev = this.head.prev;
		node.next = this.head;
		node.prev.next = node;
		node.next.prev = node;
	}

	public void insertFront(Node node) {
		if (node.prev != null)
			node.unlink();
		node.prev = this.head;
		node.next = this.head.next;
		node.prev.next = node;
		node.next.prev = node;
	}

	public Node popFront() {
		Node node = this.head.next;
		if (node == this.head)
			return null;
		node.unlink();
		return node;
	}

	public Node getFront() {
		Node node = this.head.next;
		if (node == this.head) {
			this.current = null;
			return null;
		}
		this.current = node.next;
		return node;
	}

	public Node getBack() {
		Node node = this.head.prev;
		if (node == this.head) {
			this.current = null;
			return null;
		}
		this.current = node.prev;
		return node;
	}

	public Node getNext() {
		Node node = this.current;
		if (node == this.head) {
			this.current = null;
			return null;
		}
		this.current = node.next;
		return node;
	}

	public Node getPrevious() {
		Node node = this.current;
		if (node == this.head) {
			this.current = null;
			return null;
		}
		this.current = node.prev;
		return node;
	}

	public void clear() {
		if (this.head.next == this.head)
			return;
		while (true) {
			Node node = this.head.next;
			if (node == this.head)
				return;
			node.unlink();
		}
	}
}