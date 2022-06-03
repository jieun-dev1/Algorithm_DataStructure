package com.company;

//Tree의 멤버 변수는 Node와 Node 타입의 루트다.
public class Tree {
    class Node {
        int data;
        Node left;
        Node right;

        Node (int data) {
            this.data = data;
        }
    }
    Node root;
    //루트가 재귀함수를 시작함.
    public void makeTree(int[] a) {
        root = makeTreeR(a, 0, a.length-1);
    }

    public Node makeTreeR(int[]a, int start, int end){
        if(start > end) return null; // 함수 반복적으로 호출하다가
        // 시작점이 끝나는 점보다 커지면 재귀호출 마치고 null 반환.
        // 재귀호출에서 가장 명확한 부분: 끝나는 시점 명확히 해주기.
        int mid = (start + end) /2;
        Node node = new Node(a[mid]);
        node.left = makeTreeR(a, start, mid-1);
        node.right = makeTreeR(a, mid+1, end);
        return node;
    }
    public void searchBTree (Node n, int find) {
        if(find < n.data) {
            System.out.println("Data is smaller than "+ n.data);
            searchBTree(n.left, find);
        } else if (find > n.data) {
            System.out.println("Data is bigger than" + n.data);
            searchBTree(n.right, find);
        } else {
            System.out.println("Data found");
        }
    }
}



