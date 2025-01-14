package com.aston.lesson2;

/**
 * Универсальная реализация динамического массива, поддерживающая различные операции.
 * Этот класс предоставляет методы для добавления, удаления, получения элементов,
 * а также сортировки с использованием компараторов и естественного порядка.
 *
 * @param <T> тип элементов в этом списке
 */

import java.util.Arrays;
import java.util.Comparator;

public class MyArrayList<T> {
    /**
     * Внутренний массив для хранения элементов.
     */
    private Object[] elements;

    /**
     * Текущее количество элементов в списке.
     */
    private int size;

    /**
     * Стандартная начальная ёмкость списка.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Создаёт пустой список с начальной ёмкостью 10.
     */
    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Добавляет указанный элемент в конец списка.
     *
     * @param element элемент, который необходимо добавить.
     */
    public void add(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    /**
     * Добавляет указанный элемент в указанную позицию списка.
     *
     * @param index индекс, в который будет вставлен элемент.
     * @param element элемент, который необходимо вставить.
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    public void add(int index, T element) {
        checkIndexForAdd(index);
        ensureCapacity();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Возвращает элемент, находящийся на указанной позиции в списке.
     *
     * @param index индекс элемент, который нужно вернуть.
     * @return элемент находящийся на указанной позиции в списке.
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона.
     */
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    /**
     * Удаляет элемент на указанной позиции в списке.
     *
     * @param index индекс элемента, который нужно удалить.
     * @return возвращает удалённый из списка элемент
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона.
     */
    public T remove(int index) {
        checkIndex(index);
        T removedElement = (T) elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null;
        return removedElement;
    }

    /**
     * Удаляет все элементы из списка.
     * После выполнения этого метода список будет пустым.
     */
    public void clear() {
        Arrays.fill(elements, 0, size, null);
        size = 0;
    }

    /**
     * Сортирует элементы списка с использованием предоставленного компаратора.
     *
     * @param comparator компаратор для определения порядка элементов.
     */
    public void sort(Comparator<T> comparator) {
        Arrays.sort((T[]) elements, 0, size, comparator);
    }

    /**
     *Сортирует элементы списка с использованием алгоритма быстрой сортировки.
     *
     * @param comparator компаратор для определения порядка элементов.
     */
    public void quickSort(Comparator<T> comparator) {
        quickSort(0, size - 1, comparator);
    }

    /**
     * Выполняет быструю сортировку элементов между указанными индексами.
     *
     * @param low начальный индекс.
     * @param high конечный индекс.
     * @param comparator компаратор для определения порядка элементов.
     */
    private void quickSort(int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(low, high, comparator);
            quickSort(low, pivotIndex - 1, comparator);
            quickSort(pivotIndex + 1, high, comparator);
        }
    }

    /**
     * Разделяет элементы вокруг опорного элемента для быстрой сортировки.
     *
     * @param low начальный индекс.
     * @param high конечный индекс.
     * @param comparator компаратор для определения порядка элементов.
     * @return индекс опорного элемента
     */
    private int partition(int low, int high, Comparator<T> comparator) {
        T pivot = (T) elements[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare((T) elements[j], pivot) <=0 ) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    /**
     * Меняет местами два элемента в списке.
     *
     * @param i индекс первого элемента.
     * @param j индекс второго элемента.
     */
    private void swap(int i, int j) {
        Object temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

    /**
     * Проверяет достаточную ёмкость внутреннего массива для хранения новых элементов.
     */
    public void ensureCapacity() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
    }

    /**
     * Проверяет, является ли указанный индекс допустимым для получения элементов.
     *
     * @param index индекс для проверки.
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона.
     */
    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Проверяет, является ли указанный индекс допустимым для добавления элементов.
     *
     * @param index индекс для проверки.
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона.
     */
    public void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * @return количество элементов в списке.
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, является ли список пустым.
     *
     * @return {@code true}, если список не содержит элементов; {@code false} в противном случае
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return строковое представление списка.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Демонстрирует функциональность класса MyArrayList.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>();

        list.add("Aston");
        list.add("Homework");
        list.add(2, "Lesson");

        System.out.println("Element at index 1: " + list.get(1));

        System.out.println("Removed element: " + list.remove(2));

        System.out.println("Current number of elements in the list: " + list.size());

        list.sort(String::compareTo);

        System.out.println("List: " + list);

        list.clear();
        System.out.println("After clear: " + list);

        // Пример быстрой сортировки
        MyArrayList<Integer> numberList = new MyArrayList<>();
        numberList.add(34);
        numberList.add(7);
        numberList.add(23);
        numberList.add(32);
        numberList.add(5);
        numberList.add(62);

        System.out.println("Before quickSort: " + numberList);
        numberList.quickSort(Integer::compareTo);
        System.out.println("After quickSort: " + numberList);

        // Тест с большим количеством данных
        MyArrayList<Integer> largeList = new MyArrayList<>();
        for (int i = 1000; i >= 1; i--) {
            largeList.add(i);
        }

        System.out.println("Before sorting large list: " + largeList);
        largeList.quickSort(Integer::compareTo);
        System.out.println("After sorting large list: " + largeList);

        // Тест с типом данных Double
        MyArrayList<Double> doubleList = new MyArrayList<>();
        doubleList.add(5.6);
        doubleList.add(3.1);
        doubleList.add(9.8);
        doubleList.add(1.4);

        System.out.println("Before sorting double list: " + doubleList);
        doubleList.quickSort(Double::compareTo);
        System.out.println("After sorting double list: " + doubleList);
    }
}
