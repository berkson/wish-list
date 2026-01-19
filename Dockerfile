# Build
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copiar pom.xml e fazer o download das dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fonte e compilar o aplicativo
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Criar um usuário que não seja root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar o .jar compilado do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Teste de saúde do app
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Roda a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
