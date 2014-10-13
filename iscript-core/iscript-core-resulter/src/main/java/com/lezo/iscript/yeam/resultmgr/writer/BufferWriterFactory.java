package com.lezo.iscript.yeam.resultmgr.writer;

import java.util.HashMap;
import java.util.Map;

import com.lezo.iscript.common.BufferObjectWriter;
import com.lezo.iscript.common.ObjectWriter;
import com.lezo.iscript.service.crawler.dto.ProductDto;
import com.lezo.iscript.service.crawler.dto.ProductStatDto;
import com.lezo.iscript.utils.ObjectUtils;

public class BufferWriterFactory {

	private static final Map<String, BufferWriteFactor> BEAN_WRITER_MAP = new HashMap<String, BufferWriteFactor>();
	static {
		addWriter(ProductDto.class, new ProductWriter(), 200);
		addWriter(ProductStatDto.class, new ProductStatWriter(), 200);
	}

	public static <T> void addWriter(Class<T> beanClass, ObjectWriter<T> objectWriter, int bufferSize) {
		BufferWriteFactor factor = new BufferWriteFactor();
		factor.setBeanClass(beanClass);
		factor.setObjectWriter(objectWriter);
		factor.setBufferSize(bufferSize);
		BEAN_WRITER_MAP.put(beanClass.getSimpleName(), factor);
	}

	public static BufferObjectWriter<?> createBufferObjectWriter(Class<?> beanClass) throws Exception {
		return createBufferObjectWriter(beanClass.getSimpleName());
	}

	public static BufferObjectWriter<?> createBufferObjectWriter(String beanName) throws Exception {
		BufferWriteFactor factor = BEAN_WRITER_MAP.get(beanName);
		return ObjectUtils.newObject(BufferObjectWriter.class, factor.getObjectWriter(), factor.getBufferSize());
	}
}