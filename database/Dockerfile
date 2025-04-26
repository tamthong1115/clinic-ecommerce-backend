FROM postgres:17

RUN apt-get update && apt-get install -y dos2unix

COPY init-multi-db.sh /docker-entrypoint-initdb.d/

# Convert line endings, set permissions, and ownership
RUN dos2unix /docker-entrypoint-initdb.d/init-multi-db.sh \
    && chmod +x /docker-entrypoint-initdb.d/init-multi-db.sh \
    && chown postgres:postgres /docker-entrypoint-initdb.d/init-multi-db.sh

# Optional: Clean up to reduce image size
RUN apt-get remove -y dos2unix && apt-get autoremove -y && rm -rf /var/lib/apt/lists/*
