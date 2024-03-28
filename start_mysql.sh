#!/bin/bash
# MySQL 서비스 시작
service mysql start

# 초기 설정 실행
mysql -u root -e "CREATE DATABASE IF NOT EXISTS mydb;"
mysql -u root -e "CREATE USER 'ssafy'@'localhost'
