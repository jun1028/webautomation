@echo off
call ant -f build.xml >test.log
@echo ����ʧ�ܵĲ���������ץͼ�ļ���ο�pngsĿ¼�µ��ļ�
@echo ��������򿪲��Ա���test-output\Report.html
pause 
call report
