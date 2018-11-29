package com.fish.learn.demo.designmodel.proxy;

import com.fish.learn.demo.designmodel.proxy.cglib.CglibDbQueryInterceptor;
import com.fish.learn.demo.designmodel.proxy.javasist.JavassistDynDbQueryHandler;
import com.fish.learn.demo.designmodel.proxy.jdk.JdkDbQueryHandler;
import javassist.*;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/9/8 13:23
 */
public class MainEntranceTest {

    public static final int CIRCLE = 30000000;

    public static void main(String args[]){
        IDBQuery query = new DBQueryProxy();
        String str = query.request();
        System.out.println(str);


        try {
            testPerformance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    public static void testPerformance() throws InstantiationException, IllegalAccessException, NotFoundException, CannotCompileException {
        IDBQuery d = null;
        //测试JDK动态代理
        long begin = System.currentTimeMillis();
        d = createJdkProxy();
        System.out.println("createJdkProxy:" + (System.currentTimeMillis() - begin));
        System.out.println("JdkProxy class:"+d.getClass().getName());
        begin = System.currentTimeMillis();
        for(int i = 0;i<CIRCLE;i++){
            d.request();
        }
        System.out.println("callJdkProxy:" + (System.currentTimeMillis() - begin));

        //测试cglib动态代理

        begin = System.currentTimeMillis();
        d = createCglibProxy();
        System.out.println("createCglibProxy:" + (System.currentTimeMillis() - begin));
        System.out.println("CglibProxy class:"+d.getClass().getName());
        begin = System.currentTimeMillis();
        for(int i = 0;i<CIRCLE;i++){
            d.request();
        }
        System.out.println("callCglibProxy:" + (System.currentTimeMillis() - begin));

        //测试javassit代理，工厂实现
        begin = System.currentTimeMillis();
        d = createJavassitDynProxy();
        System.out.println("createJavassitDynProxy:" + (System.currentTimeMillis() - begin));
        System.out.println("JavassitDynProxy class:"+d.getClass().getName());
        begin = System.currentTimeMillis();
        for(int i = 0;i<CIRCLE;i++){
            d.request();
        }
        System.out.println("callJavassitDynProxy:" + (System.currentTimeMillis() - begin));

        //测试javassit代理，动态代码实现
        begin = System.currentTimeMillis();
        d = createJavassitBytecodeDynamicProxy();
        System.out.println("createJavassitBytecodeDynamicProxy:" + (System.currentTimeMillis() - begin));
        System.out.println("JavassitBytecodeDynamicProxy class:"+d.getClass().getName());
        begin = System.currentTimeMillis();
        for(int i = 0;i<CIRCLE;i++){
            d.request();
        }
        System.out.println("callJavassitBytecodeDynamicProxy:" + (System.currentTimeMillis() - begin));
    }

    /**
     * jdk代理
     * @return
     */
    public static IDBQuery createJdkProxy(){
        IDBQuery jdkProxy = (IDBQuery) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[] {IDBQuery.class},new JdkDbQueryHandler());

        return jdkProxy;
    }

    /**
     * cglib代理
     * @return
     */
    public static IDBQuery createCglibProxy(){
        Enhancer enhancer = new Enhancer();
        //指定切入器，定义代理类逻辑
        enhancer.setCallback(new CglibDbQueryInterceptor());
        //指定实现的接口
        enhancer.setInterfaces(new Class[]{IDBQuery.class});
        //生成代理类实例
        IDBQuery cglibProxy = (IDBQuery) enhancer.create();

        return cglibProxy;
    }

    /**
     * javassit代理，使用代理工厂创建
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static IDBQuery createJavassitDynProxy() throws IllegalAccessException, InstantiationException {
        ProxyFactory proxyFactory = new ProxyFactory();
        //指定接口
        proxyFactory.setInterfaces(new Class[] {IDBQuery.class});
        Class proxyClass = proxyFactory.createClass();
        IDBQuery javassitProxy = (IDBQuery) proxyClass.newInstance();
        //设置Handler处理器
        ((ProxyObject)javassitProxy).setHandler(new JavassistDynDbQueryHandler());
        return javassitProxy;
    }


    /**
     * javassit代理，动态代码实现
     * @return
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static IDBQuery createJavassitBytecodeDynamicProxy() throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {
        ClassPool mPool = new ClassPool(true);
        //定义类名
        CtClass mCtc = mPool.makeClass(IDBQuery.class.getName()+"javaassitBytecodeProxy");
        //需要实现的接口
        mCtc.addInterface(mPool.get(IDBQuery.class.getName()));
        //添加构造函数
        mCtc.addConstructor(CtNewConstructor.defaultConstructor(mCtc));
        //添加类的字段信息，使用动态java代码
        mCtc.addField(CtField.make("public " + IDBQuery.class.getName() + " real;",mCtc));
        String dbQueryName = DBQuery.class.getName();
        //添加方法，这里使用动态java代码指定内部逻辑
        mCtc.addMethod(CtNewMethod.make("public String request (){if(real == null){ real = new "+dbQueryName+"(); } return real.request();}",mCtc));
        //基于以上信息，生成动态类
        Class pc = mCtc.toClass();
        //生成动态类的实例
        IDBQuery byteCodeProxy = (IDBQuery) pc.newInstance();
        return byteCodeProxy;
    }

}
