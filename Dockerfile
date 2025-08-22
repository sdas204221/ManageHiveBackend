# Use official Tomcat 9 image
FROM tomcat:9.0-jdk17

# Remove default webapps (optional, to clean up ROOT)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file to webapps folder
COPY target/ManageHive.war /usr/local/tomcat/webapps/ROOT.war

# Expose Tomcat's default port
EXPOSE 8080

# Start Tomcat
ENTRYPOINT ["catalina.sh", "run"]
#CMD ["java","-jar","./usr/local/tomcat/webapps/ROOT.war"]
#docker run -p 8080:8080 --name managehive-app `
#   -e SPRING_DATASOURCE_URL="jdbc:postgresql://host.docker.internal:5432/manage_hive" `
#   -e SPRING_DATASOURCE_USERNAME="postgres" `
#   -e SPRING_DATASOURCE_PASSWORD="1234" `
#   managehive