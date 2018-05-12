alter TABLE image add column thumbnail varchar;

update image i set thumbnail = replace((SELECT f.filename from files f where f.id = i.id), '.PNG', '_thumb.jpg') where i.id in (select id from files where filename like '%.PNG');
update image i set thumbnail = replace((SELECT f.filename from files f where f.id = i.id), '.jpg', '_thumb.jpg') where i.id in (select id from files where filename like '%.jpg');
update image i set thumbnail = replace((SELECT f.filename from files f where f.id = i.id), '.JPG', '_thumb.jpg') where i.id in (select id from files where filename like '%.JPG');
update image i set thumbnail = replace((SELECT f.filename from files f where f.id = i.id), '.png', '_thumb.jpg') where i.id in (select id from files where filename like '%.png');
update image i set thumbnail = replace((SELECT f.filename from files f where f.id = i.id), '.jpeg', '_thumb.jpg') where i.id in (select id from files where filename like '.%jpeg');
