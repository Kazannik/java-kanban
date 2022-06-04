package dev.service;

import dev.domain.TaskBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomLinkedList<E extends TaskBase> {
    Map<Integer, Node<E>> nodesMap = new HashMap<Integer, Node<E>>();

    Node<E> first;

    Node<E> last;

    public void add(TaskBase task) {
        remove(task.getTaskId());
        linkLast((E) task);
        nodesMap.put(task.getTaskId(), last);
    }

    /* linkLast будет добавлять задачу в конец этого списка  */
    void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
    }

    public void remove(int id) {
        final Node<E> removedNode = nodesMap.remove(id);
        if (removedNode != null) {
            removeNode(removedNode);
        }
    }

    void removeNode(Node<E> node) {
        final Node<E> next = node.next;
        final Node<E> prev = node.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
    }

    public void clear() {
        nodesMap.clear();
        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
    }

    /* ТЗ №5: собирать все задачи из него в обычный ArrayList.
    Реализация метода getHistory должна перекладывать задачи из связного списка
    в ArrayList для формирования ответа. */
    public List<TaskBase> getTasks() {
        List<TaskBase> result = new ArrayList<>();
        for (Node<E> x = first; x != null; x = x.next) {
            result.add(x.item);
        }
        return result;
    }

    private static class Node<E extends TaskBase> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}