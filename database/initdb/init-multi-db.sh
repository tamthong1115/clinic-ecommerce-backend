#!/bin/bash
set -e

# Path to SQL schema files
SCHEMAS_DIR="/schemas"

# Ordered list of databases
ordered_databases=("user" "doctor" "clinic" "appointment")

# Mapping database names to their schema files
declare -A schemas=(
  ["user"]="user_schema.sql"
  ["doctor"]="doctor_schema.sql"
  ["clinic"]="clinic_schema.sql"
  ["appointment"]="appointment_schema.sql"
)

# Create a database if it doesn't exist
create_db() {
  local db_name=$1
  if psql -U "$POSTGRES_USER" -lqt | cut -d \| -f 1 | grep -qw "$db_name"; then
    echo "Database \"$db_name\" already exists."
  else
    echo "Creating database \"$db_name\"..."
    createdb -U "$POSTGRES_USER" "$db_name"
  fi
}

# Iterate in defined order
for db in "${ordered_databases[@]}"; do
  create_db "$db"
  echo "Setting up schema for \"$db\"..."
  psql -U "$POSTGRES_USER" -d "$db" -f "${SCHEMAS_DIR}/${schemas[$db]}"
done
