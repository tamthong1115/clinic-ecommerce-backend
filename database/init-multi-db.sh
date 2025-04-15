#!/bin/bash
set -e

create_db() {
  DB_NAME=$1
  # Query returns "1" if the database exists.
  if psql -U "$POSTGRES_USER" -tAc "SELECT 1 FROM pg_database WHERE datname='$DB_NAME'" | grep -q 1; then
    echo "Database \"$DB_NAME\" already exists."
  else
    echo "Creating database \"$DB_NAME\"..."
    psql -U "$POSTGRES_USER" -c "CREATE DATABASE \"$DB_NAME\""
  fi
}

# Create additional databases if they do not exist.
create_db "user"
create_db "doctor"
create_db "clinic"
create_db "patient"
