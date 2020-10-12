create table if not exists reported_issues
(
    id char(36) not null constraint reported_issues_pkey primary key,
    json_document jsonb not null,
    last_modified timestamptz default now()
);