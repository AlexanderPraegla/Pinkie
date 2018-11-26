ALTER TABLE club_meeting add COLUMN team_id bigint;
ALTER TABLE club_meeting add COLUMN courtHallName varchar;
ALTER TABLE club_meeting ADD CONSTRAINT fk_clubmeeting_team_id FOREIGN KEY (team_id) REFERENCES team(id);