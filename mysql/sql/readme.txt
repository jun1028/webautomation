���ݿ�ҳ��Ԫ�ر�д�淶
1.��Ϊ�ܱ��ҳ�����ṹ������
2.��Ϊ����ҳ�������õ�Ԫ�ػ�����ҳ�����ܱ����ѯ��ť��������ҳ��������ܱ������ģ�keyvalue��Ҫ����ҳ����.key����
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