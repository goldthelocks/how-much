-- test data for stop table
INSERT INTO stop (stop_id, stop_name) VALUES ('Stop1', 'Stop 1');
INSERT INTO stop (stop_id, stop_name) VALUES ('Stop2', 'Stop 2');
INSERT INTO stop (stop_id, stop_name) VALUES ('Stop3', 'Stop 3');

-- test data for fare table
INSERT INTO fare (fare_id, from_stop_id, to_stop_id, amount, enabled) VALUES (default, 'Stop1', 'Stop2', '3.25', true);
INSERT INTO fare (fare_id, from_stop_id, to_stop_id, amount, enabled) VALUES (default, 'Stop2', 'Stop3', '5.50', true);
INSERT INTO fare (fare_id, from_stop_id, to_stop_id, amount, enabled) VALUES (default, 'Stop1', 'Stop3', '7.30', true);
INSERT INTO fare (fare_id, from_stop_id, to_stop_id, amount, enabled) VALUES (default, 'Stop2', 'Stop1', '3.25', true);
INSERT INTO fare (fare_id, from_stop_id, to_stop_id, amount, enabled) VALUES (default, 'Stop3', 'Stop2', '5.50', true);
INSERT INTO fare (fare_id, from_stop_id, to_stop_id, amount, enabled) VALUES (default, 'Stop3', 'Stop1', '7.30', true);