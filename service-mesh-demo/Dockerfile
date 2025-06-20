# --------------------------
# Stage 1: Build com Maven
# --------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Define diretório de trabalho dentro do container
WORKDIR /app

# Copia o pom.xml e arquivos de código
COPY pom.xml .
COPY src ./src

# Faz o build do projeto (gera o .war dentro de /app/target)
RUN mvn clean package -DskipTests

# Base oficial do WildFly
FROM quay.io/wildfly/wildfly:30.0.0.Final-jdk17

# Copia o seu WAR para a pasta de deployments
COPY --from=build /app/target/service-mesh-demo.war /opt/jboss/wildfly/standalone/deployments/

# Expor a porta padrão HTTP do WildFly
EXPOSE 8080

# Comando padrão de inicialização
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
