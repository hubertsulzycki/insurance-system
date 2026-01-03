# 1. Wybieramy bazę - system z zainstalowaną Javą 21 (bo taką masz w pom.xml)
FROM eclipse-temurin:21-jdk-alpine

# 2. Tworzymy zmienną na plik JAR (Twoją aplikację)
ARG JAR_FILE=target/*.jar

# 3. Kopiujemy Twój zbudowany plik .jar do wnętrza kontenera pod nazwą app.jar
COPY ${JAR_FILE} app.jar

# 4. Komenda startowa - to się wykona, gdy kontener wstanie
ENTRYPOINT ["java","-jar","/app.jar"]