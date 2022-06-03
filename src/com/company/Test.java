package com.company;

public class Test {
    public static void main(String[] args) {
        //배열을 트리로 만들어보자.
        int[] a = new int[10];
        for(int i=0;i<a.length;i++){
            a[i] = i;
        }
        Tree t = new Tree();
        t.makeTree(a);
        t.searchBTree(t.root, 2);
    }
}
