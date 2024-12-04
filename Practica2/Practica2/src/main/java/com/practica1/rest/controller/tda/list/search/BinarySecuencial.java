package com.practica1.rest.controller.tda.list.search;

import java.lang.reflect.Method;
import java.util.LinkedList;

public class BinarySecuencial {

    public LinkedList<Object> binaryPrimitiveSecuencial(Object[] array, Object data, int low, int high) {
        LinkedList<Object> result = new LinkedList<>();
        if (low > high) {
            return result;
        }

        int mid = (low + high) / 2;
        if (array[mid].equals(data)) {
            result.add(array[mid]);
        }

        if (mid + 1 <= high) {
            result.addAll(binaryPrimitiveSecuencial(array, data, mid + 1, high));
        }
        if (mid - 1 >= low) {
            result.addAll(binaryPrimitiveSecuencial(array, data, low, mid - 1));
        }
        return result;
    }

    public LinkedList<Object> binaryModelsSecuencial(LinkedList<Object> list, Object data, int low, int high, String attribute) throws Exception {
        LinkedList<Object> result = new LinkedList<>();
        if (low > high) {
            return result;
        }

        int mid = (low + high) / 2;
        Object model = list.get(mid);
        String targetValue = (String) getAttributeValue(model, attribute);
        String searchValue = data.toString().toLowerCase();

        if (targetValue.equals(searchValue)) {
            result.add(model);
        }

        if (mid + 1 <= high) {
            result.addAll(binaryModelsSecuencial(list, data, mid + 1, high, attribute));
        }
        if (mid - 1 >= low) {
            result.addAll(binaryModelsSecuencial(list, data, low, mid - 1, attribute));
        }

        return result;
    }

   
    private Object getAttributeValue(Object model, String attribute) throws Exception {
        Method method = model.getClass().getMethod("get" + capitalize(attribute));
        return method.invoke(model);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

