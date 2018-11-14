alter table season add column seasonnickname varchar;
UPDATE season set season.seasonnickname = '18/19' Where name = '2018/2019';
alter table team add column nuliga_team_id varchar; 
alter table team add column nuliga_group_id varchar;
