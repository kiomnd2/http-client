package param.processor;

import param.Param;
import param.annotation.TargetData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Processor<T> {

    public final T process(Param ... params) {
        Param param = this.setCommon(params);
        HashMap<String, Object> result = new HashMap<>();
        recursive(param, result);
        return makeResult(result);
    }

    private HashMap<String, Object> recursive(Param p, HashMap<String, Object> map)  {
        Field[] declaredFields = p.getClass().getDeclaredFields();
        for (Field declaredField: declaredFields) {
            declaredField.setAccessible(true);
            TargetData annotation = declaredField.getAnnotation(TargetData.class);
            String key = annotation.value();
            try {
                Object object = getObject(declaredField, p);
                if (object instanceof String) {
                    map.put(key, object);
                } else if (object instanceof Param) {
                    Param op = (Param) object;
                    map.put(key, recursive(op, new HashMap<>()));
                } else if (object instanceof List) {
                    List<Param> list = (List<Param>) object;
                    ArrayList<Map<String, Object>> resultList = new ArrayList<>();
                    list.forEach(o -> resultList.add(recursive(o, new HashMap<>())));
                    map.put(key, resultList);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    private Object getObject(Field field, Object target) throws IllegalAccessException {
        return field.get(target);
    }

    // 공통부를 세팅
    protected abstract Param setCommon(Param ... params);
    // 결과 데이터를 생성합니다
    protected abstract T makeResult(Map<String ,Object> map);
}
