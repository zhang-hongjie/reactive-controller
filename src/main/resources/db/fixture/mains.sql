insert into "main" (id, comment, date, estimation, isout)
select *
from (values ('id', 'comment', '2017-10-10'::timestamp, 3, false),
             ('id2', 'comment2', '2017-10-12'::timestamp, 4, true)) as t
where not exists(
        select * from "main"
    );