package com.systemvx.androiddevtest

import com.systemvx.androiddevtest.data.AccountData
import com.systemvx.androiddevtest.data.daoImpl.AccountDAOImpl
import org.junit.Assert
import org.junit.Test

class DAOTest {
    init {
        val agent = SQLAgent()
        agent.connectToDB("127.0.0.1", "modeling", "root", "123456")
        agent.execute("SET FOREIGN_KEY_CHECKS=0;")
        agent.execute("TRUNCATE `modeling`.`credit`;")
        agent.execute("TRUNCATE `modeling`.`money`;")
        agent.execute("TRUNCATE `modeling`.`chat`;")
        agent.execute("TRUNCATE `modeling`.`coin`;")
        agent.execute("TRUNCATE `modeling`.`complaint`;")
        agent.execute("TRUNCATE `modeling`.`comment`;")
        agent.execute("TRUNCATE `modeling`.`order_detail`;")
        agent.execute("TRUNCATE `modeling`.`order`;")
        agent.execute("TRUNCATE `modeling`.`account`;")
        agent.execute("SET FOREIGN_KEY_CHECKS=1;")
        agent.execute("INSERT INTO `modeling`.`account` (`account_id`, `account_name`, `account_nickname`, `account_passwd`, `account_sex`, `account_signature`, `account_student_number`, `account_phone`) VALUES ('1', 'alyce', 'nickA', 'pwaaaa', '2', 'nothing', '20772077', '18912341234');")
        agent.execute("INSERT INTO `modeling`.`account` (`account_id`, `account_name`, `account_nickname`, `account_passwd`, `account_sex`, `account_signature`, `account_student_number`, `account_phone`) VALUES ('2', 'bob', 'nickB', 'pwbbbb', '1', 'yesq', '20771142', '12312312312');")
        agent.execute("INSERT INTO `modeling`.`order` (`order_id`, `order_state`, `order_task_state`, `order_publisher`) VALUES ('1', '1', '0', '1');\n")
        agent.execute("INSERT INTO `modeling`.`order` (`order_id`, `order_state`, `order_task_state`, `order_publisher`, `order_receiver`) VALUES ('2', '2', '1', '2', '1');\n")
        agent.execute("INSERT INTO `modeling`.`order_detail` (`order_id`, `order_version`, `order_pub_time`, `order_deadline`, `order_address`) VALUES ('1', '0', '2020-12-12', '2020-12-30', '0');\n")
        agent.execute("INSERT INTO `modeling`.`order_detail` (`order_id`, `order_version`, `order_title`, `order_details`, `order_price`, `order_type`, `order_pub_time`, `order_deadline`, `order_address`) VALUES ('1', '1', 'titleA', 'maintextyoooooo', '12.1', '0', '2020-12-13 06:33:33', '2020-12-24', '12');\n")
        agent.execute("INSERT INTO `modeling`.`order_detail` (`order_id`, `order_version`, `order_title`, `order_details`, `order_price`, `order_type`, `order_pub_time`, `order_deadline`, `order_address`, `order_is_final`) VALUES ('2', '0', 'titleB', 'maintextB', '2', '0', '2020-12-11 11:11:11', '2020-12-27', '5', '1');")
    }

    @Test
    fun testAccountDao() {
        val a = AccountDAOImpl()
        a.createAccount("Charlie","pwcccc","")
        var get = a.getAccountByName("Charlie")
        println(get)
        Assert.assertEquals(AccountData("Charlie", null, "pwcccc", null, null, 100, 0.0, null, 3, 0), get)
        get = a.getAccountByName("Alyce")
        println(get)
        get = a.getAccountById(2)
        println(get)
        a.updateAccount(AccountData("CharlieNOTModified", "NEWC", null, null, "1223456", null, 203.23, null, 3, 2))
        get = a.getAccountById(3)
        println(get)
        Assert.assertEquals(AccountData("Charlie", "NEWC", "pwcccc", null, "1223456", 100, 203.23, null, 3, 2), get)

        a.updateAccountCoin(get,300.05)
        get = a.getAccountById(get.id)
        Assert.assertEquals(300.05,get.coin)

        a.updateAccountCredit(get,125)
        get = a.getAccountById(get.id)
        Assert.assertEquals(125,get.credit)

    }

}