package nvyas.spring;

import nvyas.spring.entity.Cat;
import nvyas.spring.entity.HelloWorld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);
        HelloWorld helloworld1 = applicationContext.getBean("helloworld", HelloWorld.class);
        HelloWorld helloWorld2 = applicationContext.getBean("helloworld", HelloWorld.class);
        System.out.println("HelloWorld - это 1 и тот же объект?\n" + (helloworld1 == helloWorld2) + "\n");

        Cat cat1 = applicationContext.getBean("catBean", Cat.class);
        Cat cat2 = applicationContext.getBean("catBean", Cat.class);
        System.out.println("Cat - это 1 и тот же объект?\n" + (cat1 == cat2));
    }
}