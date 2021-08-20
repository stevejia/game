package com.gongyu.service.distribute.game.utils;

import java.util.List;

import org.springframework.beans.BeanUtils;

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
}
