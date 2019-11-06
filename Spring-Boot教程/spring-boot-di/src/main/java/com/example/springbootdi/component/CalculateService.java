package com.example.springbootdi.component;




import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 *  自定义 component
 *
 * @date:2019/11/6 19:54
 * @author: <a href='mailto:fanhaodong516@qq.com'>Anthony</a>
 */

@Component
public class CalculateService implements InitializingBean {
    public CalculateService() {
        System.out.println("构造方法");
    }

    public String getDesc() {
        return desc;
    }

    private String desc="sb";


    public void setDesc(String desc) {
        System.out.println("设置 CalculateService 的 desc , 值为 : "+desc);
        this.desc = desc;
    }


    /**
     * 类似于  {@link javax.annotation.PostConstruct} 的作用
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.desc = "通过 CalculateService 的  afterPropertiesSet() 改的 ";
        System.out.println("初始化完成 : "+this);

        new Thread(() -> {
            while (true) {
                //
            }
        }).start();
    }
}
