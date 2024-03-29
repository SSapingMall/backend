#!/bin/bash
# MySQL 서비스 시작
#service mysql start
#!/bin/bash

# MySQL 서비스 시작
/etc/init.d/mysql start

# 컨테이너가 계속 실행 상태로 유지되도록 하는 명령어
tail -f /dev/null

# 초기 설정 실행
mysql -u root -e "CREATE DATABASE IF NOT EXISTS mydb;"
mysql -u root -e "CREATE USER 'ssafy'@'localhost'
