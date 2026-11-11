create table business (
    id uuid primary key default gen_random_uuid(),
    name varchar(200) not null,
    currency char(3) not null default 'EUR',
    timezone varchar(64) not null default 'Europe/Sofia',
    created_at timestamptz not null default now()
);