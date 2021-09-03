package com.gongyu.service.distribute.game.service.impl.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.gongyu.service.distribute.game.utils.BeanCopyUtils;

public class BaseKlineService {
	public <D, K, M> List<D> queryKline(K k, String tableName, String methodName, Class<D> dClass) {
		List<D> dtoList = null;
		try {
			String packageStr = "com.gongyu.service.distribute.game";
			String firstStr = tableName.substring(0, 1);
			String mapperName = tableName.replaceFirst(firstStr, firstStr.toLowerCase()) + "Mapper";

			String exampleCls = packageStr + ".model.entity." + tableName + "Example";

			Class<?> ExampleClass = Class.forName(exampleCls);

			Field mapperField = this.getClass().getDeclaredField(mapperName);
			Object mapper = mapperField.get(this);

			Method method = mapper.getClass().getMethod(methodName, ExampleClass);

			List<?> entities = (List<?>) method.invoke(mapper, BeanCopyUtils.copyObject(k, ExampleClass));
			dtoList = new ArrayList<D>();

			BeanCopyUtils.copyList(entities, dtoList, dClass);
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException
				| IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtoList;
	}
}
