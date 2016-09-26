package lpm;

public class Trie {
	private TrieNode root;
	private final int INTLENGTH = 8;

	public Trie(){
		root = new TrieNode(0);
	}
	public TrieNode getRoot() {
		return root;
	}

	public void setRoot(TrieNode root) {
		this.root = root;
	}
	
	private boolean bitInN(long prefix, int bit){
		System.out.println("prefix " + Long.toBinaryString(prefix) + " bit " + bit + " is " + ((prefix >>> bit) % 2 == 0));
		return (prefix >>> bit) % 2 == 0;
	}

	public void insert(long prefix, int prefixLen){
		TrieNode node = root;
		
		for(int i = INTLENGTH; i > INTLENGTH - prefixLen; i--){
			if(bitInN(prefix, i - 1)){
				node.leftChild = (node.leftChild == null ? new TrieNode(0) : node.leftChild);
				node = node.leftChild;
			} else {
				node.rightChild = (node.rightChild == null ? new TrieNode(1) : node.rightChild);
				node = node.rightChild;
			}
		}
		node.isLeaf = true;
	}
	
	public boolean remove(long prefix, int prefixLen){
		TrieNode node = root;
		TrieNode nodeDelete = null;
		TrieNode parent = root;
		
		for(int i = INTLENGTH; i > INTLENGTH - prefixLen; i--){
			if(bitInN(prefix, i - 1)){
				if(node.leftChild == null) return false;
				if(node.isLeaf == false) nodeDelete = parent;
				else nodeDelete = null;
				parent = node;
				node = node.leftChild;
			} else {
				if(node.rightChild == null) return false;
				if(node.isLeaf == false) nodeDelete = parent;
				else nodeDelete = null;
				parent = node;
				node = node.rightChild;
			}
		}
		
		if(nodeDelete != null){
			nodeDelete.leftChild = nodeDelete.rightChild = null;
		} else {
			node.isLeaf = false;
		}
		return true;
	}
	
	public boolean search(long prefix, int prefixLen){
		TrieNode node = root;
		
		for(int i = INTLENGTH; i > INTLENGTH - prefixLen; i--){
			if(bitInN(prefix, i - 1)){
				if(node.leftChild == null) return false;
				node = node.leftChild;
			} else {
				if(node.rightChild == null) return false;
				node = node.rightChild;
			}
		}
		return node.isLeaf;
	}
	
	public long searchPrefix(long prefix){
		TrieNode node = root;
		long longestMatch = 0;
		
		for(int i = INTLENGTH; i > 0; i--){
			if(bitInN(prefix, i - 1)){
				if(node.leftChild == null) return longestMatch;
				node = node.leftChild;
			} else {
				if(node.rightChild == null) return longestMatch;
				node = node.rightChild;
				longestMatch += 1 << (i - 1);
			}
		}
		return longestMatch;
	}
	
	public void dump(){
		System.out.println("===== start dump =====");
		dumpInternal(root);
		System.out.println("\n=====  end  dump =====");
	}
	
	private void dumpInternal(TrieNode node){
		System.out.print(node.val + ",");
		if(node.leftChild != null)  dumpInternal(node.leftChild);
		if(node.rightChild != null) dumpInternal(node.rightChild);
	}

	public class TrieNode{
		private TrieNode leftChild;
		private TrieNode rightChild;
		private int val;
		private boolean isLeaf;
		public int getVal() {
			return val;
		}
		public void setVal(int val) {
			this.val = val;
		}
		public boolean isLeaf() {
			return isLeaf;
		}
		public void setLeaf(boolean isLeaf) {
			this.isLeaf = isLeaf;
		}
		public TrieNode(){
			leftChild = rightChild = null;
			isLeaf = false;
		}
		public TrieNode(int v){
			leftChild = rightChild = null;
			val = v;
			isLeaf = false;
		}
		public TrieNode getLeftChild() {
			return leftChild;
		}
		public void setLeftChild(TrieNode leftChild) {
			this.leftChild = leftChild;
		}
		public TrieNode getRightChild() {
			return rightChild;
		}
		public void setRightChild(TrieNode rightChild) {
			this.rightChild = rightChild;
		}
		
	}
	
	public static void main(String args[]){
		Trie trie = new Trie();
		trie.insert(128,1);
		trie.dump();
		System.out.println(trie.search(127, 1));
		System.out.println(trie.search(128, 1));
		System.out.println(trie.search(192, 1));
		System.out.println(trie.search(192, 2));
		System.out.println(trie.searchPrefix(192));
		trie.insert(192,2);
		trie.dump();
		System.out.println(trie.searchPrefix(192));
		System.out.println(trie.searchPrefix(193));
		System.out.println(trie.searchPrefix(194));
		trie.insert(224,3);
		trie.dump();
		System.out.println(trie.search(224, 3));
		System.out.println(trie.searchPrefix(224));
		System.out.println(trie.remove(192,2));
		trie.dump();
		System.out.println(trie.searchPrefix(192));
		System.out.println(trie.searchPrefix(224));
		System.out.println(trie.remove(224,3));
		trie.dump();
		System.out.println(trie.searchPrefix(224));
	}
}
