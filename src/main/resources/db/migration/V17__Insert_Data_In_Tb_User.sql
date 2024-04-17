INSERT INTO tb_user
    (username, email, password, roles)
VALUES
    ('admin', 'admin@futS3.com', '$argon2id$v=19$m=16384,t=2,p=1$ZQ8UUj94/Z65EP0DTaJ/MA$gda5jaXHRDtSnUlNRBbmbC9HEpAJVPtJhb5aHLxAcTI', 'ROLE_ADMIN'), -- PASSWORD: '!@Futs3$$$19V4A'
    ('user', 'user@futS3.com', '$argon2id$v=19$m=16384,t=2,p=1$ZQ8UUj94/Z65EP0DTaJ/MA$gda5jaXHRDtSnUlNRBbmbC9HEpAJVPtJhb5aHLxAcTI', 'ROLE_USER'); -- PASSWORD: '!@Futs3$$$19V4A'
