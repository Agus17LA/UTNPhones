CREATE USER 'backoffice'@'%' identified by 'backoffice1';

GRANT INSERT ON UtnPhones.* to 'backoffice'@'%';
GRANT SELECT ON UtnPhones.* to 'backoffice'@'%';
GRANT UPDATE ON UtnPhones.* to 'backoffice'@'%';

/*--------------------------------------------------------*/
CREATE USER 'sys'@'%' identified by 'sys1';

GRANT INSERT ON UtnPhones.* to 'sys'@'%';
GRANT SELECT ON UtnPhones.* to 'sys'@'%';
GRANT UPDATE ON UtnPhones.* to 'sys'@'%';
GRANT DELETE ON UtnPhones.* to 'sys'@'%';

/*--------------------------------------------------------*/
CREATE USER 'web'@'%' identified by 'web1';
GRANT SELECT ON UtnPhones.* to 'web'@'%';