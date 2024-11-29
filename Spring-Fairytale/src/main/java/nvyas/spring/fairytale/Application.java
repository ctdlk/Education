package nvyas.spring.fairytale;

import nvyas.spring.fairytale.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        KoscheiTheDeathless koschei = context.getBean(KoscheiTheDeathless.class);
        System.out.println(koschei.getRulesByDeth());
    }
}