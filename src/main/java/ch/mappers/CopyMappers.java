package ch.mappers;

import org.springframework.beans.BeanUtils;

public class CopyMappers {
    public static  void copy(Object s, Object d){
        BeanUtils.copyProperties(s, d);
    }
}
