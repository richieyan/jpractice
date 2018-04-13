package io.jpractice.javagc;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Author: Richie Yan
 * Date: 08/04/2018
 */
public class JSPromise {
    private BiFunction<UnaryOperator<Object>, Function<Exception, Object>, Object> func;

    private final boolean isError;

    public JSPromise(BiFunction<UnaryOperator<Object>, Function<Exception, Object>, Object> func) {
        this(func, false);
    }

    public JSPromise(BiFunction<UnaryOperator<Object>, Function<Exception, Object>, Object> func, boolean error) {
        this.func = func;
        this.isError = error;
    }

    public JSPromise then(UnaryOperator<Object> resolveFunc) {
        if (isError) return this; //直接返回自己，直到调用 onError
        try {
            Object result = this.func.apply(resolveFunc, null);
            return new JSPromise((rs, error) -> rs.apply(result));
        } catch (Exception e) {
            return new JSPromise((rs, error) -> error.apply(e), true);
        }
    }

    public Object onError(Function<Exception, Object> errorFunc) {
        return this.func.apply(null, errorFunc);
    }

    public static void main(String[] args) {
        long userId = 10005;
        Object data = new JSPromise((rs, error) -> {
            return rs.apply(userId); //闭包参数，userId
        }).then(result -> {
            System.out.println("first then called:params:" + result);
            return "UID_" + result; // prefix userId
        }).then(result -> {
            System.out.println("second then called:params:" + result);
            return result.toString().toLowerCase();
        }).then(result -> {
            System.out.println("third then called:params:" + result);
            throw new IllegalStateException("error happen"); // throw an exception
        }).then(result -> { // 不会被调用
            System.out.println("fourth then not called:" + result);
            return "second then";
        }).onError(e -> { // 捕获到异常
            System.out.println("onError# message is :" + e.getMessage());
            System.out.println("----------------print error done--------------");
            return "error data";
        });

        System.out.println("final result:" + data);
    }

}
