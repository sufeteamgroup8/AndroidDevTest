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
                "account_id INT PRIMARY KEY $AUTOINC," +// 账户id
                "account_name CHAR(30) NOT NULL," +//账户名
                "account_nickname VARCHAR(30)," + //账户昵称，界面显示的用户名称用这个
                "account_passwd CHAR(255) NOT NULL," +//账户密码,TODO 存明文还是hash一次？
                "account_sex INT NOT NULL DEFAULT 0," +//性别，0不明1男2女
                "account_signature CHAR(200)," +
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
                "order_id INT PRIMARY KEY NOT NULL $AUTOINC," + //订单id
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
                "order_id INT NOT NULL,\n" +//订单id
                "order_version INT NOT NULL,\n" +//详情版本号
                "order_title VARCHAR(30),\n" +//标题
                "order_details TEXT,\n" +//正文
                "order_price DOUBLE,\n" +//悬赏价格
                "order_type INT NOT NULL DEFAULT 0,\n" +//任务类型
                "order_pub_time DATETIME DEFAULT NOW(),\n" + //发布时间
                "order_deadline DATETIME,\n" +//到期时间
                "order_address INT,\n" +//地址位置
                "order_is_final TINYINT,\n" +
                "PRIMARY KEY (order_id,order_version),\n" +
                "FOREIGN KEY (order_id) REFERENCES `order`(order_id)" +
                ")"
        print(createTableSQL)
        agent.execute(createTableSQL)
        // 投诉complaint建表
        tableName = "`complaint`"
        createTableSQL = "$createstmt $tableName(" +
                "complaint_id INT $PK $AUTOINC,\n" +
                "complaint_time DATETIME DEFAULT NOW(),\n" +
                "complaint_type INT,\n" +
                "complaint_detail TEXT,\n" +
                "complaint_state INT,\n" +
                "complaint_from INT,\n" +
                "complaint_to INT,\n" +
                "complaint_order INT,\n" +
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
                "account_id INT," +
                "order_id INT," +
                "$FK (order_id) $REF `order`(order_id)," +
                "$FK (account_id) $REF `account`(account_id)" +
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

        agent.execute("CREATE VIEW `order_full` \n" +
                "AS \n" +
                "select \n" +
                "`temp`.`order_id` AS `order_id`,\n" +
                "`temp`.`order_state` AS `order_state`,\n" +
                "`temp`.`order_task_state` AS `order_task_state`,\n" +
                "`temp`.`order_publisher` AS `order_publisher`,\n" +
                "`temp`.`order_receiver` AS `order_receiver`,\n" +
                "`temp`.`order_version` AS `order_version`,\n" +
                "`temp`.`order_title` AS `order_title`,\n" +
                "`temp`.`order_details` AS `order_details`,\n" +
                "`temp`.`order_price` AS `order_price`,\n" +
                "`temp`.`order_type` AS `order_type`,\n" +
                "`temp`.`order_pub_time` AS `order_pub_time`,\n" +
                "`temp`.`order_deadline` AS `order_deadline`,\n" +
                "`temp`.`order_address` AS `order_address`,\n" +
                "row_number() OVER(partition by `order_id` order by `order_pub_time` desc) AS `rn` \n" +
                "from \n" +
                "(select `order`.`order_id` AS `order_id`,\n" +
                "`order`.`order_state` AS `order_state`,\n" +
                "`order`.`order_task_state` AS `order_task_state`,\n" +
                "`order`.`order_publisher` AS `order_publisher`,\n" +
                "`order`.`order_receiver` AS `order_receiver`,\n" +
                "`order_detail`.`order_version` AS `order_version`,\n" +
                "`order_detail`.`order_title` AS `order_title`,\n" +
                "`order_detail`.`order_details` AS `order_details`,\n" +
                "`order_detail`.`order_price` AS `order_price`,\n" +
                "`order_detail`.`order_type` AS `order_type`,\n" +
                "`order_detail`.`order_pub_time` AS `order_pub_time`,\n" +
                "`order_detail`.`order_deadline` AS `order_deadline`,\n" +
                "`order_detail`.`order_address` AS `order_address`\n" +
                " from `order_detail` left join `order` on `order`.`order_id` = `order_detail`.`order_id` order by `order_detail`.`order_pub_time` desc) `temp` group by `temp`.`order_id`;")

        agent.execute("CREATE VIEW `order_complete` AS \n" +
                "SELECT `order_full`.`order_id`,\n" +
                "    `order_full`.`order_state`,\n" +
                "    `order_full`.`order_task_state`,\n" +
                "    `order_full`.`order_publisher`,\n" +
                "    `order_full`.`order_receiver`,\n" +
                "    `order_full`.`order_version`,\n" +
                "    `order_full`.`order_title`,\n" +
                "    `order_full`.`order_details`,\n" +
                "    `order_full`.`order_price`,\n" +
                "    `order_full`.`order_type`,\n" +
                "    `order_full`.`order_pub_time`,\n" +
                "    `order_full`.`order_deadline`,\n" +
                "    `order_full`.`order_address`,\n" +
                "\t`publisher`.`account_id` as `publisher_id`,\n" +
                "    `publisher`.`account_name` as `publisher_name`,\n" +
                "    `publisher`.`account_nickname` as `publisher_nickname`,\n" +
                "    `publisher`.`account_passwd` as `publisher_passwd`,\n" +
                "    `publisher`.`account_sex` as `publisher_sex`,\n" +
                "    `publisher`.`account_signature` as `publisher_signature`,\n" +
                "    `publisher`.`account_credit` as `publisher_credit`,\n" +
                "    `publisher`.`account_coin_balance` as `publisher_coin_balance`,\n" +
                "    `publisher`.`account_student_number` as `publisher_student_number`,\n" +
                "    `publisher`.`account_phone` as `publisher_phone`,\n" +
                "    `publisher`.`account_portrait` as `publisher_portrait`,\n" +
                "     `receiver`.`account_id` as  `receiver_id`,\n" +
                "         `receiver`.`account_name` as `receiver_name`,\n" +
                "         `receiver`.`account_nickname` as `receiver_nickname`,\n" +
                "         `receiver`.`account_passwd` as `receiver_passwd`,\n" +
                "         `receiver`.`account_sex` as `receiver_sex`,\n" +
                "         `receiver`.`account_signature` as `receiver_signature`,\n" +
                "         `receiver`.`account_credit` as `receiver_credit`,\n" +
                "         `receiver`.`account_coin_balance` as `receiver_coin_balance`,\n" +
                "         `receiver`.`account_student_number` as `receiver_student_number`,\n" +
                "         `receiver`.`account_phone` as `receiver_phone`,\n" +
                "         `receiver`.`account_portrait` as `receiver_portrait`\n" +
                "\n" +
                "FROM `order_full`\n" +
                " LEFT JOIN `account` as `publisher` ON `order_full`.`order_publisher` = `publisher`.`account_id`\n" +
                " LEFT JOIN `account` as `receiver` ON `order_full`.`order_receiver` =  `receiver`.`account_id`;\n" +
                "\n")

    }
}