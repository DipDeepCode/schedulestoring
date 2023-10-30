INSERT INTO employee (firstname, lastname, birthdate, employee_type, patronymic)  VALUES ('firstname1', 'lastname1', '2022-11-11', 'physician', 'patronymic1');
INSERT INTO employee (firstname, lastname, birthdate, employee_type, patronymic)  VALUES ('firstname2', 'lastname2', '2022-11-11', 'physician', 'patronymic2');
INSERT INTO employee (firstname, lastname, birthdate, employee_type, patronymic)  VALUES ('firstname3', 'lastname3', '2022-11-11', 'physician', 'patronymic3');

-- id=1
INSERT INTO vacancy (salary, position, added_at, is_deleted) VALUES (100, 'Physician1', '2022-01-01', FALSE);
-- id=2
INSERT INTO vacancy (salary, position, added_at, is_deleted, employee_id) VALUES (100, 'Operator', '2022-01-01', FALSE, 2);
-- id=3
INSERT INTO vacancy (salary, position, added_at, is_deleted) VALUES (100, 'Physician2', '2022-01-01', FALSE);
-- id=4
INSERT INTO vacancy (salary, position, added_at, is_deleted) VALUES (100, 'Physician3', '2022-01-01', FALSE);
-- id=5
INSERT INTO vacancy (salary, position, added_at, is_deleted, deleted_at) VALUES (100, 'Physician4', '2022-01-01', TRUE, '2000-01-01');
-- id=6
INSERT INTO vacancy (salary, position, added_at, is_deleted, deleted_at) VALUES (100, 'Physician5', '2022-01-01', TRUE, '2000-01-01');
-- id=7
INSERT INTO vacancy (salary, position, added_at, is_deleted, deleted_at) VALUES (100, 'Physician6', '2022-01-01', TRUE, '2000-01-01');
