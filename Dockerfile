# 베이스 이미지로 Ubuntu 20.04.2 LTS 사용
FROM ubuntu:20.04

# 비대화면 패키지 설치 중 사용자 입력 대기를 방지
ARG DEBIAN_FRONTEND=noninteractive

# Java 17 설치를 위한 PPA 추가 및 패키지 설치
RUN apt-get update && apt-get install -y \
    software-properties-common \
    && add-apt-repository -y ppa:openjdk-r/ppa \
    && apt-get update \
    && apt-get install -y \
    openjdk-17-jdk \
    maven \
    git \
    mysql-server \
    && rm -rf /var/lib/apt/lists/*

# MySQL 설정 (주의: 실제 프로덕션 환경에서는 보안을 강화해야 합니다.)
RUN service mysql start && \
    mysql -e "CREATE DATABASE IF NOT EXISTS mydb;" && \
    mysql -e "CREATE USER 'ssafy'@'localhost' IDENTIFIED BY 'ssafy';" && \
    mysql -e "GRANT ALL PRIVILEGES ON mydb.* TO 'ssafy'@'localhost';" && \
    mysql -e "FLUSH PRIVILEGES;"

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 소스 추가
COPY . /app

CMD ["git","config","credential.helper","store"]
