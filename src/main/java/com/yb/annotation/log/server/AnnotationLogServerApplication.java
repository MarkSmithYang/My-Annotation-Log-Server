package com.yb.annotation.log.server;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 关于配置@ComponentScan(basePackages = {"com.yb.annotation.log.server.mapper"})的大坑,
 * 如果配置了这个,就会覆盖原本的大范围扫描,导致很多类无法被扫描到,也就是无法使用,虽然服务正常启动了,但是无法使用
 * 这次是由于要扫描mapper包,没用对正确的扫描注解,导致的问题,使用的是@MapperScan注解================
 * <p>
 * 必须写mapper包的扫描,否则无法入住bean
 * @author biaoyang
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.yb.annotation.log.server.mapper"},annotationClass = Repository.class)
public class AnnotationLogServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnotationLogServerApplication.class, args);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
