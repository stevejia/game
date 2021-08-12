package com.futures.utils;

public interface DTOConvert<T, S> {

    T convert(S s);
}
