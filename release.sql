alter table report add column released boolean;
alter table report add column releasedBy_id bigint
		constraint fk_report_releasedBy_id
			references member;
alter table report add column	released_on timestamp;
update report set released = true;