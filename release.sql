alter table report add column released boolean;
alter table report add column releasedBy_id bigint
		constraint fk_report_releasedBy_id
			references member;
alter table report add column	released_on timestamp;
update report set released = true;

INSERT INTO public.communication_template (communication_type, notification_type, body, created_on, subject) VALUES
('EMAIL', 'REPORT_IN_REVIEW',
'Hallo [FIRSTNAME],<br/><br/>

Es gibt einen neuen Bericht von [AUTHOR] in der Freigabe. Bitte logge dich zeitnah auf der Homepage ein um diesen zu prüfen und freizugeben, damit der Bericht auf der Homepage erscheinen kann.<br/><br/>

Klicke <a href="www.altenerding-biber.de">hier</a> um die Homepage aufzurufen.<br/><br/>

Viele Grüße,<br/>
Dein Admin<br/><br/>

PS: Wenn du diese Benachrichtigung nicht mehr haben willst, kannst du sie auf der Webseite im eingeloggten Bereicht unter dem Menüpunkt "Benachrichtigungen" ausschalten.'
, now(), 'Es ist einer Bericht in der Freigabe');