一、设定页面元素的DB
1）安装Mysql。设定登陆的密码!
3）修改con/silencer.properties中的#数据库链接配置，为
	dbUser = UserName(default root)
	dbPwd = Password 
4）点击run test.bat，运行自动化测试
5）测试完毕按enter键查看测试结果或查看test-output/repoter.html
6) 测试失败的抓图文件，请查看/pngs目录

test case编写规范(请参考)
test case sheet name需包含testcasse,如supermanger_testcase,输入的参数名称与supermanger_params sheet需要map如username,password,   使用#表示是一个变量，如#userName, 若需要用#请用\# 行之说明,测试时，必须在测试配置xml指定要测试的sheet name
行之说明
第一行为标题header
第二行为注释note
第三行为小标题
第四行起编写具体的测试步骤
列之说明
第一列为操作动作，如点击，输入
第二列为操作对象，如登录输入框
第一列为操作数值，如#username, 1, 2等





