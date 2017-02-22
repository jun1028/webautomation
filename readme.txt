配置自动化测试环境(日后将会把页面元素数据放在指定的服务器上，可直接到4)运行自动化测试，不用配置数据库)
1）安装Mysql。设定登陆的密码!
2）恢复页面元素数据
   编辑resotore.bat， 修改用户名，密码为本机mysql的用户和密码 -uUserName -pPassword,如下
   mysql -uroot --default-character-set=gbk -p1234   page  <backup.bak
3）修改con/silencer.properties中的#数据库链接配置，为
	dbUser = UserName(default root)
	dbPwd = Password 
4）点击run test.bat，运行自动化测试
5）测试完毕按enter键查看测试结果或查看test-output/index.html
6) 测试失败的抓图文件，请查看/pngs目录

test case编写规范(请参考)
test case sheet name需包含testcasse,如supermanger_testcase,输入的参数名称与supermanger_params sheet需要map如user,pwd,   使用#表示是一个变量，如#userName, 若需要用#请用\# 行之说明,测试时，必须在测试配置xml指定要测试的sheet name
行之说明
第一行为标题header
第二行为注释note
第三行为小标题
第四行起编写具体的测试步骤
列之说明
第一列为操作动作，如点击，输入
第二列为操作对象，如登录输入框
第一列为操作数值，如#username, 1, 2等


Excel 参数编写规范
1. sheet name命名必须在后面加_params
2. 若是excel 输入的testcase需要在测试配置xml指定sheet name
3. 若是测试类， sheet name需与测试类的方法关联，但不用在测试配置xml指定
4. expected result, 若检查多项用|分割， 若需要check 指定的element item，格式为 element item = value
   需要检查业务数据库需要写select语句 like "select * from ..." or "select id , name from "




