package com.bincsoft.forms.properties;


import com.bincsoft.forms.BincsoftBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oracle.dss.graph.Graph;


public class DynamicFormsProperty extends FormsPropertyHandler {
    @Override
    public boolean handleProperty(String sParams, BincsoftBean bean) {
        if (super.handleProperty(sParams, bean)) {
            String[] params = sParams.split(bean.getDelimiter());
            if (params.length > 0) {
                Object[] args = Arrays.copyOfRange(params, 1, params.length);
                Method method = getMethod(params[0], args.length);

                if (method == null) {
                    log(String.format("Could not find any method named '%s' with '%s' args", params[0], args.length));
                    return true;
                }

                try {
                    method.setAccessible(true);
                    if (args.length == 0) {
                        method.invoke(bean.getWrappedObject());
                    } else {
                        log(String.format("Found method '%s', trying to invoke with the following args:",
                                          method.getName()));
                        for (Object obj : args) {
                            log(obj.toString());
                        }

                        try {
                            args = tryConvertObjects(args);
                            method.invoke(bean.getWrappedObject(), args);
                        } catch (ConvertException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private Method getMethod(String name, int paramCount) {
        Class graphClass = Graph.class;
        Method[] methods = graphClass.getMethods();
        for (Method m : methods) {
            if (m.getName().equals(name) && m.getParameterTypes().length == paramCount) {
                return m;
            }
        }
        return null;
    }

    private Object[] tryConvertObjects(Object[] args) throws ConvertException {
        List<Object> list = new ArrayList<Object>();
        boolean processed = false;

        for (int i = 0; i < args.length; i++) {
            processed = false;
            try {
                list.add(Integer.valueOf(args[i].toString()));
                processed = true;
            } catch (NumberFormatException e) {
                //e.printStackTrace();
                log("Argument wasn't an Integer");
            }

            if (args[i].toString().equalsIgnoreCase("true") || args[i].toString().equalsIgnoreCase("false")) {
                list.add(Boolean.valueOf(args[i].toString().equalsIgnoreCase("true")));
                processed = true;
            }

            if (!processed) {
                list.add(args[i].toString());
            }
        }

        if (list.size() != args.length) {
            throw new ConvertException("Could not convert all parameters.");
        }

        return list.toArray();
    }

    @Override
    public IFormsProperty getFormsProperty() {
        return super.getFormsProperty();
    }

    private class ConvertException extends Exception {
        public ConvertException(String msg) {
            super(msg);
        }
    }
}
