package com.systemvx.androiddevtest

import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun initSql() {
        val AUTOINC = "AUTO_INCREMENT"
        val PK = "PRIMARY KEY"
        val FK = "FOREIGN KEY"
        val REF = "REFERENCES"

        val createstmt = "CREATE TABLE IF NOT EXISTS "
        var createTableSQL: String
        val agent = SQLAgent()
        Assert.assertEquals(true, agent.connectToDB("127.0.0.1", "modeling", "root", "123456"))
        //account建表
        var tableName = "`account`"
        createTableSQL = "$createstmt $tableName(" +
                "account_id INT PRIMARY KEY NOT NULL," +// 账户id
                "account_name CHAR(30) NOT NULL," +//账户名
                "account_nickname VARCHAR(30)," + //账户昵称，界面显示的用户名称用这个
                "account_passwd CHAR(255) NOT NULL," +//账户密码,TODO 存明文还是hash一次？
                "account_sex INT NOT NULL DEFAULT 0," +//性别，0不明1男2女
                "account_credit INT NOT NULL DEFAULT 100," +// 信用分 暂定默认100
                "account_coin_balance DOUBLE NOT NULL DEFAULT 0.00," +// 用户金币余额
                "account_student_number CHAR(10) NOT NULL," +// 学\工号
                "account_phone CHAR(13)," + //电话号码
                "account_portrait MEDIUMBLOB" +//头像
                ")"
        agent.execute(createTableSQL)
        //订单order建表
        tableName = "`order`"
        createTableSQL = "$createstmt $tableName(" +
                "order_id INT PRIMARY KEY NOT NULL," + //订单id
                "order_state INT NOT NULL DEFAULT 0," +//订单状态
                "order_task_state INT DEFAULT 0," +//订单执行过程状态
                "order_publisher INT NOT NULL," +//发布者id
                "order_receiver INT," +//承接者id
                "FOREIGN KEY(order_publisher)REFERENCES `account`(account_id)," +
                "FOREIGN KEY(order_receiver)REFERENCES `account`(account_id)" +
                ")"
        agent.execute(createTableSQL)
        // 订单详情order_detail建表
        tableName = "`order_detail`"
        createTableSQL = "$createstmt $tableName(" +
                "order_id INT NOT NULL," +//订单id
                "order_version INT NOT NULL," +//详情版本号
                "order_title VARCHAR(30)," +//标题
                "order_details TEXT," +//正文
                "order_price DOUBLE," +//悬赏价格
                "order_type INT NOT NULL DEFAULT 0," +//任务类型
                "order_pub_time DATETIME DEFAULT NOW()," + //发布时间
                "order_deadline DATETIME," +//到期时间
                "order_address INT," +//地址位置
                "PRIMARY KEY (order_id,order_version)," +
                "FOREIGN KEY (order_id) REFERENCES `order`(order_id)" +
                ")"
        print(createTableSQL)
        agent.execute(createTableSQL)
        // 投诉complaint建表
        tableName = "`complaint`"
        createTableSQL = "$createstmt $tableName(" +
                "complaint_id INT $PK $AUTOINC," +
                "complaint_time DATETIME DEFAULT NOW()," +
                "complaint_type INT," +
                "complaint_detail TEXT," +
                "complaint_state INT," +
                "complaint_from INT," +
                "complaint_to INT," +
                "complaint_order INT," +
                "$FK (complaint_order) $REF `order`(order_id)," +
                "$FK (complaint_from) $REF `account`(account_id)," +
                "$FK (complaint_to) $REF `account`(account_id)" +
                ")"
        agent.execute(createTableSQL)

        tableName = "`coin`"
        createTableSQL = "$createstmt $tableName(" +
                "coin_id INT $PK $AUTOINC," +
                "coin_time DATETIME DEFAULT NOW()," +
                "coin_amount DOUBLE DEFAULT 0.0," +
                "coin_account_id INT," +
                "order_id INT," +
                "coin_is_income TINYINT," +
                "$FK (order_id) $REF `order`(order_id)," +
                "$FK (coin_account_id) $REF `account`(account_id)" +
                ")"
        agent.execute(createTableSQL)

        tableName = "`comment`"
        createTableSQL = "$createstmt $tableName(" +
                "comment_id INT $PK $AUTOINC," +
                "comment_star INT DEFAULT 4," +
                "comment_message VARCHAR(200)," +
                "comment_sender INT," +
                "order_id INT," +
                "$FK (order_id) $REF `order`(order_id)," +
                "$FK (comment_sender) $REF `account`(account_id)" +
                ")"
        agent.execute(createTableSQL)

        tableName = "`credit`"
        createTableSQL = "$createstmt $tableName(" +
                "credit_id INT $PK $AUTOINC," +
                "credit_value INT NOT NULL DEFAULT 0," +
                "credit_time DATETIME DEFAULT NOW()," +
                "credit_source CHAR(10)," +
                "order_id INT," +
                "$FK (order_id) $REF `order`(order_id)" +
                ")"
        agent.execute(createTableSQL)

        tableName = "`money`"
        createTableSQL = "$createstmt $tableName(" +
                "money_trans_id INT $PK $AUTOINC," +
                "money_trans_time DATETIME DEFAULT NOW()," +
                "money_trans_amount DOUBLE DEFAULT 0.0," +
                "money_trans_account_id INT," +
                "money_trans_type INT," +
                "money_trans_method INT," +
                "$FK (money_trans_account_id) $REF `account`(account_id)" +
                ")"
        agent.execute(createTableSQL)

        tableName = "`chat`"
        createTableSQL = "$createstmt $tableName(" +
                "chat_id INT $PK $AUTOINC," +
                "chat_time DATETIME DEFAULT NOW()," +
                "chat_message TEXT," +
                "chat_sender INT NOT NULL," +
                "chat_receiver INT NOT NULL," +
                "chat_is_read TINYINT NOT NULL DEFAULT 0," +
                "$FK (chat_sender) $REF `account`(account_id)," +
                "$FK (chat_receiver) $REF `account`(account_id)" +
                ")"
        agent.execute(createTableSQL)
    }
}