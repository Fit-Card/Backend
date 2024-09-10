# 베이스 이미지로 OpenJDK 사용
FROM mdernst/cf-ubuntu-jdk21

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 설치 - 공식 Gradle 사이트에서 최신 버전 다운로드
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-7.4.2-bin.zip && \
    unzip gradle-7.4.2-bin.zip && \
    mv gradle-7.4.2 /opt/gradle && \
    ln -s /opt/gradle/bin/gradle /usr/bin/gradle && \
    apt-get clean

# 소스 파일을 컨테이너에 복사
COPY . .

# gradlew 파일에 실행 권한 추가
RUN chmod +x ./gradlew

# Gradle을 사용하여 프로젝트 빌드 (테스트 제외)
RUN ./gradlew build -x test

# 빌드된 JAR 파일을 컨테이너에 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 8081 포트 노출
EXPOSE 8081

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
