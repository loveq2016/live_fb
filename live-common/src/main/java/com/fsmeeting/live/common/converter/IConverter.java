package com.fsmeeting.live.common.converter;

/**
 * 源对象——>目标对象
 * 
 * @author yicai.liu<moon>
 *
 * @param <S>
 *            The source type
 * @param <T>
 *            The target type
 */
public interface IConverter<S, T> {

	T convert(S source);

}