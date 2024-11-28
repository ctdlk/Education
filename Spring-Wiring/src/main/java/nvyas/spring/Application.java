package nvyas.spring;

import nvyas.spring.config.AppConfig;
import nvyas.spring.entity.AnimalsCage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        for (int i = 0; i < 5; i++) {
            AnimalsCage bean = context.getBean(AnimalsCage.class);
            bean.whatAnimalSay();
        }
    }
}