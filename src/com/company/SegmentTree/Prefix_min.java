package com.company.SegmentTree;

import java.util.*;
import java.io.*;
//백준 https://www.acmicpc.net/problem/14438
//최솟값 구하는 문제
//node 에 저장되어 있는 합의 범위가 start - end (ex. 0-14)
//실제 값은 배열 a 의 index 에 저장됨.
public class Prefix_min {
    static void init(int[] a, int[] tree, int node, int start, int end) {
        if(start == end) {
            tree[node] = a[start];
        } else {
            init(a, tree, node*2, start, (start/end)/2);
            init(a, tree, node*2+1, (start/end)/2+1, end);
            tree[node] = Math.min(tree[node*2],tree[node*2+1]);
        }
    }

    static int query(int[] tree, int node, int start, int end, int left, int right) {
        if (left > end || right < start) {
            return -1; // 합을 구할 때는 0을 넣었었다. index 범위 밗일 때는 최소값이라는 게 없으니까 -1이 됨.
        }
        if (left <= start && end <= right){
            return tree[node]; //ex) node 가 1일 때 tree 배열의 1인덱스를 리턴하는 것.
        }

        int lmin = query (tree, node*2, start, (start+end)/2, left, right);
        int rmin = query (tree, node*2+1, (start+end)/2+1, end, left, right);
        if(lmin == -1) {
            return rmin; //lmin 이나 rmin 이 구하는 범위 값에 없다면(-1이라면), 다른 한쪽을 최소 값으로 간주한다.
        } else if (rmin == -1) {
            return lmin; //
        } else {
            return Math.min(lmin, rmin);
        }
    }

    //수를 변경하기. index 번째 수를 val 로 변경하기.
    //tree 는 구간이고 (0-14, 0-7, 8-14) a는 숫자가 하나씩 나열된 배열인듯하다. a= (0,1,2,,,,14)
    static void update(int[] a, int[] tree, int node, int start, int end, int index, int val) {
        if( index < start || index > end) { //index 가 주어진 start-end범위 내에 없다.
            return;
        }
        //여기서 index와 node의 차이란?

        if(start == end) {
            a[index] = val;
            tree[node] = val;
            return;
        }

        update(a, tree, node*2, start, (start+end)/2, index, val);
        update(a, tree, node*2+1, (start+end)/2+1, end, index, val);
        tree[node] = Math.min(tree[node*2], tree[node*2+1]); //update 된 값으로, min 값을 업데이트 해야하기 때문. 상위 레벨에서 min 값이 바뀔 수도 있으니깐.
    }

    //Integer.ParseInt. String 타입 숫자를 int 로 변환.
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine()); //readLine(): Reads a line of text. parseInt(): String 타입의 숫자를 int 타입으로 변환. ex) 5
        int[] a = new int[n]; //n 개 주어지는 값을 배열에 넣어야 하니까.
        String[] line = br.readLine().split(" "); //배열에 띄어쓰기 " " 를 기준으로 하나씩 입력합니다. [ ex) "5","4","3", "2", "1"]
        for(int i=0; i<n;i++) {
            a[i] = Integer.parseInt(line[i]); //[5,4,3,2,1] 숫자로 변환.
        }

        // Math.Ceil: 주어진 숫자보다 크거나 같은 숫자 중 가장 작은 숫자를 integer 로 반환. 소수를 무조건 올림. n이 15라면, 4겠구나.
        int h = (int)Math.ceil(Math.log(n) / Math.log(2)); //Math.log2N 이 불가해서 이렇게 해준 것 같다.
        int tree_size = (1 << (h+1)); //비트 시프트 연산자. 1은 1. 왼쪽으로 4만큼 이동하면 10000 = 2의 4승. 16.
        int[] tree = new int[tree_size]; //트리사이즈는 9개지만 넉넉하게 할당. 4n 할당하는 경우도 많다. 무방.
        init(a, tree, 1, 0, n-1);
        int m = Integer.parseInt(br.readLine());
        //--는 m--; m>0; 두 가지 조건을 합친 것이다. 이럴 경우 m 번만큼 반복이 가능함. 6,5,4,3,2,1 만큼 while 문을 도는 것.
        while(m--> 0) {
            line = br.readLine().split(" "); //[2,1,3]
            int what = Integer.parseInt(line[0]); //what은 1 혹은 2. 첫 번째 값 읽기.
            if(what == 1) {
                int index = Integer.parseInt(line[1]); //1이면 첫 번쨰 인덱스
                int val = Integer.parseInt(line[2]);  //값을 바꿔준다.
                update(a, tree, 1, 0, n-1, index-1, val); //index 가 0부터 시작하기 때문이다. 인덱스는 n-1까지가 맞다.
            } else { // 2인 경우.
                int left = Integer.parseInt(line[1]);
                int right = Integer.parseInt(line[2]);
                bw.write(query(tree, 1, 0, n-1, left-1, right-1) + "\n"); //얘가 곧 tree 의 시작점인 tree[1]이다.
            }
        }
        bw.flush();
    }
}
