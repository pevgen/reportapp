create table tasks
(
    issue_id  varchar(50) not null primary key,
    issue_key  varchar(50) not null,
    issue_type  varchar(50) not null,
    summary varchar(1000) not null,
    created timestamp not null,
    start_process_init timestamp,
    start_process_update timestamp,
    to_test_init timestamp,
    testing_init timestamp,
    resolved timestamp,
    updated timestamp,
    story_points int
);

alter table tasks
    owner to myuser;