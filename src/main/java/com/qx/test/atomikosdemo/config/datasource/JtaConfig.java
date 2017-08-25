package com.qx.test.atomikosdemo.config.datasource;


import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * Created by qinxue on 2017/8/24.
 */
@Configuration
public class JtaConfig {
    public JtaConfig(){
    }

    @Bean(
            name = "userTransaction"
    )
    public UserTransaction userTransaction() {
        UserTransaction userTransaction = new UserTransactionImp();
        try {
            userTransaction.setTransactionTimeout(300);
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return userTransaction;
    }

    @Bean(
            name = "transactionManager",
            initMethod = "init",
            destroyMethod = "close"
    )
    public TransactionManager transactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        userTransactionManager.setStartupTransactionService(false);
        return userTransactionManager;
    }

    @Bean(
            name = "platformTransactionManager"
    )
    @DependsOn(value = {
            "transactionManager",
            "userTransaction"
    })
    public PlatformTransactionManager platformTransactionManager(
                                            @Autowired TransactionManager transactionManager,
                                            @Autowired UserTransaction userTransaction) {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setUserTransaction(userTransaction);
        jtaTransactionManager.setTransactionManager(transactionManager);
        return jtaTransactionManager;
    }

}
