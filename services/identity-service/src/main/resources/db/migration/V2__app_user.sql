create table app_user (
    id uuid primary key,
    business_id uuid not null references business (id),
    email varchar(320) not null unique,
    password_hash varchar(100) not null,
    full_name varchar(200),
    role varchar(20) not null,
    created_at timestamptz not null
);