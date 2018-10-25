package com.meteor.curator.core.utils.lib.lang;

import com.meteor.curator.core.utils.lib.StrUtil;

/**
 * created by Meteor on 2018/10/21
 * 断言
 * 断言某些对象或值是否符合规定，否则抛出异常。经常用于做变量检查
 */
public class Assert {


    /**
     * 断言对象是否不为{@code null} ，如果为{@code null} 抛出{@link IllegalArgumentException} 异常
     *
     * <pre class="code">
     * Assert.notNull(clazz);
     * </pre>
     *
     * @param <T> 被检查对象类型
     * @param object 被检查对象
     * @return 非空对象
     * @throws NullPointerException if the object is {@code null}
     */
    public static <T> T notNull(T object) throws NullPointerException {
        return notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    //----------------------------------------------------------------------------------------------------------- Check not null
    /**
     * 断言对象是否不为{@code null} ，如果为{@code null} 抛出{@link IllegalArgumentException} 异常 Assert that an object is not {@code null} .
     *
     * <pre class="code">
     * Assert.notNull(clazz, "The class must not be null");
     * </pre>
     *
     * @param <T> 被检查对象泛型类型
     * @param object 被检查对象
     * @param errorMsgTemplate 错误消息模板，变量使用{}表示
     * @param params 参数
     * @return 被检查后的对象
     * @throws NullPointerException if the object is {@code null}
     */
    public static <T> T notNull(T object, String errorMsgTemplate, Object... params) throws NullPointerException {
        if (object == null) {
            throw new NullPointerException(StrUtil.format(errorMsgTemplate, params));
        }
        return object;
    }

}
