数据库页面元素编写规范
1.分为总表和页面表，表结构，如下
2.若为所有页面所公用的元素或是主页面入总表如查询按钮，其他如页面表，若与总表重名的，keyvalue需要加上页面名.key名称
CREATE TABLE `firstpage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `KEYVALUE` varchar(45) DEFAULT NULL,
  `IDVALUE` varchar(45) DEFAULT NULL,
  `CSSVALUE` varchar(45) DEFAULT NULL,
  `XPATHVALUE` varchar(45) DEFAULT NULL,
  `NAMEVALUE` varchar(45) DEFAULT NULL,
  `DESCVALUE` varchar(45) DEFAULT NULL,
  `PAGENAME` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) 