@echo off
@echo start to test, test log pls check test.log
call ant
@echo 测试失败的测试用例的抓图文件请参看pngs目录下的文件
@echo 按任意键打开测试报告test-output\Report.html
pause 
call report
