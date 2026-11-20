-- one row per uploaded file
create table upload (
    id uuid primary key,
    business_id uuid not null,
    uploaded_by uuid not null,
    data_type varchar(20) not null,     -- AD_SPEND, SIGNUPS or SALES
    file_name varchar(255) not null,
    status varchar(30) not null,
    headers jsonb not null,
    total_rows int not null,
    created_at timestamptz not null
);

-- every row of an uploaded file
create table staged_row (
    id uuid primary key,
    upload_id uuid not null references upload (id),
    row_number int not null,            -- the row number in the spreadsheet
    raw jsonb not null,                 -- the row as uploaded
    normalized jsonb,                   -- the cleaned row
    status varchar(10) not null,        -- PENDING, VALID or REJECTED
    error varchar(1000)                 -- why the row was rejected
);

create index staged_row_upload_idx on staged_row (upload_id);