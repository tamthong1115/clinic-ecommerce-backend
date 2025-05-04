FROM postgres:17

RUN apt-get update && apt-get install -y dos2unix

# Copy schema files to a separate folder
COPY initdb/sql/ /schemas/

# Copy orchestrator script only to the init hook
COPY initdb/init-multi-db.sh /docker-entrypoint-initdb.d/

RUN dos2unix /docker-entrypoint-initdb.d/init-multi-db.sh \
    && chmod +x /docker-entrypoint-initdb.d/init-multi-db.sh \
    && chown postgres:postgres /docker-entrypoint-initdb.d/init-multi-db.sh

RUN apt-get remove -y dos2unix && apt-get autoremove -y && rm -rf /var/lib/apt/lists/*
