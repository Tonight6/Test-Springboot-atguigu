package com.springboot;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class ShiroJwtApplicationTests {

    @Test
    void contextLoads() {
        // stream 流方式遍历list集合
        List<Integer> nums = Lists.newArrayList(1,null,3,4,null,6);
        nums.stream().forEach(item -> {
            System.out.println(item + " ==");
        });
    }

    @Test
    void contextLoads1() {
        // stream 流方式将list转换为Map
        List<Person> lists = Lists.newArrayList(
                new Person("1001","小A"),
                new Person("1002", "小B"),
                new Person("1003", "小C")
                );

        //将list转换map
        Map<String, String> map = lists.stream().collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println(map);

    }

    class Person{
        String id;
        String name;

        public Person(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
