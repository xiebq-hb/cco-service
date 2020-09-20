package com.cco.ccoservice;

import com.cco.ccoservice.business.entity.Vuser;
import com.cco.ccoservice.business.service.VuserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CcoServiceApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private VuserService vuserService;

    /**
     * 测试乐观锁
     * 实体的version字段需要加上@Version注解
     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date: 2020/3/3 0003 下午 16:51
     */
    @Test
    public void testOptmisticLock() {

        // 获取数据
        Vuser vuser = vuserService.selectById("12121");

        System.out.println(vuser);

        // 修改数据
        vuser.setName("张勒");
        vuser.setAge(22);
        // 如果正常逻辑需要手动更改version的值，但是这里mybatis-plus自动帮忙增加
        //vuser.setVersion(vuser.getVersion() + 1);

        try {
            System.out.println("开始休眠");
            // 可以延迟10000s ,手动去修改数据库这条数据的version 为其他数字 ，最后的结果就是无法更新成功
            Thread.sleep(10000);// Thread.sleep(10000);
            vuserService.updateById(vuser); // // 3.根据ID修改这条数据（mybatis plus 的内层帮我们多加了一个where条件，保证乐观锁的实现） /
            System.out.println("休眠结束");

            System.out.println("=================================再次查询该数据=================================");
            System.out.println(vuserService.selectById("12121"));
        }catch (InterruptedException  e){
            e.printStackTrace();
        }

    }

}
