package com.gongyu.service.distribute.game.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.google.gson.Gson;

public class BeanCopyUtils extends BeanUtils {
	public static <S, T> void copyList(List<S> sourceList, List<T> targetList, Class<T> targetClass) {
		sourceList.forEach(source -> {
			try {
				T target = targetClass.newInstance();
				BeanCopyUtils.copyProperties(source, target);
				targetList.add(target);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}
	
	public static <S, T> T copyObject(S source, Class<T> targetClass) {
		// 使用Gson序列化进行深拷贝
	    Gson gson = new Gson();
	    T target = gson.fromJson(gson.toJson(source), targetClass);
        return target;
	}
}
