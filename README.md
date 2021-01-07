# spring-data-read-replica
#How to run
#Docker run
docker-compose -f ./postgresql-stack.yaml up

#check db
docker exec -it deploy_read-db-1_1 /bin/bash

root@07c502968cb3:/# psql -v --username "db-user" --dbname "postgres"
db-user=# select*from spring_data_jpa_example.employee;