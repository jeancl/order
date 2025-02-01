FROM eclipse-temurin:17-jre
VOLUME /tmp
COPY target/order.jar order.jar
ENTRYPOINT ["java","-jar","/order.jar"]
