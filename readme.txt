�����Զ������Ի���(�պ󽫻��ҳ��Ԫ�����ݷ���ָ���ķ������ϣ���ֱ�ӵ�4)�����Զ������ԣ������������ݿ�)
1����װMysql���趨��½������!
2���ָ�ҳ��Ԫ������
   �༭resotore.bat�� �޸��û���������Ϊ����mysql���û������� -uUserName -pPassword,����
   mysql -uroot --default-character-set=gbk -p1234   page  <backup.bak
3���޸�con/silencer.properties�е�#���ݿ��������ã�Ϊ
	dbUser = UserName(default root)
	dbPwd = Password 
4�����run test.bat�������Զ�������
5��������ϰ�enter���鿴���Խ����鿴test-output/index.html
6) ����ʧ�ܵ�ץͼ�ļ�����鿴/pngsĿ¼

test case��д�淶(��ο�)
test case sheet name�����testcasse,��supermanger_testcase,����Ĳ���������supermanger_params sheet��Ҫmap��user,pwd,   ʹ��#��ʾ��һ����������#userName, ����Ҫ��#����\# ��֮˵��,����ʱ�������ڲ�������xmlָ��Ҫ���Ե�sheet name
��֮˵��
��һ��Ϊ����header
�ڶ���Ϊע��note
������ΪС����
���������д����Ĳ��Բ���
��֮˵��
��һ��Ϊ��������������������
�ڶ���Ϊ�����������¼�����
��һ��Ϊ������ֵ����#username, 1, 2��


Excel ������д�淶
1. sheet name���������ں����_params
2. ����excel �����testcase��Ҫ�ڲ�������xmlָ��sheet name
3. ���ǲ����࣬ sheet name���������ķ����������������ڲ�������xmlָ��
4. expected result, ����������|�ָ ����Ҫcheck ָ����element item����ʽΪ element item = value
   ��Ҫ���ҵ�����ݿ���Ҫдselect��� like "select * from ..." or "select id , name from "




