create table configuration
(
	property varchar not null
		constraint configuration_pkey
			primary key,
	value varchar
)
;



insert into "configuration" (property, "value") VALUES ('nuliga.api.token', 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJudVBvcnRhbFJTIiwic3ViIjoiY2JmNTkyZGItZGE2ZC00NmFhLWEwNjAtMzVmYjUxNTNiMGRmIiwiZXhwIjoxNTQyODExNzgxLCJpYXQiOjAsImFjY2Vzc2VzIjp7Im51UG9ydGFsUlNfY2x1YiI6WyJCSFZfMTA2NDAiXX19.fMqL3F4G1ZinWJzNfeC_AvJJ010ulzsBSJ4Dft_QtkY');
insert into "configuration" (property, "value") VALUES ('nuliga.api.token.refresh', 'f6557ce4-27f5-4f3e-9815-cdfce8beef07');
insert into "configuration" (property, "value") VALUES ('nuliga.api.client.id', 'cbf592db-da6d-46aa-a060-35fb5153b0df');
insert into "configuration" (property, "value") VALUES ('nuliga.api.client.secret', 'RQpEwT9$AjAPSxDvMZSb');
insert into "configuration" (property, "value") VALUES ('resources.folder', 'E:\Meine Dateien\Projekte\Pinkie\TestFiles\');
insert into "configuration" (property, "value") VALUES ('nuliga.api.grant.type', 'client_credentials');
insert into "configuration" (property, "value") VALUES ('nuliga.api.grant.type.refresh', 'refresh_token');
insert into "configuration" (property, "value") VALUES ('nuliga.api.scope', 'nuPortalRS_club');
insert into "configuration" (property, "value") VALUES ('nuliga.api.base.url', 'https://hbde-portal.liga.nu/rs/');