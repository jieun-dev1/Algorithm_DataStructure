package com.company.SegmentTree;

//출처: https://book.acmicpc.net/ds/segment-tree

//a: 배열 A
//tree: 세그먼트 트리
//node: 노드 번호
//node 에 저장되어 있는 합의 범위가 start - end
public class SegmentTree {
    void init (long[] a, long[] tree, int node, int start, int end) {
        if(start == end) { // 리프 노드인 경우.
            tree[node] = a[start]; //트리의 노드 값이 곧 배열의 수. 가지쳐서 왼쪽/오른쪽 합이 아니라, 하나만 더하면 됨.
        } else {
            init(a, tree, node*2, start, (start+end)/2);
            init(a, tree, node*+1, (start+end)/2+1, end);
            tree[node] = tree[node*2] + tree[node*2+1];
        }

    }
}
