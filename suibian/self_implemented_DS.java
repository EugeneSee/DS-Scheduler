package suibian;

interface CustomDataStructure<T> {
    void add(T data);
    T remove();
    int getSize();
    String getName();
}

class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}

class CustomLinkedList<T> implements CustomDataStructure<T> {
    Node<T> head;
    int size;

    public CustomLinkedList() {
        this.head = null;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public T remove() {
        if (head == null) {
            return null;
        }
        T data = head.data;
        head = head.next;
        size--;
        return data;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String getName() {
        return "Linked List";
    }
}

class CustomQueue<T> implements CustomDataStructure<T> {
    CustomLinkedList<T> list;
    int size;

    public CustomQueue() {
        this.list = new CustomLinkedList<>();
        this.size = 0;
    }

    @Override
    public void add(T data) {
        enqueue(data);
    }

    @Override
    public T remove() {
        return list.remove();
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getName() {
        return "Queue";
    }

    public void enqueue(T data) {
        list.add(data);
        size++;
    }

    public T dequeue() {
        return list.remove();
    }
}


class CustomStack<T> implements CustomDataStructure<T> {
    CustomLinkedList<T> list;

    public CustomStack() {
        this.list = new CustomLinkedList<>();
    }

    public void add(T data) {
        list.add(data);
    }

    public T remove() {
        if (list.head == null) {
            return null;
        }
        Node<T> current = list.head;
        Node<T> prev = null;
        while (current.next != null) {
            prev = current;
            current = current.next;
        }
        if (prev != null) {
            prev.next = null;
        } else {
            list.head = null;
        }
        return current.data;
    }

    public int getSize() {
        return list.getSize();
    }

    @Override
    public String getName() {
        return "Stack";
    }
}
