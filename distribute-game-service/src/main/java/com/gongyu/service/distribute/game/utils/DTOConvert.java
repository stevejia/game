package com.gongyu.service.distribute.game.utils;

public interface DTOConvert<T, S> {

    T convert(S s);
}
