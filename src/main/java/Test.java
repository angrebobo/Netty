import java.lang.reflect.Method;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 21:59 2022/5/26
 */
public class Test {
    public static void main(String[] args) {
        People people = new People();
        Class clazz = people.getClass();
        System.out.println(clazz.getSimpleName());

//        Method[] declaredMethods = clazz.getDeclaredMethods();
//        for(Method method : declaredMethods){
//            System.out.println("方法名" + method.getName());
//            System.out.println("方法返回类型" + method.getReturnType().getSimpleName());
//            System.out.println("参数方法：");
//            for (Class<?> parameterType : method.getParameterTypes()) {
//                System.out.print(parameterType.getSimpleName() + "   ");
//            }
//            System.out.println();
//        }



    }
}
