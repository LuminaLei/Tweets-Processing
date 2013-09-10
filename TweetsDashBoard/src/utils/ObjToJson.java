package utils;

import java.lang.reflect.Field;

public class ObjToJson {
    public static String objToJsonString(Object obj) {
        String temp, temp1;
        String json = "str_empty";

        if (obj == null) {
            return json;
        }

        StringBuilder buff = new StringBuilder();
        Field fields[] = obj.getClass().getFields();
        // System.out.println(fields.length);
        try {
            buff.append("{");
            int i = 0;

            for (Field field : fields) {
                if (i != 0) {
                    buff.append(",");
                }
                buff.append("\"");
                buff.append(field.getName());
                buff.append("\"");
                buff.append(":");
                buff.append("\"");
                if (field.get(obj) == null)
                    buff.append("");
                else {
                    if (field.get(obj).getClass().getName().contains("String")) {
                        temp = field.get(obj).toString();
                        temp = temp.replace("\"", "\\\"");
                        buff.append(temp);
                    } else {
                        buff.append(field.get(obj));
                    }
                }
                buff.append("\"");
                i++;
            }
            buff.append("}");
            json = buff.toString();
        } catch (Exception e) {
            throw new RuntimeException("cause:" + e.toString());
        }
        return json;
    }
}