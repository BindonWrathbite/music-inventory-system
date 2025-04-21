UPDATE "user"
SET password = '$2a$10$Dlraw7VyazIpA9craYzKcOY0lM6Zu9xAoQDexaiWOGVUYnLqcXpX6'
WHERE username = 'thompsz';

SELECT id, username, password, role FROM "user" WHERE username = 'thompsz';

