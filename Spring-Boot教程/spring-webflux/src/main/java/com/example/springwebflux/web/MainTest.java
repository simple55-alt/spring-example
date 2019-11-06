package com.example.springwebflux.web;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.stream.IntStream;

public class MainTest {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(1000);

        // 使用 ThreadLocal
        Bank bank = new Bank();
        IntStream.range(0, 3).forEach(e->{
            pool.execute(()->{
                bank.deposit(200,"新员工 : "+e);
            });
        });

        pool.shutdown();
    }
}

class Bank {
    // 初始化账户余额为 1000
    ThreadLocal<Integer> account = ThreadLocal.withInitial(() -> 1000);

    public void deposit(int money,String name) {
        System.out.println(name + "--当前账户余额为：" + account.get());
        account.set(account.get() + money);
        System.out.println(name + "--存入 " + money + " 后账户余额为：" + account.get());
        account.remove();
    }
}
class Bank2 {

    private int money = 1000;

    public void deposit(int money,String name) {
//        System.out.println(name + "--当前账户余额为：" + this.money);
//        this.money += money;
        System.out.println(name + "--存入 " + money + " 后账户余额为：" + (this.money+money));
    }

    public static void main(String[] args) {
        // 不使用 ThreadLocal

   /*     Bank2 bank2 = new Bank2();
        IntStream.range(0, 1000).forEach(e->{
            pool.execute(()->{
                bank2.deposit(200+e,"员工 : "+e);
            });
        });*/
    }
}