# Use uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR gerado para o diretório de trabalho do container
COPY target/quizzgameapi-0.0.1-SNAPSHOT.jar /app/quizzgameapi.jar

# Define o comando de entrada para o container
ENTRYPOINT ["java", "-jar", "/app/quizzgameapi.jar"]
