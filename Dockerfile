# 1️⃣ Image de base OpenJDK 17 officielle
FROM eclipse-temurin:17

# 2️⃣ Définir le répertoire de travail
WORKDIR /app

# 3️⃣ Copier le JAR compilé
COPY target/student-management-0.0.1-SNAPSHOT.jar app.jar

# 4️⃣ Exposer un port si nécessaire
EXPOSE 8089



# 6️⃣ Commande pour lancer ton application
CMD ["java", "-jar", "app.jar"]

