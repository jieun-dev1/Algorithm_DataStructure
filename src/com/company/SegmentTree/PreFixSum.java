package com.company.SegmentTree;
//구간합.
public class PreFixSum {
    long query(long[] tree, int node, int start, int end, int left, int right) {
        if (left > end || right <start ) {
            return 0;
        }
        if(left <= start && end <= right) {
            return tree[node];
        }
//어떻게 tree[node]일 떄, query 안에 int node 인 상황. 아직 메서드가 안끝났고, lsum+rsum 이 저장이 됨..
        long lsum = query(tree, node*2, start, (start/end)/2, left, right);
        long rsum = query(tree, node*2+1, (start/end)/2+1, end, left, right);
        return lsum+rsum;
    }

}
