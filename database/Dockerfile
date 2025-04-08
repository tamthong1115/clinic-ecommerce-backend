FROM postgres:17

COPY init-multi-db.sh /docker-entrypoint-initdb.d/
RUN chmod +x /docker-entrypoint-initdb.d/init-multi-db.sh