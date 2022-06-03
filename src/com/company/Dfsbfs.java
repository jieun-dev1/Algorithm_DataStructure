package com.company;

// 출처: https://www.youtube.com/watch?v=W3jNbNGyjMs&t=0s
//여러번 볼 것.. 인덱스를 다르게 넣었을 때/ 재귀적으로 넣었을 때의 차이가 좀 어렵다.

import java.util.*;

//Queue 클래스 구현
//LinkedList 를 사용해서 구현하면, 데이터의 추가 삭제가 쉽다.
//<T>를 사용하는 건 제네릭을 사용한다는 것.



class Queue<T> {

    class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    //Queue는 앞뒤의 데이터를 알고 있어야 하기 때문에.

    private Node<T> first;
    private Node<T> last;

    public void add(T item) {
        Node<T> t = new Node<T>(item); //item 을 가지고 노드 하나 생성.
        if (last != null) {  //마지막 노드가 있다면
            last.next = t; //뒤에 새로 생성한 노드를 붙이고
        }
        last = t; //새로 생성한 노드가 가장 끝에 있으니까 last 를 t 로 다시 정의한다. last 가 null 이든 아니든 항상 실행해야 하는 구문.


        if (first == null) {
            first = last; //데이터가 없을 때는 첫 번째 데이터가 곧 마지막 데이터임.
        }
    }

    public T remove() {
        if (first == null) { //queue가 비어있으면.
            throw new NoSuchElementException();
        }

        T data = first.data; //데이터를 백업 하고.
        first = first.next; //다음 애를 first 로 만들어줌.

        if (first == null) {
            last = null;
        }
        return data;
    }

    public T peek() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.data;
    }

    public boolean isEmpty() {
        return first == null;
    }
}

//Graph 구현
class Graph {
    class Node{
        int data;
        LinkedList<Node> adjacent; //인접한 노드들과의 관계를 표현
        boolean marked;
        Node (int data) {
            this.data = data;
            this.marked = false; //marked 를 false 로 초기화
            adjacent = new LinkedList<Node>(); //링크드 리스트 준비시킴.
        }
    }
    Node[] nodes;        //노드를 저장할 배열 필요.
    Graph(int size) {
        nodes = new Node[size]; //size 만큼 배열방을 만들어주자. Node, Node, Node
        for (int i=0; i<size; i++) {
            nodes[i] = new Node(i); //Node(0), Node(1), Node(2),
        }
    }
    void addEdge(int i1, int i2) {
        Node n1 = nodes[i1]; //데이터가 index 와 같기 때문에, 받은 숫자를 인덱스로 사용할 수 있다.
        Node n2 = nodes[i2];
        //상대방이 있는지 확인하고, 없으면 추가해준다.
        if (!n1.adjacent.contains(n2)){
            n1.adjacent.add(n2);
        }
        if (!n2.adjacent.contains(n1)){
            n2.adjacent.add(n1);
        }
    }

    void dfs() {
        dfs(0); //dfs 를 그냥 호출할 경우 0 부터 시작한다.
    }

    void dfs(int index) { //시작 index 를 받아서 dfx 순회를 출력함.
        Node root = nodes[index]; // 해당 인덱스로 노드를 가져옴.
        Stack<Node> stack = new Stack<Node>();
        stack.push(root); //현재 노드를 스택에 추가해주고
        root.marked = true; //스택에 들어갔다고 표시해주기.
        while(!stack.isEmpty()) {
            Node r = stack.pop();
            //노드의 자식/인접 노드를 추가하되, stack 에 marked 되지 않은 애들만
            for(Node n: r.adjacent) {
                if(n.marked == false) {
                    n.marked = true;
                    stack.push(n);
                }
            }
            visit(r);
        }
    }

    List<Integer> list = new ArrayList<>();

    void bfs() {
        bfs(0); //dfs 를 그냥 호출할 경우 0 부터 시작한다.
    }

    void bfs(int index) {
        Node root = nodes[index];
        Queue<Node> queue = new Queue<Node>();
        queue.add(root);
        root.marked = true;
        while(!queue.isEmpty()) { //queue 에 데이터 없을 떄까지 반복
            Node r = queue.remove();
            for (Node n: r.adjacent) {
                if(n.marked == false) {
                    n.marked = true;
                    queue.add(n);
                }
            }
            visit(r);
        }
    }

    //재귀 호출을 할 때는 링크드 리스트가 노드의 주소를 가지고 있음. 그래서 재귀함수는 노드의 주소를 받는 형태가 되어야 한다.
    void dfsR(Node r) {
        if (r == null) return;
        r.marked = true;
        visit(r);
        for (Node n: r.adjacent) {
            if (n.marked == false) { //호출이 되지 않은 자식들 호출해주기.
                dfsR(n);
            }
        }
    }

    void dfsR(int index) {
        Node r = nodes[index];
        dfsR(r); //해당 노드 시작으로 재귀호출 진행.
    }

    void dfsR() {
        dfsR(0); //dfs 를 그냥 호출할 경우 0 부터 시작한다.
    }

    void visit(Node n) {
        System.out.print(n.data + " ");
    }
}

public class Dfsbfs {
    public static void main(String[] args) {
        Graph g = new Graph(9);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(3, 5);
        g.addEdge(5, 6);
        g.addEdge(5, 7);
        g.addEdge(6, 8);
        g.dfsR(3);
    }
}



//Queue만 있을 때.
//public class Test {
//    public static void main(String[] args) {
//        Queue<Integer> q = new Queue<Integer>();
//        q.add(1);
//        q.add(2);
//        q.add(3);
//        q.add(4);
//        System.out.println(q.remove());
//        System.out.println(q.remove());
//        System.out.println(q.peek());
//        System.out.println(q.remove());
//        System.out.println(q.isEmpty());
//        System.out.println(q.remove());
//        System.out.println(q.isEmpty());
//
//    }
//}