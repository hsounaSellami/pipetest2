FROM eclipse-temurin:17
WORKDIR /app

ADD http://nexus:8081/repository/maven-releases/com/yourgroup/student-management/0.0.1/student-management-0.0.1.jar app.jar

EXPOSE 8089
CMD ["java","-jar","app.jar"]
