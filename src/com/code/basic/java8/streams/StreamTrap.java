package com.code.basic.java8.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

public class StreamTrap {

    public static void main(String[] args) {
      List<Integer> list = new ArrayList<>();
      list = new CopyOnWriteArrayList<>();
      IntStream.range(1,10000).parallel().forEach(list::add);
      //System.out.println(list);
      List<Integer> list1 = Arrays.asList(1,2,3,4);
      //list1.add(5); // exception java.lang.UnsupportedOperationException AbstractList.java:155)
      list = List.of(4,5,6,7,7); // valid
      List<Integer> list2 = List.of(4,5,6,7,7);
      //list2.add(33); // java.lang.UnsupportedOperationException ImmutableCollections.java:142)
      System.out.println(list2);
    }
}
