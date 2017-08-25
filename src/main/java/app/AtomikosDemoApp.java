package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Created by qinxue on 2017/8/24.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "app",
        "com.qx.test.atomikosdemo"
})
@EnableSwagger2
public class AtomikosDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(AtomikosDemoApp.class, args);
    }
}
