FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=Neshtorandom268#
ENV MYSQL_DATABASE=my_database

COPY ./init/init.sql /docker-entrypoint-initdb.d/

EXPOSE 3306
