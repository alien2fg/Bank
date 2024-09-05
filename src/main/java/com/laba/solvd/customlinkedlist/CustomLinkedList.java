package com.laba.solvd.customlinkedlist;

import java.util.*;

public class CustomLinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T>{
        T item;
        Node<T> next;

        Node(T item) {
            this.item = item;
        }
    }

    public CustomLinkedList(){
        this.head=null;
        this.tail=null;
        this.size=0;
    }

    private Node<T> node(int index){
        Node<T>x =head;
        for (int i =0;i<index;i++){
            x=x.next;
        }
        return x;
    }

    private void checkIndex(int index) {
        if (index <0 || index >size ) throw new IndexOutOfBoundsException();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o)>=0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T item = current.item;
                current = current.next;
                return item;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i =0;
        for (Node<T> x = head; x!=null; x=x.next){
            array[i++]=x.item;
        }
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length>size)
            a=(T1[])java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(),size);
        int i =0;
        Object[] result =a;
        for (Node<T> x = head;x!=null;x=x.next){
            result[i++]=x.item;
        }
        if (a.length>size)
            a[size]=null;

        return a;
    }

    @Override
    public boolean add(T t) {
        final Node<T> newNode = new Node<>(t);
        if (tail == null){
            head=tail=newNode;
        }else {
            tail.next=newNode;
            tail=newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<T> prev = null;
        Node<T> current = head;
        while (current!=null){
            if (Objects.equals(current.item,o)){
                if (prev==null){
                    head=current.next;
                    if (head==null){
                        tail=null;
                    }
                }else{
                    prev.next=current.next;
                    if(current.next==null){
                        tail=prev;
                    }
                }
                size--;
                return true;
            }
            prev=current;
            current=current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o:c){
            if (!contains(o)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified= false;
        for (T t : c){
            if (add(t)){
                modified=true;
            }
        }
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndex(index);
        boolean modified = false;
        Node<T> prev= (index ==0 )? null: node(index-1);
        Node<T> next = (index ==size)? null: node(index);
        for (T t:c){
            Node<T> newNode = new Node<>(t);
            if (prev==null){
                newNode.next=head;
                head = newNode;
            }else {
                newNode.next=prev.next;
                prev.next=newNode;
            }
            prev=newNode;
            size++;
            modified=true;
        }
        if (tail==null){
            tail=prev;
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified =false;
        for (Object o:c){
            while (remove(o)){
                modified=true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified=false;
        Node<T> current = head;
        Node<T> prev = null;

        while (current!=null){
            if (!c.contains(current.item)){
                if (prev==null){
                    head=current.next;
                } else {
                    prev.next=current.next;
                }
                if (current==tail){
                    tail=prev;
                }
                current= current.next;
                size--;
                modified=true;
            }else {
                prev=current;
                current=current.next;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        head=tail=null;
        size=0;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);
        Node<T> node= node(index);
        T odlValue = node.item;
        node.item=element;
        return odlValue;
    }

    @Override
    public void add(int index, T element) {
        checkIndex(index);
        Node<T> newNode = new Node<>(element);
        if (index ==0 ){
            newNode.next=head;
            head=newNode;
            if (tail==null){
                tail=newNode;
            }
        }else {
            Node<T> prev = node(index-1);
            newNode.next=prev.next;
            prev.next=newNode;
            if(newNode.next == null){
                tail=newNode;
            }
        }
        size++;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> prev = (index==0)? null:node(index-1);
        Node<T> toRemove = (prev==null)? head: prev.next;
        T item = toRemove.item;
        if (prev==null){
            head=head.next;
            if (head==null){
                tail=null;
            }
        }else {
            prev.next = toRemove.next;
            if(toRemove.next==null){
                tail=prev;
            }
        }
        size--;
        return item;
    }

    @Override
    public int indexOf(Object o) {
        int index =0;
        for (Node<T>x =head;x!=null;x=x.next){
            if (Objects.equals(0,x.item)){
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index =0;
        int lastIndex = -1;
        for (Node<T>x =head;x!=null;x=x.next){
            if (Objects.equals(0,x.item)){
                lastIndex=index;
            }
            index++;
        }
        return lastIndex;
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListIterator<T>() {
            private Node<T> current = (index == size)?null: node(index);
            private Node<T> lastReturned = null;
            private int nextIndex = index;

            @Override
            public boolean hasNext() {
                return nextIndex<size;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                lastReturned= current;
                current=current.next;
                nextIndex++;
                return lastReturned.item;
            }

            @Override
            public boolean hasPrevious() {
                return nextIndex>0;
            }

            @Override
            public T previous() {
                if (!hasPrevious()) throw new NoSuchElementException();
                current=(current==null)? tail: node(nextIndex-1);
                lastReturned=current;
                nextIndex--;
                return current.item;
            }

            @Override
            public int nextIndex() {
                return nextIndex;
            }

            @Override
            public int previousIndex() {
                return nextIndex-1;
            }

            @Override
            public void remove() {
                if (lastReturned==null) throw new NoElementToRemoveException("No element to remove");
                Node<T> lastNext = lastReturned.next;
                CustomLinkedList.this.remove(lastReturned.item);
                if (current == lastReturned)
                    current = lastNext;
                else
                    nextIndex--;
                lastReturned = null;
            }

            @Override
            public void set(T t) {
                if (lastReturned==null) throw new NoElementToRemoveException("No element to remove");
                lastReturned.item=t;
            }

            @Override
            public void add(T t) {
                lastReturned = null;
                if (current == null) {
                    add(t);
                } else {
                    Node<T> newNode = new Node<>(t);
                    newNode.next = current;
                    if (current == head) {
                        head = newNode;
                    } else {
                        Node<T> prev = node(nextIndex - 1);
                        prev.next = newNode;
                    }
                }
                size++;
                nextIndex++;
            }
        };
    }


    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();
        List<T> subList = new CustomLinkedList<>();
        Node<T> current = node(fromIndex);
        for (int i =fromIndex;i<toIndex;i++){
            subList.add(current.item);
            current=current.next;
        }
        return subList;
    }


}
