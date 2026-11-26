-- the column mapping the owner chose, and the result of cleaning
alter table upload add column mapping jsonb;               -- "email": "E-mail"
alter table upload add column date_format varchar(30);
alter table upload add column decimal_separator varchar(1); -- "," or "."
alter table upload add column file_channel varchar(100);    -- one channel for the whole file
alter table upload add column valid_rows int;
alter table upload add column rejected_rows int;