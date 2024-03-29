# 베이스 이미지로 Ubuntu 20.04 사용
FROM ubuntu:20.04

# 비대화면 패키지 설치 중 사용자 입력 대기를 방지
ARG DEBIAN_FRONTEND=noninteractive

# 필요한 패키지 설치
RUN apt-get update && apt-get install -y \
    software-properties-common \
    openjdk-17-jdk \
    maven \
    git \
    mysql-server \
 && rm -rf /var/lib/apt/lists/*

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 소스 추가
COPY . /app

RUN service mysql start && \
    mysql -e "CREATE DATABASE IF NOT EXISTS mydb;" && \
    mysql -e "CREATE USER 'ssafy'@'localhost' IDENTIFIED BY 'ssafy';" && \
    mysql -e "GRANT ALL PRIVILEGES ON mydb.* TO 'ssafy'@'localhost';" && \
    mysql -e "FLUSH PRIVILEGES;"
# MySQL 설정 스크립트 추가
COPY init_db.sql /docker-entrypoint-initdb.d/

# MySQL 서비스를 시작하고 초기 설정을 수행하는 스크립트 추가
COPY start_mysql.sh /usr/local/bin/start_mysql.sh

RUN chmod +x /usr/local/bin/start_mysql.sh
RUN service mysql start && mysql -u root mydb < /docker-entrypoint-initdb.d/init_db.sql