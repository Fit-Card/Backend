# 베이스 이미지로 OpenJDK 사용
FROM mdernst/cf-ubuntu-jdk21

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 빌드를 위한 환경 설치 (Ubuntu 패키지 설치)
RUN apt-get update && \
    apt-get install -y gradle && \
    apt-get clean

# 소스 파일을 컨테이너에 복사
COPY . .

# Gradle을 사용하여 프로젝트 빌드
RUN ./gradlew build

# 빌드된 JAR 파일을 컨테이너에 추가
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} app.jar

# 8081 포트 노출
EXPOSE 8081

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
