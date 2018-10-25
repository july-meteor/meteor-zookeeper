package com.meteor.curator.core.utils.lib.io;


import com.meteor.curator.core.utils.lib.StrUtil;
import com.meteor.curator.core.utils.lib.CharsetUtil;
import com.meteor.curator.core.utils.lib.excpetion.IORuntimeException;
import com.meteor.curator.core.utils.lib.lang.Assert;

import java.io.*;
import java.nio.charset.Charset;

/**
 * IO工具类<br>
 * IO工具类只是辅助流的读写，并不负责关闭流。原因是流可能被多次读写，读写关闭后容易造成问题。
 * 
 * 
 *
 */
public class IoUtil {

	/** 默认缓存大小 */
	public static final int DEFAULT_BUFFER_SIZE = 1024;
	/** 默认缓存大小 */
	public static final int DEFAULT_LARGE_BUFFER_SIZE = 4096;
	/** 数据流末尾 */
	public static final int EOF = -1;

	/**
	 * String 转为流
	 *
	 * @param content 内容
	 * @param charsetName 编码
	 * @return 字节流
	 */
	public static ByteArrayInputStream toStream(String content, String charsetName) {
		return toStream(content, CharsetUtil.charset(charsetName));
	}

	/**
	 * String 转为流
	 *
	 * @param content 内容
	 * @param charset 编码
	 * @return 字节流
	 */
	public static ByteArrayInputStream toStream(String content, Charset charset) {
		if (content == null) {
			return null;
		}
		return new ByteArrayInputStream(StrUtil.bytes(content, charset));
	}



	/**
	 * 拷贝流
	 *
	 * @param in 输入流
	 * @param out 输出流
	 * @param bufferSize 缓存大小
	 * @return 传输的byte数
	 * @throws IORuntimeException IO异常
	 */
	public static long copy(InputStream in, OutputStream out, int bufferSize) throws IORuntimeException {
		return copy(in, out, bufferSize, null);
	}
	/**
	 * 拷贝流，使用默认Buffer大小
	 *
	 * @param in 输入流
	 * @param out 输出流
	 * @return 传输的byte数
	 * @throws IORuntimeException IO异常
	 */
	public static long copy(InputStream in, OutputStream out) throws IORuntimeException {
		return copy(in, out, DEFAULT_BUFFER_SIZE);
	}



	/**
	 * 拷贝流
	 *
	 * @param in 输入流
	 * @param out 输出流
	 * @param bufferSize 缓存大小
	 * @param streamProgress 进度条
	 * @return 传输的byte数
	 * @throws IORuntimeException IO异常
	 */
	public static long copy(InputStream in, OutputStream out, int bufferSize, StreamProgress streamProgress) throws IORuntimeException {
		Assert.notNull(in, "InputStream is null !");
		Assert.notNull(out, "OutputStream is null !");
		if (bufferSize <= 0) {
			bufferSize = DEFAULT_BUFFER_SIZE;
		}

		byte[] buffer = new byte[bufferSize];
		long size = 0;
		if (null != streamProgress) {
			streamProgress.start();
		}
		try {
			for (int readSize = -1; (readSize = in.read(buffer)) != EOF;) {
				out.write(buffer, 0, readSize);
				size += readSize;
				out.flush();
				if (null != streamProgress) {
					streamProgress.progress(size);
				}
			}
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
		if (null != streamProgress) {
			streamProgress.finish();
		}
		return size;
	}
	/**
	 * 关闭<br>
	 * 关闭失败不会抛出异常
	 *
	 * @param closeable 被关闭的对象
	 */
	public static void close(Closeable closeable) {
		if (null != closeable) {
			try {
				closeable.close();
			} catch (Exception e) {
				// 静默关闭
			}
		}
	}

	/**
	 * 按行读取数据，针对每行的数据做处理<br>
	 * {@link Reader}自带编码定义，因此读取数据的编码跟随其编码。
	 *
	 * @param reader {@link Reader}
	 * @param lineHandler 行处理接口，实现handle方法用于编辑一行的数据后入到指定地方
	 * @throws IORuntimeException IO异常
	 */
	public static void readLines(Reader reader, LineHandler lineHandler) throws IORuntimeException {
		Assert.notNull(reader);
		Assert.notNull(lineHandler);

		// 从返回的内容中读取所需内容
		final BufferedReader bReader = getReader(reader);
		String line = null;
		try {
			while ((line = bReader.readLine()) != null) {
				lineHandler.handle(line);
			}
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}


	/**
	 * 获得一个Reader
	 *
	 * @param in 输入流
	 * @param charset 字符集
	 * @return BufferedReader对象
	 */
	public static BufferedReader getReader(InputStream in, Charset charset) {
		if (null == in) {
			return null;
		}

		InputStreamReader reader = null;
		if (null == charset) {
			reader = new InputStreamReader(in);
		} else {
			reader = new InputStreamReader(in, charset);
		}

		return new BufferedReader(reader);
	}
	/**
	 * 获得{@link BufferedReader}<br>
	 * 如果是{@link BufferedReader}强转返回，否则新建。如果提供的Reader为null返回null
	 *
	 * @param reader 普通Reader，如果为null返回null
	 * @return {@link BufferedReader} or null
	 * @since 3.0.9
	 */
	public static BufferedReader getReader(Reader reader) {
		if(null == reader) {
			return null;
		}

		return (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
	}

}
