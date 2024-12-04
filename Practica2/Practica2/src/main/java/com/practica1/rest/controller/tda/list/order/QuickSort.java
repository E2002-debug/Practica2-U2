package com.practica1.rest.controller.tda.list.order;


import com.practica1.rest.controller.tda.list.LinkedList;
import java.lang.reflect.Method;
import java.util.Random;

public class QuickSort<E> {

    /**
     * Ordena una lista enlazada de valores primitivos de forma ascendente.
     */
    public LinkedList<E> sortPrimitiveAscendent(LinkedList<E> linkedList) throws Exception {
        E[] array = linkedList.toArray();
        quickSortPrimitive(array, 0, array.length - 1, LinkedList.ASC);
        linkedList.toList(array);
        return linkedList;
    }

    /**
     * Ordena una lista enlazada de valores primitivos de forma descendente.
     */
    public LinkedList<E> sortPrimitiveDescendent(LinkedList<E> linkedList) throws Exception {
        E[] array = linkedList.toArray();
        quickSortPrimitive(array, 0, array.length - 1, LinkedList.DESC);
        linkedList.toList(array);
        return linkedList;
    }

    /**
     * Ordena una lista enlazada de objetos basada en un atributo específico de forma ascendente.
     */
    public LinkedList<E> sortModelsAscendent(LinkedList<E> linkedList, String attribute) throws Exception {
        E[] array = linkedList.toArray();
        quickSortModels(array, 0, array.length - 1, attribute, LinkedList.ASC);
        linkedList.toList(array);
        return linkedList;
    }

    /**
     * Ordena una lista enlazada de objetos basada en un atributo específico de forma descendente.
     */
    public LinkedList<E> sortModelsDescendent(LinkedList<E> linkedList, String attribute) throws Exception {
        E[] array = linkedList.toArray();
        quickSortModels(array, 0, array.length - 1, attribute, LinkedList.DESC);
        linkedList.toList(array);
        return linkedList;
    }

    // Método QuickSort para valores primitivos
    private void quickSortPrimitive(E[] array, int low, int high, int order) {
        if (low < high) {
            int pivotIndex = partitionPrimitive(array, low, high, order);
            quickSortPrimitive(array, low, pivotIndex - 1, order);
            quickSortPrimitive(array, pivotIndex + 1, high, order);
        }
    }

    private int partitionPrimitive(E[] array, int low, int high, int order) {
        E pivot = array[new Random().nextInt(high - low + 1) + low];
        int i = low - 1;
        for (int j = low; j <= high; j++) {
            if (compare(array[j], pivot, order)) {
                i++;
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        return i;
    }

    // Método QuickSort para objetos con atributos
    private void quickSortModels(E[] array, int low, int high, String attribute, int order) throws Exception {
        if (low < high) {
            int pivotIndex = partitionModels(array, low, high, attribute, order);
            quickSortModels(array, low, pivotIndex - 1, attribute, order);
            quickSortModels(array, pivotIndex + 1, high, attribute, order);
        }
    }

    private int partitionModels(E[] array, int low, int high, String attribute, int order) throws Exception {
        E pivot = array[new Random().nextInt(high - low + 1) + low];
        Object pivotValue = existAttribute(pivot, attribute);
        int i = low - 1;
        for (int j = low; j <= high; j++) {
            if (compare(existAttribute(array[j], attribute), pivotValue, order)) {
                i++;
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        return i;
    }

    // Métodos de ayuda
    private Object existAttribute(E obj, String attribute) throws Exception {
        Method method = null;
        attribute = "get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        for (Method m : obj.getClass().getMethods()) {
            if (m.getName().equals(attribute)) {
                method = m;
                break;
            }
        }
        if (method == null) {
            throw new NoSuchMethodException("Attribute " + attribute + " not found in class " + obj.getClass().getName());
        }
        return method.invoke(obj);
    }

    private boolean compare(Object a, Object b, int order) {
        if (a instanceof Comparable && b instanceof Comparable) {
            Comparable<Object> compA = (Comparable<Object>) a;
            if (order == LinkedList.ASC) {
                return compA.compareTo(b) < 0;
            } else {
                return compA.compareTo(b) > 0;
            }
        }
        return false;
    }
}

