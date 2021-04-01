package de.stulu.strader.util;

import java.util.ArrayList;
import java.util.List;

public class SMap <A,B>{
    public SMap(){
    }
    public List<A> AList = new ArrayList<A>();
    public List<B> BList = new ArrayList<B>();

    public void add(A a, B b){
        AList.add(a);
        BList.add(b);
    }
    public void put(A a, B b){
        AList.add(a);
        BList.add(b);
    }
    public int count(){
        int b = BList.size();
        int a = AList.size();
        return Math.min(a,b);
    }
    public boolean remove(A a, B b){
        return (BList.remove(b) && AList.remove(a));
    }
    public boolean delete(A a, B b){
        return (BList.remove(b) && AList.remove(a));
    }
    public boolean contains(A a, B b){
        return (BList.contains(b) && AList.contains(a));
    }
    public boolean containsA(A a){
        return (AList.contains(a));
    }
    public boolean containsB(B b){
        return (BList.contains(b));
    }

    public A getA(int index){
        return AList.get(index);
    }
    public B getB(int index){
        return BList.get(index);
    }

    public A findA(B b){
        int i = BList.indexOf(b);
        return AList.get(i);
    }
    public B findB(A a){
        int i = AList.indexOf(a);
        return BList.get(i);
    }
}
